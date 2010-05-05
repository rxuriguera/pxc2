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

package speerker.db;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import speerker.Song;
import speerker.types.Play;

public class TestPlayGateway {

	@Test
	public void testFindByUserName() {
		// Get song by Id
		List<Play> plays = PlayGateway.findByUsername("user1");
		assertEquals(2, plays.size());

		plays = PlayGateway.findByUsername("userx");
		assertEquals(0, plays.size());
	}

	@Test
	public void testGetUserPlays() {
		Integer plays = PlayGateway.getUserPlays("user1");
		assertEquals(2, plays.intValue());

		plays = PlayGateway.getUserPlays("userx");
		assertEquals(0, plays.intValue());
	}

	@Test
	public void testGetTotalPlays() {
		Integer plays = PlayGateway.getTotalPlays();
		assertTrue(plays >= 5);
	}

	@Test
	public void testNewPlay() {
		// Play with new song
		Song song = new Song("Butter3", "Bacon", "Jam");
		Play play = new Play("mittens", song);
		Integer playId = PlayGateway.newPlay(play);
		assertTrue(playId != -1);

		// Play with existent song
		song = new Song("Butter1", "Bacon", "Jam");
		play = new Play("mittens", song);
		playId = PlayGateway.newPlay(play);
		assertTrue(playId != -1);
	}
}
