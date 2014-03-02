/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nitram509.gateways.controller;

import net.nitram509.controller.SessionVisitor;
import net.nitram509.gateways.api.Gateway;
import net.nitram509.gateways.api.GatewayId;
import net.nitram509.gateways.api.UserId;
import net.nitram509.gateways.repository.TweetGateway;
import net.nitram509.gateways.repository.TweetGatewayRepository;
import net.nitram509.recaptcha.ReCaptchaService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;
import static net.nitram509.page.managegateways.ManageGatewaysHtmlController.MANAGE_GATEWAYS_URL;

@Path("/gateways")
public class GatewaysResourceHttpController {

  public static final String ACTION_DELETE = "delete";

  TweetGatewayRepository repository = TweetGateway.getRepository();
  IdGenerator idGenerator = new IdGenerator();
  ReCaptchaService reCaptchaService = new ReCaptchaService();

  @POST
  @Consumes({APPLICATION_FORM_URLENCODED})
  public Response postGateways(@FormParam("suffix") String suffix,
                               @FormParam("recaptcha_challenge_field") String recaptcha_challenge_field,
                               @FormParam("recaptcha_response_field") String recaptcha_response_field,
                               @Context HttpServletRequest request) throws URISyntaxException {

    final HttpSession session = request.getSession(false);
    if (session != null) {
      if (reCaptchaService.isValidCaptcha(request.getRemoteAddr(), recaptcha_challenge_field, recaptcha_response_field)) {
        final SessionVisitor sessionVisitor = new SessionVisitor(session);
        if (sessionVisitor.isAuthenticatedUser()) {
          final UserId currentUser = sessionVisitor.loadCurrentUser();
          createNewGateway(currentUser, suffix);
        }
      }
    }

    return Response.seeOther(new URI(MANAGE_GATEWAYS_URL)).build();
  }

  @POST
  @Consumes({APPLICATION_FORM_URLENCODED})
  @Path("/{gatewayId}")
  public Response postGateways(@PathParam("gatewayId") String gatewayId,
                               @FormParam("action") String action,
                               @Context HttpServletRequest request) throws URISyntaxException {

    if (gatewayId == null || gatewayId.trim().length() != 16) {
      return Response.status(UNAUTHORIZED).entity("ERROR: Missing GatewayId. Please provide the GatewayId (16 alpha-numeric character).").build();
    }

    final HttpSession session = request.getSession(false);
    if (session != null) {
      final SessionVisitor sessionVisitor = new SessionVisitor(session);
      if (sessionVisitor.isAuthenticatedUser() && ACTION_DELETE.equals(action)) {
        final UserId currentUser = sessionVisitor.loadCurrentUser();
        deleteGateway(currentUser, new GatewayId(gatewayId));
      }
    }

    return Response.seeOther(new URI(MANAGE_GATEWAYS_URL)).build();
  }

  private void createNewGateway(UserId currentUser, String suffix) {
    Gateway gateway = new Gateway(idGenerator.nextId());
    gateway.setSuffix(suffix);
    gateway.setOwner(currentUser);
    repository.save(gateway);
  }

  private void deleteGateway(UserId currentUser, GatewayId gatewayId) {
    repository.remove(gatewayId);
  }
}