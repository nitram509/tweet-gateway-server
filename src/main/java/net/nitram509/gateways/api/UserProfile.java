package net.nitram509.gateways.api;

import java.io.Serializable;

public class UserProfile implements Serializable{

  private final UserId userId;
  private String name;
  private String screenName;
  private String profileImageUrlHttps;
  private String profileImageUrl;
  private String url;

  public UserProfile(UserId userId) {
    this.userId = userId;
  }

  public UserId getId() {
    return userId;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public String getScreenName() {
    return screenName;
  }

  public void setScreenName(String screenName) {
    this.screenName = screenName;
  }

  public void setProfileImageUrlHttps(String profileImageUrlHttps) {
    this.profileImageUrlHttps = profileImageUrlHttps;
  }

  public String getProfileImageUrlHttps() {
    return profileImageUrlHttps;
  }

  public void setProfileImageUrl(String profileImageUrl) {
    this.profileImageUrl = profileImageUrl;
  }

  public String getProfileImageUrl() {
    return profileImageUrl;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getUrl() {
    return url;
  }
}
