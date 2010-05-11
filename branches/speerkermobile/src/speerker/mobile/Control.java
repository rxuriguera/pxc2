package speerker.mobile;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.widget.Toast;
import speerker.App;
import speerker.p2p.SearchResult;
import speerker.search.SearchManager;

public class Control {
	protected static Activity currentContext;

	protected static String TAG = "SpeerkerMobile";
	protected static String username;
	protected static String password;

	protected static List<SearchResult> playlist;

	public static SearchManager searchManager;

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
		App.logger.info("Toast: "+Control.currentContext.toString());
		playlist.add(result);
		Control.toastMessage(Control.currentContext, "New song added to playlist");
	}
}
