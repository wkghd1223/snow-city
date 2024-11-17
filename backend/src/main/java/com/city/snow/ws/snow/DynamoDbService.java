package com.city.snow.ws.snow;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Service
public class DynamoDbService {

  private final String TABLE_NAME = "snowcity";

  private final DynamoDbTable<SnowDto.DynamoRequestDto> itemTable;

  public DynamoDbService(DynamoDbClient dynamoDbClient) {
    DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
        .dynamoDbClient(dynamoDbClient)
        .build();

    this.itemTable = enhancedClient.table(TABLE_NAME, TableSchema.fromBean(SnowDto.DynamoRequestDto.class));
  }

  public void saveItem(SnowDto.DynamoRequestDto item) {
    itemTable.putItem(item);
  }
}
