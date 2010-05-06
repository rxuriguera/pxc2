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

import peerbase.PeerConnection;
import speerker.App;
import speerker.p2p.QueryProcessor;
import speerker.p2p.SearchQuery;
import speerker.p2p.SpeerkerNode;

/**
 * Handles SpeerkerMessage.QUERY messages, which will consist of a search.
 */
public class QueryHandler extends SpeerkerMessageHandler {

	private SpeerkerNode peer;

	public QueryHandler(SpeerkerNode peer) {
		this.peer = peer;
	}

	@Override
	public void handleMessage(PeerConnection peerconn, SpeerkerMessage msg) {
		SearchQuery query = null;

		// Get the message. It should be a SearchQuery object
		try {
			query = (SearchQuery) msg.getMsgContent();
		} catch (ClassCastException e) {
			this.sendErrorMessage(peerconn, msg.getMsgType(),
					"Incorrect arguments.", e);
			return;
		} catch (ClassNotFoundException e) {
			this.sendErrorMessage(peerconn, msg.getMsgType(),
					"Incorrect arguments.", e);
			return;
		}

		App.logger
				.info(this.peer.getInfo().toString()
						+ ": Handling QUERY message. Sent query acknowledgement for search "
						+ query.getQuery());
		peerconn.sendData(new SpeerkerMessage(SpeerkerMessage.REPLY,
				"Query: ACK"));

		// Perform the search in a separate thread
		QueryProcessor qp = new QueryProcessor(peer, query);
		qp.setName("QueryProcessor");
		qp.start();
	}

}
