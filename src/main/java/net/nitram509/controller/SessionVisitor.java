package net.nitram509.controller;

import net.nitram509.gateways.api.UserId;

import javax.servlet.http.HttpSession;

import static java.lang.Boolean.TRUE;

public class SessionVisitor {

  private static final String REQUEST_TOKEN = "requestToken";
  private static final String USER_ID = "userId";
  private static final String IS_AUTHENTICATED = "authenticated";

  private final HttpSession session;

  public SessionVisitor(HttpSession session) {
    this.session = session;
  }

  public boolean hasRequestToken() {
    if (session == null) return false;
    String token = loadRequestToken();
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
    Boolean isAuthenticated = (Boolean) session.getAttribute(IS_AUTHENTICATED);
    return TRUE.equals(isAuthenticated) && loadCurrentUser() != null;
  }

  public void setAuthenticatedUser(boolean isAuthenticated) {
    if (isAuthenticated) {
      session.setAttribute(IS_AUTHENTICATED, TRUE);
    } else {
      session.removeAttribute(IS_AUTHENTICATED);
    }
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
