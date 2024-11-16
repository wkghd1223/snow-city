package com.city.snow.config;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SocketIOConfig {

  @Bean
  public SocketIOServer socketIOServer() {
    com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
    config.setHostname("localhost");
    config.setPort(8080); // Set the port for Socket.io server

    return new SocketIOServer(config);
  }
}