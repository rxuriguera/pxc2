package speerker.player;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import speerker.Tools;
import speerker.inter.PlaylistInter;
import speerker.types.Play;

import javazoom.jl.decoder.JavaLayerException;


public class Playlist {
	
	ArrayList<Song> list;
	
	SpeerkerPlayer player;
	PlaylistInter inter;
	Tools tools;
	
	int current;
	
	
	public Playlist(){
		list = new ArrayList<Song>(0);
		current = -1;
	}

	public void setPlayer(SpeerkerPlayer p){
		player = p;
	}
	
	public void add(String t, String a, String alb, Long songSize, String p){
		
		if (list.size()==0) current = 0;
		list.add(new Song(t,a,alb,p));
		player.setEnableControls(true);
		if (list.size()==1)
			try {
				player.loadSong((String) list.get(0).path);
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
			
		if (inter != null) inter.refreshTable();
	}
	
	public boolean play(int i){
		
		player.stop();
		
		if (i <= list.size()-1){
		
			try {
				player.loadSong((String) list.get(i).path);
				player.play();
				
				Play p = new Play();
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
				
			if (inter != null) inter.refreshTable();
		}
		
		
		
		return false;
		
	}

	public void next() {
		
		play(current+1);
		
	}

	public void prev() {
		
		play(current-1);
		
	}

	public void setInter(PlaylistInter p) {
		inter = p;
		
	}
	
	public int getSize(){
		return list.size();
	}
	
	public int getCurrent(){
		return current;
	}
	
	public String getTitle(int i){
		return list.get(i).title;
	}
	
	public String getArtist(int i){
		return list.get(i).artist;
	}
	
	public String getAlbum(int i){
		return list.get(i).album;
	}
	
	public String getPath(int i){
		return list.get(i).path;
	}
}

class Song {
	
	String title;
	String artist;
	String album;
	String path;
	
	public Song(String t, String a, String alb, String p){
		title = t;
		artist = a;
		album = alb;
		path = p;
		
		
	}
	
	
}
