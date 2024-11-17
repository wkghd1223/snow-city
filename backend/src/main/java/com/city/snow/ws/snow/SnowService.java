package com.city.snow.ws.snow;

import com.corundumstudio.socketio.SocketIOServer;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.net.InetSocketAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Slf4j
@Service
public class SnowService {
  private final SocketIOServer server;
  private final String RECEIVE_PATH = "snow";
  @Autowired
  private DynamoDbService dynamoDbService;

  public SnowService(SocketIOServer server) {
    this.server = server;
  }

  @PostConstruct
  public void startServer() {
    server.start();

    // Event listener for client connections
    server.addConnectListener(client -> log.info("Client connected: " + client.getSessionId()));

    // Event listener for client disconnections
    server.addDisconnectListener(client -> log.info("Client disconnected: " + client.getSessionId()));

    // Listen for the "makeItSnow" event from clients
    server.addEventListener(RECEIVE_PATH, String.class, (client, _data, ackSender) -> {
//      UUID uuid = UUID.randomUUID();
//      log.info("Received 'make it snow' command: " + city);
      InetSocketAddress remoteAddress = (InetSocketAddress) client.getRemoteAddress();
      String clientIp = remoteAddress.getAddress().getHostAddress();

      SnowDto.DynamoRequestDto data = SnowDto.DynamoRequestDto.of(_data);
//      SnowDto.DynamoRequestDto data = new SnowDto.DynamoRequestDto();
//      data.setCity(city);
//      data.setSnowid(uuid.toString());
      data.setIp(clientIp);
      DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
      dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
      data.setUtcCreatedAt(dateFormat.format(new Date()));
      dynamoDbService.saveItem(data);
    });
  }

  @PreDestroy
  public void stopServer() {
    server.stop();
  }

  public void sendToClient(String city, String message) {

    // Broadcast the "makeItSnow" event to all connected clients
    server.getBroadcastOperations().sendEvent(city, message);

  }
}
