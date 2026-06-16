package com.coffeeconnect.websocket;

import com.coffeeconnect.service.VideoCallService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class VideoCallWebSocketHandler extends TextWebSocketHandler {

    private final VideoCallService videoCallService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final Map<String, String> sessionRoomMap = new ConcurrentHashMap<>();

    public VideoCallWebSocketHandler(VideoCallService videoCallService) {
        this.videoCallService = videoCallService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String query = session.getUri() != null ? session.getUri().getQuery() : "";
        String userId = extractParam(query, "userId");
        if (userId != null) {
            sessions.put(userId, session);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String payload = message.getPayload();
        try {
            Map<String, Object> data = objectMapper.readValue(payload, Map.class);
            String type = (String) data.get("type");
            String roomId = (String) data.get("roomId");
            String userId = String.valueOf(data.get("userId"));

            switch (type) {
                case "join" -> {
                    sessionRoomMap.put(session.getId(), roomId);
                    VideoCallService.CallRoom room = videoCallService.joinCall(roomId, Long.parseLong(userId));
                    Long joinedUserId = Long.parseLong(userId);
                    if (room.getInitiatorId().equals(joinedUserId)) {
                        room.setInitiatorJoined(true);
                    } else {
                        room.setReceiverJoined(true);
                    }
                    Long otherUserId = room.getInitiatorId().equals(joinedUserId)
                            ? room.getReceiverId() : room.getInitiatorId();
                    WebSocketSession otherSession = sessions.get(String.valueOf(otherUserId));
                    if (otherSession != null && otherSession.isOpen()) {
                        otherSession.sendMessage(new TextMessage(
                                "{\"type\":\"user-joined\",\"userId\":" + joinedUserId + "}"));
                    }
                }
                case "offer", "answer", "ice-candidate" -> {
                    VideoCallService.CallRoom room = videoCallService.getCallRoom(roomId);
                    if (room != null) {
                        Long targetUserId = room.getInitiatorId().equals(Long.parseLong(userId))
                                ? room.getReceiverId() : room.getInitiatorId();
                        WebSocketSession targetSession = sessions.get(String.valueOf(targetUserId));
                        if (targetSession != null && targetSession.isOpen()) {
                            targetSession.sendMessage(new TextMessage(payload));
                        }
                    }
                }
                case "end" -> {
                    VideoCallService.CallRoom room = videoCallService.getCallRoom(roomId);
                    if (room != null) {
                        Long targetUserId = room.getInitiatorId().equals(Long.parseLong(userId))
                                ? room.getReceiverId() : room.getInitiatorId();
                        WebSocketSession targetSession = sessions.get(String.valueOf(targetUserId));
                        if (targetSession != null && targetSession.isOpen()) {
                            targetSession.sendMessage(new TextMessage(payload));
                        }
                    }
                    videoCallService.endCall(roomId);
                }
            }
        } catch (Exception e) {
            session.sendMessage(new TextMessage("{\"type\":\"error\",\"message\":\"" + e.getMessage() + "\"}"));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String roomId = sessionRoomMap.remove(session.getId());
        sessions.values().remove(session);
    }

    private String extractParam(String query, String param) {
        if (query == null) return null;
        for (String pair : query.split("&")) {
            String[] kv = pair.split("=", 2);
            if (kv.length == 2 && kv[0].equals(param)) return kv[1];
        }
        return null;
    }
}
