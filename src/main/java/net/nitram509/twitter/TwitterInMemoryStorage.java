package net.nitram509.twitter;

import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

class TwitterInMemoryStorage {

  private static RequestToken requestToken = null;
  private static AccessToken accessToken = null;

  public RequestToken getRequestToken() {
    return requestToken;
  }

  public AccessToken getAccessToken() {
    return accessToken;
  }

  public void setRequestToken(RequestToken token) {
    TwitterInMemoryStorage.requestToken = token;
  }

  public void setAccessToken(AccessToken accessToken) {
    TwitterInMemoryStorage.accessToken = accessToken;
  }
}
