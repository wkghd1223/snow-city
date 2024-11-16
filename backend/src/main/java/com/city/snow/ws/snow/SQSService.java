package com.city.snow.ws.snow;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SQSService {

  @Autowired
  private SnowService snowService;

  @SqsListener("${spring.cloud.aws.sqs.queue-name}")
  public void listenQueue(@Payload SnowDto.SQSResponseDto dto) throws JsonProcessingException {
    log.info("LISTEN"+ dto.getSnowid() + " " + dto.getCity());
    snowService.sendToClient(dto.getCity().get("S"), dto.getSnowid().get("S"));
  }
}
