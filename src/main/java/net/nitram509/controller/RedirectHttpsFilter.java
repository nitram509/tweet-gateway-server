package net.nitram509.controller;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class RedirectHttpsFilter implements ContainerRequestFilter {

  public static final String X_FORWARDED_PROTO = "X-Forwarded-Proto";

  @Context
  HttpServletResponse response;

  @Override
  public void filter(ContainerRequestContext ctx) throws IOException {
    String x_forwarded_proto_header = ctx.getHeaderString(X_FORWARDED_PROTO);
    if (x_forwarded_proto_header != null && !x_forwarded_proto_header.startsWith("https")) {
      HttpServletRequest request = (HttpServletRequest) ctx.getRequest();
      String pathInfo = (request.getPathInfo() != null) ? request.getPathInfo() : "";
      response.sendRedirect("https://" + request.getServerName() + pathInfo);
    }
  }

}