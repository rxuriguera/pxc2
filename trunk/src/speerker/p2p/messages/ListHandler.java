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

import peerbase.Node;
import peerbase.PeerConnection;
import speerker.App;

/**
 * This class handles SpeerkerMessage.LIST messages. The purpose of this handler
 * is to reply with the list of peers that the receiver knows.
 */
public class ListHandler extends SpeerkerMessageHandler {
	private Node peer;

	public ListHandler(Node peer) {
		this.peer = peer;
	}

	@Override
	public void handleMessage(PeerConnection peerconn, SpeerkerMessage msg) {
		// Send number of peers
		peerconn.sendData(new SpeerkerMessage(SpeerkerMessage.REPLY, peer
				.getNumberOfPeers()));

		App.logger.info(this.peer.getInfo().toString()
				+ ": Handling LIST request. Sending info about "
				+ peer.getPeerKeys().size() + " peers.");

		// Send peers' information
		for (String pid : peer.getPeerKeys()) {
			peerconn.sendData(new SpeerkerMessage(SpeerkerMessage.REPLY, peer
					.getPeer(pid)));
		}
	}
}
