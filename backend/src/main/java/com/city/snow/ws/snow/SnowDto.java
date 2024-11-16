package com.city.snow.ws.snow;

import lombok.Getter;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.time.Instant;
import java.util.Map;

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
  }

  @Setter
  @Getter
  public static class SQSResponseDto {
    private Map<String, String> snowid;
    private Map<String, String> city;
    private Map<String, String> ip;

  }
}
