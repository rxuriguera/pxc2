package speerker.mobile;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import speerker.App;
import speerker.p2p.SearchResult;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SearchActivity extends SpeerkerActivity {

	protected EditText search;
	protected Button searchButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);

		this.search = (EditText) findViewById(R.id.searchEditText);
		this.searchButton = (Button) findViewById(R.id.searchButton);
	}

	public void searchClicked(View view) {
		String query = this.search.getText().toString();

		try {
			Control.searchManager.newSearch(query);
		} catch (Exception e) {
			App.logger.error("Error searching: " + e.toString());
			Control.toastMessage(this, "Exception: " + e.toString());
		}

		new ResultsRefreshTask().execute();
	}

	private class ResultsRefreshTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			Control.toastMessage(SearchActivity.this, "Waiting for results...");
		}

		@Override
		protected Void doInBackground(Void... params) {
			Integer waitPeriods = 5;
			Integer periodTime = 2000;
			for (int i = 0; i < waitPeriods; i++) {
				try {
					Thread.sleep(periodTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				this.publishProgress(params);
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			Control.toastMessage(SearchActivity.this, "Updated results...");
		}
	}

}
