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

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import speerker.App;
import speerker.p2p.SearchResult;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class SearchActivity extends SpeerkerActivity implements
		OnItemClickListener {

	protected EditText search;
	protected ImageButton searchButton;
	protected List<HashMap<String, String>> listItems;
	protected ListView resultsListView;
	private SimpleAdapter resultsAdapter;

	private String query = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);

		this.search = (EditText) findViewById(R.id.searchEditText);
		this.searchButton = (ImageButton) findViewById(R.id.searchButton);
		this.resultsListView = (ListView) findViewById(R.id.resultsListView);

		this.listItems = new LinkedList<HashMap<String, String>>();

		// Set search results list adapter
		this.resultsAdapter = new SimpleAdapter(this, this.listItems,
				R.layout.row, new String[] { TITLE, ARTIST, ALBUM }, new int[] {
						R.id.songTitle, R.id.songArtist, R.id.songAlbum });
		this.resultsListView.setAdapter(resultsAdapter);
		this.resultsListView.setItemsCanFocus(true);

		this.resultsListView.setOnItemClickListener(this);
	}

	public void searchClicked(View view) {
		String query = this.search.getText().toString();

		// Clear past search results
		this.listItems.clear();

		try {
			App.logger.info("Starting search for: " + query);
			this.query = query;
			Control.searchManager.newSearch(query);
			new ResultsRefreshTask().execute(query);
		} catch (Exception e) {
			App.logger.error("Error searching: ", e);
			Control.toastMessage(this, "Exception: " + e.toString());
		}
	}

	private class ResultsRefreshTask extends AsyncTask<String, String, Void> {
		@Override
		protected void onPreExecute() {
			Control.toastMessage(SearchActivity.this, "Waiting for results...");
		}

		@Override
		protected Void doInBackground(String... queries) {
			Integer waitPeriods = 5;
			Integer periodTime = 2000;
			for (int i = 0; i < waitPeriods; i++) {
				try {
					Thread.sleep(periodTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				this.publishProgress(queries);
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(String... queries) {
			try {
				SearchActivity.this.listItems.clear();
				HashMap<String, SearchResult> results = Control.searchManager
						.getResults().get(queries[0]);

				if (results == null) {
					App.logger.error("No results");
				}

				App.logger.debug("Updating search results. Total results: "
						+ results.size());

				SearchResult result;
				Iterator<SearchResult> iterator = results.values().iterator();
				HashMap<String, String> item;

				while (iterator.hasNext()) {
					result = iterator.next();
					item = new HashMap<String, String>();
					item.put(TITLE, result.getSong().getTitle());
					item.put(ARTIST, result.getSong().getArtist());
					item.put(ALBUM, result.getSong().getAlbum());
					item.put(HASH, result.getSong().getHash());
					App.logger.debug("Adding result:\n\tSong title: "
							+ result.getSong().getTitle() + "\n\tArtist: "
							+ result.getSong().getArtist() + "\n\tAlbum: "
							+ result.getSong().getAlbum());
					SearchActivity.this.listItems.add(item);
				}

				SearchActivity.this.resultsAdapter.notifyDataSetChanged();
			} catch (Exception e) {
				App.logger.error("Error searching: ", e);
				Control.toastMessage(SearchActivity.this, "Exception: "
						+ e.toString());
			}
		}
	}

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		try {
			final String hash = this.listItems.get(position).get(HASH);
			App.logger.info("File " + hash + " selected");
			// Get file in a new thread so we can change activities and do
			// more searches
			(new Thread() {
				public void run() {
					Looper.prepare();
					Control.searchManager.getFile(SearchActivity.this.query,
							hash);
				}
			}).start();
			Control.toastMessage(this, "Adding song to playlist");
		} catch (Exception e) {
			App.logger.error("Error getting file:", e);
			Control.toastMessage(this, "Could not get song");
		}
	}

}
