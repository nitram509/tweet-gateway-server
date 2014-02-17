package net.nitram509.gateways.repository;

import net.nitram509.gateways.api.GatewayInfo;
import net.nitram509.gateways.api.UserId;
import net.nitram509.gateways.api.UserProfile;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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

  public UserProfile getUser(UserId userId) {
    for (UserProfile profile : profiles) {
      if (profile.getId().equals(userId)) {
        return profile;
      }
    }
    throw new NoSuchElementException("UserProfile doesn't exists for " + userId);
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
