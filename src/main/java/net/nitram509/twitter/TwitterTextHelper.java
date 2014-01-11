package net.nitram509.twitter;

public class TwitterTextHelper {

  public static final String HASHTAG_SEPARATOR = " ";

  private final String defaultHashtag;

  public TwitterTextHelper() {
    this(null);
  }

  public TwitterTextHelper(String defaultHashtag) {
    this.defaultHashtag = defaultHashtag;
  }

  public String appendDefaultHashtag(String text) {
    if (this.defaultHashtag == null || this.defaultHashtag.trim().isEmpty()) {
      return text;
    }
    String s = text.toLowerCase();
    if (s.contains(defaultHashtag.toLowerCase())) {
      return text;
    }
    if ((text.length() + HASHTAG_SEPARATOR.length() + defaultHashtag.length()) < 140) {
      return text + HASHTAG_SEPARATOR + defaultHashtag;
    }
    return text;
  }
}
