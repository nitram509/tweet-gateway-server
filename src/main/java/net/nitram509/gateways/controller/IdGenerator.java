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
