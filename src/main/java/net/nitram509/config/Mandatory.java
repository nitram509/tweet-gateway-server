package net.nitram509.config;

import java.net.URI;
import java.sql.Connection;

public interface Mandatory {
  String consumerKey();

  String consumerSecret();

  URI getConnectionUri();
}
