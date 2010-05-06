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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import speerker.App;

public class SpeerkerConnection {
	private static Connection connection = null;

	private static void createConnection() {

		// Get connection data
		String url = App.getProperty("DBUrl");
		String user = App.getProperty("DBUser");
		String password = App.getProperty("DBPassword");

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(url, user, password);
		} catch (InstantiationException e) {
			App.logger.error("Error creating DB connection: ", e);
		} catch (IllegalAccessException e) {
			App.logger.error("Error creating DB connection: ", e);
		} catch (ClassNotFoundException e) {
			App.logger.error("Error creating DB connection: ", e);
		} catch (SQLException e) {
			App.logger.error("Error creating DB connection: ", e);
		}

	}

	public static Connection getConnection() {
		if (connection == null)
			createConnection();
		return connection;
	}
}
