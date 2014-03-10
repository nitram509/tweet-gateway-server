/*
 * Copyright (c) 2014 Martin W. Kirst (nitram509 at bitkings dot de)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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
