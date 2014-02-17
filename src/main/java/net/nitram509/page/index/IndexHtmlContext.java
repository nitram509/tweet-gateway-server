package net.nitram509.page.index;

import net.nitram509.gateways.api.UserProfile;

import java.util.List;

public class IndexHtmlContext {

  private UserProfile userProfile;
  private List<GatewayInfo> gatewayInfos;

  IndexHtmlContext(UserProfile userProfile, List<GatewayInfo> gatewayInfos) {
    this.userProfile = userProfile;
    this.gatewayInfos = gatewayInfos;
  }

  public UserProfile getUserProfile() {
    return userProfile;
  }

  public List<GatewayInfo> getGatewayInfos() {
    return gatewayInfos;
  }
}
