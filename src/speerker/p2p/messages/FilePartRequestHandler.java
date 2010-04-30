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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import peerbase.PeerConnection;
import speerker.App;
import speerker.p2p.FilePart;
import speerker.p2p.SpeerkerNode;

public class FilePartRequestHandler extends SpeerkerMessageHandler {
	private SpeerkerNode peer;

	public FilePartRequestHandler(SpeerkerNode peer) {
		this.peer = peer;
	}

	public void handleMessage(PeerConnection peerconn, SpeerkerMessage msg) {
		// Get FilePart
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

		String path = this.peer.getFilePath(part.getFileHash());
		if (path == null) {
			App.logger.error("File does not exist in this peer");
			return;
		}

		// Load file part
		byte[] filePartData = null;
		try {
			FileInputStream infile = new FileInputStream(path);
			int fileLength = infile.available();
			int len = (int) Math.min(fileLength, part.getPartSize());
			int offset = (int) ((int) part.getPart() * part.getPartSize());
			int numRead = 0, totalRead = 0;
			filePartData = new byte[len];
			while ((totalRead < filePartData.length)
					&& ((numRead = infile.read(filePartData, offset,
							filePartData.length - totalRead)) >= 0)) {
				offset += numRead;
				totalRead += numRead;
			}
			infile.close();
		} catch (FileNotFoundException e) {
			App.logger.error(
					"Error while loading file part: Could not find file", e);
			return;
		} catch (IOException e) {
			App.logger.error("Error while loading file part.", e);
			return;
		}

		// Send a SpeeerkerMessage.PARTRSP with the file part.
		part.setData(filePartData);
		SpeerkerMessage message = new SpeerkerMessage(SpeerkerMessage.PARTRSP,
				part);
		peerconn.sendData(message);
	}
}
