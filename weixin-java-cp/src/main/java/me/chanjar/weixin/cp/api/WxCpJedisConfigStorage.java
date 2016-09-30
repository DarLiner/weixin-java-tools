package me.chanjar.weixin.cp.api;

import java.io.File;

import me.chanjar.weixin.common.bean.WxAccessToken;
import me.chanjar.weixin.common.util.http.ApacheHttpClientBuilder;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Jedis client implementor for wechat config storage
 *
 * @author gaigeshen
 */
public class WxCpJedisConfigStorage implements WxCpConfigStorage {

	/* Redis keys here */
	private static final String ACCESS_TOKEN_KEY = "WX_CP_ACCESS_TOKEN";
	private static final String ACCESS_TOKEN_EXPIRES_TIME_KEY = "WX_CP_ACCESS_TOKEN_EXPIRES_TIME";
	private static final String JS_API_TICKET_KEY = "WX_CP_JS_API_TICKET";
	private static final String JS_API_TICKET_EXPIRES_TIME_KEY = "WX_CP_JS_API_TICKET_EXPIRES_TIME";

	private volatile String corpId;
	private volatile String corpSecret;

	private volatile String token;
	private volatile String aesKey;
	private volatile String agentId;

	private volatile String oauth2redirectUri;

	private volatile String httpProxyHost;
	private volatile int httpProxyPort;
	private volatile String httpProxyUsername;
	private volatile String httpProxyPassword;

	private volatile File tmpDirFile;

	private volatile ApacheHttpClientBuilder apacheHttpClientBuilder;

	/* Redis clients pool */
	private final JedisPool jedisPool;

	public WxCpJedisConfigStorage(String host, int port) {
		this.jedisPool = new JedisPool(host, port);
	}

	/**
	 *
	 * This method will be destroy jedis pool
	 */
	public void destroy() {
		this.jedisPool.destroy();
	}

	@Override
	public String getAccessToken() {
		try (Jedis jedis = this.jedisPool.getResource()) {
			return jedis.get(ACCESS_TOKEN_KEY);
		}
	}

	@Override
	public boolean isAccessTokenExpired() {
		try (Jedis jedis = this.jedisPool.getResource()) {
			String expiresTimeStr = jedis.get(ACCESS_TOKEN_EXPIRES_TIME_KEY);

			if (expiresTimeStr != null) {
				Long expiresTime = Long.parseLong(expiresTimeStr);
				return System.currentTimeMillis() > expiresTime;
			}

			return true;

		}
	}

	@Override
	public void expireAccessToken() {
		try (Jedis jedis = this.jedisPool.getResource()) {
		  jedis.set(ACCESS_TOKEN_EXPIRES_TIME_KEY, "0");
		}
	}

	@Override
	public synchronized void updateAccessToken(WxAccessToken accessToken) {
		this.updateAccessToken(accessToken.getAccessToken(), accessToken.getExpiresIn());
	}

	@Override
	public synchronized void updateAccessToken(String accessToken, int expiresInSeconds) {
		try (Jedis jedis = this.jedisPool.getResource()) {
			jedis.set(ACCESS_TOKEN_KEY, accessToken);

			jedis.set(ACCESS_TOKEN_EXPIRES_TIME_KEY,
					(System.currentTimeMillis() + (expiresInSeconds - 200) * 1000L) + "");
		}
	}

	@Override
	public String getJsapiTicket() {
		try (Jedis jedis = this.jedisPool.getResource()) {
		  return jedis.get(JS_API_TICKET_KEY);
		}
	}

	@Override
	public boolean isJsapiTicketExpired() {

		try (Jedis jedis = this.jedisPool.getResource()) {
			String expiresTimeStr = jedis.get(JS_API_TICKET_EXPIRES_TIME_KEY);

			if (expiresTimeStr != null) {
				Long expiresTime = Long.parseLong(expiresTimeStr);
				return System.currentTimeMillis() > expiresTime;
			}

			return true;

		}
	}

	@Override
	public void expireJsapiTicket() {
		try (Jedis jedis = this.jedisPool.getResource()) {
			jedis.set(JS_API_TICKET_EXPIRES_TIME_KEY, "0");
		}
	}

	@Override
	public synchronized void updateJsapiTicket(String jsapiTicket, int expiresInSeconds) {

		try (Jedis jedis = this.jedisPool.getResource()) {
			jedis.set(JS_API_TICKET_KEY, jsapiTicket);

			jedis.set(JS_API_TICKET_EXPIRES_TIME_KEY,
					(System.currentTimeMillis() + (expiresInSeconds - 200) * 1000L + ""));
		}

	}

	@Override
	public String getCorpId() {
		return this.corpId;
	}

	@Override
	public String getCorpSecret() {
		return this.corpSecret;
	}

	@Override
	public String getAgentId() {
		return this.agentId;
	}

	@Override
	public String getToken() {
		return this.token;
	}

	@Override
	public String getAesKey() {
		return this.aesKey;
	}

	@Override
	public long getExpiresTime() {
		try (Jedis jedis = this.jedisPool.getResource()) {
			String expiresTimeStr = jedis.get(ACCESS_TOKEN_EXPIRES_TIME_KEY);

			if (expiresTimeStr != null) {
				Long expiresTime = Long.parseLong(expiresTimeStr);
				return expiresTime;
			}

			return 0L;

		}
	}

	@Override
	public String getOauth2redirectUri() {
		return this.oauth2redirectUri;
	}

	@Override
	public String getHttpProxyHost() {
		return this.httpProxyHost;
	}

	@Override
	public int getHttpProxyPort() {
		return this.httpProxyPort;
	}

	@Override
	public String getHttpProxyUsername() {
		return this.httpProxyUsername;
	}

	@Override
	public String getHttpProxyPassword() {
		return this.httpProxyPassword;
	}

	@Override
	public File getTmpDirFile() {
		return this.tmpDirFile;
	}

	@Override
	public ApacheHttpClientBuilder getApacheHttpClientBuilder() {
		return this.apacheHttpClientBuilder;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public void setCorpSecret(String corpSecret) {
		this.corpSecret = corpSecret;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setAesKey(String aesKey) {
		this.aesKey = aesKey;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	// ============================ Setters below

	public void setOauth2redirectUri(String oauth2redirectUri) {
		this.oauth2redirectUri = oauth2redirectUri;
	}

	public void setHttpProxyHost(String httpProxyHost) {
		this.httpProxyHost = httpProxyHost;
	}

	public void setHttpProxyPort(int httpProxyPort) {
		this.httpProxyPort = httpProxyPort;
	}

	public void setHttpProxyUsername(String httpProxyUsername) {
		this.httpProxyUsername = httpProxyUsername;
	}

	public void setHttpProxyPassword(String httpProxyPassword) {
		this.httpProxyPassword = httpProxyPassword;
	}

	public void setTmpDirFile(File tmpDirFile) {
		this.tmpDirFile = tmpDirFile;
	}

	public void setApacheHttpClientBuilder(ApacheHttpClientBuilder apacheHttpClientBuilder) {
		this.apacheHttpClientBuilder = apacheHttpClientBuilder;
	}

}
