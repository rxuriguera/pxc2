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

package speerker.p2p;

import java.util.HashMap;

import peerbase.PeerInfo;
import peerbase.util.SimplePingStabilizer;
import speerker.App;
import speerker.p2p.messages.SpeerkerMessage;

/**
 * This is a facade class to handle all P2P related functions. It exposes a
 * high-level API to the rest of the Speerker application.
 */
public class SpeerkerP2PLayer {
	protected SpeerkerNode peer;
	protected Thread peerThread;

	public SpeerkerP2PLayer() {
		this(App.getProperty("DefaultHost"), Integer.parseInt(App
				.getProperty("DefaultPort")), App.getProperty("ServerHost"),
				Integer.parseInt(App.getProperty("ServerPort")), 50);
	}

	public SpeerkerP2PLayer(String thisHost, int thisPort, String remoteHost,
			int remotePort, int maxPeers) {
		this(new PeerInfo(thisHost, thisPort), new PeerInfo(remoteHost,
				remotePort), maxPeers);
	}

	/**
	 * Creates a new Speerker P2P layer object
	 * 
	 * @param peerInfo
	 *            a PeerInfo instance containing the host and port of the local
	 *            peer
	 * @param remotePeerInfo
	 *            a PeerInfo instance containing the host and port of a known
	 *            remote peer
	 * @param maxPeers
	 *            maximum number of peers that the local peer will know
	 */
	public SpeerkerP2PLayer(PeerInfo peerInfo, PeerInfo remotePeerInfo,
			int maxPeers) {
		peer = new SpeerkerNode(peerInfo, maxPeers);
		peer.buildPeers(remotePeerInfo, 2);

		this.peerThread = (new Thread() {
			public void run() {
				this.setName("Speerker-P2P-" + peer.getId());
				peer.mainLoop();
			}
		});
		this.peerThread.start();

		App.logger.info("Peer with ID: " + peer.getId() + " started.");

		peer.startStabilizer(new SimplePingStabilizer(peer), 3000);
	}

	/**
	 * Get the number of peers that the current peer is connected to.
	 * 
	 * @return an Integer with the number of connected peers
	 */
	public int getNConnectedPeers() {
		return this.peer.getNumberOfPeers();
	}

	/**
	 * Tries to close all the connections with all the peers that the local node
	 * knows.
	 */
	public void closeConnections() {
		this.peer.closeConnections();
	}

	/**
	 * Sends a query to all known peers.
	 * 
	 * @param query
	 *            a SearchQuery instance.
	 * @param searchQueue
	 */
	public void search(SearchQuery query) {
		SpeerkerMessage message = new SpeerkerMessage(SpeerkerMessage.QUERY,
				query);
		for (String key : this.peer.getPeerKeys()) {
			peer.sendToPeer(key, message, false);
		}
	}

	/**
	 * Sends a query to all known peers.
	 * 
	 * @param queryID
	 *            And ID for the query so multiple results can be distinguished
	 * @param query
	 *            The query string
	 */
	public void search(String queryID, String query) {
		Integer ttl = Integer.valueOf(App.getProperty("SearchTTL"));
		SearchQuery squery = new SearchQuery(this.peer.getInfo(), queryID,
				query, ttl);
		this.search(squery);
	}

	/**
	 * Returns all the search results that have been received up until now.
	 * 
	 * @return a HashMap of search results
	 */
	public HashMap<String, HashMap<String, SearchResult>> getSearchResults() {
		return this.peer.getSearchResults();
	}

	/**
	 * Clear all search results
	 */
	public void clearSearchResults() {
		this.peer.clearSearchResults();
	}

	/**
	 * Tries to get a file from the peers from which it is available.
	 * 
	 * @param result
	 *            Search result containing information about the file and the
	 *            peers that own it.
	 */
	public void getFile(SearchResult result) {
		this.peer.newFileTransfer(result);
	}
}
