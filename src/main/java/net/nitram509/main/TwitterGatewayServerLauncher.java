package net.nitram509.main;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class TwitterGatewayServerLauncher {

  public static void main(String[] args) throws Exception {
    Server server = new Server(getPort());

    HandlerList handlerList = new HandlerList();
    handlerList.addHandler(createServletContextHandler());
    handlerList.addHandler(createDefaultHandler());
    server.setHandler(handlerList);

    server.start();
    server.join();
  }

  private static ServletContextHandler createServletContextHandler() {
    ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
    servletContextHandler.setContextPath("/");
    servletContextHandler.addServlet(createRestServletHolder(), "/rest/*");
    return servletContextHandler;
  }

  private static ServletHolder createRestServletHolder() {
    ServletHolder restServletHolder = new ServletHolder();
    restServletHolder.setName("Jersey REST Service");
    restServletHolder.setClassName("com.sun.jersey.spi.container.servlet.ServletContainer");
    restServletHolder.getInitParameters().put("com.sun.jersey.config.property.packages", "net.nitram509");
    restServletHolder.setInitOrder(1);
    return restServletHolder;
  }

  private static Handler createDefaultHandler() {
    ResourceHandler resourceHandler = new ResourceHandler();
    resourceHandler.setResourceBase("./web");
    resourceHandler.setWelcomeFiles(new String[]{"index.html"});
    resourceHandler.setDirectoriesListed(false);
    return resourceHandler;
  }

  private static int getPort() {
    System.out.println("consumerKey >>> " + System.getProperty("consumerKey"));
    String port = System.getenv("PORT") == null ? "5000" : System.getenv("PORT");
    return Integer.parseInt(port);
  }
}