/*
 * Copyright (c) 2014 Martin W. Kirst (nitram509 at bitkings dot de)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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
