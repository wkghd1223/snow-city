package com.city.snow.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;

@Configuration
public class AmazonConfig {

  @Value("${spring.cloud.aws.region.static}")
  private String region;

  @Value("${spring.cloud.aws.credentials.access-key}")
  private String accessKey;

  @Value("${spring.cloud.aws.credentials.secret-key}")
  private String secretKey;

  // 클라이언트 설정
  @Bean
  public SqsAsyncClient sqsAsyncClient() {
    return SqsAsyncClient.builder()
        .credentialsProvider(() -> new AwsCredentials() {
          @Override
          public String accessKeyId() {
            return accessKey;
          }
          @Override
          public String secretAccessKey() {
            return secretKey;
          }
        })
        .region(Region.of(region))
        .build();
  }

  // Listener Factory 설정 (Listener쪽에서만 설정하면 됨)
  @Bean
  public SqsMessageListenerContainerFactory<Object> defaultSqsListenerContainerFactory() {
    return SqsMessageListenerContainerFactory
        .builder()
        .sqsAsyncClient(sqsAsyncClient())
        .build();
  }

  // 메세지 발송을 위한 SQS 템플릿 설정 (Sender쪽에서만 설정하면 됨)
  @Bean
  public SqsTemplate sqsTemplate() {
    return SqsTemplate.newTemplate(sqsAsyncClient());
  }
  @Bean
  public ThreadPoolTaskExecutor messageThreadPoolTaskExecutor() {
    ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
    taskExecutor.setThreadNamePrefix("sqs-task-");
    taskExecutor.setCorePoolSize(20);
    taskExecutor.setMaxPoolSize(20);
    taskExecutor.afterPropertiesSet();
    return taskExecutor;
  }

  @Bean
  public DynamoDbClient dynamoDbClient() {
    return DynamoDbClient.builder()
        .credentialsProvider(() -> new AwsCredentials() {
          @Override
          public String accessKeyId() {
            return accessKey;
          }
          @Override
          public String secretAccessKey() {
            return secretKey;
          }
        })
        .region(Region.of(region))
        .build();
  }

  @Bean
  public DynamoDbEnhancedClient dynamoDbEnhancedClient(
      @Qualifier("dynamoDbClient") DynamoDbClient dynamoDbClient
  ){
    return DynamoDbEnhancedClient.builder()
        .dynamoDbClient(dynamoDbClient).build();
  }

  /**
   * SQS 메세지 변환
   * String 형태의 SQS 메세지를 DTO로 변환시켜 줍니다.
   * ObjectMapper를 사용하며, 필드명을 지정하고 싶은 경우 @JsonProperty 어노테이션을 사용합니다.
   * @param objectMapper 오브젝트 맵퍼
   * @return 맵핑 컨버터
   */
  @Bean
  public MappingJackson2MessageConverter mappingJackson2MessageConverter(ObjectMapper objectMapper) {
    MappingJackson2MessageConverter jackson2MessageConverter = new MappingJackson2MessageConverter();
    jackson2MessageConverter.setObjectMapper(objectMapper);
    jackson2MessageConverter.setSerializedPayloadClass(String.class);
    jackson2MessageConverter.setStrictContentTypeMatch(false);
    return jackson2MessageConverter;
  }
}