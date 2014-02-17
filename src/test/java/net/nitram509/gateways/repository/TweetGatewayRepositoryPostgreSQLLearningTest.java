package net.nitram509.gateways.repository;

import net.nitram509.gateways.api.Gateway;
import net.nitram509.gateways.api.GatewayId;
import net.nitram509.gateways.api.UserId;
import net.nitram509.gateways.api.UserProfile;
import net.nitram509.gateways.controller.IdGenerator;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class TweetGatewayRepositoryPostgreSQLLearningTest {

  private TweetGatewayRepositoryPostgreSQL repo;
  private Connection connection;
  private IdGenerator idGenerator;
  private UserId userId;

  @BeforeMethod
  public void setUp() throws Exception {
    idGenerator = new IdGenerator();
    connection = TweetGateway.getDatabaseConnection();
    repo = new TweetGatewayRepositoryPostgreSQL(connection);
    userId = new UserId(System.currentTimeMillis());
  }

  @AfterMethod
  public void afterClass() throws Exception {
    connection.close();
  }

  @Test(enabled = false) // writes to actual database
  public void userProfile_save_and_load() {
    final UserProfile testUser = createTestUser(userId);

    repo.save(testUser);

    UserProfile loadedUser = repo.getUser(userId);

    assertThat(loadedUser.getId()).isEqualTo(testUser.getId());
    assertThat(loadedUser.getName()).isEqualTo(testUser.getName());
    assertThat(loadedUser.getScreenName()).isEqualTo(testUser.getScreenName());
    assertThat(loadedUser.getProfileImageUrl()).isEqualTo(testUser.getProfileImageUrl());
    assertThat(loadedUser.getProfileImageUrlHttps()).isEqualTo(testUser.getProfileImageUrlHttps());
    assertThat(loadedUser.getUrl()).isEqualTo(testUser.getUrl());
  }

  @Test(enabled = false) // writes to actual database
  public void gateway_save_and_load() {
    GatewayId gatewayId = idGenerator.nextId();
    Gateway gateway = createGatewayInfo(gatewayId, userId);

    repo.save(gateway);

    List<Gateway> gateways = repo.findGateways(userId);
    assertThat(gateways.size()).isGreaterThan(0);

    Gateway loadedGW = gateways.get(0);
    assertThat(loadedGW.getId()).isEqualTo(gatewayId);
    assertThat(loadedGW.getActivity()).isEqualTo(gateway.getActivity());
    assertThat(loadedGW.getSuffix()).isEqualTo(gateway.getSuffix());
    assertThat(loadedGW.getOwner()).isEqualTo(gateway.getOwner());
  }

  public UserProfile createTestUser(UserId userId) {
    final UserProfile profile = new UserProfile(userId);
    profile.setName("name");
    profile.setUrl("http://url");
    profile.setProfileImageUrl("http://img");
    profile.setProfileImageUrlHttps("https://img");
    profile.setScreenName("screenName");
    return profile;
  }

  public Gateway createGatewayInfo(GatewayId gatewayId, UserId owner) {
    final Gateway gateway = new Gateway(gatewayId);
    gateway.setOwner(owner);
    gateway.setActivity(123);
    gateway.setSuffix("#suffix");
    return gateway;
  }
}
