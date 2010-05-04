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

/**
 * This class handles messages oft the type SpeerkerMessage.INFO. The purpose of
 * this handler is to reply with the peer ID.
 */
public class InfoHandler extends SpeerkerMessageHandler {
	private Node peer;

	/**
	 * Creates a new handler for INFO messages.
	 * 
	 * @param peer
	 *            a Node instance representing the peer that will handle the
	 *            messages
	 */
	public InfoHandler(Node peer) {
		this.peer = peer;
	}

	@Override
	public void handleMessage(PeerConnection peerconn, SpeerkerMessage msg) {
		peerconn.sendData(new SpeerkerMessage(SpeerkerMessage.REPLY, peer
				.getInfo()));
	}
}