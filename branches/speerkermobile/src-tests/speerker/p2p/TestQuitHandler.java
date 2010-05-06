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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import peerbase.PeerInfo;

import speerker.p2p.messages.QuitHandler;
import speerker.p2p.messages.SpeerkerMessage;

public class TestQuitHandler {
	MockPeerNode peer01, peer02;
	QuitHandler quitHandler;

	@Before
	public void setUp() throws Exception {
		this.peer01 = new MockPeerNode(9001);
		this.peer02 = new MockPeerNode(9002);

		// Connect peers
		this.peer01.addPeer(this.peer02.getInfo());
		this.peer02.addPeer(this.peer01.getInfo());

		this.quitHandler = new QuitHandler(peer01);
	}

	@Test
	public void testHandleMessagePeer() throws InterruptedException {
		PeerInfo info;
		SpeerkerMessage message;
		MockPeerConnection peerconn = null;
		try {
			peerconn = new MockPeerConnection();
		} catch (Exception e) {
			fail("Exception thrwon " + e.toString());
		}

		// Quit: unexistent peer
		info = new PeerInfo("nonExistent", "someHost", 9001);
		message = new SpeerkerMessage(SpeerkerMessage.QUIT, info);
		try {
			this.quitHandler.handleMessage(peerconn, message);
		} catch (Exception e) {
			fail("Exception thrwon " + e.toString());
		}

		Thread.sleep(100);
		assertEquals("Non-existent peer is not removed", 1, this.peer01
				.getNumberOfPeers());

		// Quit: existing peer
		info = this.peer02.getInfo();
		message = new SpeerkerMessage(SpeerkerMessage.QUIT, info);
		try {
			this.quitHandler.handleMessage(peerconn, message);
		} catch (Exception e) {
			fail("Exception thrwon " + e.toString());
		}

		Thread.sleep(100);
		assertEquals("Existent peer is removed", 0, this.peer01
				.getNumberOfPeers());
	}
}
