package net.nitram509.gateways.repository;

import net.nitram509.gateways.api.GatewayId;
import net.nitram509.gateways.api.GatewayInfo;
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
    GatewayInfo gatewayInfo = createGatewayInfo(gatewayId, userId);

    repo.save(gatewayInfo);

    List<GatewayInfo> gateways = repo.findGateways(userId);
    assertThat(gateways.size()).isGreaterThan(0);

    GatewayInfo loadedGW = gateways.get(0);
    assertThat(loadedGW.getGatewayId()).isEqualTo(gatewayId);
    assertThat(loadedGW.getActivity()).isEqualTo(gatewayInfo.getActivity());
    assertThat(loadedGW.getSuffix()).isEqualTo(gatewayInfo.getSuffix());
    assertThat(loadedGW.getOwner()).isEqualTo(gatewayInfo.getOwner());
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

  public GatewayInfo createGatewayInfo(GatewayId gatewayId, UserId owner) {
    final GatewayInfo gatewayInfo = new GatewayInfo(gatewayId);
    gatewayInfo.setOwner(owner);
    gatewayInfo.setActivity(123);
    gatewayInfo.setSuffix("#suffix");
    return gatewayInfo;
  }
}
