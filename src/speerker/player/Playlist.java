package speerker.player;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import javazoom.jl.decoder.JavaLayerException;


public class Playlist {
	
	ArrayList<Object> list;
	SpeerkerPlayer player;
	int current;
	
	
	public Playlist(SpeerkerPlayer p){
		player = p;
		list = new ArrayList<Object>(0);
	}

	public void setPlayer(SpeerkerPlayer p){
		player = p;
	}
	
	public void add(String song){
		
		if (list.size()==0) current = 0;
		list.add(song);
		player.setEnableControls(true);
		if (list.size()==1)
			try {
				player.loadSong((String) list.get(0));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JavaLayerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (current == 0) player.setEnablePrev(false);
			else player.setEnablePrev(true);
			
			if (current == list.size()-1) player.setEnableNext(false);
			else player.setEnableNext(true);
	}
	
	public boolean play(int i){
		
		if (i <= list.size()-1){
			
			try {
				player.loadSong((String) list.get(i));
				player.play();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JavaLayerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			current = i;
			
			if (current == 0) player.setEnablePrev(false);
			else player.setEnablePrev(true);
			
			if (current == list.size()-1) player.setEnableNext(false);
			else player.setEnableNext(true);
			
		}
		
		return false;
		
	}

	public void next() {
		
		play(current+1);
		
	}

	public void prev() {
		
		play(current-1);
		
	}
}
