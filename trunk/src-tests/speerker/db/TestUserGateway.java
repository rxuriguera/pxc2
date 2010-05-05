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
		User user = UserGateway.findByUserName("user1");
		assertEquals(true, user.getValid());
		assertEquals("Test", user.getFirstName());

		user = UserGateway.findByUserName("userx");
		assertEquals(false, user.getValid());
		assertEquals("", user.getFirstName());
	}

	@Test
	public void testNewUser() {
		User user = new User(0, "mittens3", "love 'em", "First", "Last", true);
		Boolean result = UserGateway.newUser(user);
		assertEquals(true, result);
	}
}
