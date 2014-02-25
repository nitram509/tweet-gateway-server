/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nitram509.page.managegateways;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import net.nitram509.controller.SessionVisitor;
import net.nitram509.gateways.GatewayUrlBuilder;
import net.nitram509.gateways.api.Gateway;
import net.nitram509.gateways.api.UserId;
import net.nitram509.gateways.api.UserProfile;
import net.nitram509.gateways.repository.TweetGateway;
import net.nitram509.gateways.repository.TweetGatewayRepository;
import net.nitram509.recaptcha.ReCaptchaService;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static javax.ws.rs.core.MediaType.TEXT_HTML;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static net.nitram509.gateways.GatewayUrlBuilder.createUrl;

@Path("/manageGateways.html")
public class ManageGatewaysHtmlController {

  public static String MANAGE_GATEWAYS_URL = "/manageGateways.html";

  private final Mustache mustache;
  private final TweetGatewayRepository repository = TweetGateway.getRepository();
  private final ReCaptchaService reCaptchaService = new ReCaptchaService();

  @Context
  UriInfo uriInfo;

  public ManageGatewaysHtmlController() {
    MustacheFactory mf = new DefaultMustacheFactory();
    mustache = mf.compile("web/manageGateways.mustache");
  }

  @GET
  @Produces({TEXT_HTML, TEXT_PLAIN})
  public Response getIndexHtml(@Context HttpServletRequest request) throws IOException, URISyntaxException {

    SessionVisitor sessionVisitor = new SessionVisitor(request.getSession(false));
    if (!sessionVisitor.isAuthenticatedUser()) {
      return Response.temporaryRedirect(new URI("/signin.html")).build();
    }

    ManageGatewaysHtmlContext model = createModel(sessionVisitor);
    String pageContent = renderTemplate(model);

    return Response.ok(pageContent).build();
  }

  private ManageGatewaysHtmlContext createModel(SessionVisitor sessionVisitor) {
    UserId userId = sessionVisitor.loadCurrentUser();
    UserProfile userProfile = repository.getUser(userId);
    List<Gateway> gateways = repository.findGateways(userId);
    List<GatewayInfo> gatewayInfos = enrichGateways(gateways);
    String recaptchaHtml = reCaptchaService.createCaptchaHtml();
    return new ManageGatewaysHtmlContext(userProfile, gatewayInfos, recaptchaHtml);
  }

  private List<GatewayInfo> enrichGateways(List<Gateway> gateways) {
    ArrayList<GatewayInfo> gatewayInfos = new ArrayList<>();
    for (Gateway gateway : gateways) {
      final GatewayInfo gwi = new GatewayInfo(gateway);
      gwi.setUrl(createUrl(uriInfo.getBaseUri(), gateway.getId()));
      gatewayInfos.add(gwi);
    }
    return gatewayInfos;
  }

  private String renderTemplate(ManageGatewaysHtmlContext model) throws IOException {
    StringWriter html = new StringWriter();
    mustache.execute(html, model).flush();
    return html.toString();
  }

}