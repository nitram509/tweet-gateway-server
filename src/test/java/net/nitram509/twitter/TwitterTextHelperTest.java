package net.nitram509.twitter;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

public class TwitterTextHelperTest {

  public static final String EXAMPLE_SUFFIX = "#HASHTAG";

  private TwitterTextHelper textHelper;
  private String suffix;

  @BeforeMethod
  public void setUp() throws Exception {
    textHelper = new TwitterTextHelper();
    suffix = EXAMPLE_SUFFIX;
  }

  @Test
  public void append_hashtag_if_not_yet_within_text() {
    String text = textHelper.appendDefaultHashtag("test", suffix);

    assertThat(text).endsWith(" " + EXAMPLE_SUFFIX);
  }

  @Test
  public void hashtag_is_appended_with_case_preserved() {
    String text = textHelper.appendDefaultHashtag("test", suffix);

    assertThat(text).endsWith(" " + EXAMPLE_SUFFIX);
  }

  @Test
  public void when_checking_hashtag_already_exists__the_case_doesnt_matter__thus_it_stays_lower_case() {
    String hashtagLowerCase = EXAMPLE_SUFFIX.toLowerCase();
    String text = textHelper.appendDefaultHashtag(hashtagLowerCase, suffix);

    assertThat(text).isEqualTo(hashtagLowerCase);
  }

  @Test
  public void hashtag_only_appended_when_there_is_enough_characters_left() {
    String hashtagLowerCase = EXAMPLE_SUFFIX.toLowerCase();
    String text = textHelper.appendDefaultHashtag(hashtagLowerCase, suffix);

    assertThat(text).isEqualTo(hashtagLowerCase);
  }

  @Test
  public void when_there_is_no_hashtag_append_nothing() {
    suffix = null;
    String text = new TwitterTextHelper().appendDefaultHashtag("empty", suffix);

    assertThat(text).isEqualTo("empty");
  }
}
