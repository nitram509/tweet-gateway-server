package net.nitram509.twitter;

import net.nitram509.config.EnvironmentConfig;
import net.nitram509.gateways.api.UserId;
import net.nitram509.gateways.api.UserProfile;
import net.nitram509.gateways.repository.TweetGateway;
import net.nitram509.gateways.repository.TweetGatewayRepository;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;

public class TwitterService {

  private TwitterClientToolbox twitterClientToolbox = new TwitterClientToolbox();
  private EnvironmentConfig config = new EnvironmentConfig();
  private TwitterTextHelper twitterTextHelper = new TwitterTextHelper(config.defaultHashTag());
  private TweetGatewayRepository gatewayRepository = TweetGateway.getRepository();

  public void postMessage(UserId userId, String message) throws TwitterException {
    AccessToken accessToken = createAccessTokenFor(userId);
    Twitter twitter = twitterClientToolbox.getTwitterFor(accessToken);
    message = twitterTextHelper.appendDefaultHashtag(message);
    twitter.updateStatus(new StatusUpdate(formatMessage(message)));
  }

  private String formatMessage(String message) {
    message = message.substring(0, Math.min(message.length(), 140));
    return message;
  }

  private AccessToken createAccessTokenFor(UserId userId) {
    final UserProfile user = gatewayRepository.getUser(userId);
    return new AccessToken(user.getAccessToken(), user.getAccessTokenSecret());
  }

}
