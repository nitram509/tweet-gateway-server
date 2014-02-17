package net.nitram509.gateways.repository;

import net.nitram509.gateways.api.GatewayInfo;
import net.nitram509.gateways.api.UserId;
import net.nitram509.gateways.api.UserProfile;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class TweetGatewayRepositoryInMemory implements TweetGatewayRepository {

  private final List<UserProfile> profiles = new ArrayList<>();
  private final List<GatewayInfo> gatewayInfos = new ArrayList<>();

  TweetGatewayRepositoryInMemory() {
    // singleton, make constructor private
  }

  @Override
  public void save(UserProfile userProfile) {
    profiles.add(userProfile);
  }

  @Override
  public void save(GatewayInfo gatewayInfo) {
    gatewayInfos.add(gatewayInfo);
  }

  @Override
  public UserProfile getUser(UserId userId) {
    for (UserProfile profile : profiles) {
      if (profile.getId().equals(userId)) {
        return profile;
      }
    }
    throw new NoSuchElementException("UserProfile doesn't exists for " + userId);
  }

  @Override
  public List<GatewayInfo> findGateways(UserId owner) {
    List<GatewayInfo> result = new ArrayList<>(3);
    for (GatewayInfo gatewayInfo : gatewayInfos) {
      if (gatewayInfo.getOwner().equals(owner)) {
        result.add(gatewayInfo);
      }
    }
    return result;
  }
}
