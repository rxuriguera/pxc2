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

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Queue;

import peerbase.PeerInfo;
import peerbase.PeerMessage;
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

		this.peerThread =(new Thread() {
			public void run() {
				this.setName("Speerker-P2P-"+peer.getId());
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
	 * 
	 * @param query
	 * @param searchQueue
	 */
	public void search(SearchQuery query) {
		SpeerkerMessage message = new SpeerkerMessage(SpeerkerMessage.QUERY,
				query);
		for (String key : this.peer.getPeerKeys()) {
			peer.sendToPeer(key, message, false);
		}
	}

	public void search(String query) {
		Integer ttl = Integer.valueOf(App.getProperty("SearchTTL"));
		SearchQuery squery = new SearchQuery(this.peer.getInfo(), query, ttl);
		this.search(squery);
	}

	public void search(String query, Integer ttl) {
		SearchQuery squery = new SearchQuery(this.peer.getInfo(), query, ttl);
		this.search(squery);
	}

	public void search(String query, Queue<SearchResult> searchQueue) {
		this.peer.setSearchQueue(searchQueue);
		this.search(query);
	}

	/**
	 * Tries to get a file from the peers from which it is available.
	 * 
	 * @param result
	 *            Search result containing information about the file and the
	 *            peers that own it.
	 * @return True or false depending if the file has been sent by the peers
	 * 
	 */
	public Boolean getFile(SearchResult result) {
		SpeerkerMessage message = new SpeerkerMessage(SpeerkerMessage.FILEGET,
				result.song);
		List<PeerMessage> responseList = this.peer.connectAndSend(result.peer,
				message, true);

		if (responseList.size() == 0)
			return false;

		PeerMessage response = responseList.get(0);
		if (response.getMsgType().equals(SpeerkerMessage.ERROR)) {
			App.logger
					.error("Error getting the file: " + response.getMsgData());
			return false;
		}

		try {
			String filename = App.getProperty("DestFilePath") + "/"
					+ result.song.getHash();

			App.logger.info("Saving song " + result.song.getTitle() + " to "
					+ filename);

			FileOutputStream outfile = new FileOutputStream(filename);
			outfile.write(responseList.get(0).getMsgDataBytes());
			outfile.close();
		} catch (IOException ex) {
			App.logger.warn("Could not get file: " + ex);
			return false;
		}
		return true;
	}
}
