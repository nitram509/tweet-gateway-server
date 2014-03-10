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

package net.nitram509.shared;

import net.nitram509.config.EnvironmentConfig;

import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class AbstractHttpController {

  /**
   * [...] a de facto standard for identifying the originating protocol of an HTTP request,
   * since a reverse proxy (load balancer) communicates with a web server using HTTP [...]
   * @see <a href="http://de.wikipedia.org/wiki/Liste_der_HTTP-Headerfelder">Wikipedia</a>
   * @return
   */
  public static final String HTTP_HEADER__X_FORWARDED_PROTO = "X-Forwarded-Proto";

  @Context
  protected HttpHeaders httpHeaders;

  @Context
  protected UriInfo uriInfo;

  protected String replaceCorrectProtocol(String requestUrl) {
    List<String> forwardedProtoHeaders = httpHeaders.getRequestHeader(HTTP_HEADER__X_FORWARDED_PROTO);
    if (forwardedProtoHeaders != null && !forwardedProtoHeaders.isEmpty()) {
      int i = requestUrl.indexOf("://");
      if (i >= 0) {
        return forwardedProtoHeaders.get(0) + "://" + requestUrl.substring(i + 3);
      }
    }
    return requestUrl;
  }

  public UriBuilder getSecureAbsolutePathBuilder() {
    List<String> forwardedProtoHeaders = httpHeaders.getRequestHeader(HTTP_HEADER__X_FORWARDED_PROTO);
    String scheme = "http";
    if (forwardedProtoHeaders != null && !forwardedProtoHeaders.isEmpty()) {
      scheme = forwardedProtoHeaders.get(0);
    }
    return uriInfo.getAbsolutePathBuilder().scheme(scheme);
  }

  public Response respondTemporaryRedirect(String absolutePath) {
    return Response.temporaryRedirect(getSecureAbsolutePathBuilder().replacePath(absolutePath).build()).build();
  }

  public Response respondSeeOther(String absolutePath) throws URISyntaxException {
    return Response.seeOther(getSecureAbsolutePathBuilder().replacePath(absolutePath).build()).build();
  }
}
