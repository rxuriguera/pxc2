package speerker.p2p;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import speerker.p2p.messages.QueryHandler;
import speerker.p2p.messages.SpeerkerMessage;

public class TestQueryHandler {
	MockPeerNode peer01, peer02, peer03;
	QueryHandler queryHandler;
	MockPeerConnection peercon;

	@Before
	public void setUp() throws Exception {
		this.peer01 = new MockPeerNode(9001);

		this.queryHandler = new QueryHandler(peer01);
		this.peercon = null;
		try {
			peercon = new MockPeerConnection(this.peer01.getInfo());
		} catch (Exception e) {
			fail("Exception thrwon " + e.toString());
		}
	}

	@Test
	public void testHandleMessagePeer() {
		SpeerkerMessage message;
		// Peer01 sends an invalid query message to peer02
		String invalidQuery = "Some invalid query";
		message = new SpeerkerMessage(SpeerkerMessage.QUERY, invalidQuery);
		try {
			this.queryHandler.handleMessage(this.peercon, message);
		} catch (Exception e) {
			fail("Exception thrwon " + e.toString());
		}

		// Peer01 sends a query message to peer02
		SearchQuery query = new SearchQuery(this.peer01.getInfo(), "query id",
				"song query", 2);
		message = new SpeerkerMessage(SpeerkerMessage.QUERY, query);

		try {
			this.queryHandler.handleMessage(this.peercon, message);
		} catch (Exception e) {
			fail("Exception thrwon " + e.toString());
		}
	}
}
