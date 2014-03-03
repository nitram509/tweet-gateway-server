/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nitram509.controller;

import net.nitram509.gateways.api.Gateway;
import net.nitram509.gateways.api.GatewayId;
import net.nitram509.gateways.api.Tweet;
import net.nitram509.gateways.repository.TweetGateway;
import net.nitram509.gateways.repository.TweetGatewayRepository;
import net.nitram509.logger.ConsoleLogger;
import net.nitram509.twitter.TwitterService;
import twitter4j.TwitterException;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static javax.ws.rs.core.Response.Status.*;
import static net.nitram509.gateways.GatewayUrlBuilder.GATEWAY_URL_PREFIX;

@Path(GATEWAY_URL_PREFIX)
public class TweetGatewayHttpController {

  private TwitterService twitterService = new TwitterService();
  private ConsoleLogger logger = new ConsoleLogger();
  private TweetGatewayRepository gatewayRepository = TweetGateway.getRepository();

  @GET
  @Produces({TEXT_PLAIN})
  @Path("/{gatewayId}")
  public Response getSmsGateway_GET(
      @PathParam("gatewayId") String gatewayId,
      @QueryParam("text") String text) throws TwitterException {

    if (text == null || text.trim().isEmpty()) {
      return Response.status(BAD_REQUEST).entity("ERROR: Text is not allowed to be empty").build();
    }

    if (gatewayId == null || gatewayId.trim().length() != 16) {
      return Response.status(UNAUTHORIZED).entity("ERROR: Missing GatewayId. Please provide the GatewayId (16 alpha-numeric character).").build();
    }

    final Gateway gateway = gatewayRepository.getGateway(new GatewayId(gatewayId));
    if (gateway == null) {
      return Response.status(NOT_FOUND).entity("ERROR: Wrong GatewayId. Please provide a valid GatewayId (16 alpha-numeric character).").build();
    }

    postMessage(gateway, text);
    return Response.ok("OK").build();
  }

  void postMessage(Gateway gateway, String text) throws TwitterException {
    Tweet tweet = new Tweet()
        .setText(text)
        .setSuffix(gateway.getSuffix())
        .setGatewayId(gateway.getId());
    logger.infoAsJson("TweetGatewayHttpController postMessage", tweet);
    twitterService.postMessage(gateway.getOwner(), tweet);
    gatewayRepository.incrementActivity(gateway.getId());
  }

}