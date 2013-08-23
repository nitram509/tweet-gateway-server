package net.nitram509.twitter;

import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

/**
 * Created with IntelliJ IDEA.
 * User: maki
 * Date: 25.02.13
 * Time: 22:52
 * To change this template use File | Settings | File Templates.
 */
public class TwitterInMemoryStorage {

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
