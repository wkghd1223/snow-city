package com.city.snow.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    //발행자가 "/topic"의 경로로 메시지를 주면 구독자들에게 전달
    config.enableSimpleBroker("/topic");
    // 발행자가 "/app"의 경로로 메시지를 주면 가공을 해서 구독자들에게 전달
    config.setApplicationDestinationPrefixes("/app");
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/ws-message") // WebSocket endpoint
        .setAllowedOrigins("http://localhost:5173");
  }
}
