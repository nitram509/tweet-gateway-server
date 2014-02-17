package net.nitram509.gateways.api;

public class TextMessage {

  private String device;
  private String phone;
  private String smscenter;
  private String text;

  public TextMessage() {
  }

  public String getDevice() {
    return device;
  }

  public TextMessage setDevice(String device) {
    this.device = device;
    return this;
  }

  public String getPhone() {
    return phone;
  }

  public TextMessage setPhone(String phone) {
    this.phone = phone;
    return this;
  }

  public String getSmscenter() {
    return smscenter;
  }

  public TextMessage setSmscenter(String smscenter) {
    this.smscenter = smscenter;
    return this;
  }

  public String getText() {
    return text;
  }

  public TextMessage setText(String text) {
    this.text = text;
    return this;
  }
}
