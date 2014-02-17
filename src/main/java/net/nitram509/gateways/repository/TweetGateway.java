package net.nitram509.gateways.repository;

import net.nitram509.config.EnvironmentConfig;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TweetGateway {

  private final static EnvironmentConfig ENVIRONMENT_CONFIG = new EnvironmentConfig();

  public static TweetGatewayRepository getRepository() {
    if (ENVIRONMENT_CONFIG.getConnectionUri() != null) {
      return new TweetGatewayRepositoryPostgreSQL(getDatabaseConnection());
    }
    return new TweetGatewayRepositoryInMemory();
  }

  public static Connection getDatabaseConnection() {
    URI dbUri = ENVIRONMENT_CONFIG.getConnectionUri();
    String username = dbUri.getUserInfo().split(":")[0];
    String password = dbUri.getUserInfo().split(":")[1];
    String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + (dbUri.getPort() > 0 ? ":" + dbUri.getPort() : "") + dbUri.getPath();
    try {
      return DriverManager.getConnection(dbUrl, username, password);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

}
