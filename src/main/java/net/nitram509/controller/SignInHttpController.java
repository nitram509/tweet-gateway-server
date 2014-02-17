package net.nitram509.controller;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("/doTwitterSignIn")
public class SignInHttpController {

  public static final String DO_TWITTER_SIGN_IN = "/doTwitterSignIn";
  public static final String DO_TWITTER_CALLBACK = "/doTwitterCallback";

  @POST
  @Produces({TEXT_PLAIN})
  public Response doTwitterSignIn(@Context HttpServletRequest request) throws TwitterException, IOException, URISyntaxException {

    Twitter twitter = new TwitterFactory().getInstance();

    String callbackURL = computeCallbackUrl(request);

    RequestToken requestToken = twitter.getOAuthRequestToken(callbackURL);
    new SessionVisitor(request.getSession()).saveRequestToken(requestToken.getToken());

    String authenticationURL = requestToken.getAuthenticationURL();
    return Response.seeOther(new URI(authenticationURL)).build();
  }

  private String computeCallbackUrl(HttpServletRequest request) {
    String requestUrl = request.getRequestURL().toString();
    return requestUrl.substring(0, requestUrl.indexOf(DO_TWITTER_SIGN_IN)) + DO_TWITTER_CALLBACK;
  }

}
