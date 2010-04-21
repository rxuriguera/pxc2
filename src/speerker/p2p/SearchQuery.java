package speerker.p2p;

import java.io.Serializable;

public class SearchQuery implements Serializable {

	private static final long serialVersionUID = -6575177247988240691L;

	protected String peerId = "";
	protected String query = "";
	protected Integer ttl = 4;

	public String getPeerId() {
		return peerId;
	}

	public void setPeerId(String peerId) {
		this.peerId = peerId;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public Integer getTtl() {
		return ttl;
	}

	public void setTtl(Integer ttl) {
		this.ttl = ttl;
	}

}
