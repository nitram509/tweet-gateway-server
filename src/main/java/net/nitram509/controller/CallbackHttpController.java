package net.nitram509.controller;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static javax.ws.rs.core.Response.ResponseBuilder;
import static net.nitram509.controller.SignInHttpController.DO_TWITTER_CALLBACK;

@Path(DO_TWITTER_CALLBACK)
public class CallbackHttpController {

  @GET
  @Produces({TEXT_PLAIN})
  public Response doTwitterCallback(@QueryParam(value = "oauth_token") String oauth_token,
                                    @QueryParam(value = "oauth_verifier") String oauth_verifier,
                                    @Context HttpServletRequest request) throws TwitterException, IOException, URISyntaxException {

    SessionVisitor sessionVisitor = new SessionVisitor(request.getSession());
    if (sessionVisitor.hasRequestToken()) {
      Twitter twitter = new TwitterFactory().getInstance();
      RequestToken requestToken = new RequestToken(sessionVisitor.loadRequestToken(), "");
      AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, oauth_verifier);
      sessionVisitor.saveAccessToken(accessToken.getToken());
      sessionVisitor.saveAccessTokenSecret(accessToken.getTokenSecret());
    }

    return Response.temporaryRedirect(new URI("/")).build();
  }

}
