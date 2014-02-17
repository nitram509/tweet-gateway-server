package net.nitram509.gateways.api;

public class GatewayInfo {

  private final GatewayId gatewayId;
  private UserId owner;
  private String suffix;
  private int activity;

  public GatewayInfo(GatewayId gatewayId) {
    this.gatewayId = gatewayId;
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

}
