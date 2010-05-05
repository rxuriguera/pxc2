package speerker.search;

import java.util.Collection;
import java.util.HashMap;

import speerker.p2p.SearchResult;
import speerker.p2p.SpeerkerP2PLayer;

public class SearchManager {
	
	private SpeerkerP2PLayer speerkerP2PLayer;
	private HashMap<String, HashMap<String, SearchResult>> results;
	
	public SearchManager(){
		
		speerkerP2PLayer =  new SpeerkerP2PLayer();
		results = speerkerP2PLayer.getSearchResults();
	
	}
	
	public void newSearch(String field){
	
		speerkerP2PLayer.search(field);
		
	}
	
	public HashMap<String, HashMap<String, SearchResult>> getResults(){
		return this.results;
	}
	
	
	
}
