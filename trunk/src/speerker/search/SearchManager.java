package speerker.search;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;

import speerker.App;
import speerker.Tools;
import speerker.p2p.SearchResult;
import speerker.p2p.SpeerkerP2PLayer;
import speerker.player.Playlist;
import speerker.types.Play;

public class SearchManager {
	
	private SpeerkerP2PLayer speerkerP2PLayer;
	private HashMap<String, HashMap<String, SearchResult>> results;
	private Playlist playlist;
	private Tools tools;
	
	
	public SearchManager(Tools t){
		
		tools = t;
		
		speerkerP2PLayer =  new SpeerkerP2PLayer();
		results = speerkerP2PLayer.getSearchResults();
		playlist = tools.getPlaylist();
	
	}
	
	public void newSearch(String field){
	
		speerkerP2PLayer.search(field);
		
	}
	
	public void getFile(final String field, final String hash){
		
		speerkerP2PLayer.getFile(speerkerP2PLayer.getSearchResults().get(field).get(hash));
		new Thread () {
			public void run () {
				File f =  new File(App.getProperty("DestFilePath")+"/"+ hash);
				while(f.length()==0)
					try {
						sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				playlist.add(speerkerP2PLayer.getSearchResults().get(field).get(hash).getSong().getTitle(), speerkerP2PLayer.getSearchResults().get(field).get(hash).getSong().getArtist(), speerkerP2PLayer.getSearchResults().get(field).get(hash).getSong().getAlbum(), speerkerP2PLayer.getSearchResults().get(field).get(hash).getSong().getSize(), f.getAbsolutePath());
			}
		}.start ();
		
		Play p = new Play(tools.getUser().getUsername(), speerkerP2PLayer.getSearchResults().get(field).get(hash).getSong());
		tools.getSpeerkerRMIClient().sendPlay(p);
		
		
	}
	
	public HashMap<String, HashMap<String, SearchResult>> getResults(){
		return this.results;
	}
	
	
	
}
