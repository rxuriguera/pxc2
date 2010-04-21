package speerker.p2p.messages;

import peerbase.HandlerInterface;
import peerbase.Node;
import peerbase.PeerConnection;
import peerbase.PeerMessage;

/* msg syntax: RESP file-name pid */
public class QResponseHandler implements HandlerInterface {
	@SuppressWarnings("unused")
	private Node peer;

	public QResponseHandler(Node peer) {
		this.peer = peer;
	}

	public void handleMessage(PeerConnection peerconn, PeerMessage msg) {
		String[] data = msg.getMsgData().split("\\s");
		if (data.length != 2) {
			peerconn.sendData(new PeerMessage(SpeerkerMessage.ERROR, "Resp: "
					+ "incorrect arguments"));
			return;
		}

		/*
		String filename = data[0];
		String pid = data[1];
		
		if (files.containsKey(filename)) {
			peerconn.sendData(new PeerMessage(SpeerkerMessage.ERROR, "Resp: "
					+ "can't add duplicate file " + filename));
			return;
		}

		files.put(filename, pid);
		peerconn.sendData(new PeerMessage(SpeerkerMessage.REPLY, "Resp: "
				+ "file info added " + filename));
				*/
	}
}