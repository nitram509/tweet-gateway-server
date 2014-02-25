/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nitram509.controller;

import net.nitram509.gateways.GatewayUrlBuilder;
import net.nitram509.gateways.api.Gateway;
import net.nitram509.gateways.api.GatewayId;
import net.nitram509.gateways.api.TextMessage;
import net.nitram509.gateways.repository.TweetGateway;
import net.nitram509.gateways.repository.TweetGatewayRepository;
import net.nitram509.logger.ConsoleLogger;
import net.nitram509.twitter.TwitterService;
import twitter4j.TwitterException;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static net.nitram509.gateways.GatewayUrlBuilder.GATEWAY_URL_PREFIX;

@Path(GATEWAY_URL_PREFIX)
public class TweetGatewayHttpController {

  private TwitterService twitter = new TwitterService();
  private ConsoleLogger logger = new ConsoleLogger();
  private TweetGatewayRepository gatewayRepository = TweetGateway.getRepository();

  @GET
  @Produces({TEXT_PLAIN})
  @Path("/{gatewayId}")
  public Response getSmsGateway(
      @PathParam("gatewayId") String gatewayId,
      @QueryParam("device") String device,
      @QueryParam("phone") String phone,
      @QueryParam("smscenter") String smscenter,
      @QueryParam("text") String text) throws TwitterException {

    if (gatewayId == null || gatewayId.trim().length() != 16) {
      Response.status(Response.Status.UNAUTHORIZED).entity("ERROR: Missing GatewayId. Please provide the GatewayId (16 alpha-numeric character).").build();
    }

    final Gateway gateway = gatewayRepository.getGateway(new GatewayId(gatewayId));
    if (gateway == null) {
      Response.status(Response.Status.NOT_FOUND).entity("ERROR: Wrong GatewayId. Please provide a valid GatewayId (16 alpha-numeric character).").build();
    }

    TextMessage textMessage = new TextMessage().setDevice(device).setPhone(phone).setSmscenter(smscenter).setText(text);
    if (postMessage(text, textMessage)) {
      return Response.serverError().entity("ERROR: Text is not allowed to be empty").build();
    }
    return Response.ok("OK").build();
  }

  public boolean postMessage(String text, TextMessage textMessage) throws TwitterException {
    logger.infoAsJson("SmsGateway action", textMessage);
    if (text == null || text.trim().isEmpty()) {
      return true;
    } else {
      twitter.postMessage(text);
    }
    return false;
  }

}