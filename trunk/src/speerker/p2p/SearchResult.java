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

import speerker.Song;

public class SearchResult implements Serializable {

	private static final long serialVersionUID = -7292223140154581486L;

	protected Song song;
	protected PeerInfo peer;

	public SearchResult() {

	}

	public SearchResult(Song song, PeerInfo peer) {
		this.song = song;
		this.peer = peer;
	}

	public Song getSong() {
		return song;
	}

	public void setSong(Song song) {
		this.song = song;
	}

	public PeerInfo getPeer() {
		return peer;
	}

	public void setPeer(PeerInfo peer) {
		this.peer = peer;
	}

}
