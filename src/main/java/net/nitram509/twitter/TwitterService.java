package net.nitram509.twitter;

import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class TwitterService {

  TwitterInMemoryStorage storage = new TwitterInMemoryStorage();

  private String consumerKey = "";
  private String consumerSecret = "";
  private String accessToken = "";
  private String accessTokenSecret = "";

  public String signinAndGetAuthenticationUrl(String requestUrl) throws TwitterException {
    Twitter twitter = getTwitter();

    StringBuilder callbackUrl = new StringBuilder(requestUrl);
    int index = callbackUrl.lastIndexOf("/");
    callbackUrl.replace(index, callbackUrl.length(), "").append("/callback");

    RequestToken requestToken = twitter.getOAuthRequestToken(callbackUrl.toString());
    storage.setRequestToken(requestToken);
    return requestToken.getAuthenticationURL();
  }

  public boolean isSignedIn() {
    return storage.getAccessToken() != null;
  }

  public void doCallback(String oauth_verifier) throws TwitterException {
    Twitter twitter = getTwitter();
    RequestToken requestToken = storage.getRequestToken();
    AccessToken oAuthAccessToken = twitter.getOAuthAccessToken(requestToken, oauth_verifier);
    storage.setAccessToken(oAuthAccessToken);
    storage.setRequestToken(null);
  }

  public void postMessage(String number, String message) throws TwitterException {
    Twitter twitter = getTwitter();
    if (storage.getAccessToken() != null) {
      twitter.setOAuthAccessToken(storage.getAccessToken());
    }
    twitter.updateStatus(new StatusUpdate(createMessage(formatMessage(number, message))));
  }

  private String formatMessage(String number, String message) {
    message = message.substring(0, Math.min(message.length(), 140));
    return message;
  }

  private String createMessage(String message) {
    return message;
  }

  private Twitter getTwitter() {
    Twitter twitter = TwitterFactory.getSingleton();
    try {
      twitter.setOAuthConsumer(consumerKey, consumerSecret);
      twitter.setOAuthAccessToken(new AccessToken(accessToken, accessTokenSecret));
    } catch (IllegalStateException e) {
      // already set - ignore ! -- hacky but workd ;-)
    }
    return twitter;
  }
}
