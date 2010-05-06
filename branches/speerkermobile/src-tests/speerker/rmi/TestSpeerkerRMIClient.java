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

package speerker.rmi;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import speerker.Song;
import speerker.types.Play;
import speerker.types.User;

public class TestSpeerkerRMIClient {
	protected SpeerkerRMIClient client;
	
	@Before
	public void setUp(){
		client = new SpeerkerRMIClient();
	}
	
	@Test
	public void testLogin() {
		User user = new User("abc","def");
		user = this.client.login(user);
		assertEquals(false, user.getValid());
		
		user = new User("mittens","love 'em");
		user = this.client.login(user);
		assertEquals(true, user.getValid());
	}
	
	@Test
	public void testSendStats() {
		Play play = new Play("mittens2", new Song("a","b","c","d",33l));
		this.client.sendPlay(play);
	}
	

}
