package net.nitram509.config;

public interface Provided {

  /**
   * [...] a de facto standard for identifying the originating protocol of an HTTP request,
   * since a reverse proxy (load balancer) communicates with a web server using HTTP [...]
   * @see <a href="http://de.wikipedia.org/wiki/Liste_der_HTTP-Headerfelder">Wikipedia</a>
   * @return
   */
  public String getForwardedProto();

}
