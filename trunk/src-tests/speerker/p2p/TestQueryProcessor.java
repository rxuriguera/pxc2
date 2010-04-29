package speerker.p2p;

import org.junit.Before;
import org.junit.Test;

public class TestQueryProcessor {
	private QueryProcessor qp;
	
	@Before
	public void setUp() throws Exception {
		MockPeerNode node = new MockPeerNode();
		SearchQuery query = new SearchQuery(node.getInfo(),"xx",2);
		this.qp = new QueryProcessor(node,query);
	}

	@Test
	public void testRun() {
		this.qp.start();
	}

}
