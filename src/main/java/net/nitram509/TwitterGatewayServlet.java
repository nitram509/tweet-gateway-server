/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nitram509;


import net.nitram509.twitter.TwitterService;
import twitter4j.TwitterException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.io.IOException;

import static javax.ws.rs.core.MediaType.TEXT_HTML;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("/")
public class TwitterGatewayServlet {

  TwitterService twitter = new TwitterService();

  @GET
  @Path("smsgateway")
  @Produces({TEXT_PLAIN})
  public String getSmsGateway(@QueryParam("phone") String phone, @QueryParam("text") String text) throws TwitterException {
    twitter.postMessage(text);
    return "OK";
  }

  @GET
  @Path("twitter")
  @Produces({TEXT_HTML})
  public String doTwitterSignIn(@Context HttpServletRequest servletRequest, @Context HttpServletResponse servletResponse) throws TwitterException, IOException {
    String requestUrl = servletRequest.getRequestURL().toString();
    String authenticationUrl = requestUrl;
    if (!twitter.isSignedIn()) {
      authenticationUrl = twitter.signinAndGetAuthenticationUrl(requestUrl);
    }
    servletResponse.sendRedirect(authenticationUrl);
    return "OK";
  }

  @GET
  @Path("callback")
  @Produces({TEXT_HTML})
  public String doTwitterCallback(@Context HttpServletRequest servletRequest, @Context HttpServletResponse servletResponse, @QueryParam("oauth_verifier") String oauth_verifier) throws TwitterException, IOException {
    if (oauth_verifier != null) {
      twitter.doCallback(oauth_verifier);
      servletResponse.sendRedirect(servletRequest.getContextPath() + "/");
      return "OK";
    }
    return "NICHT OK";
  }

}
