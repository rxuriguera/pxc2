package speerker.mobile;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public abstract class SpeerkerActivity extends Activity {
	protected static String TITLE = "title";
	protected static String ARTIST = "artist";
	protected static String ALBUM = "album";
	protected static String HASH = "hash";
	
	public SpeerkerActivity() {
		super();
		Control.currentContext = this;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = null;
		switch (item.getItemId()) {
		case R.id.searchMenuItem:
			intent = new Intent(this, SearchActivity.class);
			break;
		default:
			intent = new Intent(this, PlayerActivity.class);
			break;
		}
		startActivity(intent);
		return super.onOptionsItemSelected(item);
	}
}