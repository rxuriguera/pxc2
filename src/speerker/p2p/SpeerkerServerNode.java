package speerker.p2p;

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
		PeerInfo serverInfo = new PeerInfo("SpeerkerServer", serverHost, serverPort);
		Integer maxPeers = Integer.valueOf(App.getProperty("ServerMaxPeers"));
		
		final SpeerkerServerNode peer = new SpeerkerServerNode(serverInfo, maxPeers);
		
		// Start server main loop
		Thread peerThread = (new Thread() {
			public void run() {
				this.setName("Speerker-P2P-" + peer.getId());
				peer.mainLoop();
			}
		});
		peerThread.start();
		App.logger.info("Server peer on: "+serverInfo.toString()+" started.");

		peer.startStabilizer(new SimplePingStabilizer(peer), 10000);
	}
}
