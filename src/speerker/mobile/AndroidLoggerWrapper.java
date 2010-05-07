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
