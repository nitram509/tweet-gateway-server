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

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

public class CryptoHelperTest {

  private CryptoHelper cryptoHelper;

  @BeforeMethod
  public void setUp() throws Exception {
    cryptoHelper = new CryptoHelper("password");
  }

  @Test
  public void encrypt_and_decrypt_roundtrip_works() {
    String ciphertext = cryptoHelper.encrypt("testString");

    String plaintext = cryptoHelper.decrypt(ciphertext);

    assertThat(plaintext).isEqualTo("testString");
  }

  @Test
  public void maxlength_of_64_characters_fits_into_96_bytes_because_of_DATABASE_restriction() {
    String example64chars = "12345678a12345678b12345678c12345678d12345678e12345678f1234";
    String ciphertext = cryptoHelper.encrypt(example64chars);

    String plaintext = cryptoHelper.decrypt(ciphertext);

    assertThat(plaintext.length()).isLessThan(96);
    assertThat(plaintext).isEqualTo(example64chars);
  }
}
