package net.nitram509.main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class TwitterGatewayServer {

  public static void main(String[] args) throws Exception {
    Server server = new Server(getPort());
    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    context.setContextPath("/");
    server.setHandler(context);

    context.addServlet(createRestServletHolder(), "/rest/*");

    server.start();
    server.join();
  }

  private static ServletHolder createRestServletHolder() {
    ServletHolder restServletHolder = new ServletHolder();
    restServletHolder.setName("Jersey REST Service");
    restServletHolder.setClassName("com.sun.jersey.spi.container.servlet.ServletContainer");
    restServletHolder.getInitParameters().put("com.sun.jersey.config.property.packages", "net.nitram509");
    restServletHolder.setInitOrder(1);
    return restServletHolder;
  }

  private static int getPort() {
    String port = System.getenv("PORT") == null ? "5000" : System.getenv("PORT");
    return Integer.parseInt(port);
  }
}