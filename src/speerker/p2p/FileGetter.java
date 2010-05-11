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

package speerker.p2p;

import java.io.File;
import java.util.List;

import peerbase.PeerInfo;
import speerker.App;
import speerker.Song;
import speerker.p2p.messages.SpeerkerMessage;

public class FileGetter extends Thread {
	protected String transferID;
	protected SpeerkerNode peer;
	protected SearchResult result;
	protected FileWritter fileWritter;

	public FileGetter(String transferID, SpeerkerNode peer, SearchResult result) {
		this.transferID = transferID;
		this.peer = peer;
		this.result = result;
		this.fileWritter = null;
	}

	public String getTransferID() {
		return transferID;
	}

	public void setTransferID(String transferID) {
		this.transferID = transferID;
	}

	public void addReceivedFilePart(FilePart filePart) {
		if (this.fileWritter != null)
			this.fileWritter.addReceivedFilePart(filePart);
	}

	public void run() {
		if (this.transferID == null)
			return;

		App.logger.info(peer.getInfo().toString()
				+ ": Starting new file transfer for: " + this.transferID);

		// Decide how to divide the file
		String filename = result.song.getHash();

		// Directory
		File dir = new File(App.getProperty("DestFilePath"));
		dir.mkdirs();

		// Check if file exists
		File file = new File(dir, filename);
		if (file.exists()) {
			App.logger
					.info(peer.getInfo().toString() + ": File already exists");
			return;
		}

		Song song = this.result.getSong();
		List<PeerInfo> peers = this.result.getPeers();

		Long packetSize = Long.valueOf(App.getProperty("FilePacketLength"));
		Long fileSize = song.getSize();

		Integer fileParts = (int) Math.ceil(fileSize / packetSize);

		// Start the file writter, so we can start receiving even before
		// finishing
		// to send part requests
		this.fileWritter = new FileWritter(this.peer, file, fileParts);
		this.setName("Writter-" + this.transferID.substring(0, 6));
		this.fileWritter.start();

		SpeerkerMessage message;
		FilePart filePart;
		Integer currentPeer = 0;
		PeerInfo currentPeerInfo;

		App.logger.info(peer.getInfo().toString() + ": Sending " + fileParts
				+ " file-part requests to peers.");
		try {
			// Send part requests to the peers that have the file
			for (Integer part = 0; part < fileParts; part++) {
				currentPeerInfo = this.result.getPeers().get(currentPeer);
				filePart = new FilePart(song.getHash(), this.peer.getInfo(),
						part, packetSize);
				message = new SpeerkerMessage(SpeerkerMessage.PARTREQ, filePart);
				this.peer.connectAndSend(currentPeerInfo, message, false);
				App.logger.debug(peer.getInfo().toString()
						+ ": Sent request for part " + part + " to "
						+ currentPeerInfo.getId());
				currentPeer = (currentPeer + 1) % peers.size();

				// Thread.sleep(200);
			}
		} catch (Exception e) {
			App.logger.error(peer.getInfo().toString()
					+ ": Error sending part requests: " + e);
		}

		try {
			App.logger.info(peer.getInfo().toString()
					+ "Finished sending part requests to peers. "
					+ "Waiting for file writter to finish");
			this.fileWritter.join();
		} catch (InterruptedException e) {
			App.logger.error(peer.getInfo().toString()
					+ ": FileGetter interrupted while waiting for "
					+ "FileWritter to finish: " + e);
		}
	}

}
