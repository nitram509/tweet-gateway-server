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

package net.nitram509;

import net.nitram509.config.EnvironmentConfig;
import net.nitram509.logger.ConsoleLogger;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;

import javax.servlet.DispatcherType;
import java.net.URI;
import java.util.EnumSet;

public class TweetGatewayServerLauncher {

  public static void main(String[] args) throws Exception {
    checkConfig();
    printConfig();

    Server server = new Server(getPort());

    HandlerList handlerList = new HandlerList();
    handlerList.addHandler(createStaticResourceHandler());
    handlerList.addHandler(createServletContextHandler());
    server.setHandler(handlerList);

    server.start();
    server.join();
  }

  private static void checkConfig() {
    EnvironmentConfig config = new EnvironmentConfig();
    config.getConnectionUri();
  }

  private static ServletContextHandler createServletContextHandler() {
    ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
    servletContextHandler.setContextPath("/");
    servletContextHandler.addFilter(createRestJerseyFilterHolder(), "/*", EnumSet.allOf(DispatcherType.class));
    return servletContextHandler;
  }

  private static FilterHolder createRestJerseyFilterHolder() {
    FilterHolder filterHolder = new FilterHolder(org.glassfish.jersey.servlet.ServletContainer.class);
    filterHolder.setName("Jersey REST");
    filterHolder.getInitParameters().put("jersey.config.server.provider.packages", "net.nitram509.controller net.nitram509.gateways.controller net.nitram509.page.managegateways");
    filterHolder.getInitParameters().put("com.sun.jersey.config.feature.FilterForwardOn404", "true"); // allow static resources
    return filterHolder;
  }

  private static Handler createStaticResourceHandler() {
    ResourceHandler resourceHandler = new ResourceHandler();
    resourceHandler.setResourceBase("web");
    resourceHandler.setWelcomeFiles(new String[]{"index.html"});
    resourceHandler.setDirectoriesListed(false);
    return resourceHandler;
  }

  private static int getPort() {
    String port = System.getenv("PORT") == null ? "5000" : System.getenv("PORT");
    return Integer.parseInt(port);
  }

  private static void printConfig() {
    EnvironmentConfig config = new EnvironmentConfig();
    ConsoleLogger logger = new ConsoleLogger();
    final URI uri = config.getConnectionUri();
    logger.info("DATABASE_URL                >>> " + uri.getHost() + (uri.getPort() > 0 ? ":" + uri.getPort() : "") + uri.getPath());
    logger.info("twitter4j.oauth.consumerKey >>> " + config.consumerKey());
    logger.info("reCaptcha.public.key        >>> " + config.reCaptchaPublicKey());
  }
}