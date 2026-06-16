package com.coffeeconnect.service;

import com.coffeeconnect.entity.User;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class VideoCallService {

    private final Map<String, CallRoom> activeCalls = new ConcurrentHashMap<>();

    public CallRoom createCall(User initiator, User receiver) {
        String roomId = UUID.randomUUID().toString().substring(0, 8);
        CallRoom room = new CallRoom(roomId, initiator.getId(), receiver.getId());
        activeCalls.put(roomId, room);
        return room;
    }

    public CallRoom joinCall(String roomId, Long userId) {
        CallRoom room = activeCalls.get(roomId);
        if (room == null) {
            throw new RuntimeException("Call room not found");
        }
        if (!room.getInitiatorId().equals(userId) && !room.getReceiverId().equals(userId)) {
            throw new RuntimeException("Not a participant of this call");
        }
        return room;
    }

    public void endCall(String roomId) {
        activeCalls.remove(roomId);
    }

    public CallRoom getCallRoom(String roomId) {
        return activeCalls.get(roomId);
    }

    public static class CallRoom {
        private final String roomId;
        private final Long initiatorId;
        private final Long receiverId;
        private String initiatorSignal;
        private String receiverSignal;
        private boolean initiatorJoined;
        private boolean receiverJoined;

        public CallRoom(String roomId, Long initiatorId, Long receiverId) {
            this.roomId = roomId;
            this.initiatorId = initiatorId;
            this.receiverId = receiverId;
        }

        public String getRoomId() { return roomId; }
        public Long getInitiatorId() { return initiatorId; }
        public Long getReceiverId() { return receiverId; }
        public String getInitiatorSignal() { return initiatorSignal; }
        public void setInitiatorSignal(String s) { this.initiatorSignal = s; }
        public String getReceiverSignal() { return receiverSignal; }
        public void setReceiverSignal(String s) { this.receiverSignal = s; }
        public boolean isInitiatorJoined() { return initiatorJoined; }
        public void setInitiatorJoined(boolean b) { this.initiatorJoined = b; }
        public boolean isReceiverJoined() { return receiverJoined; }
        public void setReceiverJoined(boolean b) { this.receiverJoined = b; }
        public boolean isBothJoined() { return initiatorJoined && receiverJoined; }
    }
}
