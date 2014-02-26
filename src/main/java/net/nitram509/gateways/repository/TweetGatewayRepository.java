package net.nitram509.gateways.repository;

import net.nitram509.gateways.api.Gateway;
import net.nitram509.gateways.api.GatewayId;
import net.nitram509.gateways.api.UserId;
import net.nitram509.gateways.api.UserProfile;

import java.util.List;

public interface TweetGatewayRepository {

  void save(UserProfile userProfile);

  void save(Gateway gateway);

  UserProfile getUser(UserId userId);

  void incrementActivity(GatewayId gatewayId);

  List<Gateway> findGateways(UserId owner);

  Gateway getGateway(GatewayId gatewayId);
}
