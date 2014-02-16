package net.nitram509.tweetgateway.repository;

import net.nitram509.tweetgateway.api.GatewayInfo;
import net.nitram509.tweetgateway.api.UserId;
import net.nitram509.tweetgateway.api.UserProfile;

import java.util.ArrayList;
import java.util.List;

public class TweetGatewayRepository {

  private static TweetGatewayRepository instance;

  private final List<UserProfile> profiles = new ArrayList<>();
  private final List<GatewayInfo> gatewayInfos = new ArrayList<>();

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

  public void save(GatewayInfo gatewayInfo) {
    gatewayInfos.add(gatewayInfo);
  }

  public UserProfile findBy() {
    return this.profiles.get(0);
  }

  public List<GatewayInfo> findGateway(UserId owner) {
    List<GatewayInfo> result = new ArrayList<>(3);
    for (GatewayInfo gatewayInfo : gatewayInfos) {
      if (gatewayInfo.getOwner().equals(owner)) {
        result.add(gatewayInfo);
      }
    }
    return result;
  }

}
