/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nitram509.controller;

import net.nitram509.logger.ConsoleLogger;
import net.nitram509.model.GatewayTextMessage;
import net.nitram509.twitter.TwitterService;
import twitter4j.TwitterException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("/smsgateway")
public class TweetGatewayHttpController {

  private TwitterService twitter = new TwitterService();
  private ConsoleLogger logger = new ConsoleLogger();

  @GET
  @Produces({TEXT_PLAIN})
  public Response getSmsGateway(
      @QueryParam("device") String device,
      @QueryParam("phone") String phone,
      @QueryParam("smscenter") String smscenter,
      @QueryParam("text") String text) throws TwitterException {
    GatewayTextMessage gatewayTextMessage = new GatewayTextMessage().setDevice(device).setPhone(phone).setSmscenter(smscenter).setText(text);
    logger.infoAsJson("SmsGateway action", gatewayTextMessage);
    if (text == null || text.trim().isEmpty()) {
      return Response.serverError().entity("ERROR: Text is not allowed to be empty").build();
    } else {
      twitter.postMessage(text);
    }
    return Response.ok("OK").build();
  }

}