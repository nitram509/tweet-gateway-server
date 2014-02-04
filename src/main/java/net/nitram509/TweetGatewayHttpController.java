/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nitram509;


import net.nitram509.logger.ConsoleLogger;
import net.nitram509.twitter.TwitterService;
import net.nitram509.twitter.TwitterTextHelper;
import twitter4j.TwitterException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import java.io.IOException;

import static javax.ws.rs.core.MediaType.TEXT_HTML;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("/")
public class TweetGatewayHttpController {

  private TwitterService twitter = new TwitterService();
  private ConsoleLogger logger = new ConsoleLogger();

  @GET
  @Path("smsgateway")
  @Produces({TEXT_PLAIN})
  public String getSmsGateway(
      @QueryParam("device") String device,
      @QueryParam("phone") String phone,
      @QueryParam("smscenter") String smscenter,
      @QueryParam("text") String text) throws TwitterException {
    GatewayTextMessage gatewayTextMessage = new GatewayTextMessage().setDevice(device).setPhone(phone).setSmscenter(smscenter).setText(text);
    logger.infoAsJson("SmsGateway action", gatewayTextMessage);
    if (text == null || text.trim().isEmpty()) {
      return "ERROR: Text is not allowed to be empty";
    } else {
      twitter.postMessage(text);
    }
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
