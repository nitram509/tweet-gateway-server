package net.nitram509.config;

public class EnvironmentConfig implements Mandatory, Optional {

  @Override
  public String consumerKey() {
    return System.getenv("consumerKey");
  }

  @Override
  public String consumerSecret() {
    return System.getenv("consumerSecret");
  }

  @Override
  public String accessToken() {
    return System.getenv("accessToken");
  }

  @Override
  public String accessTokenSecret() {
    return System.getenv("accessTokenSecret");
  }

}
