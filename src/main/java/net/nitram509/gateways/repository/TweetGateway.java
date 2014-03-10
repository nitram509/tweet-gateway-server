/*
 * Copyright (c) 2014 Martin W. Kirst (nitram509 at bitkings dot de)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package net.nitram509.gateways.repository;

import net.nitram509.config.EnvironmentConfig;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TweetGateway {

  private final static EnvironmentConfig ENVIRONMENT_CONFIG = new EnvironmentConfig();

  public static TweetGatewayRepository getRepository() {
    if (ENVIRONMENT_CONFIG.getConnectionUri() != null) {
      return new TweetGatewayRepositoryPostgreSQL(getDatabaseConnection());
    }
    return new TweetGatewayRepositoryInMemory();
  }

  public static Connection getDatabaseConnection() {
    URI dbUri = ENVIRONMENT_CONFIG.getConnectionUri();
    String username = dbUri.getUserInfo().split(":")[0];
    String password = dbUri.getUserInfo().split(":")[1];
    String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + (dbUri.getPort() > 0 ? ":" + dbUri.getPort() : "") + dbUri.getPath();
    try {
      return DriverManager.getConnection(dbUrl, username, password);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

}
