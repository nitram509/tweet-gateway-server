package net.nitram509.recaptcha;

import net.nitram509.config.EnvironmentConfig;
import net.nitram509.config.Optional;
import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaFactory;
import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

public class ReCaptchaService {

  private Optional config = new EnvironmentConfig();
  private final ReCaptchaImpl reCaptcha;

  public ReCaptchaService() {
    reCaptcha = new ReCaptchaImpl();
    reCaptcha.setPrivateKey(config.reCaptchaPrivateKey());
  }

  public String createCaptchaHtml() {
    if (config.hasCaptchaKeys()) {
      ReCaptcha reCaptcha = ReCaptchaFactory.newSecureReCaptcha(config.reCaptchaPublicKey(), config.reCaptchaPrivateKey(), false);
      return reCaptcha.createRecaptchaHtml(null, null);
    }
    return "";
  }

  public boolean isValidCaptcha(String remoteAddr, String recaptchaChallenge, String recaptchaResponse) {
    if (config.hasCaptchaKeys()) {
      ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, recaptchaChallenge, recaptchaResponse);
      return reCaptchaResponse.isValid();
    }
    return true;
  }

}
