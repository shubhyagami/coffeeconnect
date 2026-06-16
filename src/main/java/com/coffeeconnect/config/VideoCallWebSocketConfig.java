package com.coffeeconnect.config;

import com.coffeeconnect.websocket.VideoCallWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class VideoCallWebSocketConfig implements WebSocketConfigurer {

    private final VideoCallWebSocketHandler videoCallWebSocketHandler;

    public VideoCallWebSocketConfig(VideoCallWebSocketHandler videoCallWebSocketHandler) {
        this.videoCallWebSocketHandler = videoCallWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(videoCallWebSocketHandler, "/ws-call")
                .setAllowedOriginPatterns("*");
    }
}
