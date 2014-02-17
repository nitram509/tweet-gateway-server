package net.nitram509.recaptcha;

import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaFactory;
import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

public class ReCaptchaService {

  private final ReCaptchaImpl reCaptcha;

  public ReCaptchaService() {
    reCaptcha = new ReCaptchaImpl();
    reCaptcha.setPrivateKey("your private key");
  }

  public String createCaptchaHtml() {
    ReCaptcha reCaptcha = ReCaptchaFactory.newReCaptcha("your public key", "your private key", false);
    return reCaptcha.createRecaptchaHtml(null, null);
  }

  public boolean isValidCaptcha(String remoteAddr, String recaptchaChallenge, String recaptchaResponse) {
    ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, recaptchaChallenge, recaptchaResponse);
    return reCaptchaResponse.isValid();
  }

}
