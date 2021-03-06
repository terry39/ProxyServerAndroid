package org.connectbot.bean;

import android.content.ContentValues;
import android.net.Uri;

import java.nio.charset.Charset;

/**
 * @author zyq 16-3-5
 */
public class HostBean extends AbstractBean {

	public static final String BEAN_NAME = "host";
	private static final int DEFAULT_PORT = 22;
	public static final int PUBKEYID_NEVER = -2;
	public static final int PUBKEYID_ANY = -1;

	/* Database fields */
	private long id = 1;
	private String nickname = null;
	private String username = null;
	private String hostname = null;
	private int port = 22;
	private String protocol = "ssh";
	private long lastConnect = -1;
	private String color;
	private boolean useKeys = true;
	private String useAuthAgent = "no";
	private String postLogin = null;
	private long pubkeyId = 1;
	private boolean wantSession = true;
	private String delKey = "del";
	private boolean compression = false;
	private String encoding = Charset.defaultCharset().name();
	private boolean stayConnected = false;
	private boolean quickDisconnect = false;

	public HostBean() {
	}

	;

	public HostBean(String nickname, String protocol, String username, String hostname, int port) {
		this.nickname = nickname;
		this.protocol = protocol;
		this.username = username;
		this.hostname = hostname;
		this.port = port;
	}


	public String getDescription() {
		String description = String.format("%s@%s", username, hostname);

		if (port != 22)
			description += String.format(":%d", port);

		return description;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getNickname() {
		return nickname;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getHostname() {
		return hostname;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getPort() {
		return port;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setLastConnect(long lastConnect) {
		this.lastConnect = lastConnect;
	}

	public long getLastConnect() {
		return lastConnect;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getColor() {
		return color;
	}

	public void setUseKeys(boolean useKeys) {
		this.useKeys = useKeys;
	}

	public boolean getUseKeys() {
		return useKeys;
	}

	public void setUseAuthAgent(String useAuthAgent) {
		this.useAuthAgent = useAuthAgent;
	}

	public String getUseAuthAgent() {
		return useAuthAgent;
	}

	public void setPostLogin(String postLogin) {
		this.postLogin = postLogin;
	}

	public String getPostLogin() {
		return postLogin;
	}

	public void setPubkeyId(long pubkeyId) {
		this.pubkeyId = pubkeyId;
	}

	public long getPubkeyId() {
		return pubkeyId;
	}

	public void setWantSession(boolean wantSession) {
		this.wantSession = wantSession;
	}

	public boolean getWantSession() {
		return wantSession;
	}

	public void setDelKey(String delKey) {
		this.delKey = delKey;
	}

	public String getDelKey() {
		return delKey;
	}

	public void setCompression(boolean compression) {
		this.compression = compression;
	}

	public boolean getCompression() {
		return compression;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getEncoding() {
		return this.encoding;
	}

	public void setStayConnected(boolean stayConnected) {
		this.stayConnected = stayConnected;
	}

	public boolean getStayConnected() {
		return stayConnected;
	}

	public void setQuickDisconnect(boolean quickDisconnect) {
		this.quickDisconnect = quickDisconnect;
	}

	public boolean getQuickDisconnect() {
		return quickDisconnect;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof HostBean))
			return false;

		HostBean host = (HostBean) o;

		if (id != -1 && host.getId() != -1)
			return host.getId() == id;

		if (nickname == null) {
			if (host.getNickname() != null)
				return false;
		} else if (!nickname.equals(host.getNickname()))
			return false;

		if (protocol == null) {
			if (host.getProtocol() != null)
				return false;
		} else if (!protocol.equals(host.getProtocol()))
			return false;

		if (username == null) {
			if (host.getUsername() != null)
				return false;
		} else if (!username.equals(host.getUsername()))
			return false;

		if (hostname == null) {
			if (host.getHostname() != null)
				return false;
		} else if (!hostname.equals(host.getHostname()))
			return false;

		if (port != host.getPort())
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int hash = 7;

		if (id != -1)
			return (int) id;

		hash = 31 * hash + (null == nickname ? 0 : nickname.hashCode());
		hash = 31 * hash + (null == protocol ? 0 : protocol.hashCode());
		hash = 31 * hash + (null == username ? 0 : username.hashCode());
		hash = 31 * hash + (null == hostname ? 0 : hostname.hashCode());
		hash = 31 * hash + port;

		return hash;
	}

	/**
	 * @return URI identifying this HostBean
	 */
	public Uri getUri() {
		StringBuilder sb = new StringBuilder();
		sb.append(protocol)
				.append("://");

		if (username != null)
			sb.append(Uri.encode(username))
					.append('@');

		sb.append(Uri.encode(hostname))
				.append(':')
				.append(port)
				.append("/#")
				.append(nickname);
		return Uri.parse(sb.toString());
	}


	/**
	 * Generates a "pretty" string to be used in the quick-connect host edit view.
	 */
	@Override
	public String toString() {
		if (protocol == null)
			return "";


		if (username == null || hostname == null ||
				username.equals("") || hostname.equals(""))
			return "";

		if (port == DEFAULT_PORT)
			return username + "@" + hostname;
		else
			return username + "@" + hostname + ":" + port;

	}

	@Override
	public ContentValues getValues() {
		return null;
	}

	@Override
	public String getBeanName() {
		return BEAN_NAME;
	}
}
