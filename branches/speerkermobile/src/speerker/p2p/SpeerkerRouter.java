package speerker.p2p;

import peerbase.Node;
import peerbase.PeerInfo;
import peerbase.RouterInterface;

public class SpeerkerRouter implements RouterInterface {
	private Node peer;

	public SpeerkerRouter(Node peer) {
		this.peer = peer;
	}

	public PeerInfo route(String peerid) {
		if (peer.getPeerKeys().contains(peerid))
			return peer.getPeer(peerid);
		else
			return null;
	}
}