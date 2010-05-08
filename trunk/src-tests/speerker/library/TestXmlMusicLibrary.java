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

package speerker.library;

import org.junit.Before;
import org.junit.Test;

public class TestXmlMusicLibrary {

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void testAddDirectory(){
		XmlMusicLibrary library = new XmlMusicLibrary("fixtures/library/lib.xml");
		library.add("/Users/bartru/Music");
		library.saveLibrary();
	}
}
