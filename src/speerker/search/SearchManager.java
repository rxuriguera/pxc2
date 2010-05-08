package speerker.search;

import java.io.File;
import java.util.HashMap;

import speerker.App;
import speerker.mobile.Control;
import speerker.p2p.SearchResult;
import speerker.p2p.SpeerkerP2PLayer;

public class SearchManager {

	private SpeerkerP2PLayer speerkerP2PLayer;
	private HashMap<String, HashMap<String, SearchResult>> results;

	public SearchManager() {
		speerkerP2PLayer = new SpeerkerP2PLayer(true);
		results = speerkerP2PLayer.getSearchResults();

	}

	public void newSearch(String field) {

		speerkerP2PLayer.search(field);

	}

	public void getFile(String query, String hash) {
		SearchResult result = this.speerkerP2PLayer.getSearchResults().get(
				query).get(hash);
		if (result != null) {
			this.getFile(result);
		} else {
			App.logger.error("Could not get result for query: " + query
					+ " and hash: " + hash);
		}
	}

	public void getFile(final SearchResult result) {
		final String hash = result.getSong().getHash();
		final File f = new File(App.getProperty("DestFilePath"), hash);

		if (f.length() != 0) {
			Control.addToPlaylist(result);
			return;
		}

		speerkerP2PLayer.getFile(result);
		App.logger.debug("Receiving file " + hash);
		new Thread() {
			public void run() {
				Integer nPeriods = Integer.valueOf(App
						.getProperty("WaitingPeriods"));
				Integer waitingTime = Integer.valueOf(App
						.getProperty("PeriodMillis"));

				while (f.length() == 0 && nPeriods > 0)
					try {
						sleep(waitingTime);
						nPeriods--;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				if (f.length() != 0) {
					App.logger.info("File for song " + hash + " is present");
					Control.addToPlaylist(result);
				} else {
					App.logger.info("File for song " + hash
							+ " not present. Timed out.");
				}
			}
		}.start();
	}

	public HashMap<String, HashMap<String, SearchResult>> getResults() {
		return this.results;
	}

	public void clearSearchResults() {
		this.speerkerP2PLayer.clearSearchResults();
	}

}
