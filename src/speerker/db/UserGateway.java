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

import speerker.App;
import speerker.types.User;

public class UserGateway {

	static public User findByUserName(String userName) {
		App.logger.info("Finding string by username");

		User user = new User();
		String query = "SELECT * FROM users u WHERE username=?";

		try {
			PreparedStatement statement = SpeerkerConnection.getConnection()
					.prepareStatement(query);
			statement.setString(1, userName);
			ResultSet resultSet = statement.executeQuery();

			if (!resultSet.first())
				return user;
			return fillUser(resultSet);
		} catch (SQLException e) {
			App.logger.error("Error finding user: ", e);
		}

		return user;
	}

	static protected User fillUser(ResultSet resultSet) throws SQLException {
		User user = new User();
		user.setId(resultSet.getInt("id"));
		user.setUsername(resultSet.getString("username"));
		user.setPassword(resultSet.getString("password"));
		user.setFirstName(resultSet.getString("firstName"));
		user.setLastName(resultSet.getString("lastName"));
		user.setValid(true);
		return user;
	}

	static public Boolean userExists(User user) {
		return findByUserName(user.getUsername()).getValid();
	}

	static public Boolean newUser(User user) {
		if (userExists(user))
			return false;

		String query = "INSERT INTO users(username,password,firstName,lastName) VALUES(?, ?, ?, ?)";

		try {
			PreparedStatement statement = SpeerkerConnection.getConnection()
					.prepareStatement(query);
			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getFirstName());
			statement.setString(4, user.getLastName());

			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			App.logger.error("Error finding user: ", e);
		}
		return false;
	}

}
