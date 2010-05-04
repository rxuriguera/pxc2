package speerker.p2p;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import peerbase.PeerConnection;
import peerbase.PeerInfo;

import speerker.p2p.messages.FilePartRequestHandler;
import speerker.p2p.messages.SpeerkerMessage;

public class TestFilePartRequestHandler {
	MockPeerNode peer01, peer02;
	FilePartRequestHandler fgetHandler;

	@Before
	public void setUp() throws Exception {
		this.peer01 = new MockPeerNode(9001);
		this.peer02 = new MockPeerNode(9002);

		// Connect peers
		this.peer01.addPeer(this.peer02.getInfo());
		this.peer02.addPeer(this.peer01.getInfo());

		this.fgetHandler = new FilePartRequestHandler(peer01);
	}

	@Test
	public void testHandleMessagePeer() throws InterruptedException {
		String hash = "070EA649CB6D0C646FD34BB36B7D3CC2";
		FilePart part = new FilePart(hash, 0, 0l);

		PeerConnection peerconn = null;

		PeerInfo nonExistent = new PeerInfo("nonExistent", "", 9100);
		PeerInfo remotePeer = new PeerInfo("peer01", "localhost", 9101);
		@SuppressWarnings("unused")
		SpeerkerP2PLayer app01 = new SpeerkerP2PLayer(remotePeer, nonExistent,
				5);

		try {
			peerconn = new PeerConnection(remotePeer);
		} catch (Exception e) {
			fail("Error creating connection");
		}

		SpeerkerMessage message = new SpeerkerMessage(SpeerkerMessage.PARTREQ,
				part);
		this.fgetHandler.handleMessage(peerconn, message);
	}
}
