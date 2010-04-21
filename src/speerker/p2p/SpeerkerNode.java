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
import java.util.List;

import org.jdom.Element;

import peerbase.*;
import speerker.App;
import speerker.library.XmlMusicLibrary;
import speerker.p2p.messages.FileGetHandler;
import speerker.p2p.messages.JoinHandler;
import speerker.p2p.messages.ListHandler;
import speerker.p2p.messages.NameHandler;
import speerker.p2p.messages.QResponseHandler;
import speerker.p2p.messages.QueryHandler;
import speerker.p2p.messages.QuitHandler;
import speerker.p2p.messages.SpeerkerMessage;

/**
 * The backend implementation of a simple peer-to-peer file sharing application.
 * This class mostly implements a simple protocol by defining the appropriate
 * handlers (as inner classes) for the inherited methods of the Node class from
 * the PeerBase system.
 * 
 * @author Nadeem Abdul Hamid
 */
public class SpeerkerNode extends Node {
	// Maps from file hash to peer-id
	private HashMap<String, String> owners;
	// Maps from file hash to local path
	private HashMap<String, String> paths;
	// Maps from file info to hashe
	private HashMap<String, String> searchInfo;

	public SpeerkerNode(int maxPeers, PeerInfo myInfo) {
		super(maxPeers, myInfo);

		this.loadFiles();

		this.addRouter(new SpeerkerRouter(this));

		this.addHandler(SpeerkerMessage.INSERTPEER, new JoinHandler(this));
		this.addHandler(SpeerkerMessage.LISTPEER, new ListHandler(this));
		this.addHandler(SpeerkerMessage.PEERNAME, new NameHandler(this));
		this.addHandler(SpeerkerMessage.QUERY, new QueryHandler(this));
		this.addHandler(SpeerkerMessage.QRESPONSE, new QResponseHandler(this));
		this.addHandler(SpeerkerMessage.FILEGET, new FileGetHandler(this));
		this.addHandler(SpeerkerMessage.PEERQUIT, new QuitHandler(this));
	}

	public HashMap<String, String> getOwners() {
		return owners;
	}

	public void setOwners(HashMap<String, String> owners) {
		this.owners = owners;
	}

	public HashMap<String, String> getPaths() {
		return paths;
	}

	public void setPaths(HashMap<String, String> paths) {
		this.paths = paths;
	}

	public HashMap<String, String> getSearchInfo() {
		return searchInfo;
	}

	public void setSearchInfo(HashMap<String, String> searchInfo) {
		this.searchInfo = searchInfo;
	}

	/**
	 * Registers all the files from the library as being locally available.
	 * Stores their info and hash so they can later be found by other peers.
	 */
	public void loadFiles() {
		owners = new HashMap<String, String>();
		paths = new HashMap<String, String>();
		searchInfo = new HashMap<String, String>();

		XmlMusicLibrary library = new XmlMusicLibrary(App
				.getProperty("MusicLibrary"));
		List<Element> music = library.getSongs();

		Element song;
		String str, hash, path;
		Iterator<Element> it = music.iterator();
		while (it.hasNext()) {
			song = it.next();
			str = song.getChildText("title") + " "
					+ song.getChildText("artist") + " "
					+ song.getChildText("album");
			hash = song.getChildText("hash");
			path = song.getChildText("path");
			this.owners.put(hash, this.getId());
			this.paths.put(hash, path);
			this.searchInfo.put(str, hash);
		}
	}

	public String getFileOwner(String filehash) {
		return owners.get(filehash);
	}

	public void buildPeers(String host, int port, int hops) {
		LoggerUtil.getLogger().fine("build peers");

		if (this.maxPeersReached() || hops <= 0)
			return;

		PeerInfo pd = new PeerInfo(host, port);
		List<PeerMessage> resplist = this.connectAndSend(pd,
				SpeerkerMessage.PEERNAME, "", true);
		if (resplist == null || resplist.size() == 0)
			return;
		String peerid = resplist.get(0).getMsgData();
		LoggerUtil.getLogger().fine("contacted " + peerid);
		pd.setId(peerid);

		String resp = this.connectAndSend(
				pd,
				SpeerkerMessage.INSERTPEER,
				String.format("%s %s %d", this.getId(), this.getHost(), this
						.getPort()), true).get(0).getMsgType();
		if (!resp.equals(SpeerkerMessage.REPLY)
				|| this.getPeerKeys().contains(peerid))
			return;

		this.addPeer(pd);

		// do recursive depth first search to add more peers
		resplist = this.connectAndSend(pd, SpeerkerMessage.LISTPEER, "", true);

		if (resplist.size() > 1) {
			resplist.remove(0);
			for (PeerMessage pm : resplist) {
				String[] data = pm.getMsgData().split("\\s");
				String nextpid = data[0];
				String nexthost = data[1];
				int nextport = Integer.parseInt(data[2]);
				if (!nextpid.equals(this.getId()))
					buildPeers(nexthost, nextport, hops - 1);
			}
		}
	}

}
