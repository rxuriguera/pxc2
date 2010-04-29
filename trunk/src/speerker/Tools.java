package speerker;

import speerker.player.Playlist;
import speerker.player.SpeerkerPlayer;

public class Tools {

	private Playlist playlist;
	private SpeerkerPlayer player;

	public Tools(){
		playlist = new Playlist();
		player = new SpeerkerPlayer();
		
		playlist.setPlayer(player);
		player.setPlaylist(playlist);
	}

	public SpeerkerPlayer getPlayer() {
		return player;
	}
	
	public Playlist getPlaylist() {
		return playlist;
	}
	
}
