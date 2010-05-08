/*
 * Speerker 
 * Copyright (C) 2010  Jordi Bartrolí, Hector Mañosas i Ramon Xuriguera
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
import speerker.p2p.FileGetter;
import speerker.p2p.FilePart;
import speerker.p2p.SpeerkerNode;

/**
 * This is a handler for SpeerkerMessage.PARTRSP messages. This handler defines
 * how a node must behave when it receives a file part
 * 
 */
public class FilePartResponseHandler extends SpeerkerMessageHandler {
	private SpeerkerNode peer;

	public FilePartResponseHandler(SpeerkerNode peer) {
		this.peer = peer;
	}

	@Override
	public void handleMessage(PeerConnection peerconn, SpeerkerMessage msg) {
		FilePart part = null;
		try {
			part = (FilePart) msg.getMsgContent();
		} catch (ClassCastException e) {
			this.sendErrorMessage(peerconn, msg.getMsgType(),
					"Incorrect arguments.", e);
			return;
		} catch (ClassNotFoundException e) {
			this.sendErrorMessage(peerconn, msg.getMsgType(),
					"Incorrect arguments.", e);
			return;
		}

		String transferID = part.getTransferID();
		FileGetter transfer = this.peer.getFileTransfer(transferID);

		App.logger.info(this.peer.getInfo().toString()
				+ ": Handling PARTRSP request. File transfer: " + transferID);

		if (transfer == null) {
			App.logger.info(this.peer.getInfo().toString()
					+ ": Transfer does not exist anymore");
			return;
		}

		transfer.addReceivedFilePart(part);

		App.logger.debug(this.peer.getInfo().toString() + ": Filepart "
				+ part.getPart() + " added for transfer " + transferID);
	}

}
