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

import android.util.Log;

public class AndroidLoggerWrapper {
	protected String tag;

	public AndroidLoggerWrapper(String tag) {
		this.tag = tag;
	}

	public void debug(String message) {
		Log.d(this.tag, message);
	}

	public void debug(String message, Throwable e) {
		Log.d(this.tag, message, e);
	}

	public void info(String message) {
		Log.i(this.tag, message);
	}

	public void info(String message, Throwable e) {
		Log.i(this.tag, message, e);
	}

	public void log(String message) {
		Log.i(this.tag, message);
	}

	public void log(String message, Throwable e) {
		Log.i(this.tag, message, e);
	}

	public void error(String message) {
		Log.e(this.tag, message);
	}

	public void error(String message, Throwable e) {
		Log.e(this.tag, message, e);
	}

	public void warn(String message) {
		Log.w(this.tag, message);
	}

	public void warn(String message, Throwable e) {
		Log.w(this.tag, message, e);
	}

	public static AndroidLoggerWrapper getLogger(String tag) {
		return new AndroidLoggerWrapper(tag);
	}
}
