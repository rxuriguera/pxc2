package speerker.mobile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import speerker.App;
import speerker.p2p.SearchResult;

import android.R.drawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class PlayerActivity extends SpeerkerActivity implements
		OnCompletionListener, OnItemClickListener {

	protected static Integer PLAY = 0;
	protected static Integer PAUSE = 1;

	protected ListView playListView;
	protected SimpleAdapter listAdapter;
	protected List<HashMap<String, String>> listItems;

	protected ImageButton previous;
	protected ImageButton playPause;
	protected ImageButton next;

	protected Integer status;
	protected MediaPlayer player;

	protected Integer currentSong;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.player);

		this.previous = (ImageButton) findViewById(R.id.previousButton);
		this.playPause = (ImageButton) findViewById(R.id.playPauseButton);
		this.next = (ImageButton) findViewById(R.id.nextButton);

		this.player = new MediaPlayer();
		this.player.setOnCompletionListener(this);

		this.playListView = (ListView) findViewById(R.id.playListView);
		this.playListView.setSelector(drawable.list_selector_background);
		this.listItems = new LinkedList<HashMap<String, String>>();
		this.listAdapter = new SimpleAdapter(this, this.listItems,
				R.layout.row, new String[] { TITLE, ARTIST, ALBUM }, new int[] {
						R.id.songTitle, R.id.songArtist, R.id.songAlbum });
		this.playListView.setAdapter(listAdapter);
		this.playListView.setItemsCanFocus(true);
		this.playListView.setOnItemClickListener(this);
		this.populatePlayList();

		this.currentSong = 0;
		this.status = PAUSE;

		this.setPlayerButtons();
	}

	/**
	 * Listener for play/pause button
	 * 
	 * @param view
	 * @throws IOException
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 */
	public void playPauseClicked(View view) {
		// If there are no available songs, the button shouldn't be enabled
		if (this.currentSong >= this.listItems.size()
				&& !this.player.isPlaying()) {
			this.playPause.setEnabled(false);
			return;
		}

		if (this.status.equals(PAUSE)) {
			App.logger.debug("Playing current song");
			this.playCurrentSong();
		} else {
			App.logger.debug("Pausing current song");
			this.player.pause();
			this.setStatus(PAUSE);
		}
	}

	public void previousClicked(View view) {
		this.stopIfPlaying();
		this.currentSong--;
		if (this.currentSong >= 0) {
			this.playCurrentSong();
		}
		this.setPlayerButtons();
	}

	public void nextClicked(View view) {
		this.stopIfPlaying();
		this.currentSong++;
		if (this.currentSong >= this.listItems.size()) {
			this.currentSong = 0;
			this.setPlayerButtons();
			this.setStatus(PAUSE);
			return;
		} else {
			this.playCurrentSong();
		}
		this.setPlayerButtons();
	}

	public void stopIfPlaying() {
		if (this.player.isPlaying())
			this.player.stop();
		this.player.reset();
	}

	public void populatePlayList() {
		Iterator<SearchResult> iterator = Control.playlist.iterator();
		HashMap<String, String> item;
		SearchResult result;
		while (iterator.hasNext()) {
			result = iterator.next();
			item = new HashMap<String, String>();
			item.put(TITLE, result.getSong().getTitle());
			item.put(ARTIST, result.getSong().getArtist());
			item.put(ALBUM, result.getSong().getAlbum());
			item.put(HASH, result.getSong().getHash());
			PlayerActivity.this.listItems.add(item);
		}
	}

	public void onCompletion(MediaPlayer arg0) {
		this.nextClicked(this.next);
	}

	public void playCurrentSong() {
		this.playListView.setSelection(this.currentSong);
		this.setStatus(PLAY);
		// Get currentSong file path
		String path = App.getProperty("DestFilePath") + "/"
				+ this.listItems.get(this.currentSong).get(HASH);

		try {
			App.logger.debug("Setting datasource: " + path);
			this.player.setDataSource(path);
			this.player.prepare();
			this.player.start();
		} catch (IllegalArgumentException e) {
			App.logger.error("Error playing: ", e);
		} catch (IllegalStateException e) {
			App.logger.error("Error playing: ", e);
		} catch (IOException e) {
			App.logger.error("Error playing: ", e);
		}
	}

	public void setPlayerButtons() {
		// Previous Button
		if (this.listItems.size() > 0 && this.currentSong > 0)
			this.previous.setEnabled(true);
		else
			this.previous.setEnabled(false);

		// Play/Pause Button
		if (this.listItems.size() > 0
				&& this.listItems.size() > this.currentSong)
			this.playPause.setEnabled(true);
		else
			this.playPause.setEnabled(false);

		// Next Button
		if (this.listItems.size() > this.currentSong + 1) {
			this.next.setEnabled(true);
		} else
			this.next.setEnabled(false);
	}

	public void changeStatus() {
		if (this.status.equals(PAUSE)) {
			this.setStatus(PLAY);
		} else {
			this.setStatus(PAUSE);
		}
	}

	public void setStatus(Integer status) {
		if (status.equals(PAUSE)) {
			this.status = PAUSE;
			this.playPause.setImageResource(drawable.ic_media_play);

		} else {
			this.status = PLAY;
			this.playPause.setImageResource(drawable.ic_media_pause);
		}
	}

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		this.stopIfPlaying();
		this.currentSong = position;
		this.setPlayerButtons();
		this.playCurrentSong();
	}
}
