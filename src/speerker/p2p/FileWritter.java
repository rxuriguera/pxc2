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

import speerker.App;

public class FileWritter extends Thread {
	protected SpeerkerNode peer;
	protected File file;

	protected List<FilePart> partsBuffer;
	protected Integer fileParts = 0;
	protected Integer expectedPart = 0;
	protected Long periodMillis;
	protected Integer nPeriods;

	public FileWritter(SpeerkerNode peer, File file, Integer fileParts) {
		this.peer = peer;
		this.file = file;
		this.fileParts = fileParts;

		this.expectedPart = 0;
		this.partsBuffer = new LinkedList<FilePart>();

		// Waiting cycles
		this.periodMillis = Long.valueOf(App.getProperty("PeriodMillis"));
		this.nPeriods = Integer.valueOf(App.getProperty("WaitingPeriods"));

	}

	public void addReceivedFilePart(FilePart filePart) {
		this.partsBuffer.add(filePart);
	}

	public void run() {
		// Prepare streams
		FileOutputStream outfile = null;
		try {
			outfile = new FileOutputStream(this.file, true);
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
			while (partPeriods > 0 && this.expectedPart < fileParts) {
				App.logger.debug("Waiting part " + this.expectedPart);
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
			App.logger.info("Removing empty file");
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
				App.logger.debug(peer.getInfo().toString() + ": Writing part: "
						+ currentPart.getPart());
				this.expectedPart++;
				// Reinitialize iterator
				iterator = this.partsBuffer.iterator();
			}
		}
		App.logger.debug(peer.getInfo().toString()
				+ ": Checked buffer parts. Current expected part: "
				+ this.expectedPart);
	}
}

