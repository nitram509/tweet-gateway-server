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

  private Twitter twitterInstance;

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

  public void postMessage(String message) throws TwitterException {
    Twitter twitter = getTwitter();
    if (storage.getAccessToken() != null) {
      twitter.setOAuthAccessToken(storage.getAccessToken());
    }
    twitter.updateStatus(new StatusUpdate(createMessage(formatMessage(message))));
  }

  private String formatMessage(String message) {
    message = message.substring(0, Math.min(message.length(), 140));
    return message;
  }

  private String createMessage(String message) {
    return message;
  }

  private Twitter getTwitter() {
    if (twitterInstance == null) {
      twitterInstance = TwitterFactory.getSingleton();
      twitterInstance.setOAuthConsumer(consumerKey, consumerSecret);
    }
    return twitterInstance;
  }
}
