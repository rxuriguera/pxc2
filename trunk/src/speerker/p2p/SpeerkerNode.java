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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import peerbase.*;
import speerker.App;
import speerker.Song;
import speerker.p2p.messages.FilePartRequestHandler;
import speerker.p2p.messages.FilePartResponseHandler;
import speerker.p2p.messages.InfoHandler;
import speerker.p2p.messages.JoinHandler;
import speerker.p2p.messages.ListHandler;
import speerker.p2p.messages.ResponseHandler;
import speerker.p2p.messages.QueryHandler;
import speerker.p2p.messages.QuitHandler;
import speerker.p2p.messages.SpeerkerMessage;

/**
 * The backend implementation the Speerker's P2P module. This class mostly
 * implements the speerker protocol by defining the appropriate handlers for the
 * inherited methods of the Node class from the PeerBase system.
 * 
 * @author Nadeem Abdul Hamid
 */
public class SpeerkerNode extends Node {
	protected FileHashLibrary filesLibrary;
	protected HashMap<String, SearchResult> searchResults;
	protected List<FileGetter> fileTransfers;

	/**
	 * Creates a new P2P Speerker Node
	 * 
	 * @param info
	 *            PeerInfo instance with information of this node.
	 * @param maxPeers
	 *            Maximum number of peers
	 */
	public SpeerkerNode(PeerInfo info, int maxPeers) {
		super(maxPeers, info);
		this.filesLibrary = new FileHashLibrary(this.getId());
		this.searchResults = new HashMap<String, SearchResult>();
		this.fileTransfers = new LinkedList<FileGetter>();

		this.addRouter(new SpeerkerRouter(this));

		// Message Handlers
		this.addHandler(SpeerkerMessage.JOIN, new JoinHandler(this));
		this.addHandler(SpeerkerMessage.LIST, new ListHandler(this));
		this.addHandler(SpeerkerMessage.INFO, new InfoHandler(this));
		this.addHandler(SpeerkerMessage.QUERY, new QueryHandler(this));
		this.addHandler(SpeerkerMessage.RESPONSE, new ResponseHandler(this));
		this.addHandler(SpeerkerMessage.PARTREQ, new FilePartRequestHandler(
				this));
		this.addHandler(SpeerkerMessage.PARTRSP, new FilePartResponseHandler(
				this));
		this.addHandler(SpeerkerMessage.QUIT, new QuitHandler(this));
	}

	public void clearSearchResults() {
		this.searchResults = new HashMap<String, SearchResult>();
	}

	public void addToResults(SearchResult result) {
		SearchResult existentResult = this.searchResults.get(result.song
				.getHash());
		if (existentResult != null) {
			existentResult.addPeer(result.getPeers().get(0));
		} else {
			this.searchResults.put(result.song.getHash(), result);
		}
	}

	public HashMap<String, SearchResult> getSearchResults() {
		return this.searchResults;
	}

	/**
	 * Returns a list with all the songs in the local peer that match a query
	 * 
	 * @param query
	 * @return a list of SearchResults
	 */
	public List<SearchResult> search(SearchQuery query) {
		List<SearchResult> results = new LinkedList<SearchResult>();

		Iterator<Song> iterator = this.filesLibrary.getMatchingSongs(
				query.query).iterator();
		while (iterator.hasNext()) {
			results.add(new SearchResult(query.queryID, iterator.next(), this
					.getInfo()));
		}
		return results;
	}

	public String getFilePath(String hash) {
		return this.filesLibrary.getFilePath(hash);
	}

	/**
	 * This method can be used to join a P2P network using a known remote peer
	 * as a gateway.
	 * 
	 * @param remotePeer
	 *            PeerInfo instance with information about the remote peer
	 * @param hops
	 *            Number of levels of peer discovery.
	 */
	public void buildPeers(PeerInfo remotePeer, int hops) {
		if (this.maxPeersReached()) {
			App.logger.warn("Maximum peers reached");
			return;
		} else if (hops <= 0) {
			return;
		}

		// Get peer information
		SpeerkerMessage message = new SpeerkerMessage(SpeerkerMessage.INFO);
		List<PeerMessage> responseList = this.connectAndSend(remotePeer,
				message, true);
		if (responseList == null || responseList.size() == 0)
			return;

		// If the peer has replied, its information will be the first item in
		// the response list
		try {
			remotePeer = (PeerInfo) SpeerkerMessage
					.valueOf(responseList.get(0)).getMsgContent();
		} catch (ClassNotFoundException e) {
			App.logger.error("Error trying to get peer info:", e);
			return;
		}

		App.logger.info("Contacted peer: " + remotePeer.getId());

		// Send the remote peer a message to join
		message = new SpeerkerMessage(SpeerkerMessage.JOIN, this.getInfo());
		String response = this.connectAndSend(remotePeer, message, true).get(0)
				.getMsgType();
		if (!response.equals(SpeerkerMessage.REPLY)
				|| this.getPeerKeys().contains(remotePeer.getId())) {
			App.logger.info("Could not get peer information for "
					+ remotePeer.toString());
			return;
		}
		this.addPeer(remotePeer);

		// Once we've joined the remote peer, we do a recursive depth first
		// search to add more peers
		message = new SpeerkerMessage(SpeerkerMessage.LIST);
		responseList = this.connectAndSend(remotePeer, message, true);

		// First element of response list is the number of known peers.
		if (responseList.size() == 0)
			return;
		responseList.remove(0);

		Iterator<PeerMessage> iterator = responseList.iterator();
		PeerInfo nextPeer;
		while (iterator.hasNext()) {
			message = SpeerkerMessage.valueOf(iterator.next());
			try {
				nextPeer = (PeerInfo) message.getMsgContent();
			} catch (ClassNotFoundException e) {
				App.logger.error("Error deserializing LIST response", e);
				continue;
			}

			if (!nextPeer.getId().equals(this.getId())) {
				this.buildPeers(nextPeer, hops - 1);
			}
		}

	}

	/**
	 * Removes the current peer from all its connected peers.
	 */
	public void closeConnections() {
		PeerInfo current;
		PeerMessage message;
		@SuppressWarnings("unused")
		List<PeerMessage> responseList;

		Set<String> connectedPeers = this.getPeerKeys();
		Iterator<String> iterator = connectedPeers.iterator();

		// Request all the known peers to remove us from their list
		while (iterator.hasNext()) {
			current = this.getPeer(iterator.next());
			message = new SpeerkerMessage(SpeerkerMessage.QUIT, this.getInfo());
			responseList = this.connectAndSend(current, message, false);
		}
	}

	/**
	 * Adds a new file transfer thread
	 * 
	 * @param fileGetter
	 * @return and integer representing the file transfer ID
	 */
	public void newFileTransfer(SearchResult result) {
		FileGetter fp = new FileGetter(this.fileTransfers.size(), this, result);
		fp.setName("Speerker-" + this.getId() + "-Transfer-"
				+ this.fileTransfers.size());
		this.fileTransfers.add(fp);
		fp.start();
	}

	public FileGetter getFileTransfer(Integer transferID) {
		FileGetter transfer = this.fileTransfers.get(transferID);
		return transfer;
	}
}
