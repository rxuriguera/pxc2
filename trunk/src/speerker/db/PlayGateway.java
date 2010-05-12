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
import java.util.LinkedList;
import java.util.List;

import speerker.App;
import speerker.Song;
import speerker.types.Play;
import speerker.types.User;

public class PlayGateway {
	/**
	 * Find last plays from a user
	 * 
	 * @param username
	 *            Name of the user
	 * @param limit
	 *            Number of plays
	 * @return a list of plays. If a user does not exist it returns an empty
	 *         list.
	 */
	static public List<Play> findByUsername(String username, Integer limit) {
		App.logger.debug("Finding " + limit + " plays for user " + username);

		List<Play> plays = new LinkedList<Play>();

		String query = "SELECT plays.id AS id, plays.timeStamp, "
				+ "songs.id AS songId, songs.title, "
				+ "songs.artist, songs.album FROM plays INNER JOIN songs "
				+ "ON plays.song=songs.id "
				+ "INNER JOIN users ON plays.user=users.id "
				+ "WHERE users.username LIKE ? "
				+ "ORDER BY timeStamp DESC LIMIT 0,?";

		try {
			PreparedStatement statement = SpeerkerConnection.getConnection()
					.prepareStatement(query);
			statement.setString(1, username);
			statement.setInt(2, limit);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				plays.add(fillPlay(resultSet, username));
			}

			return plays;
		} catch (SQLException e) {
			App.logger.error("Error finding user: ", e);
		}

		return plays;
	}

	static public List<Play> findByUsername(String username) {
		return findByUsername(username, 10);
	}

	static protected Play fillPlay(ResultSet resultSet, String username)
			throws SQLException {
		// Fill song
		Song song = new Song();
		song.setId(resultSet.getInt("songId"));
		song.setTitle(resultSet.getString("title"));
		song.setArtist(resultSet.getString("artist"));

		Play play = new Play();

		play.setTimestamp(resultSet.getTimestamp("timeStamp"));
		play.setId(resultSet.getInt("id"));
		play.setSong(song);
		play.setUsername(username);
		return play;
	}

	/**
	 * Returns the number of plays for a given user
	 * 
	 * @return
	 */
	static public Integer getUserPlays(String username) {
		App.logger.debug("Finding number of plays for user " + username);
		Integer plays = 0;
		String query = "SELECT COUNT(plays.id) FROM plays INNER JOIN users ON "
				+ "plays.user=users.id WHERE users.username=?";

		try {
			PreparedStatement statement = SpeerkerConnection.getConnection()
					.prepareStatement(query);
			statement.setString(1, username);
			ResultSet resultSet = statement.executeQuery();

			if (!resultSet.first())
				return 0;
			else
				return resultSet.getInt(1);
		} catch (SQLException e) {
			App.logger.error("Error finding user: ", e);
		}

		return plays;
	}

	static public Integer getTotalPlays() {
		App.logger.debug("Finding total number of plays");
		Integer plays = 0;
		String query = "SELECT COUNT(plays.id) FROM plays";

		try {
			PreparedStatement statement = SpeerkerConnection.getConnection()
					.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();

			if (!resultSet.first())
				return 0;
			else
				return resultSet.getInt(1);
		} catch (SQLException e) {
			App.logger.error("Error finding user: ", e);
		}
		return plays;
	}

	/**
	 * Adds a new play and return its id
	 * 
	 * @param play
	 * @return the id of the new play or -1 if there are errors.
	 */
	static public Integer newPlay(Play play) {
		// Get user Id
		User user = UserGateway.findByUserName(play.getUsername());
		if (user == null) {
			App.logger.debug("Could not add play. User " + play.getUsername()
					+ " not found");
			return -1;
		}
		Integer userId = user.getId();

		// Check if the song exists. And add it otherwise
		Song song = SongGateway.findBySongInfo(play.getSong());
		Integer songId;
		if (song == null) {
			App.logger
					.debug("Same song does not exist in the database. Adding it");
			songId = SongGateway.newSong(play.getSong());
		} else {
			songId = song.getId();
		}

		String query = "INSERT INTO plays(timeStamp,user,song) VALUES(?, ?, ?)";

		try {
			PreparedStatement statement = SpeerkerConnection.getConnection()
					.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			statement.setTimestamp(1, play.getTimestamp());
			statement.setInt(2, userId);
			statement.setInt(3, songId);

			statement.executeUpdate();
			ResultSet resultSet = statement.getGeneratedKeys();

			if (!resultSet.first())
				return -1;
			else
				return resultSet.getInt(1);
		} catch (SQLException e) {
			App.logger.error("Error adding new play: ", e);
		}
		return -1;
	}
}
