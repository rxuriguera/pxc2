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

import java.io.IOException;

import peerbase.HandlerInterface;
import peerbase.PeerConnection;
import peerbase.PeerMessage;
import speerker.App;
import speerker.p2p.SearchQuery;
import speerker.p2p.SpeerkerNode;
import speerker.p2p.QueryProcessor;

import speerker.util.Serializer;

/**
 * Handles query messages
 * 
 */
public class QueryHandler implements HandlerInterface {

	private SpeerkerNode peer;

	public QueryHandler(SpeerkerNode peer) {
		this.peer = peer;
	}

	@Override
	public void handleMessage(PeerConnection peerconn, PeerMessage msg) {
		Boolean error = false;
		SearchQuery query = new SearchQuery();

		// Get the message. It should be a SearchQuery object
		try {
			query = (SearchQuery) Serializer.deserialize(msg.getMsgDataBytes());
		} catch (IOException e) {
			App.logger.error("Could not unserialize object: ", e);
			error = true;
		} catch (ClassNotFoundException e) {
			App.logger.error("Could not unserialize object: ", e);
			error = true;
		}

		if (error) {
			peerconn.sendData(new PeerMessage(SpeerkerMessage.ERROR,
					"Query: incorrect arguments"));
			return;
		}

		peerconn.sendData(new PeerMessage(SpeerkerMessage.REPLY, "Query: ACK"));

		/*
		 * After acknowledging the query, this connection will be closed. A
		 * separate thread will be started to actually perform the task of the
		 * query...
		 */
		QueryProcessor qp = new QueryProcessor(peer, query);
		qp.start();
	}

}

/* msg syntax: QUER return-pid ttl query */
