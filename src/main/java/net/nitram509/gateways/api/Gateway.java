package net.nitram509.gateways.api;

public class Gateway implements Comparable<Gateway> {

  private final GatewayId gatewayId;
  private UserId owner;
  private String suffix;
  private int activity;

  public Gateway(GatewayId gatewayId) {
    this.gatewayId = gatewayId;
  }

  public GatewayId getId() {
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

  @Override
  public int compareTo(Gateway o) {
    return this.gatewayId.getId().compareTo(o.gatewayId.getId());
  }
}
