package speerker.p2p.messages;

import peerbase.HandlerInterface;
import peerbase.Node;
import peerbase.PeerConnection;
import peerbase.PeerMessage;

/* msg syntax: NAME */
public class NameHandler implements HandlerInterface {
	private Node peer;

	public NameHandler(Node peer) {
		this.peer = peer;
	}

	public void handleMessage(PeerConnection peerconn, PeerMessage msg) {
		peerconn.sendData(new PeerMessage(SpeerkerMessage.REPLY, peer.getId()));
	}
}
