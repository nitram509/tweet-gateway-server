package net.nitram509.twitter;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class TwitterClientToolbox {

  public Twitter getTwitterFor(AccessToken accessToken) {
    return new TwitterFactory().getInstance(accessToken);
  }

  public Twitter getTwitterAsTweetGatewayApp() {
    return new TwitterFactory().getInstance();
  }

}
