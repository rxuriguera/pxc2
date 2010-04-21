package speerker.p2p.messages;

import peerbase.HandlerInterface;
import peerbase.Node;
import peerbase.PeerConnection;
import peerbase.PeerMessage;

/* msg syntax: LIST */
public class ListHandler implements HandlerInterface {
	private Node peer;

	public ListHandler(Node peer) {
		this.peer = peer;
	}

	public void handleMessage(PeerConnection peerconn, PeerMessage msg) {
		peerconn.sendData(new PeerMessage(SpeerkerMessage.REPLY, String.format(
				"%d", peer.getNumberOfPeers())));
		for (String pid : peer.getPeerKeys()) {
			peerconn.sendData(new PeerMessage(SpeerkerMessage.REPLY, String
					.format("%s %s %d", pid, peer.getPeer(pid).getHost(), peer
							.getPeer(pid).getPort())));
		}
	}
}
