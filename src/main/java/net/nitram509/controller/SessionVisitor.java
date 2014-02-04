package net.nitram509.controller;

import javax.servlet.http.HttpSession;

class SessionVisitor {

  private final HttpSession session;

  public SessionVisitor(HttpSession session) {
    this.session = session;
  }

  public boolean hasRequestToken() {
    String token = (String) session.getAttribute("requestToken");
    return token != null && !token.isEmpty();
  }

  public void saveRequestToken(String token) {
    session.setAttribute("requestToken", token);
  }

  public String loadRequestToken() {
    return (String) session.getAttribute("requestToken");
  }

  public void saveAccessToken(String token) {
    session.setAttribute("accessToken", token);
  }

  public void saveAccessTokenSecret(String tokenSecret) {
    session.setAttribute("accessTokenSecret", tokenSecret);
  }

}
