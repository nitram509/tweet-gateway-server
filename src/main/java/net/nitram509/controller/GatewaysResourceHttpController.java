/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nitram509.controller;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.net.URI;
import java.net.URISyntaxException;

import static javax.ws.rs.core.MediaType.*;

@Path("/gateways")
public class GatewaysResourceHttpController {

  @POST
  @Consumes({APPLICATION_FORM_URLENCODED})
  public Response postGateways(@FormParam("url") String url, @FormParam("hashtags") String hashtags) throws URISyntaxException {
    return Response.seeOther(new URI("/index.html")).build();
  }
}