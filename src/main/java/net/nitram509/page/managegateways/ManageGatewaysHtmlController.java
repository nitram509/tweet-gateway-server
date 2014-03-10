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

package net.nitram509.page.managegateways;

import com.github.mustachejava.Mustache;
import net.nitram509.controller.SessionVisitor;
import net.nitram509.gateways.api.Gateway;
import net.nitram509.gateways.api.UserId;
import net.nitram509.gateways.api.UserProfile;
import net.nitram509.gateways.repository.TweetGateway;
import net.nitram509.gateways.repository.TweetGatewayRepository;
import net.nitram509.mustache.MustacheToolbox;
import net.nitram509.recaptcha.ReCaptchaService;
import net.nitram509.shared.AbstractHttpController;

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
import java.util.ArrayList;
import java.util.List;

import static javax.ws.rs.core.MediaType.TEXT_HTML;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static net.nitram509.gateways.GatewayUrlBuilder.createUrl;

@Path("/manageGateways.html")
public class ManageGatewaysHtmlController extends AbstractHttpController {

  public static String MANAGE_GATEWAYS_URL = "/manageGateways.html";

  private final MustacheToolbox mustacheToolbox = new MustacheToolbox();

  private final Mustache mustache;
  private final TweetGatewayRepository repository = TweetGateway.getRepository();
  private final ReCaptchaService reCaptchaService = new ReCaptchaService();

  public ManageGatewaysHtmlController() {
    mustache = mustacheToolbox.compileOptimized("web/manageGateways.mustache");
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
    return new ManageGatewaysHtmlContext(userProfile, gatewayInfos, recaptchaHtml, replaceCorrectProtocol(uriInfo.getBaseUri().toString()));
  }

  private List<GatewayInfo> enrichGateways(List<Gateway> gateways) {
    ArrayList<GatewayInfo> gatewayInfos = new ArrayList<>();
    for (Gateway gateway : gateways) {
      final GatewayInfo gwi = new GatewayInfo(gateway);
      gwi.setUrl(createUrl(replaceCorrectProtocol(uriInfo.getBaseUri().toString()), gateway.getId()));
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