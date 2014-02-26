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
