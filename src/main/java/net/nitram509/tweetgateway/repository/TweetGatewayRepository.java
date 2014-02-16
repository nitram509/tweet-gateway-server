package net.nitram509.tweetgateway.repository;

import net.nitram509.tweetgateway.api.UserProfile;

import java.util.ArrayList;
import java.util.List;

public class TweetGatewayRepository {

  private static TweetGatewayRepository instance;

  private final List<UserProfile> profiles = new ArrayList<>();

  private TweetGatewayRepository() {
    // singleton, make constructor private
  }

  public static synchronized TweetGatewayRepository instance() {
    if (instance == null) {
      instance = new TweetGatewayRepository();
    }
    return instance;
  }

  public void save(UserProfile userProfile) {
    profiles.add(userProfile);
  }

  public UserProfile findBy() {
    return this.profiles.get(0);
  }

}
