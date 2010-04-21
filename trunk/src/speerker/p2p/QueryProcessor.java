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

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import peerbase.PeerMessage;
import speerker.p2p.messages.SpeerkerMessage;
import speerker.util.Serializer;

/**
 * 
 *
 */
public class QueryProcessor extends Thread {
	private SpeerkerNode peer;
	private SearchQuery query;

	public QueryProcessor(SpeerkerNode peer, SearchQuery query) {
		this.peer = peer;
		this.query = query;
	}

	public void run() {
		// search through this node's list of files for a filename
		// containing the key

		/*
		 * for (String filename : this.peer.files.keySet()) { if
		 * (filename.toUpperCase().indexOf(key.toUpperCase()) >= 0) { String
		 * fpid = this.peer.files.get(filename); String[] data =
		 * ret_pid.split(":"); String host = data[0]; int port =
		 * Integer.parseInt(data[1]);
		 */
		
		List<SearchResult> response = new LinkedList<SearchResult>();

		try {
			@SuppressWarnings("unused")
			PeerMessage responseMessage = new PeerMessage(
					SpeerkerMessage.QRESPONSE, Serializer
							.serialize((Serializable) response));
		} catch (IOException e) {
			// TODO: Process exception
		}
		
		/*
		 * this.peer.connectAndSend(new PeerInfo(ret_pid, host, port),
		 * SpeerkerNode.QRESPONSE, filename + " " + fpid, true);
		 * LoggerUtil.getLogger().fine( "Sent QRESP " + new PeerInfo(ret_pid,
		 * host, port) + " " + filename + " " + fpid); return; } }
		 */

		// Will only reach here if key not found...
		// in which case propagate query to neighbors, if there is still
		// time-to-live for the query
		if (this.query.ttl > 0) {
			this.query.ttl--;
			try {
				PeerMessage newMessage = new PeerMessage(SpeerkerMessage.QUERY,
						Serializer.serialize(this.query));
				for (String nextpid : peer.getPeerKeys())
					peer.sendToPeer(nextpid, newMessage, true);
			} catch (IOException e) {
				return;
			}
		}
	}
}
