/*
 * Copyright (c) 2014 Martin W. Kirst (nitram509 at bitkings dot de)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package net.nitram509.twitter;

import net.nitram509.config.EnvironmentConfig;
import net.nitram509.gateways.api.Tweet;
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
  private TwitterTextHelper twitterTextHelper = new TwitterTextHelper();
  private TweetGatewayRepository gatewayRepository = TweetGateway.getRepository();

  public void postMessage(UserId userId, Tweet tweet) throws TwitterException {
    AccessToken accessToken = createAccessTokenFor(userId);
    Twitter twitter = twitterClientToolbox.getTwitterFor(accessToken);
    String message = twitterTextHelper.appendDefaultHashtag(tweet.getText(), tweet.getSuffix());
    System.out.println(message);
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
