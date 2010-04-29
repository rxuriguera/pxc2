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

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import peerbase.PeerInfo;

import speerker.Song;
import speerker.p2p.messages.ResponseHandler;
import speerker.p2p.messages.SpeerkerMessage;

public class TestResponseHandler {
	protected SpeerkerNode peer;
	protected ResponseHandler responseHandler;

	@Before
	public void setUp() throws Exception {
		this.peer = new MockPeerNode(9001);
		this.responseHandler = new ResponseHandler(this.peer);
	}

	@Test
	public void testHandleMessage() {
		List<SearchResult> results = new LinkedList<SearchResult>();
		PeerInfo info = new PeerInfo("RandomHost", 3333);
		results.add(new SearchResult(new Song("a", "b", "c", "d"), info));
		results.add(new SearchResult(new Song("e", "f", "g", "h"), info));
		results.add(new SearchResult(new Song("i", "j", "k", "l"), info));

		MockPeerConnection peerconn = null;
		try {
			peerconn = new MockPeerConnection();
		} catch (Exception e) {
			fail("Exception thrwon " + e.toString());
		}

		SpeerkerMessage message = new SpeerkerMessage(SpeerkerMessage.RESPONSE,
				(Serializable) results);

		try {
			this.responseHandler.handleMessage(peerconn, message);
		} catch (Exception e) {
			fail("Exception thrwon " + e.toString());
		}

		assertEquals(3, this.peer.getSearchQueue().size());
	}
}
