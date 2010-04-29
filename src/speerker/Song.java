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

package speerker;

import java.io.Serializable;

public class Song implements Serializable {

	private static final long serialVersionUID = 2273691535365963090L;

	protected String title = "";
	protected String artist = "";
	protected String album = "";
	protected String hash = "";
	protected Long size = 0l;

	public Song() {
	}

	/**
	 * Song constructor
	 * @param title String
	 * @param artist String
	 * @param album String
	 * @param hash String
	 * @param size Long
	 */
	public Song(String title, String artist, String album, String hash,
			Long size) {
		this.title = title;
		this.album = album;
		this.artist = artist;
		this.hash = hash;
		this.size = size;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

}
