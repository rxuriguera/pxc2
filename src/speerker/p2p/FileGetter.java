package speerker.p2p;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import peerbase.PeerInfo;
import speerker.App;
import speerker.Song;
import speerker.p2p.messages.SpeerkerMessage;

public class FileGetter extends Thread {
	protected String transferID;
	protected SpeerkerNode peer;
	protected SearchResult result;
	protected List<FilePart> partsBuffer;
	protected Integer expectedPart = 0;
	protected Long periodMillis;
	protected Integer nPeriods;

	public FileGetter(String transferID, SpeerkerNode peer, SearchResult result) {
		this.transferID = transferID;
		this.peer = peer;
		this.result = result;
		this.expectedPart = 0;
		this.partsBuffer = new LinkedList<FilePart>();

		// Waiting cycles
		this.periodMillis = Long.valueOf(App.getProperty("PeriodMillis"));
		this.nPeriods = Integer.valueOf(App.getProperty("WaitingPeriods"));
	}

	public String getTransferID() {
		return transferID;
	}

	public void setTransferID(String transferID) {
		this.transferID = transferID;
	}

	public void addReceivedFilePart(FilePart filePart) {
		this.partsBuffer.add(filePart);
	}

	public void run() {
		if (this.transferID == null)
			return;

		App.logger.info(peer.getInfo().toString()
				+ ": Starting new file transfer for: " + this.transferID);

		// Decide how to divide the file
		String filename = App.getProperty("DestFilePath") + "/"
				+ result.song.getHash();

		// Check if file exists
		File file = new File(filename);
		if (file.exists()) {
			App.logger.info("File already exists");
			return;
		}

		Song song = this.result.getSong();
		List<PeerInfo> peers = this.result.getPeers();

		Long packetSize = Long.valueOf(App.getProperty("FilePacketLength"));
		Long fileSize = song.getSize();

		Integer fileParts = (int) Math.ceil(fileSize / packetSize);

		SpeerkerMessage message;
		FilePart filePart;
		Integer currentPeer = 0;
		PeerInfo currentPeerInfo;

		App.logger.info(peer.getInfo().toString() + ": Sending " + fileParts
				+ "file-part requests to peers.");
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
			App.logger.info(peer.getInfo().toString()
					+ ": Sent part requests to peers");
		} catch (Exception e) {
			App.logger.error(peer.getInfo().toString()
					+ ": Error sending part requests: " + e);
		}

		// Prepare streams
		FileOutputStream outfile = null;
		try {
			outfile = new FileOutputStream(file, true);
		} catch (FileNotFoundException e) {
			App.logger.error("File not found", e);
		}
		App.logger
				.info(peer.getInfo().toString() + ": Prepared output streams");
		BufferedOutputStream buf = new BufferedOutputStream(outfile);

		Integer initiallyExpected, partPeriods;
		Integer firstPartPeriods = this.nPeriods;
		// Wait for the parts to arrive
		while (this.expectedPart < fileParts && firstPartPeriods > 0) {
			partPeriods = this.nPeriods;
			initiallyExpected = this.expectedPart;
			// Timeout for each part
			while (partPeriods > 0) {
				partPeriods--;
				try {
					this.checkPartsBuffer(buf);
					Thread.sleep(this.periodMillis);
				} catch (InterruptedException e) {
					App.logger.warn("Exception while sleeping", e);
					continue;
				} catch (IOException e) {
					App.logger.warn("Exception while writing part to file", e);
					continue;
				}
			}

			// Spend as much time as possible waiting for the first part
			if (this.expectedPart.equals(0)) {
				App.logger.debug(peer.getInfo().toString()
						+ ": Haven't received first part yet.");
				firstPartPeriods--;
				continue;
			}

			// If there's a timeout for the expected part, set it to expect the
			// following
			if (initiallyExpected.equals(this.expectedPart)) {
				App.logger.debug(peer.getInfo().toString() + ": Part "
						+ this.expectedPart + " timed out and skipped.");
				this.expectedPart++;
			}
		}
		App.logger.info(peer.getInfo().toString()
				+ ": Finished transfer or time out");

		try {
			buf.close();
			outfile.close();
		} catch (IOException e) {
			App.logger.warn("Exception while closing stream", e);
		}

		// Remove the file if it is empty
		if (file.length() == 0 && file.canWrite()) {
			file.delete();
		}
	}

	/**
	 * Look if there's the next expected part in the buffer. If so, write it to
	 * the file.
	 * 
	 * @throws IOException
	 *             if there is an error while writing.
	 */
	private void checkPartsBuffer(OutputStream out) throws IOException {
		FilePart currentPart;
		Iterator<FilePart> iterator = this.partsBuffer.iterator();
		while (iterator.hasNext()) {
			currentPart = iterator.next();
			if (currentPart.getPart().equals(this.expectedPart)) {
				this.partsBuffer.remove(currentPart);
				// Write the current part
				out.write(currentPart.getData());
				out.flush();
				App.logger.debug("Write part: " + currentPart.getPart());
				this.expectedPart++;
				// Reinitialize iterator
				iterator = this.partsBuffer.iterator();
			}
		}
	}
}
