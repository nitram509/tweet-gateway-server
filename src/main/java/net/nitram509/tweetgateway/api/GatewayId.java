package net.nitram509.tweetgateway.api;

public class GatewayId {

  private final long id;

  public GatewayId(long id) {
    this.id = id;
  }

  public long getId() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    GatewayId gatewayId = (GatewayId) o;

    if (id != gatewayId.id) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return (int) (id ^ (id >>> 32));
  }
}
