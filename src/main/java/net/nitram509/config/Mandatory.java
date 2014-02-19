package net.nitram509.config;

import java.net.URI;

public interface Mandatory {
  String consumerKey();

  String consumerSecret();

  URI getConnectionUri();
}
