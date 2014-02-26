/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nitram509.controller;

import net.nitram509.gateways.api.Gateway;
import net.nitram509.gateways.api.GatewayId;
import net.nitram509.gateways.api.TextMessage;
import net.nitram509.gateways.api.UserId;
import net.nitram509.gateways.repository.TweetGateway;
import net.nitram509.gateways.repository.TweetGatewayRepository;
import net.nitram509.logger.ConsoleLogger;
import net.nitram509.twitter.TwitterService;
import twitter4j.TwitterException;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;
import static net.nitram509.gateways.GatewayUrlBuilder.GATEWAY_URL_PREFIX;

@Path(GATEWAY_URL_PREFIX)
public class TweetGatewayHttpController {

  private TwitterService twitterService = new TwitterService();
  private ConsoleLogger logger = new ConsoleLogger();
  private TweetGatewayRepository gatewayRepository = TweetGateway.getRepository();

  @GET
  @Produces({TEXT_PLAIN})
  @Path("/{gatewayId}")
  public Response getSmsGateway(
      @PathParam("gatewayId") String gatewayId,
      @QueryParam("text") String text,
      @QueryParam("device") String device,
      @QueryParam("phone") String phone,
      @QueryParam("smscenter") String smscenter) throws TwitterException {

    if (gatewayId == null || gatewayId.trim().length() != 16) {
      return Response.status(UNAUTHORIZED).entity("ERROR: Missing GatewayId. Please provide the GatewayId (16 alpha-numeric character).").build();
    }

    final Gateway gateway = gatewayRepository.getGateway(new GatewayId(gatewayId));
    if (gateway == null) {
      return Response.status(NOT_FOUND).entity("ERROR: Wrong GatewayId. Please provide a valid GatewayId (16 alpha-numeric character).").build();
    }

    TextMessage textMessage = new TextMessage()
        .setText(text)
        .setDevice(device)
        .setPhone(phone)
        .setSmscenter(smscenter);
    if (!postMessage(gateway.getOwner(), textMessage)) {
      return Response.status(BAD_REQUEST).entity("ERROR: Text is not allowed to be empty").build();
    }
    return Response.ok("OK").build();
  }

  public boolean postMessage(UserId userId, TextMessage textMessage) throws TwitterException {
    logger.infoAsJson("TweetGateway action", textMessage);
    String text = textMessage.getText();
    if (text == null || text.trim().isEmpty()) {
      return false;
    }
    twitterService.postMessage(userId, text);
    return true;
  }


}