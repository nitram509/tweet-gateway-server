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
