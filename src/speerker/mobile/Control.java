package speerker.mobile;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;
import speerker.p2p.SpeerkerP2PLayer;
import speerker.player.Playlist;
import speerker.search.SearchManager;

public class Control {
	protected static String TAG = "SpeerkerMobile";
	protected static String username;
	protected static String password;

	public static SearchManager searchManager;
	public static Playlist playlist;

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
		searchManager = new SearchManager(playlist);
	}

	public static void toastMessage(Activity activity, String message) {
		Toast toast = Toast.makeText(activity, message, Toast.LENGTH_LONG);
		toast.show();
	}
}
