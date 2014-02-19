package net.nitram509.config;

public interface Optional {

  @Deprecated
  String defaultHashTag();

  String reCaptchaPublicKey();

  String reCaptchaPrivateKey();

  boolean hasCaptchaKeys();

}
