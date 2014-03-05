package net.nitram509.mustache;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import java.io.*;
import java.nio.charset.Charset;

public class MustacheToolbox {

  private static final Charset UTF8 = Charset.forName("UTF-8");

  public Mustache compileOptimized(String fileName) {
    MustacheFactory mf = new DefaultMustacheFactory();
    try {
      Reader reader = createTrimmingReader(fileName);
      return mf.compile(reader, fileName);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  private Reader createTrimmingReader(String fileName) throws FileNotFoundException {
    File file = new File(fileName);
    if (!file.exists()) {
      throw new IllegalArgumentException("File doesn't exist: " + fileName);
    }
    try {
      InputStream is = new FileInputStream(file);
      InputStreamReader streamReader = new InputStreamReader(is, UTF8);
      BufferedReader reader = new BufferedReader(streamReader);
      StringBuilder buffer = new StringBuilder();
      for (String line; (line = reader.readLine()) != null; ) {
        String trimmedLine = trimLeft(line);
        buffer.append(trimmedLine);
        if (!trimmedLine.isEmpty()) {
          buffer.append("\n");
        }
      }
      return new StringReader(buffer.toString());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private String trimLeft(String line) {
    if (line == null) return null;
    int firstNonWhitespaceCharacterIndex = 0;
    for (int i = 0, len = line.length(); i < len; i++) {
      if (Character.isWhitespace(line.charAt(i))) continue;
      firstNonWhitespaceCharacterIndex = i;
      break;
    }
    return line.substring(firstNonWhitespaceCharacterIndex);
  }

}
