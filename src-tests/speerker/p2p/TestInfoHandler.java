package speerker.p2p;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import peerbase.PeerMessage;
import speerker.p2p.messages.InfoHandler;

public class TestInfoHandler {
	InfoHandler infoHandler;
	MockPeerNode node;

	@Before
	public void setUp() throws Exception {
		this.node = new MockPeerNode();
		this.infoHandler = new InfoHandler(node);
	}

	@Test
	public void testHandleMessage() {
		try {
			this.infoHandler.handleMessage(new MockPeerConnection(),
					new PeerMessage("INFO", ""));
		} catch (Exception e) {
			fail("Trhown exception " + e.toString());
		}
	}

}
