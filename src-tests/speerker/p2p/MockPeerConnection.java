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

import java.io.IOException;
import java.net.UnknownHostException;

import peerbase.PeerConnection;
import peerbase.PeerInfo;
import peerbase.PeerMessage;

public class MockPeerConnection extends PeerConnection {

	public MockPeerConnection() throws IOException, UnknownHostException {
		this(new PeerInfo("localhost", 9000));
	}
	
	public MockPeerConnection(PeerInfo peer) throws IOException, UnknownHostException {
		super(peer, null);
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		super.close();
	}

	@Override
	public PeerMessage recvData() {
		return new PeerMessage("","");
	}

	@Override
	public void sendData(PeerMessage msg) {

	}
	
	

}
