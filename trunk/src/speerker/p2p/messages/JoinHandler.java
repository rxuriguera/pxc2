package speerker.p2p.messages;

import peerbase.HandlerInterface;
import peerbase.LoggerUtil;
import peerbase.Node;
import peerbase.PeerConnection;
import peerbase.PeerInfo;
import peerbase.PeerMessage;

/* msg syntax: JOIN pid host port */
public class JoinHandler implements HandlerInterface {
	private Node peer;

	public JoinHandler(Node peer) {
		this.peer = peer;
	}

	public void handleMessage(PeerConnection peerconn, PeerMessage msg) {
		if (peer.maxPeersReached()) {
			LoggerUtil.getLogger().fine(
					"maxpeers reached " + peer.getMaxPeers());
			peerconn.sendData(new PeerMessage(SpeerkerMessage.ERROR, "Join: "
					+ "too many peers"));
			return;
		}

		// check for correct number of arguments
		String[] data = msg.getMsgData().split("\\s");
		if (data.length != 3) {
			peerconn.sendData(new PeerMessage(SpeerkerMessage.ERROR, "Join: "
					+ "incorrect arguments"));
			return;
		}

		// parse arguments into PeerInfo structure
		PeerInfo info = new PeerInfo(data[0], data[1], Integer
				.parseInt(data[2]));

		if (peer.getPeer(info.getId()) != null)
			peerconn.sendData(new PeerMessage(SpeerkerMessage.ERROR, "Join: "
					+ "peer already inserted"));
		else if (info.getId().equals(peer.getId()))
			peerconn.sendData(new PeerMessage(SpeerkerMessage.ERROR, "Join: "
					+ "attempt to insert self"));
		else {
			peer.addPeer(info);
			peerconn.sendData(new PeerMessage(SpeerkerMessage.REPLY, "Join: "
					+ "peer added: " + info.getId()));
		}
	}
}