package speerker.player;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import javazoom.jl.decoder.JavaLayerException;


public class Playlist {
	
	ArrayList<Object> list;
	
	SpeerkerPlayer player;
	
	public Playlist(SpeerkerPlayer p){
		player = p;
		list = new ArrayList<Object>(0);
	}

	public void setPlayer(SpeerkerPlayer p){
		player = p;
	}
	
	public void add(String song){
		list.add(song);
		if (list.size()>0) player.enableControls();
		if (list.size()==1)
			try {
				player.loadSong((String) list.get(0));
				player.disablePrevButton();
				player.disableNextButton();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JavaLayerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public boolean play(int i){
		
		if (list.size() > i+1 ){
			try {
				player.loadSong((String) list.get(i));
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JavaLayerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return false;
		
	}
}
