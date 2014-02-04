package net.nitram509.config;

public class EnvironmentConfig implements Mandatory, Optional {

  @Override
  public String consumerKey() {
    return System.getenv("twitter4j.oauth.consumerKey");
  }

  @Override
  public String consumerSecret() {
    return System.getenv("twitter4j.oauth.consumerSecret");
  }

  @Override
  public String defaultHashTag() {
    return System.getenv("defaultHashTag");
  }

}
