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
import java.util.Random;

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

		App.logger.info(this.peer.getInfo().toString()
				+ ": Handling PARTREQ request. Sending file part: "
				+ part.getPart() + " to " + part.getRequester().toString());

		/*
		 * // Test timeouts if(part.getPart()%2==0){
		 * App.logger.debug("Not sending requested part "+part.getPart());
		 * return; } ////
		 */

		String path = this.peer.getFilePath(part.getFileHash());
		if (path == null) {
			App.logger.error(this.peer.getInfo().toString()
					+ ": File does not exist in this peer");
			return;
		}

		// Load file part
		byte[] filePartData = null;
		try {
			FileInputStream infile = new FileInputStream(path);
			int partSize = part.getPartSize().intValue();
			int offset = (int) ((int) part.getPart() * partSize);
			int available = infile.available() - offset;

			// Decide how many bytes have to be read
			int readLen = available < partSize ? available : partSize;
			filePartData = new byte[readLen];

			// Skip the first offset bytes from the input stream
			infile.skip(offset);

			int numRead = 0, totalRead = 0, abOffset = 0;
			while (totalRead < readLen && numRead >= 0) {
				numRead = infile.read(filePartData, abOffset, readLen
						- totalRead);

				abOffset += numRead;
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

		Random generator = new Random();
		try {
			Integer wait = generator.nextInt(8000);
			App.logger.debug(this.peer.getInfo().toString()
					+ ": Waiting random time before sending part: " + wait
					+ " milliseconds");
			Thread.sleep(wait);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Send part to peer
		this.peer.connectAndSend(part.getRequester(), message, false);

		App.logger.info(this.peer.getInfo().toString()
				+ ": Finished sending part: " + part.getPart() + " to "
				+ part.getRequester().toString());

	}
}
