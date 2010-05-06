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

package speerker.p2p;

import java.io.Serializable;
import java.util.List;

import peerbase.PeerInfo;
import speerker.App;
import speerker.p2p.messages.SpeerkerMessage;

/**
 * This class will process a query made by some peer in a new thread.
 */
public class QueryProcessor extends Thread {
	private SpeerkerNode peer;
	private SearchQuery query;

	public QueryProcessor(SpeerkerNode peer, SearchQuery query) {
		this.peer = peer;
		this.query = query;
	}

	@Override
	public void run() {
		// Search all this node's list of known files that match the query
		List<SearchResult> results = this.peer.search(query);

		if (results.size() > 0) {
			SpeerkerMessage responseMessage = new SpeerkerMessage(
					SpeerkerMessage.RESPONSE, (Serializable) results);

			// Get the peer that made the search in the first place
			PeerInfo searcher = this.query.getPeerInfo();
			this.peer.connectAndSend(searcher, responseMessage, false);

			App.logger.info(this.peer.getInfo().toString()
					+ ": Processing query. Sending search results to: "
					+ query.peerInfo.toString() + ". Total files found: "
					+ results.size());
		}

		// Propagate query to neighbors, if there is still time-to-live for it
		if (this.query.ttl > 0) {
			this.query.ttl--;
			SpeerkerMessage newMessage = new SpeerkerMessage(
					SpeerkerMessage.QUERY, this.query);
			for (String nextpid : peer.getPeerKeys())
				if (!nextpid.equals(this.query.getPeerInfo().getId()))
					peer.sendToPeer(nextpid, newMessage, false);
		}
	}
}
