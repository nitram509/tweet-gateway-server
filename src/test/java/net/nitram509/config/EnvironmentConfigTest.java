package net.nitram509.config;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.Connection;

import static org.fest.assertions.Assertions.assertThat;

public class EnvironmentConfigTest {

  private static String EXAMPLE_STRING = "postgres://user3123:passkja83kd8@ec2-117-21-174-214.compute-1.amazonaws.com:6212/db982398";

  private EnvironmentConfig envconf;

  @BeforeMethod
  public void setUp() {
    if (System.getenv().get("DATABASE_URL") == null) {
      // may set via runtime config
      System.getenv().put("DATABASE_URL", EXAMPLE_STRING);
    }
    envconf = new EnvironmentConfig();
  }

  @Test(enabled = false) // because it will establish real database connection
  public void connection_can_be_fetched() {
    final Connection connection = envconf.getConnection();
    assertThat(connection).isNotNull();
  }
}
