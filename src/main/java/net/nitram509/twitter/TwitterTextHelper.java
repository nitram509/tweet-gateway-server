package net.nitram509.twitter;

public class TwitterTextHelper {

  private static final int MAX_SUFFIX_LENGTH = 140;

  public static final String HASHTAG_SEPARATOR = " ";

  public String appendDefaultHashtag(String text, String suffix) {
    if (suffix == null || suffix.trim().isEmpty()) {
      return text;
    }
    String s = text.toLowerCase();
    if (s.contains(suffix.toLowerCase())) {
      return text;
    }
    if ((text.length() + HASHTAG_SEPARATOR.length() + suffix.length()) < MAX_SUFFIX_LENGTH) {
      return text + HASHTAG_SEPARATOR + suffix;
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
