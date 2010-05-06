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
		PeerConnection peerconn = null;

		PeerInfo nonExistent = new PeerInfo("nonExistent", "", 9100);
		PeerInfo remotePeer = new PeerInfo("peer01", "localhost", 9101);
		@SuppressWarnings("unused")
		SpeerkerP2PLayer app01 = new SpeerkerP2PLayer(remotePeer, nonExistent,
				5, false);

		try {
			peerconn = new PeerConnection(remotePeer);
		} catch (Exception e) {
			fail("Error creating connection");
		}

		String hash = "070EA649CB6D0C646FD34BB36B7D3CC2";
		FilePart part = new FilePart(hash, remotePeer, 1, 300000l);

		SpeerkerMessage message = new SpeerkerMessage(SpeerkerMessage.PARTREQ,
				part);
		this.fgetHandler.handleMessage(peerconn, message);
	}
}
