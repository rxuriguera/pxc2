package speerker;

import speerker.p2p.SpeerkerP2PLayer;
import speerker.player.Playlist;
import speerker.player.SpeerkerPlayer;
import speerker.search.SearchManager;

public class Tools {

	private Playlist playlist;
	private SpeerkerPlayer player;
	private SpeerkerP2PLayer speerkerP2PLayer;
	private SearchManager searchManager;

	public Tools(){
		playlist = new Playlist();
		player = new SpeerkerPlayer();
		playlist.setPlayer(player);
		player.setPlaylist(playlist);
		
		
		searchManager =  new SearchManager(playlist);

		
	}

	public SpeerkerPlayer getPlayer() {
		return player;
	}
	
	public Playlist getPlaylist() {
		return playlist;
	}
	
	public SearchManager getSearchManager() {
		return searchManager;
	}
	
}
