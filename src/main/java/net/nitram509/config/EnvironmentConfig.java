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

package net.nitram509.config;

import java.net.URI;
import java.net.URISyntaxException;

public class EnvironmentConfig implements Mandatory, Optional {

  static final String ENV_VAR__DATABASE_URL = "DATABASE_URL";
  static final String ENV_VAR__PERSONAL_DATABASE_SECRET = "personal.database.secret";
  static final String ENV_VAR__RE_CAPTCHA_PUBLIC_KEY = "reCaptcha.public.key";
  static final String ENV_VAR__RE_CAPTCHA_PRIVATE_KEY = "reCaptcha.private.key";
  static final String ENV_VAR__TWITTER4J_OAUTH_CONSUMER_KEY = "twitter4j.oauth.consumerKey";
  static final String ENV_VAR__TWITTER4J_OAUTH_CONSUMER_SECRET = "twitter4j.oauth.consumerSecret";

  static final String DEFAULT_DATABASE_SECRET = "This is the default secret key for Encryption/Decryption sensitive user date in database";

  @Override
  public String consumerKey() {
    return System.getenv(ENV_VAR__TWITTER4J_OAUTH_CONSUMER_KEY);
  }

  @Override
  public String consumerSecret() {
    return System.getenv(ENV_VAR__TWITTER4J_OAUTH_CONSUMER_SECRET);
  }

  @Override
  public String reCaptchaPublicKey() {
    final String key = System.getenv(ENV_VAR__RE_CAPTCHA_PUBLIC_KEY);
    return key == null ? "" : key;
  }

  @Override
  public String reCaptchaPrivateKey() {
    final String key = System.getenv(ENV_VAR__RE_CAPTCHA_PRIVATE_KEY);
    return key == null ? "" : key;
  }

  @Override
  public boolean hasCaptchaKeys() {
    return !reCaptchaPrivateKey().isEmpty() && !reCaptchaPublicKey().isEmpty();
  }

  /**
   * Example:
   * DATABASE_URL = "postgres://user3123:passkja83kd8@ec2-117-21-174-214.compute-1.amazonaws.com:6212/db982398"
   *
   * @see <a href="https://devcenter.heroku.com/articles/heroku-postgresql#connecting-in-java">
   * Heroku Postgres - Connecting in Java
   * </a>
   */
  @Override
  public URI getConnectionUri() {
    try {
      final String database_url = System.getenv(ENV_VAR__DATABASE_URL);
      if (database_url == null) {
        throw new IllegalStateException("Environment variable DATABASE_URL is missing. Provide one, example DATABASE_URL='postgres://user:pass@host:port/database'");
      }
      return new URI(database_url);
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String getPersonalDatabaseSecret() {
    String secret = System.getenv(ENV_VAR__PERSONAL_DATABASE_SECRET);
    return secret != null ? secret : DEFAULT_DATABASE_SECRET;
  }

}
