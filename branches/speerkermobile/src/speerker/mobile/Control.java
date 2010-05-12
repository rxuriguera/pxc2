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

package speerker.mobile;

import java.io.IOException;
import java.net.URL;
import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import android.app.Activity;
import android.widget.Toast;
import speerker.App;
import speerker.p2p.SearchResult;
import speerker.search.SearchManager;
import speerker.util.StreamToString;

public class Control {
	protected static Activity currentContext;

	protected static String TAG = "SpeerkerMobile";
	protected static String username;
	protected static String password;

	protected static List<SearchResult> playlist;

	public static SearchManager searchManager;

	public static void initializeProperties() {
		try {
			App.setPropertiesFile(currentContext.getAssets().open(
					"speerker.properties"));
		} catch (IOException e) {
			App.logger.error("Could not open properties", e);
		}
	}

	public static void initializeApplication() {
		try {
			playlist = new LinkedList<SearchResult>();
			App.logger.info("Starting search manager");
			startSearchManager();
		} catch (Exception e) {
			App.logger.error("Error while creating P2P layer", e);
		}
	}

	public static String getUsername() {
		return username;
	}

	public static void setUsername(String username) {
		Control.username = username;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		Control.password = password;
	}

	public static void startSearchManager() {
		searchManager = new SearchManager();
	}

	public static void toastMessage(Activity activity, String message) {
		Toast toast = Toast.makeText(activity, message, Toast.LENGTH_LONG);
		toast.show();
	}

	public static void addToPlaylist(SearchResult result) {
		App.logger.info("Added song to play list");
		App.logger.info("Toast: " + Control.currentContext.toString());
		playlist.add(result);
		Control.toastMessage(Control.currentContext,
				"New song added to playlist");
	}

	public static Boolean logIn() {
		Boolean result = false;
		String HTTPSServerUrl = App.getProperty("HTTPSServerUrl");
		if (HTTPSServerUrl == null) {
			App.logger.debug("Could not load property");
			return false;
		}

		App.logger.debug("Logging user " + Control.username);
		String url = String.format(HTTPSServerUrl, Control.username,
				Control.password);

		// Make an HTTPS Request to the server
		// Use SpeerkerTrustManager and SpeerkerHostVerifier so our self-signed
		// certificate is accepted
		try {
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, new TrustManager[] { new SpeerkerTrustManager() },
					new SecureRandom());
			HttpsURLConnection
					.setDefaultSSLSocketFactory(sc.getSocketFactory());
			HttpsURLConnection
					.setDefaultHostnameVerifier(new SpeerkerHostVerifier());
			HttpsURLConnection con = (HttpsURLConnection) new URL(url)
					.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.connect();

			String strResult = StreamToString.convertStreamToString(con
					.getInputStream());
			strResult = strResult.trim();
			result = Boolean.valueOf(strResult);
			App.logger.info("Logging in result: " + strResult);
		} catch (Exception e) {
			App.logger.error("Error logging in: ", e);
		}

		return result;
	}
}
