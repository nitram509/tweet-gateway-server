/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nitram509.controller;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import net.nitram509.gateways.api.GatewayInfo;
import net.nitram509.gateways.api.UserId;
import net.nitram509.gateways.api.UserProfile;
import net.nitram509.gateways.repository.TweetGateway;
import net.nitram509.gateways.repository.TweetGatewayRepository;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static javax.ws.rs.core.MediaType.TEXT_HTML;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("/index.html")
public class IndexHtmlController {

  private final Mustache mustache;
  private final TweetGatewayRepository repository = TweetGateway.getRepository();

  public IndexHtmlController() {
    MustacheFactory mf = new DefaultMustacheFactory();
    mustache = mf.compile("web/index.mustache");
  }

  @GET
  @Produces({TEXT_HTML, TEXT_PLAIN})
  public Response getIndexHtml(@Context HttpServletRequest request) throws IOException, URISyntaxException {

    final SessionVisitor sessionVisitor = new SessionVisitor(request.getSession(false));
    if (!sessionVisitor.isAuthenticatedUser()) {
      return Response.temporaryRedirect(new URI("/signin.html")).build();
    }

    IndexHtmlContext model = createModel(sessionVisitor);
    String pageContent = renderTemplate(model);

    return Response.ok(pageContent).build();
  }

  private IndexHtmlContext createModel(SessionVisitor sessionVisitor) {
    final UserId userId = sessionVisitor.loadCurrentUser();
    final UserProfile userProfile = repository.getUser(userId);
    final List<GatewayInfo> gateways = repository.findGateways(userId);
    return new IndexHtmlContext(userProfile, gateways);
  }

  private String renderTemplate(IndexHtmlContext model) throws IOException {
    StringWriter html = new StringWriter();
    mustache.execute(html, model).flush();
    return html.toString();
  }

  private static class IndexHtmlContext {

    private UserProfile userProfile;
    private List<GatewayInfo> gatewayInfos;

    private IndexHtmlContext(UserProfile userProfile, List<GatewayInfo> gatewayInfos) {
      this.userProfile = userProfile;
      this.gatewayInfos = gatewayInfos;
    }

    public UserProfile getUserProfile() {
      return userProfile;
    }

    public List<GatewayInfo> getGatewayInfos() {
      return gatewayInfos;
    }
  }

}