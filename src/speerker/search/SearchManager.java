package speerker.search;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;

import speerker.App;
import speerker.p2p.SearchResult;
import speerker.p2p.SpeerkerP2PLayer;
import speerker.player.Playlist;

public class SearchManager {
	
	private SpeerkerP2PLayer speerkerP2PLayer;
	private HashMap<String, HashMap<String, SearchResult>> results;
	private Playlist playlist;
	
	
	public SearchManager(Playlist p){
		
		speerkerP2PLayer =  new SpeerkerP2PLayer();
		results = speerkerP2PLayer.getSearchResults();
		playlist = p;
	
	}
	
	public void newSearch(String field){
	
		speerkerP2PLayer.search(field);
		
	}
	
	public void getFile(final String field, final String hash){
		
		System.out.println(field + "  " + hash);
		speerkerP2PLayer.getFile(speerkerP2PLayer.getSearchResults().get(field).get(hash));
		new Thread () {
			public void run () {
				File f =  new File(App.getProperty("DestFilePath")+"/"+ hash);
				while(!f.exists())
					try {
						sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				playlist.add(speerkerP2PLayer.getSearchResults().get(field).get(hash).getSong().getTitle(), speerkerP2PLayer.getSearchResults().get(field).get(hash).getSong().getArtist(), speerkerP2PLayer.getSearchResults().get(field).get(hash).getSong().getAlbum(), f.getAbsolutePath());
			}
		}.start ();
		
		
	}
	
	public HashMap<String, HashMap<String, SearchResult>> getResults(){
		return this.results;
	}
	
	
	
}
