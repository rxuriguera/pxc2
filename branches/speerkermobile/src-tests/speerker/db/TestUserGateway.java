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

import speerker.types.User;

public class TestUserGateway {

	@Test
	public void testFindByUserName() {
		// Get existent user by username
		User user = UserGateway.findByUserName("user1");
		assertEquals(true, user.getValid());
		assertEquals("Test", user.getFirstName());

		// Get non-existent user by username
		user = UserGateway.findByUserName("userx");
		assertEquals(null, user);
	}

	@Test
	public void testNewUser() {
		User user = new User(0, "mittens9", "love 'em", "First", "Last", true);
		Integer result = UserGateway.newUser(user);
		assertTrue(result != -1);
	}
}
