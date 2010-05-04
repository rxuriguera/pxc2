package speerker.search;

import java.util.HashMap;

import speerker.p2p.SearchResult;
import speerker.p2p.SpeerkerP2PLayer;
import speerker.player.Playlist;
import speerker.player.SpeerkerPlayer;

public class SearchManager {
	
	private SpeerkerP2PLayer speerkerP2PLayer;
	private int count;
	
	public SearchManager(){
		
		count = 0;
		speerkerP2PLayer =  new SpeerkerP2PLayer();
	
	}
	
	public void newSearch(String field){
	
		speerkerP2PLayer.search(field);
		System.out.println(speerkerP2PLayer.getSearchResults().values().size());
		
	}
	
}
