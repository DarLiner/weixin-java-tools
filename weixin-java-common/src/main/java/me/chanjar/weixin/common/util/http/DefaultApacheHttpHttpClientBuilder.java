package me.chanjar.weixin.common.util.http;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;

import me.chanjar.weixin.common.util.StringUtils;

/**
 * httpclient 连接管理器
 */
@NotThreadSafe
public class DefaultApacheHttpHttpClientBuilder implements ApacheHttpClientBuilder {
  private int connectionRequestTimeout = 3000;
  private int connectionTimeout = 5000;
  private int soTimeout = 5000;
  private int idleConnTimeout = 60000;
  private int checkWaitTime = 60000;
  private int maxConnPerHost = 10;
  private int maxTotalConn = 50;
  private String userAgent;
  private HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
    @Override
    public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
      return false;
    }
  };
  private SSLConnectionSocketFactory sslConnectionSocketFactory = SSLConnectionSocketFactory.getSocketFactory();
  private PlainConnectionSocketFactory plainConnectionSocketFactory = PlainConnectionSocketFactory.getSocketFactory();

  private String httpProxyHost;
  private int httpProxyPort;
  private String httpProxyUsername;
  private String httpProxyPassword;

  /**
   * 连接管理器
   */
  private PoolingHttpClientConnectionManager connectionManager;
  /**
   * 闲置连接监控线程
   */
  private IdleConnectionMonitorThread idleConnectionMonitorThread;

  /**
   * httpClientBuilder
   */
  private HttpClientBuilder httpClientBuilder;

  private boolean prepared = false;

  private DefaultApacheHttpHttpClientBuilder() {
  }

  public static DefaultApacheHttpHttpClientBuilder get() {
    return new DefaultApacheHttpHttpClientBuilder();
  }

  @Override
  public ApacheHttpClientBuilder httpProxyHost(String httpProxyHost) {
    this.httpProxyHost = httpProxyHost;
    return this;
  }

  @Override
  public ApacheHttpClientBuilder httpProxyPort(int httpProxyPort) {
    this.httpProxyPort = httpProxyPort;
    return this;
  }

  @Override
  public ApacheHttpClientBuilder httpProxyUsername(String httpProxyUsername) {
    this.httpProxyUsername = httpProxyUsername;
    return this;
  }

  @Override
  public ApacheHttpClientBuilder httpProxyPassword(String httpProxyPassword) {
    this.httpProxyPassword = httpProxyPassword;
    return this;
  }

  @Override
  public ApacheHttpClientBuilder sslConnectionSocketFactory(SSLConnectionSocketFactory sslConnectionSocketFactory) {
    this.sslConnectionSocketFactory = sslConnectionSocketFactory;
    return this;
  }

  public IdleConnectionMonitorThread getIdleConnectionMonitorThread() {
    return this.idleConnectionMonitorThread;
  }

  private void prepare() {
    Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
        .register("http", this.plainConnectionSocketFactory)
        .register("https", this.sslConnectionSocketFactory)
            .build();
    this.connectionManager = new PoolingHttpClientConnectionManager(registry);
    this.connectionManager.setMaxTotal(this.maxTotalConn);
    this.connectionManager.setDefaultMaxPerRoute(this.maxConnPerHost);
    this.connectionManager.setDefaultSocketConfig(
            SocketConfig.copy(SocketConfig.DEFAULT)
            .setSoTimeout(this.soTimeout)
                    .build()
    );

    this.idleConnectionMonitorThread = new IdleConnectionMonitorThread(
        this.connectionManager, this.idleConnTimeout, this.checkWaitTime);
    this.idleConnectionMonitorThread.setDaemon(true);
    this.idleConnectionMonitorThread.start();

    this.httpClientBuilder = HttpClients.custom()
        .setConnectionManager(this.connectionManager)
            .setDefaultRequestConfig(
                    RequestConfig.custom()
                .setSocketTimeout(this.soTimeout)
                .setConnectTimeout(this.connectionTimeout)
                .setConnectionRequestTimeout(this.connectionRequestTimeout)
                            .build()
            )
        .setRetryHandler(this.httpRequestRetryHandler);

    if (StringUtils.isNotBlank(this.httpProxyHost)
        && StringUtils.isNotBlank(this.httpProxyUsername)) {
      // 使用代理服务器 需要用户认证的代理服务器
      CredentialsProvider credsProvider = new BasicCredentialsProvider();
      credsProvider.setCredentials(
          new AuthScope(this.httpProxyHost, this.httpProxyPort),
          new UsernamePasswordCredentials(this.httpProxyUsername,
              this.httpProxyPassword));
      this.httpClientBuilder.setDefaultCredentialsProvider(credsProvider);
    }

    if (StringUtils.isNotBlank(this.userAgent)) {
      this.httpClientBuilder.setUserAgent(this.userAgent);
    }

  }

  @Override
  public CloseableHttpClient build() {
    if (!this.prepared) {
      prepare();
      this.prepared = true;
    }

    return this.httpClientBuilder.build();
  }

  public static class IdleConnectionMonitorThread extends Thread {
    private final HttpClientConnectionManager connMgr;
    private final int idleConnTimeout;
    private final int checkWaitTime;
    private volatile boolean shutdown;

    public IdleConnectionMonitorThread(HttpClientConnectionManager connMgr, int idleConnTimeout, int checkWaitTime) {
      super("IdleConnectionMonitorThread");
      this.connMgr = connMgr;
      this.idleConnTimeout = idleConnTimeout;
      this.checkWaitTime = checkWaitTime;
    }

    @Override
    public void run() {
      try {
        while (!this.shutdown) {
          synchronized (this) {
            wait(this.checkWaitTime);
            this.connMgr.closeExpiredConnections();
            this.connMgr.closeIdleConnections(this.idleConnTimeout,
                TimeUnit.MILLISECONDS);
          }
        }
      } catch (InterruptedException ignore) {
      }
    }

    public void trigger() {
      synchronized (this) {
        notifyAll();
      }
    }

    public void shutdown() {
      this.shutdown = true;
      synchronized (this) {
        notifyAll();
      }
    }
  }
}
