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

import peerbase.LoggerUtil;
import peerbase.Node;
import peerbase.PeerConnection;
import peerbase.PeerInfo;
import speerker.App;
import speerker.util.Serializer;

/**
 * This class handles messages oft the type SpeerkerMessage.JOIN. This kind of
 * messages are sent to establish a connection between two parts.
 */
public class JoinHandler extends SpeerkerMessageHandler {
	private Node peer;

	public JoinHandler(Node peer) {
		this.peer = peer;
	}

	/**
	 * @param peerconn
	 *            Peer connection where the errors and/or replies will be sent.
	 * @param msg
	 *            A PeerMessage with the message type and data. Message data
	 *            must be a serialized PeerInfo object.
	 */
	public void handleMessage(PeerConnection peerconn, SpeerkerMessage msg) {
		if (peer.maxPeersReached()) {
			LoggerUtil.getLogger().fine(
					"maxpeers reached " + peer.getMaxPeers());
			peerconn.sendData(new SpeerkerMessage(SpeerkerMessage.ERROR,
					"Join: " + "too many peers"));
			return;
		}

		// Get the message. It should be a PeerInfo object
		PeerInfo info = null;
		try {
			info = (PeerInfo) Serializer.deserialize(msg.getMsgDataBytes());
		} catch (ClassNotFoundException e) {
			App.logger.error("Could not unserialize object: ", e);
			peerconn.sendData(new SpeerkerMessage(SpeerkerMessage.ERROR,
					"Join: incorrect arguments"));
			return;
		}

		App.logger.info(this.peer.getInfo().toString()
				+ ": Handling JOIN request. Attempting to add peer "
				+ info.toString() + " to known-peers list");

		if (peer.getPeer(info.getId()) != null)
			peerconn.sendData(new SpeerkerMessage(SpeerkerMessage.ERROR,
					"Join: " + "peer already inserted"));
		else if (info.getId().equals(peer.getId()))
			peerconn.sendData(new SpeerkerMessage(SpeerkerMessage.ERROR,
					"Join: " + "attempt to insert self"));
		else {
			peer.addPeer(info);
			peerconn.sendData(new SpeerkerMessage(SpeerkerMessage.REPLY,
					"Join: " + "peer added: " + info.getId()));
		}
	}
}