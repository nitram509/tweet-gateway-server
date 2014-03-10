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

package net.nitram509.controller;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

public class TwitterSignInHttpControllerTest {

  private TwitterSignInHttpController controller;

  @BeforeMethod
  public void setUp() throws Exception {
    controller = new TwitterSignInHttpController();
    String getenv = System.getenv("X-Forwarded-Proto");
    if (getenv == null) {
      throw new RuntimeException("Please, provide environment variable X-Forwarded-Proto=https'");
    }
  }


  @Test(enabled = false)
  public void places_correct_protocoll() {
    String url = controller.placeCorrectProtocol("http://www.example.org/foo?bar");

    assertThat(url).isEqualTo("https://www.example.org/foo?bar");
  }


}
