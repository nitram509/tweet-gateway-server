package net.nitram509.twitter;

public class TwitterTextHelper {

  private static final int MAX_SUFFIX_LENGTH = 140;

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
    if ((text.length() + HASHTAG_SEPARATOR.length() + defaultHashtag.length()) < MAX_SUFFIX_LENGTH) {
      return text + HASHTAG_SEPARATOR + defaultHashtag;
    }
    return text;
  }

  public static String makeSafeSuffix(String suffix) {
    String safeSuffix = null;
    if (suffix != null) {
      int maxLength = Math.min(MAX_SUFFIX_LENGTH, suffix.length());
      safeSuffix = suffix.substring(0, maxLength);
    }
    return safeSuffix;
  }
}
