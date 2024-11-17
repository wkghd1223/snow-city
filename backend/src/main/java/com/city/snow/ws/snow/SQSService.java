package com.city.snow.ws.snow;

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
      log.info("LISTEN"+ dto.getSnowid() + " " + dto.getCity());
      snowService.sendToClient(dto.getCity(), dto.getSnowid());
    } catch (Exception e) {
      e.getStackTrace();
    }
  }
}
