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

import java.rmi.Naming;

import speerker.App;
import speerker.types.Play;
import speerker.types.User;

public class SpeerkerRMIClient {

	protected Integer RMIServerPort;
	protected String RMIServerBaseURI;

	public SpeerkerRMIClient() {
		// Server URI
		this.RMIServerPort = Integer.valueOf(App.getProperty("RMIServerPort"));
		this.RMIServerBaseURI = App.getProperty("RMIServerBaseURI") + ":"
				+ this.RMIServerPort + "/";

		// Set client truststore
		System.setProperty("javax.net.ssl.trustStore", App
				.getProperty("RMIClientTruststore"));
	}

	public User login(User user) {
		try {
			String loginURI = this.RMIServerBaseURI + "login";
			SpeerkerLogin login = (SpeerkerLogin) Naming.lookup(loginURI);
			user = login.login(user);
		} catch (Exception e) {
			App.logger.error("Error while logging in RMI Client: ", e);
		}
		return user;
	}

	public void sendPlay(Play play) {
		String statsURI = this.RMIServerBaseURI + "stats";
		try {
			SpeerkerStats stats = (SpeerkerStats) Naming.lookup(statsURI);
			stats.submitPlay(play);
		} catch (Exception e) {
			App.logger.error("Error while sending stats in RMI Client: ", e);
		}
	}
}
