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
import java.util.LinkedList;
import java.util.List;

import peerbase.PeerInfo;

import speerker.Song;

public class SearchResult implements Serializable {

	private static final long serialVersionUID = -7292223140154581486L;

	protected String queryID;
	protected Song song;
	protected List<PeerInfo> peers;

	public SearchResult() {

	}

	public SearchResult(String queryID, Song song, PeerInfo peer) {
		this(queryID, song);
		this.peers.add(peer);
	}

	public SearchResult(String queryID, Song song) {
		this.queryID = queryID;
		this.song = song;
		this.peers = new LinkedList<PeerInfo>();
	}

	public String getQueryID() {
		return queryID;
	}

	public void setQueryID(String queryID) {
		this.queryID = queryID;
	}

	public Song getSong() {
		return song;
	}

	public void setSong(Song song) {
		this.song = song;
	}

	public List<PeerInfo> getPeers() {
		return peers;
	}

	public void setPeers(List<PeerInfo> peers) {
		this.peers = peers;
	}

	public void addPeer(PeerInfo peer) {
		this.peers.add(peer);
	}

	public void removePeer(PeerInfo peer) {
		this.peers.remove(peer);
	}

}
