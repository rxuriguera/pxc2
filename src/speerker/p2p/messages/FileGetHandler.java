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

import java.io.FileInputStream;
import java.io.IOException;

import peerbase.PeerConnection;
import peerbase.PeerMessage;
import speerker.App;
import speerker.Song;
import speerker.p2p.SpeerkerNode;

public class FileGetHandler extends SpeerkerMessageHandler {
	private SpeerkerNode peer;

	public FileGetHandler(SpeerkerNode peer) {
		this.peer = peer;
	}

	public void handleMessage(PeerConnection peerconn, SpeerkerMessage msg) {

		// Get Song
		Song song = null;
		try {
			song = (Song) msg.getMsgContent();
		} catch (ClassCastException e) {
			this.sendErrorMessage(peerconn, msg.getMsgType(),
					"Incorrect arguments.", e);
			return;
		} catch (ClassNotFoundException e) {
			this.sendErrorMessage(peerconn, msg.getMsgType(),
					"Incorrect arguments.", e);
			return;
		}

		String path = this.peer.getFilePath(song.getHash());
		if (path == null) {
			App.logger.warn("File with hash " + song.getHash()
					+ " not available from this peer.");

			peerconn.sendData(new PeerMessage(SpeerkerMessage.ERROR,
					SpeerkerMessage.FILEGET + ": file not found "
							+ song.getHash()));
		}

		byte[] filedata = null;
		try {
			FileInputStream infile = new FileInputStream(path);
			int len = infile.available();
			filedata = new byte[len];
			infile.read(filedata);
			infile.close();
		} catch (IOException e) {
			App.logger.info("FileGet: Error reading file: " + e);
			peerconn.sendData(new PeerMessage(SpeerkerMessage.ERROR, "Fget: "
					+ "error reading file "));
			return;
		}

		peerconn.sendData(new PeerMessage(SpeerkerMessage.REPLY, filedata));
	}
}
