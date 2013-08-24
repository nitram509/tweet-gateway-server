package net.nitram509.twitter;

import org.testng.annotations.Test;
import twitter4j.TwitterException;

public class TwitterServiceLearningTest {

  @Test(enabled = false)
  public void learningTest() throws TwitterException {
    TwitterService twitterService = new TwitterService();

    // assume consumerKey and consumerSecret are provided
    twitterService.signinAndGetAuthenticationUrl("http://localhost:8080/callback");

    // twitter will redirect your browser and provides the oauth_verifier token
    twitterService.doCallback("123456789");

    // finally send a message
    twitterService.postMessage("EuropaceKonferenz");
  }

}
