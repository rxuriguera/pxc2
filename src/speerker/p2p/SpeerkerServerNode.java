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

import android.util.Log;
import peerbase.Node;
import peerbase.PeerInfo;
import peerbase.util.SimplePingStabilizer;
import speerker.App;
import speerker.p2p.messages.InfoHandler;
import speerker.p2p.messages.JoinHandler;
import speerker.p2p.messages.ListHandler;
import speerker.p2p.messages.QuitHandler;
import speerker.p2p.messages.SpeerkerMessage;

public class SpeerkerServerNode extends Node {
	private static String LOGTAG = "Node";

	/**
	 * Creates a new P2P Speerker Server Node
	 * 
	 * @param info
	 *            PeerInfo instance with information of this node.
	 * @param maxPeers
	 *            Maximum number of peers
	 */
	public SpeerkerServerNode(PeerInfo info, int maxPeers) {
		super(maxPeers, info);
		this.addRouter(new SpeerkerRouter(this));

		// Message Handlers
		this.addHandler(SpeerkerMessage.JOIN, new JoinHandler(this));
		this.addHandler(SpeerkerMessage.LIST, new ListHandler(this));
		this.addHandler(SpeerkerMessage.INFO, new InfoHandler(this));
		this.addHandler(SpeerkerMessage.QUIT, new QuitHandler(this));
	}

	public static void main(String[] args) {
		String serverHost = App.getProperty("ServerHost");
		Integer serverPort = Integer.valueOf(App.getProperty("ServerPort"));
		PeerInfo serverInfo = new PeerInfo("SpeerkerServer", serverHost,
				serverPort);
		Integer maxPeers = Integer.valueOf(App.getProperty("ServerMaxPeers"));

		final SpeerkerServerNode peer = new SpeerkerServerNode(serverInfo,
				maxPeers);

		// Start server main loop
		Thread peerThread = (new Thread() {
			public void run() {
				this.setName("Speerker-P2P-" + peer.getId());
				peer.mainLoop();
			}
		});
		peerThread.start();
		Log.i(LOGTAG, "Server peer on: " + serverInfo.toString() + " started.");

		peer.startStabilizer(new SimplePingStabilizer(peer), 10000);
	}
}
