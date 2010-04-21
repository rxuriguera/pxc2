package speerker.p2p.messages;

import peerbase.HandlerInterface;
import peerbase.Node;
import peerbase.PeerConnection;
import peerbase.PeerMessage;

/* msg syntax: QUIT pid */
public class QuitHandler implements HandlerInterface {
	private Node peer;

	public QuitHandler(Node peer) {
		this.peer = peer;
	}

	public void handleMessage(PeerConnection peerconn, PeerMessage msg) {
		String pid = msg.getMsgData().trim();
		if (peer.getPeer(pid) == null) {
			peerconn.sendData(new PeerMessage(SpeerkerMessage.ERROR,
					"Quit: peer not found: " + pid));
		} else {
			peer.removePeer(pid);
			peerconn.sendData(new PeerMessage(SpeerkerMessage.REPLY,
					"Quit: peer removed: " + pid));
		}
	}
}