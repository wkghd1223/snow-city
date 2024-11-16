package com.city.snow.ws.snow;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SnowController {
  @MessageMapping("/send/seoul") // Client sends messages to this endpoint
  @SendTo("/topic/seoul") // Broadcasts messages to subscribers of this topic
  public String processSnowFromClient(String message) {
    return message; // Echo the message back, or modify as needed
  }

  
}
