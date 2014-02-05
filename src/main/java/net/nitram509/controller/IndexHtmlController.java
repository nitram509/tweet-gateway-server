/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nitram509.controller;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import net.nitram509.model.SimpleTweet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.io.IOException;
import java.io.StringWriter;

import static javax.ws.rs.core.MediaType.TEXT_HTML;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("/")
public class IndexHtmlController {

  private final Mustache mustache;

  public IndexHtmlController() {
    MustacheFactory mf = new DefaultMustacheFactory();
    mustache = mf.compile("web/index.mustache");
  }

  @GET
  @Produces({TEXT_HTML, TEXT_PLAIN})
  @Path("index.html")
  public String getIndexHtmxl() throws IOException {
    StringWriter html = new StringWriter();
    mustache.execute(html, new SimpleTweet()).flush();
    return html.toString();
  }

}