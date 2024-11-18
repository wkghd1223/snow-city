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
    private String createdAt;
    private Long ttl;

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
      return this.ip;
    }
    @DynamoDbAttribute("created_at")
    public String getCreatedAt() {
      return this.createdAt;
    }
    @DynamoDbAttribute("ttl")
    public Long getTtl() {
      return this.ttl;
    }
    public static DynamoRequestDto of (String data) {
      ObjectMapper objectMapper = new ObjectMapper();
      try {
        return objectMapper.readValue(data, DynamoRequestDto.class);
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
