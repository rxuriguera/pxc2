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

import peerbase.HandlerInterface;
import peerbase.PeerConnection;
import peerbase.PeerMessage;
import speerker.App;

public abstract class SpeerkerMessageHandler implements HandlerInterface {

	/**
	 * PeerConnection's rcvData method creates a new PeerMessage with the
	 * message. In order to use a SpeerkerMessage, we need a wrapper that does
	 * the transformation. This is the goal of this method.
	 */
	public void handleMessage(PeerConnection peerconn, PeerMessage msg) {
		SpeerkerMessage smsg = SpeerkerMessage.valueOf(msg);
		this.handleMessage(peerconn, smsg);
	}

	public abstract void handleMessage(PeerConnection peerconn,
			SpeerkerMessage msg);

	public void sendErrorMessage(PeerConnection peerconn, String messageType,
			String message, Exception e) {
		App.logger.error(messageType + ": " + message, e);
		peerconn.sendData(new SpeerkerMessage(SpeerkerMessage.ERROR,
				messageType + ": " + message));
	}
}
