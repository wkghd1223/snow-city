package com.city.snow.config;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SocketIOConfig {

  @Value("${urls.socket.host}")
  private String host;
  @Value("${urls.socket.port}")
  private Integer port;

  @Bean
  public SocketIOServer socketIOServer() {
    com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
    config.setHostname(host);
    config.setPort(port); // Set the port for Socket.io server

    return new SocketIOServer(config);
  }
}