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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import speerker.App;
import speerker.Song;

public class SongGateway {
	static public Song findById(Integer id) {
		App.logger.debug("Finding song by id: " + id);

		String query = "SELECT * FROM songs u WHERE id=?";

		try {
			PreparedStatement statement = SpeerkerConnection.getConnection()
					.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();

			if (!resultSet.first())
				return null;

			return fillSong(resultSet);
		} catch (SQLException e) {
			App.logger.error("Error finding user: ", e);
		}

		return null;
	}

	static public Song findBySongInfo(Song song) {
		App.logger.debug("Finding song by info: " + song.getTitle() + " - "
				+ song.getArtist() + " from album " + song.getAlbum());

		String query = "SELECT * FROM songs u WHERE title=? AND artist=? AND album=?";

		try {
			PreparedStatement statement = SpeerkerConnection.getConnection()
					.prepareStatement(query);
			statement.setString(1, song.getTitle());
			statement.setString(2, song.getArtist());
			statement.setString(3, song.getAlbum());
			ResultSet resultSet = statement.executeQuery();

			if (!resultSet.first())
				return null;

			return fillSong(resultSet);
		} catch (SQLException e) {
			App.logger.error("Error finding user: ", e);
		}

		return null;
	}

	static protected Song fillSong(ResultSet resultSet) throws SQLException {
		Song song = new Song();
		song.setId(resultSet.getInt("id"));
		song.setTitle(resultSet.getString("title"));
		song.setArtist(resultSet.getString("artist"));
		song.setAlbum(resultSet.getString("album"));
		return song;
	}

	static public Boolean songExists(Song song) {
		return findBySongInfo(song) != null;
	}

	/**
	 * Returns the id of the new sog
	 * 
	 * @param song
	 * @return
	 */
	static public Integer newSong(Song song) {
		Song dbSong = findBySongInfo(song);
		if (dbSong != null) {
			App.logger.debug("Same song already exists in the database.");
			return dbSong.getId();
		}

		String query = "INSERT INTO songs(title,artist,album) VALUES(?, ?, ?)";

		try {
			PreparedStatement statement = SpeerkerConnection.getConnection()
					.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, song.getTitle());
			statement.setString(2, song.getArtist());
			statement.setString(3, song.getAlbum());

			statement.executeUpdate();
			ResultSet resultSet = statement.getGeneratedKeys();

			if (!resultSet.first())
				return -1;
			else
				return resultSet.getInt(1);
		} catch (SQLException e) {
			App.logger.error("Error adding new song: ", e);
		}
		return -1;
	}
}
