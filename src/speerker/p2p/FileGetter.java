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
	protected Integer transferID;
	protected SpeerkerNode peer;
	protected SearchResult result;
	protected List<FilePart> partsBuffer;
	protected Integer expectedPart = 0;
	protected Long periodMillis;
	protected Integer nPeriods;

	public FileGetter(Integer transferID, SpeerkerNode peer, SearchResult result) {
		this.transferID = transferID;
		this.peer = peer;
		this.result = result;
		this.expectedPart = 0;
		this.partsBuffer = new LinkedList<FilePart>();

		// Waiting cycles
		this.periodMillis = Long.valueOf(App.getProperty("PeriodMillis"));
		this.nPeriods = Integer.valueOf(App.getProperty("WaitingPeriods"));
	}

	public Integer getTransferID() {
		return transferID;
	}

	public void setTransferID(Integer transferID) {
		this.transferID = transferID;
	}

	public void addReceivedFilePart(FilePart filePart) {
		this.partsBuffer.add(filePart);
	}

	public void run() {
		if (this.transferID == null)
			return;

		// Decide how to divide the file
		String filename = App.getProperty("DestFilePath") + "/"
				+ result.song.getHash();

		Song song = this.result.getSong();
		List<PeerInfo> peers = this.result.getPeers();

		Long packetSize = Long.valueOf(App.getProperty("FilePacketLength"));
		Long fileSize = song.getSize();

		Integer fileParts = (int) Math.ceil(fileSize / packetSize);

		SpeerkerMessage message;
		FilePart filePart;
		Integer currentPeer = 0;
		PeerInfo currentPeerInfo;

		// Send part requests to the peers that have the file
		for (Integer part = 0; part < fileParts; part++) {
			currentPeerInfo = this.result.getPeers().get(currentPeer);
			filePart = new FilePart(this.transferID, part, packetSize, song
					.getHash());
			message = new SpeerkerMessage(SpeerkerMessage.PARTREQ, filePart);
			this.peer.connectAndSend(currentPeerInfo, message, false);
			currentPeer = (currentPeer + 1) % peers.size();
		}

		// Prepare file and streams
		File file = new File(filename);
		FileOutputStream outfile = null;
		try {
			outfile = new FileOutputStream(file, true);
		} catch (FileNotFoundException e) {
			App.logger.error("File not found", e);
		}
		BufferedOutputStream buf = new BufferedOutputStream(outfile);

		Integer bufferElements = 0;
		// Wait for the parts to arrive
		while (expectedPart < fileParts && this.nPeriods > 0) {
			this.nPeriods--;
			try {
				// Check if there are new parts in the buffer
				if (this.partsBuffer.size() > bufferElements) {
					this.checkPartsBuffer(buf);
					bufferElements = this.partsBuffer.size();
				}
				Thread.sleep(this.periodMillis);
			} catch (InterruptedException e) {
				App.logger.warn("Exception while sleeping", e);
				continue;
			} catch (IOException e) {
				App.logger.warn("Exception while writing part to file", e);
				continue;
			}
		}

		try {
			buf.close();
			outfile.close();
		} catch (IOException e) {
			App.logger.warn("Exception while closing stream", e);
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
			if (currentPart.getPart() == this.expectedPart) {
				this.partsBuffer.remove(currentPart);
				// Write the current part
				out.write(currentPart.getData());
				this.expectedPart++;
				// Reinitialize iterator
				iterator = this.partsBuffer.iterator();
			}
		}
	}
}
