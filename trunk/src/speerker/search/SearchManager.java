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
		//speerkerP2PLayer =  new SpeerkerP2PLayer(null, null, 0);
	
	}
	
	public void newSearch(String field){
		
		++count;
		//speerkerP2PLayer.search(Integer.toString(count), field);
		System.out.println("se");
		
	}
	

	
	class Search {
		
		int id;
		String field;
		HashMap<String, SearchResult> results;
		
		public Search(int i, HashMap<String, SearchResult> h){
			id = i;
			results = h;
		}
	}
}
