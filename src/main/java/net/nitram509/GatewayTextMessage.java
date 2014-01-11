package net.nitram509;

public class GatewayTextMessage {

  private String device;
  private String phone;
  private String smscenter;
  private String text;

  public GatewayTextMessage() {
  }

  public String getDevice() {
    return device;
  }

  public GatewayTextMessage setDevice(String device) {
    this.device = device;
    return this;
  }

  public String getPhone() {
    return phone;
  }

  public GatewayTextMessage setPhone(String phone) {
    this.phone = phone;
    return this;
  }

  public String getSmscenter() {
    return smscenter;
  }

  public GatewayTextMessage setSmscenter(String smscenter) {
    this.smscenter = smscenter;
    return this;
  }

  public String getText() {
    return text;
  }

  public GatewayTextMessage setText(String text) {
    this.text = text;
    return this;
  }
}
