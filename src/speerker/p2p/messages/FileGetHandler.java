package speerker.p2p.messages;

import java.io.FileInputStream;
import java.io.IOException;

import peerbase.HandlerInterface;
import peerbase.PeerConnection;
import peerbase.PeerMessage;
import speerker.App;
import speerker.p2p.SpeerkerNode;

/* msg syntax: FGET file-name */
public class FileGetHandler implements HandlerInterface {
	private SpeerkerNode peer;

	public FileGetHandler(SpeerkerNode peer) {
		this.peer = peer;
	}

	public void handleMessage(PeerConnection peerconn, PeerMessage msg) {
		String filehash = msg.getMsgData().trim();
		if (!this.peer.getOwners().containsKey(filehash)) {
			peerconn.sendData(new PeerMessage(SpeerkerMessage.ERROR, "Fget: "
					+ "file not found " + filehash));
			return;
		}

		byte[] filedata = null;
		try {
			FileInputStream infile = new FileInputStream(this.peer.getPaths().get(filehash));
			int len = infile.available();
			filedata = new byte[len];
			infile.read(filedata);
			infile.close();
		} catch (IOException e) {
			App.logger.info("Fget: error reading file: " + e);
			peerconn.sendData(new PeerMessage(SpeerkerMessage.ERROR, "Fget: "
					+ "error reading file "));
			return;
		}

		peerconn.sendData(new PeerMessage(SpeerkerMessage.REPLY, filedata));
	}
}
