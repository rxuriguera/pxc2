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
import peerbase.PeerInfo;

/**
 * This class handles SpeerkerMessage.QUIT messages. It expects the content of
 * the message to be a PeerInfo instance. If the receiver has the peer described
 * by the PeerInfo in its list, removes it.
 */
public class QuitHandler extends SpeerkerMessageHandler {
	private Node peer;

	public QuitHandler(Node peer) {
		this.peer = peer;
	}

	@Override
	public void handleMessage(PeerConnection peerconn, SpeerkerMessage msg) {
		// Get the message. It should be a PeerInfo object
		PeerInfo info = null;
		try {
			info = (PeerInfo) msg.getMsgContent();
		} catch (ClassCastException e) {
			this.sendErrorMessage(peerconn, msg.getMsgType(),
					"Incorrect arguments.", e);
			return;
		} catch (ClassNotFoundException e) {
			this.sendErrorMessage(peerconn, msg.getMsgType(),
					"Incorrect arguments.", e);
			return;
		}

		if (peer.getPeer(info.getId()) == null) {
			peerconn.sendData(new SpeerkerMessage(SpeerkerMessage.ERROR,
					"Quit: peer not found: " + info.toString()));
		} else {
			peer.removePeer(info.getId());
			peerconn.sendData(new SpeerkerMessage(SpeerkerMessage.REPLY,
					"Quit: peer removed: " + info.toString()));
		}
	}
}