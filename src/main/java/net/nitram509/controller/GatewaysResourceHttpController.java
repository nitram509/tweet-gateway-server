/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nitram509.controller;

import com.sun.jersey.spi.inject.Inject;
import net.nitram509.tweetgateway.api.GatewayId;
import net.nitram509.tweetgateway.api.GatewayInfo;
import net.nitram509.tweetgateway.api.UserId;
import net.nitram509.tweetgateway.repository.TweetGatewayRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.net.URI;
import java.net.URISyntaxException;

import static javax.ws.rs.core.MediaType.*;

@Path("/gateways")
public class GatewaysResourceHttpController {

  TweetGatewayRepository repository = TweetGatewayRepository.instance();

  @Context
  UriInfo uriInfo;

  @POST
  @Consumes({APPLICATION_FORM_URLENCODED})
  public Response postGateways(@FormParam("url") String url, @FormParam("hashtags") String hashtags) throws URISyntaxException {
    if (url != null & !url.isEmpty() && hashtags != null && !hashtags.isEmpty()) {
      GatewayInfo gatewayInfo = new GatewayInfo(new GatewayId(System.currentTimeMillis()));
      gatewayInfo.setHashtags(hashtags);
      gatewayInfo.setUrl(url);
      gatewayInfo.setOwner(new UserId(1));
      repository.save(gatewayInfo);
    }
    return Response.seeOther(new URI("/index.html")).build();
  }
}