package net.nitram509.controller;

import net.nitram509.gateways.api.UserId;
import net.nitram509.gateways.api.UserProfile;
import net.nitram509.gateways.controller.GatewaysResourceHttpController;
import net.nitram509.gateways.repository.TweetGateway;
import net.nitram509.gateways.repository.TweetGatewayRepository;
import net.nitram509.page.managegateways.ManageGatewaysHtmlController;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static net.nitram509.controller.SignInHttpController.DO_TWITTER_CALLBACK;
import static net.nitram509.page.managegateways.ManageGatewaysHtmlController.MANAGE_GATEWAYS_URL;

@Path(DO_TWITTER_CALLBACK)
public class CallbackHttpController {

  private TweetGatewayRepository repository = TweetGateway.getRepository();

  @Context
  UriInfo uriInfo;

  @GET
  @Produces({TEXT_PLAIN})
  public Response doTwitterCallback(@QueryParam(value = "oauth_token") String oauth_token,
                                    @QueryParam(value = "oauth_verifier") String oauth_verifier,
                                    @Context HttpServletRequest request) throws TwitterException, IOException, URISyntaxException {

    SessionVisitor sessionVisitor = new SessionVisitor(request.getSession(true));
    if (sessionVisitor.hasRequestToken()) {
      Twitter twitter = new TwitterFactory().getInstance();
      RequestToken requestToken = new RequestToken(sessionVisitor.loadRequestToken(), "");
      sessionVisitor.removeRequestToken();
      AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, oauth_verifier);
      sessionVisitor.saveAccessToken(accessToken.getToken());
      sessionVisitor.saveAccessTokenSecret(accessToken.getTokenSecret());

      UserProfile userProfile = retrieveUserProfileDetails(twitter);
      repository.save(userProfile);
      sessionVisitor.saveCurrentUser(userProfile.getId());
    }

    return Response.temporaryRedirect(new URI(MANAGE_GATEWAYS_URL)).build();
  }

  private UserProfile retrieveUserProfileDetails(Twitter twitter) throws TwitterException {
    final long twitterId = twitter.getId();
    User twitterUser = twitter.users().showUser(twitterId);
    UserProfile userProfile = new UserProfile(new UserId(twitterId));
    userProfile.setName(twitterUser.getName());
    userProfile.setScreenName(twitterUser.getScreenName());
    userProfile.setProfileImageUrlHttps(twitterUser.getProfileImageURLHttps());
    userProfile.setProfileImageUrl(twitterUser.getProfileImageURL());
    userProfile.setUrl(twitterUser.getURL());
    return userProfile;
  }

}
