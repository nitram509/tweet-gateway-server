package net.nitram509.controller;

import net.nitram509.gateways.api.UserId;

import javax.servlet.http.HttpSession;

public class SessionVisitor {

  private static final String REQUEST_TOKEN = "requestToken";
  private static final String ACCESS_TOKEN = "accessToken";
  private static final String ACCESS_TOKEN_SECRET = "accessTokenSecret";
  private static final String USER_ID = "userId";

  private final HttpSession session;

  public SessionVisitor(HttpSession session) {
    this.session = session;
  }

  public boolean hasRequestToken() {
    if (session == null) return false;
    String token = (String) session.getAttribute(REQUEST_TOKEN);
    return token != null && !token.isEmpty();
  }

  public void saveRequestToken(String token) {
    session.setAttribute(REQUEST_TOKEN, token);
  }

  public String loadRequestToken() {
    return (String) session.getAttribute(REQUEST_TOKEN);
  }

  public void removeRequestToken() {
    session.removeAttribute(REQUEST_TOKEN);
  }

  public boolean isAuthenticatedUser() {
    if (session == null) return false;
    final String accessToken = (String) session.getAttribute(ACCESS_TOKEN);
    final String accessTokenSecret = (String) session.getAttribute(ACCESS_TOKEN_SECRET);
    return accessToken != null && !accessToken.isEmpty() && accessTokenSecret != null && !accessTokenSecret.isEmpty() && loadCurrentUser() != null;
  }

  public void saveAccessToken(String token) {
    session.setAttribute(ACCESS_TOKEN, token);
  }

  public void saveAccessTokenSecret(String tokenSecret) {
    session.setAttribute(ACCESS_TOKEN_SECRET, tokenSecret);
  }

  public void saveCurrentUser(UserId userId) {
    if (userId == null) {
      session.removeAttribute(USER_ID);
    } else {
      session.setAttribute(USER_ID, userId);
    }
  }

  public UserId loadCurrentUser() {
    return (UserId) session.getAttribute(USER_ID);
  }
}
