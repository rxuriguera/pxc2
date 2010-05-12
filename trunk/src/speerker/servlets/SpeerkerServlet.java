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

package speerker.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServlet;

import speerker.db.SpeerkerConnection;

public class SpeerkerServlet extends HttpServlet {

	private static final long serialVersionUID = 1349253521289465487L;
	protected List<String> errors;

	public void initialize() {
		this.errors = new LinkedList<String>();
		InputStream is = getServletContext().getResourceAsStream(
				"speerker.properties");
		Properties properties = new Properties();
		if (is == null)
			errors.add("Can't open stream for file speerker.properties");

		try {
			properties.load(is);
			String DBUrl = properties.getProperty("DBUrl");
			String DBUser = properties.getProperty("DBUser");
			String DBPassword = properties.getProperty("DBPassword");
			is.close();

			SpeerkerConnection
					.createCustomConnection(DBUrl, DBUser, DBPassword);
		} catch (IOException e) {
			errors.add("Error connection to database: " + e.toString());
		}
	}

}
