package net.nitram509.config;

import java.sql.Connection;

public interface Mandatory {
  String consumerKey();

  String consumerSecret();

  Connection getConnection();
}
