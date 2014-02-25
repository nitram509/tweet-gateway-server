package net.nitram509.page.managegateways;

import net.nitram509.gateways.api.Gateway;
import net.nitram509.gateways.api.GatewayId;
import net.nitram509.gateways.api.UserId;

public class GatewayInfo {
  private final GatewayId gatewayId;
  private UserId owner;
  private String suffix;
  private int activity;
  private String url;

  public GatewayInfo(Gateway gateway) {
    this.gatewayId = gateway.getId();
    this.suffix = gateway.getSuffix();
    this.owner = gateway.getOwner();
    this.activity = gateway.getActivity();
  }

  public GatewayId getGatewayId() {
    return gatewayId;
  }

  public UserId getOwner() {
    return owner;
  }

  public void setOwner(UserId owner) {
    this.owner = owner;
  }

  public int getActivity() {
    return activity;
  }

  public void setActivity(int activity) {
    this.activity = activity;
  }

  public String getSuffix() {
    return suffix;
  }

  public void setSuffix(String suffix) {
    this.suffix = suffix;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
