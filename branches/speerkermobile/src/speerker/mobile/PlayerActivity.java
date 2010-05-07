package speerker.mobile;

import android.R.drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class PlayerActivity extends SpeerkerActivity {

	protected static Integer PLAY = 0;
	protected static Integer PAUSE = 1;

	protected ImageButton previous;
	protected ImageButton playPause;
	protected ImageButton next;

	protected Integer status;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.player);

		this.previous = (ImageButton) findViewById(R.id.previousButton);
		this.playPause = (ImageButton) findViewById(R.id.playPauseButton);
		this.next = (ImageButton) findViewById(R.id.nextButton);

		this.status = PAUSE;
	}

	/**
	 * Listener for play/pause button
	 * 
	 * @param view
	 */
	public void playPauseClicked(View view) {

		if (this.status.equals(PAUSE)) {
			this.playPause.setImageResource(drawable.ic_media_pause);
			this.status = PLAY;
		} else {
			this.playPause.setImageResource(drawable.ic_media_play);
			this.status = PAUSE;
		}
	}

}
