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
import speerker.App;
import speerker.Song;

public class TestSpeerkerP2PLayer {

	@Before
	public void setUp() throws Exception {
	}
/*
	@Test
	public void testP2PLayer() throws InterruptedException {
		App.setJavaLogging();

		PeerInfo nonExistent = new PeerInfo("nonExistent", "", 9001);
		PeerInfo peer01 = new PeerInfo("peer01", "localhost", 9000);
		PeerInfo peer02 = new PeerInfo("peer02", "localhost", 8900);
		PeerInfo peer03 = new PeerInfo("peer03", "localhost", 8800);
		PeerInfo peer04 = new PeerInfo("peer04", "localhost", 8700);
		PeerInfo peer05 = new PeerInfo("peer05", "localhost", 8600);
		PeerInfo peer06 = new PeerInfo("peer06", "localhost", 8500);
		PeerInfo peer07 = new PeerInfo("peer07", "localhost", 8400);

		@SuppressWarnings("unused")
		SpeerkerP2PLayer app01 = new SpeerkerP2PLayer(peer01, nonExistent, 5);
		SpeerkerP2PLayer app02 = new SpeerkerP2PLayer(peer02, peer01, 5);
		SpeerkerP2PLayer app03 = new SpeerkerP2PLayer(peer03, peer01, 5);
		SpeerkerP2PLayer app04 = new SpeerkerP2PLayer(peer04, peer01, 5);
		SpeerkerP2PLayer app05 = new SpeerkerP2PLayer(peer05, peer01, 5);
		SpeerkerP2PLayer app06 = new SpeerkerP2PLayer(peer06, peer01, 5);
		SpeerkerP2PLayer app07 = new SpeerkerP2PLayer(peer07, peer01, 5);

		Thread.sleep(200);
		assertEquals("Joined all peers", 5, app05.getNConnectedPeers());
		assertEquals("Maximum peers reached", 0, app07.getNConnectedPeers());

		// Remove one of the peers
		app03.closeConnections();
		Thread.sleep(200);
		assertEquals("Peers after quitting", 4, app04.getNConnectedPeers());

		app02.closeConnections();
		Thread.sleep(200);
		assertEquals("Peers after quitting", 3, app06.getNConnectedPeers());
	}
*/
	/*
	@Test
	public void testSearch() {
		fail("Not yet implemented");
	}*/

	@Test
	public void testGetFile() {
		PeerInfo nonExistent = new PeerInfo("nonExistent", "", 9100);
		PeerInfo peer01 = new PeerInfo("peer01", "localhost", 9101);
		PeerInfo peer02 = new PeerInfo("peer02", "localhost", 9102);

		SpeerkerP2PLayer app01 = new SpeerkerP2PLayer(peer01, nonExistent, 5);
		@SuppressWarnings("unused")
		SpeerkerP2PLayer app02 = new SpeerkerP2PLayer(peer02, peer01, 5);

		Song song = new Song();
		song.setHash("453A2D79E91541AB0F4D89E0DBF3FBFF");
		song.setSize(6649075l);

		SearchResult result = new SearchResult("ID", song, peer02);

		app01.getFile(result);
		
		try {
			Thread.sleep(300000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
