package speerker.p2p;

import static org.junit.Assert.*;

import net.jxta.exception.PeerGroupException;
import net.jxta.peergroup.PeerGroup;

import org.junit.Before;
import org.junit.Test;

public class TestClient {
	
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testRun() {
		Client client = new Client();
		try {
			client.run("test_peer", "test_group");
		} catch(Exception e) {
			
			fail("Trhowed Exception: "+e.toString());
		}
	}

}
