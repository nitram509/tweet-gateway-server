package net.nitram509.logger;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;

/**
 * According to Heroku, it's OK to log to console std out.
 *
 * @see <a href="https://devcenter.heroku.com/articles/logging">https://devcenter.heroku.com/articles/logging</a>
 */
public class ConsoleLogger {

  private ObjectMapper mapper = new ObjectMapper();

  public void info(String message) {
    System.out.println("[INFO] " + message);
  }

  public void infoAsJson(String message, Object object) {
    StringWriter writer = new StringWriter();
    try {
      mapper.writeValue(writer, object);
      writer.close();
      System.out.println("[INFO] " + message + " ==> " + writer.toString());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
