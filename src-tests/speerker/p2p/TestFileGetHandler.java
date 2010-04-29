package speerker.p2p;


import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import speerker.Song;
import speerker.p2p.messages.FileGetHandler;
import speerker.p2p.messages.SpeerkerMessage;


public class TestFileGetHandler {
	MockPeerNode peer01, peer02;
	FileGetHandler fgetHandler;

	@Before
	public void setUp() throws Exception {
		this.peer01 = new MockPeerNode(9001);
		this.peer02 = new MockPeerNode(9002);

		// Connect peers
		this.peer01.addPeer(this.peer02.getInfo());
		this.peer02.addPeer(this.peer01.getInfo());

		this.fgetHandler = new FileGetHandler(peer01);
	}
	
	@Test
	public void testHandleMessagePeer() throws InterruptedException {
		String hash = "070EA649CB6D0C646FD34BB36B7D3CC2";
		Song song = new Song();
		song.setHash(hash);
		
		MockPeerConnection peerconn = null;
		try {
			peerconn = new MockPeerConnection();
		} catch (Exception e) {
			fail("Exception thrwon " + e.toString());
		}
		
		SpeerkerMessage message = new SpeerkerMessage(SpeerkerMessage.FILEGET, song);
		this.fgetHandler.handleMessage(peerconn, message);
	}
}
