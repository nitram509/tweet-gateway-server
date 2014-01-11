package net.nitram509.logger;

import com.google.gson.Gson;

/**
 * According to Heroku, it's OK to log to console std out.
 *
 * @see <a href="https://devcenter.heroku.com/articles/logging">https://devcenter.heroku.com/articles/logging</a>
 */
public class ConsoleLogger {

  public void info(String message) {
    System.out.println("[INFO] " + message);
  }

  public void infoAsJson(String message, Object object) {
    Gson gson = new Gson();
    System.out.println("[INFO] " + message + " ==> " + gson.toJson(object));
  }

}
