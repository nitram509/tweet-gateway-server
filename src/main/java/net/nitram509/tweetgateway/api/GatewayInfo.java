package net.nitram509.tweetgateway.api;

public class GatewayInfo {

  private final GatewayId gatewayId;
  private UserId owner;
  private String url;
  private String hashtags;
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

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public int getActivity() {
    return activity;
  }

  public void setActivity(int activity) {
    this.activity = activity;
  }

  public String getHashtags() {
    return hashtags;
  }

  public void setHashtags(String hashtags) {
    this.hashtags = hashtags;
  }

}
