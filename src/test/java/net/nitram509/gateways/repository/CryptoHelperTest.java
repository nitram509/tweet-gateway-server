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
}
