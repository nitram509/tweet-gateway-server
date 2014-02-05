package net.nitram509.model;

import java.util.List;

import static java.util.Arrays.asList;

public class SimpleTweet {

  List<Tweet> tweets() {
    return asList(
        new Tweet("Tweet 1", "Max", asList(new TextLines("New!"), new TextLines("Awesome!"))),
        new Tweet("Tweet 2", "Pete", asList(new TextLines("Old."), new TextLines("Ugly.")))
    );
  }

  static class Tweet {
    Tweet(String name, String user, List<TextLines> textLines) {
      this.name = name;
      this.user = user;
      this.textLines = textLines;
    }

    String name, user;
    List<TextLines> textLines;
  }

  static class TextLines {
    TextLines(String description) {
      this.description = description;
    }

    String description;
  }
}