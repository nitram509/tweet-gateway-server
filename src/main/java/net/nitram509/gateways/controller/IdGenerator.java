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

package net.nitram509.gateways.controller;

import net.nitram509.gateways.api.GatewayId;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class IdGenerator {

  /// alnum passwords, entropy = 54^16 = 5,227,573,613,485,916,806,405,226,496 ~~ 5*10^27 ~~ 2^92
  private static final char[] CHARS = "23456789abcdefghjkmnopqrtuvwxyzABCDEFGHJKLMNPQRTUVWXYZ".toCharArray(); // not 1ilI 0O sS

  public static final int ID_LENGTH = 16;

  private final SecureRandom random;

  public IdGenerator() {
    try {
      random = SecureRandom.getInstance("SHA1PRNG");
      random.setSeed(System.currentTimeMillis());
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }

  public GatewayId nextId() {
    StringBuilder sb = new StringBuilder(16);
    for (int i = 0; i < ID_LENGTH; i++) {
      sb.append(CHARS[random.nextInt(CHARS.length)]);
    }
    return new GatewayId(sb.toString());
  }

}
