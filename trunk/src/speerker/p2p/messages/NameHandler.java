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
 * Handler for SpeerkerMessage.NAME messages. The main goal is to return the id
 * of the peer that receives the message.
 */
public class NameHandler extends SpeerkerMessageHandler {
	private Node peer;

	public NameHandler(Node peer) {
		this.peer = peer;
	}

	public void handleMessage(PeerConnection peerconn, SpeerkerMessage msg) {
		App.logger.info(this.peer.getInfo().toString()
				+ ": Handling NAME request. Sending this peer's ID.");

		peerconn.sendData(new SpeerkerMessage(SpeerkerMessage.REPLY, peer
				.getId()));
	}
}
