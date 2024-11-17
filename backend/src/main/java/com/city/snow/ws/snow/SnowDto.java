package com.city.snow.ws.snow;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

public class SnowDto {

  @Setter
  @DynamoDbBean
  public static class DynamoRequestDto {
    private String snowid;
    private String city;
    private String ip;
    private String utcCreatedAt;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("snowid")
    public String getSnowid() {
      return snowid;
    }
    @DynamoDbAttribute("city")
    public String getCity() {
      return city;
    }
    @DynamoDbAttribute("ip")
    public String getIp() {
      return ip;
    }
    @DynamoDbAttribute("created_at")
    public String getUtcCreatedAt() {
      return utcCreatedAt;
    }
    public static DynamoRequestDto of (String data) {
      ObjectMapper objectMapper = new ObjectMapper();
      try {
        DynamoRequestDto dto = objectMapper.readValue(data, DynamoRequestDto.class);
        return dto;
      } catch (JsonProcessingException e) {
        e.getStackTrace();
        return new DynamoRequestDto();
      }
    }
  }

  @Setter
  @Getter
  public static class SQSResponseDto {
    private String snowid;
    private String city;
    private String ip;

  }
}
