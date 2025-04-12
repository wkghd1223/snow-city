package com.city.snow.ws.snow;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SQSService {

  @Autowired
  private SnowService snowService;

  @SqsListener("${spring.cloud.aws.sqs.queue-name}")
  public void listenQueue(SnowDto.SQSResponseDto dto) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      snowService.sendToClient("snow", objectMapper.writeValueAsString(dto));
    } catch (Exception e) {
      e.getStackTrace();
    }
  }
}
