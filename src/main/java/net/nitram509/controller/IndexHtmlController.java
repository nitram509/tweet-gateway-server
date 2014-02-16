/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nitram509.controller;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import net.nitram509.tweetgateway.api.GatewayId;
import net.nitram509.tweetgateway.api.GatewayInfo;
import net.nitram509.tweetgateway.api.UserId;
import net.nitram509.tweetgateway.api.UserProfile;
import net.nitram509.tweetgateway.repository.TweetGatewayRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import static javax.ws.rs.core.MediaType.TEXT_HTML;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("/")
public class IndexHtmlController {

  private final Mustache mustache;
  private final TweetGatewayRepository repository = TweetGatewayRepository.instance();

  public IndexHtmlController() {
    MustacheFactory mf = new DefaultMustacheFactory();
    mustache = mf.compile("web/index.mustache");
  }

  @GET
  @Produces({TEXT_HTML, TEXT_PLAIN})
  @Path("index.html")
  public String getIndexHtmxl() throws IOException {
    StringWriter html = new StringWriter();

    UserProfile userMock = createUserMock();
    List<GatewayInfo> gateways = repository.findGateway(userMock.getId());

    IndexHtmlContext model = new IndexHtmlContext(userMock, gateways);
    mustache.execute(html, model).flush();
    return html.toString();
  }

  private UserProfile createUserMock() throws IOException {
    UserProfile userProfile = new UserProfile(new UserId(1));
    userProfile.setScreenName("screenName");
    userProfile.setUrl("https://twitter.com/nitram509");
    userProfile.setName("name");
    userProfile.setProfileImageUrl("http://pbs.twimg.com/profile_images/2852698200/ef5b9daaf433fcde804d5846d3c1f6b6_bigger.png");
    userProfile.setProfileImageUrlHttps("https://pbs.twimg.com/profile_images/2852698200/ef5b9daaf433fcde804d5846d3c1f6b6_bigger.png");
    return userProfile;
  }

  private GatewayInfo createGatewayInfoMock() {
    GatewayInfo gwi = new GatewayInfo(new GatewayId(123));
    gwi.setUrl("http://foobar");
    gwi.setHashtags("#foo #bar");
    gwi.setActivity(3);
    return gwi;
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