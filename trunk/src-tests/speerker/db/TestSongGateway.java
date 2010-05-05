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

import org.junit.Test;

import speerker.Song;

public class TestSongGateway {

	@Test
	public void testFindByUserName() {
		// Get song by Id
		Song song = SongGateway.findById(2);
		assertEquals("Swim", song.getTitle());
		assertEquals("Oh No Ono", song.getArtist());

		// Get existent song by info
		Song tsong = new Song("The Balcony", "The Rumour Said Fire",
				"The Life and Death of a Male Body");
		song = SongGateway.findBySongInfo(tsong);
		assertEquals(tsong.getTitle(), song.getTitle());

		// Get non-existen song by info
		tsong = new Song("Butterx", "Bacon", "Jam");
		song = SongGateway.findBySongInfo(tsong);
		assertEquals(null, song);
	}

	@Test
	public void testNewSong() {
		Song song = new Song("Butter1", "Bacon", "Jam");
		Integer result = SongGateway.newSong(song);
		System.out.println("Id " + result);
	}
}
