package net.nitram509.gateways.repository;

import net.nitram509.gateways.api.GatewayInfo;
import net.nitram509.gateways.api.UserId;
import net.nitram509.gateways.api.UserProfile;

import java.util.List;

public interface TweetGatewayRepository {

  void save(UserProfile userProfile);

  void save(GatewayInfo gatewayInfo);

  UserProfile getUser(UserId userId);

  List<GatewayInfo> findGateways(UserId owner);
}
