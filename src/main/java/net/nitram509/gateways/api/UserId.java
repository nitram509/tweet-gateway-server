package net.nitram509.gateways.api;

import java.io.Serializable;

public class UserId implements Serializable {

  private final long id;

  public UserId(long id) {
    this.id = id;
  }

  public long getId() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    UserId userId = (UserId) o;

    if (id != userId.id) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return (int) (id ^ (id >>> 32));
  }
}
