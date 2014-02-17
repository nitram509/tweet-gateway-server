package net.nitram509.page.index;

import net.nitram509.gateways.api.UserProfile;

import java.util.List;

public class IndexHtmlContext {

  private UserProfile userProfile;
  private List<GatewayInfo> gatewayInfos;
  private String recaptchaHtml;

  IndexHtmlContext(UserProfile userProfile, List<GatewayInfo> gatewayInfos, String recaptchaHtml) {
    this.userProfile = userProfile;
    this.gatewayInfos = gatewayInfos;
    this.recaptchaHtml = recaptchaHtml;
  }

  public UserProfile getUserProfile() {
    return userProfile;
  }

  public List<GatewayInfo> getGatewayInfos() {
    return gatewayInfos;
  }

  public String getRecaptchaHtml() {
    return recaptchaHtml;
  }
}
