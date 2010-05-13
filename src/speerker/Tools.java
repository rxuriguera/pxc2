package speerker;

import speerker.p2p.SpeerkerP2PLayer;
import speerker.player.Playlist;
import speerker.player.SpeerkerPlayer;
import speerker.rmi.SpeerkerRMIClient;
import speerker.search.SearchManager;
import speerker.types.User;

public class Tools {

	private Playlist playlist;
	private SpeerkerPlayer player;
	private SearchManager searchManager;
	private SpeerkerRMIClient speerkerRMIClient;
	private User user;

	public Tools(){
		playlist = new Playlist();
		player = new SpeerkerPlayer();
		playlist.setPlayer(player);
		player.setPlaylist(playlist);
		
		user = new User();
		speerkerRMIClient = new SpeerkerRMIClient();
		
		
		
	}
	
	public void iniP2P(){
		searchManager =  new SearchManager(this);
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
	
	public SpeerkerRMIClient getSpeerkerRMIClient() {
		return speerkerRMIClient;
	}
	
	public User getUser() {
		return user;
	}
	
}
