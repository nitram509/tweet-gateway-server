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

import de.bwaldvogel.base91.Base91;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class CryptoHelper {

  private static final Charset UTF8 = Charset.forName("UTF-8");
  public static final Charset ASCII = Charset.forName("ASCII");
  public static final String AES_ECB_PKCS5_PADDING = "AES/ECB/PKCS5Padding";

  private final SecretKeySpec key;

  public CryptoHelper(String password) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-1");
      digest.update(password.getBytes(ASCII));
      key = new SecretKeySpec(digest.digest(), 0, 16, "AES");
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }

  public String encrypt(String plaintext) {
    try {
      Cipher cipher = Cipher.getInstance(AES_ECB_PKCS5_PADDING);
      cipher.init(Cipher.ENCRYPT_MODE, key);
      byte[] ciphertext = cipher.doFinal(plaintext.getBytes(UTF8));
      byte[] base91EncodedString = Base91.encode(ciphertext);
      return new String(base91EncodedString, ASCII);
    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
      throw new RuntimeException(e);
    }
  }

  public String decrypt(String base91EncodedCiphertext) {
    try {
      Cipher cipher = Cipher.getInstance(AES_ECB_PKCS5_PADDING);
      cipher.init(Cipher.DECRYPT_MODE, key);
      byte[] base91DecodedString = Base91.decode(base91EncodedCiphertext.getBytes(ASCII));
      byte[] plainBytes = cipher.doFinal(base91DecodedString);
      return new String(plainBytes, UTF8);
    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
      throw new RuntimeException(e);
    }
  }

}
