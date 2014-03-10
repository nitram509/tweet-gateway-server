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

import net.nitram509.shared.AbstractHttpController;
import net.nitram509.twitter.TwitterClientToolbox;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.RequestToken;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("/doTwitterSignIn")
public class TwitterSignInHttpController extends AbstractHttpController {

  public static final String DO_TWITTER_SIGN_IN = "/doTwitterSignIn";
  public static final String DO_TWITTER_CALLBACK = "/doTwitterCallback";

  private TwitterClientToolbox twitterClientToolbox = new TwitterClientToolbox();

  @POST
  @Produces({TEXT_PLAIN})
  public Response doTwitterSignIn(@Context HttpServletRequest request) throws TwitterException, IOException, URISyntaxException {

    RequestToken requestToken = retrieveTwitterRequestToken(request);
    new SessionVisitor(request.getSession(true)).saveRequestToken(requestToken.getToken());

    String authenticationURL = requestToken.getAuthenticationURL();
    return Response.seeOther(new URI(authenticationURL)).build();
  }

  public RequestToken retrieveTwitterRequestToken(HttpServletRequest request) throws TwitterException {
    Twitter twitter = twitterClientToolbox.getTwitterAsTweetGatewayApp();
    String callbackURL = computeCallbackUrl(request);
    return twitter.getOAuthRequestToken(callbackURL);
  }

  private String computeCallbackUrl(HttpServletRequest request) {
    String requestUrl = request.getRequestURL().toString();
    requestUrl = replaceCorrectProtocol(requestUrl);
    return requestUrl.substring(0, requestUrl.indexOf(DO_TWITTER_SIGN_IN)) + DO_TWITTER_CALLBACK;
  }

}
