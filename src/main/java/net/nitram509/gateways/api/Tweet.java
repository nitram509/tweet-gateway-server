package net.nitram509.gateways.api;

public class Tweet {

  private GatewayId gatewayId;
  private String text;
  private String suffix;

  public Tweet() {
  }

  public String getText() {
    return text;
  }

  public Tweet setText(String text) {
    this.text = text;
    return this;
  }

  public String getSuffix() {
    return suffix;
  }

  public Tweet setSuffix(String suffix) {
    this.suffix = suffix;
    return this;
  }

  public GatewayId getGatewayId() {
    return gatewayId;
  }

  public Tweet setGatewayId(GatewayId gatewayId) {
    this.gatewayId = gatewayId;
    return this;
  }
}
