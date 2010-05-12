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

import java.util.Date;
import java.util.List;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

import speerker.App;
import speerker.db.PlayGateway;
import speerker.types.Play;

public class Stats extends SpeerkerServlet {

	private static final String ALL = "%";
	private static final long serialVersionUID = 5112852865271923993L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		this.initialize();

		// Get Parameters
		Integer last = 10;
		try {
			last = Integer.parseInt(req.getParameter("last"));
		} catch (NumberFormatException e) {
			App.logger.warn("Could not get 'last' parameter. Using last=10", e);
		}

		String user = req.getParameter("user");
		if (user == null)
			user = ALL;

		// Get play count
		Integer playCount = 0;
		if (user.equals(ALL))
			playCount = PlayGateway.getTotalPlays();
		else
			playCount = PlayGateway.getUserPlays(user);

		// Get last user plays
		List<Play> plays = PlayGateway.findByUsername(user, last);

		// Set Strings
		String showTitle = "Statistics for ";
		String playCountText = "";
		String lastPlayText = "Last " + last.toString() + " songs listened by ";
		if (user.equals(ALL)) {
			showTitle += "all users";
			playCountText += "Total plays: ";
			lastPlayText += "all users:";
		} else {
			showTitle += "user " + user;
			playCountText += "Total plays by user " + user + ":";
			lastPlayText += "user " + user + ":";
		}

		req.setAttribute("title", showTitle);
		req.setAttribute("errors", this.errors);
		req.setAttribute("playCountText", playCountText);
		req.setAttribute("lastPlayText", lastPlayText);
		req.setAttribute("playCount", playCount);
		req.setAttribute("plays", plays);

		req.setAttribute("rand", new Date());

		RequestDispatcher rd = req.getRequestDispatcher("stats.jsp");
		rd.forward(req, resp);
	}

}
