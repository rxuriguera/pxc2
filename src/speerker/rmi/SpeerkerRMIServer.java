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

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

import javax.rmi.ssl.SslRMIClientSocketFactory;
import javax.rmi.ssl.SslRMIServerSocketFactory;

import speerker.App;

public class SpeerkerRMIServer {
	protected Integer RMIServerPort;
	protected String RMIServerBaseURI;

	public SpeerkerRMIServer() {
		// Server URI
		this.RMIServerPort = Integer.valueOf(App.getProperty("RMIServerPort"));
		this.RMIServerBaseURI = App.getProperty("RMIServerBaseURI") + ":"
				+ this.RMIServerPort + "/";

		// Set keystore
		System.setProperty("javax.net.ssl.keyStore", App
				.getProperty("RMIServerKeystore"));
		System.setProperty("javax.net.ssl.keyStorePassword", App
				.getProperty("RMIServerKeystorePassword"));

		RMIClientSocketFactory rmiClientSocketFactory = new SslRMIClientSocketFactory();
		RMIServerSocketFactory rmiServerSockeyFactory = new SslRMIServerSocketFactory();

		try {
			LocateRegistry.createRegistry(this.RMIServerPort);

			// Bind Login
			SpeerkerLogin login = (SpeerkerLogin) UnicastRemoteObject
					.exportObject(new SpeerkerLoginImpl(), 0,
							rmiClientSocketFactory, rmiServerSockeyFactory);
			String loginURI = this.RMIServerBaseURI + "login";
			Naming.rebind(loginURI, login);
			System.out.println("Binding: " + loginURI);
			
			// Bind Stats
			SpeerkerStats stats = (SpeerkerStats) UnicastRemoteObject
					.exportObject(new SpeerkerStatsImpl(), 0,
							rmiClientSocketFactory, rmiServerSockeyFactory);
			String statsURI = this.RMIServerBaseURI + "stats";
			Naming.rebind(statsURI, stats);
			System.out.println("Binding: " + statsURI);
			
		} catch (RemoteException e) {
			App.logger.error("Error starting server: ", e);
		} catch (MalformedURLException e) {
			App.logger.error("Error starting server: ", e);
		}
	}

	public static void main(String args[]) {
		new SpeerkerRMIServer();
	}
}
