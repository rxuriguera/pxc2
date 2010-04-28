/*
 * Speerker 
 * Copyright (C) 2010  Jordi Bartrolí, Hector Mañosas i Ramon Xuriguera
 * Copyright 2007 by Nadeem Abdul Hamid, Patrick Valencia
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

package speerker.p2p.messages;

import java.util.Iterator;
import java.util.List;
import java.util.Queue;

import peerbase.PeerConnection;
import speerker.p2p.SearchResult;
import speerker.p2p.SpeerkerNode;

public class ResponseHandler extends SpeerkerMessageHandler {
	private SpeerkerNode peer;

	public ResponseHandler(SpeerkerNode peer) {
		this.peer = peer;
	}

	@SuppressWarnings("unchecked")
	public void handleMessage(PeerConnection peerconn, SpeerkerMessage msg) {
		List<SearchResult> searchResults;
		try {
			searchResults = (List<SearchResult>) msg.getMsgContent();
		} catch (ClassCastException e) {
			this.sendErrorMessage(peerconn, SpeerkerMessage.RESPONSE,
					"Incorrect arguments.", e);
			return;
		} catch (ClassNotFoundException e) {
			this.sendErrorMessage(peerconn, SpeerkerMessage.RESPONSE,
					"Incorrect arguments.", e);
			return;
		}

		// Add the search results to the peer's queue.
		Iterator<SearchResult> iterator = searchResults.iterator();
		Queue<SearchResult> queue = this.peer.getSearchQueue();
		while (iterator.hasNext()) {
			queue.add(iterator.next());
		}
	}
}