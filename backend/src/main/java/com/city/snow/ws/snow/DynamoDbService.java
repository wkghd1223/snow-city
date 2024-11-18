package com.city.snow.ws.snow;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import static com.city.snow.Constants.DYNAMODB_TABLE_NAME;

@Service
public class DynamoDbService {


  private final DynamoDbTable<SnowDto.DynamoRequestDto> itemTable;

  public DynamoDbService(DynamoDbClient dynamoDbClient) {
    DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
        .dynamoDbClient(dynamoDbClient)
        .build();

    this.itemTable = enhancedClient.table(DYNAMODB_TABLE_NAME, TableSchema.fromBean(SnowDto.DynamoRequestDto.class));
  }

  public void saveItem(SnowDto.DynamoRequestDto item) {
    itemTable.putItem(item);
  }
}
