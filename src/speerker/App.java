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

import java.io.InputStream;
import java.util.Properties;

import speerker.mobile.AndroidLoggerWrapper;

import android.util.Log;

public class App {
	private static String LOGTAG = "Speerker";
	public static AndroidLoggerWrapper logger = AndroidLoggerWrapper
			.getLogger(App.LOGTAG);

	private static InputStream propertiesFile;

	private static Properties properties = null;

	protected static void loadProperties() {
		if (properties == null) {
			try {
				properties = new Properties();
				// java.net.URL url = ClassLoader
				// .getSystemResource("speerker.properties");
				// properties.load(url.openStream());
				properties.load(propertiesFile);
			} catch (Exception e) {
				Log.e(LOGTAG, "Couldn't load application properties.", e);
			}
		}
	}

	public static String getProperty(String name) {
		if (properties == null)
			loadProperties();
		return properties.getProperty(name);
	}

	public static void setJavaLogging() {
		System.setProperty("java.util.logging.config.file", App
				.getProperty("JavaLogging"));
	}

	public static InputStream getPropertiesFile() {
		return propertiesFile;
	}

	public static void setPropertiesFile(InputStream propertiesFile) {
		App.propertiesFile = propertiesFile;
	}
}
