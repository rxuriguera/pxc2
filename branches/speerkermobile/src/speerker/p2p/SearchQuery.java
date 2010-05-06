/*
 * Speerker 
 * Copyright (C) 2010  Jordi Bartrolí, Hector Mañosas i Ramon Xuriguera
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package speerker.p2p;

import java.io.Serializable;

import peerbase.PeerInfo;

public class SearchQuery implements Serializable {

	private static final long serialVersionUID = -6575177247988240691L;
	protected String queryID;
	protected PeerInfo peerInfo;
	protected String query = "";
	protected Integer ttl = 4;

	public SearchQuery() {
	}

	public SearchQuery(PeerInfo peerInfo, String queryID, String query,
			Integer ttl) {
		this.queryID = queryID;
		this.peerInfo = peerInfo;
		this.query = this.buildRegex(query);
		this.ttl = ttl;
	}

	public String getPeerId() {
		return this.peerInfo.getId();
	}

	public PeerInfo getPeerInfo() {
		return peerInfo;
	}

	public void setPeerInfo(PeerInfo peerInfo) {
		this.peerInfo = peerInfo;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = this.buildRegex(query);
	}

	public Integer getTtl() {
		return ttl;
	}

	public void setTtl(Integer ttl) {
		this.ttl = ttl;
	}

	public String buildRegex(String query) {
		query = query.toLowerCase();
		query = ".*" + query + ".*";
		return query;
	}

}
