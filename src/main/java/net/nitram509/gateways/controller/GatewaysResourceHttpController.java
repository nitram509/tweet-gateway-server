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
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;
import static net.nitram509.page.managegateways.ManageGatewaysHtmlController.MANAGE_GATEWAYS_URL;

@Path("/gateways")
public class GatewaysResourceHttpController {

  public static final String ACTION_DELETE = "delete";
  private static final String ACTION_UPDATE = "update";
  public static final int MAX_SUFFIX_LENGTH = 140;

  TweetGatewayRepository gatewayRepository = TweetGateway.getRepository();
  IdGenerator idGenerator = new IdGenerator();
  ReCaptchaService reCaptchaService = new ReCaptchaService();

  @POST
  @Consumes({APPLICATION_FORM_URLENCODED})
  public Response postGateways_CREATE_NEW(@FormParam("suffix") String suffix,
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
  public Response postGateways_DELETE_AND_UPDATE(@PathParam("gatewayId") String gatewayId,
                                                 @FormParam("action") String action,
                                                 @FormParam("suffix") String suffix,
                                                 @Context HttpServletRequest request) throws URISyntaxException {

    if (gatewayId == null || gatewayId.trim().length() != 16) {
      return Response.status(UNAUTHORIZED).entity("ERROR: Missing GatewayId. Please provide the GatewayId (16 alpha-numeric character).").build();
    }

    final Gateway gateway = gatewayRepository.getGateway(new GatewayId(gatewayId));
    if (gateway == null) {
      return Response.status(NOT_FOUND).entity("ERROR: Wrong GatewayId. Please provide a valid GatewayId (16 alpha-numeric character).").build();
    }

    final HttpSession session = request.getSession(false);
    if (session != null) {
      final SessionVisitor sessionVisitor = new SessionVisitor(session);

      if (!sessionVisitor.isAuthenticatedUser()) {
        return Response.temporaryRedirect(new URI("/signin.html")).build();
      }

      if (ACTION_DELETE.equals(action)) {
        deleteGateway(gateway.getId());
      }

      if (ACTION_UPDATE.equals(action)) {
        updateGateway(gateway.getId(), suffix);
      }
    }

    return Response.seeOther(new URI(MANAGE_GATEWAYS_URL)).build();
  }

  private void createNewGateway(UserId currentUser, String suffix) {
    Gateway gateway = new Gateway(idGenerator.nextId());
    gateway.setSuffix(suffix);
    gateway.setOwner(currentUser);
    gatewayRepository.save(gateway);
  }

  private void deleteGateway(GatewayId gatewayId) {
    gatewayRepository.remove(gatewayId);
  }

  private void updateGateway(GatewayId gatewayId, String suffix) {
    String safeSuffix;
    if (suffix == null) {
      safeSuffix = "";
    } else {
      int maxLength = Math.min(MAX_SUFFIX_LENGTH, suffix.length());
      safeSuffix = suffix.substring(0, maxLength);
    }
    gatewayRepository.update(gatewayId, safeSuffix);
  }
}