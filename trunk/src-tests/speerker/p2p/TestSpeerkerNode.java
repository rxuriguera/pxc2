/*
 * Speerker 
 * Copyright (C) 2010  Jordi Bartrolí, Hector Mañosas i Ramon Xuriguera
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package speerker.p2p;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import peerbase.PeerInfo;

public class TestSpeerkerNode {
	SpeerkerNode node;

	@Before
	public void setUp() throws Exception {
		PeerInfo info = new PeerInfo("localhost", 9000);
		this.node = new SpeerkerNode(info, 5);
	}

	@Test
	public void testBuildPeers() {
		this.node.buildPeers(new PeerInfo("localhost", 3), 3);
	}

	@Test
	public void testSearch() {
		List<SearchResult> results = this.node.search(new SearchQuery(this.node
				.getInfo(), ".*money.*", 2));
		assertEquals(1, results.size());
	}

}
