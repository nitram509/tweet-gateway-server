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
    InputStream is = null;
    try {
      is = new FileInputStream(file);
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
    } finally {
      try {
        if (is != null) is.close();
      } catch (IOException e) {
        // ignore
      }
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
