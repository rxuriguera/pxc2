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

package speerker.types;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import speerker.Song;

public class Play implements Serializable {

	private static final long serialVersionUID = 6029414375163624398L;
	protected int id;
	protected String username;
	protected Song song;
	protected Timestamp timestamp;

	public Play() {
		this("", null);
	}

	public Play(String username, Song song) {
		this(username, song, new Timestamp(new Date().getTime()));
	}

	public Play(String username, Song song, Timestamp date) {
		super();
		this.id = 0;
		this.username = username;
		this.song = song;
		this.timestamp = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Song getSong() {
		return song;
	}

	public void setSong(Song song) {
		this.song = song;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

}
