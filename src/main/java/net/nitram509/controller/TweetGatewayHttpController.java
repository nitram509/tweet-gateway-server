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