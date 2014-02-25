package net.nitram509.gateways;

import net.nitram509.gateways.api.GatewayId;

import java.net.URI;

public class GatewayUrlBuilder {

  public static final String GATEWAY_URL_PREFIX = "gw";

  public static String createUrl(URI baseUri, GatewayId gatewayId) {
    return createUrl(baseUri.toString(), gatewayId);
  }

  public static String createUrl(String baseUrl, GatewayId gatewayId) {
    return baseUrl + GATEWAY_URL_PREFIX + "/" + gatewayId.getId();
  }

}
