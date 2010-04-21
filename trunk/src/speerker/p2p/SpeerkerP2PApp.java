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

import java.io.IOException;

import peerbase.PeerInfo;
import peerbase.util.SimplePingStabilizer;
import speerker.App;
import speerker.library.XmlMusicLibrary;
import speerker.p2p.messages.SpeerkerMessage;

public class SpeerkerP2PApp {
	private SpeerkerNode peer;

	private SpeerkerP2PApp(String initialhost, int initialport, int maxpeers,
			PeerInfo mypd) {
		peer = new SpeerkerNode(maxpeers, mypd);
		peer.buildPeers(initialhost, initialport, 2);

		(new Thread() {
			public void run() {
				peer.mainLoop();
			}
		}).start();

		System.out.println("Peer ID: " + peer.getId());

		peer.startStabilizer(new SimplePingStabilizer(peer), 3000);
	}

	public void search(String query) {
		String peerId = this.peer.getId();
		Integer ttl = 4;
		String queryMessage = peerId + " " + ttl.toString() + " " + query + " ";

		for (String pid : peer.getPeerKeys()) {
			peer.sendToPeer(pid, SpeerkerMessage.QUERY, queryMessage, true);
		}
	}

	public static void main(String[] args) throws IOException {
		System.setProperty("java.util.logging.config.file",
				"./logging.properties");
		int port = 9000;
		/*
		 * if (args.length != 1) {System.out.println(
		 * "Usage: java ... peerbase.sample.FileShareApp <host-port>"); } else {
		 * port = Integer.parseInt(args[0]); }
		 */

		@SuppressWarnings("unused")
		XmlMusicLibrary library = new XmlMusicLibrary(App
				.getProperty("SongLibrary"));
		@SuppressWarnings("unused")
		SpeerkerP2PApp app01 = new SpeerkerP2PApp("localhost", 9001, 5,
				new PeerInfo("first", "localhost", port));
		@SuppressWarnings("unused")
		SpeerkerP2PApp app02 = new SpeerkerP2PApp("localhost", 9000, 5,
				new PeerInfo("second", "localhost", 8000));

		for (int i = 0; i < 5; i++) {
			try {
				Thread.sleep(2000);
			} catch (Exception e) {

			}
		}
	}

}
