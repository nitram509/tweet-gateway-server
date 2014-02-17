package net.nitram509.gateways.api;

import java.io.Serializable;

public class GatewayId implements Serializable {

  private final String id;

  public GatewayId(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    GatewayId gatewayId = (GatewayId) o;

    if (!id.equals(gatewayId.id)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override
  public String toString() {
    return "GatewayId=" + id;
  }
}
