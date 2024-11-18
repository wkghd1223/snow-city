package com.city.snow;

public class Constants {
  public static final String SOCKET_LISTEN_PATH = "snow";
  public static final String DYNAMODB_TABLE_NAME = "snowcity";
  /**
   * 3 Days (Unit: Second)
   * */
  public static final Long DYNAMODB_TIME_TO_LIVE = 60L * 60 * 24 * 3;

}
