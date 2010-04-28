package speerker.player;

import java.io.File;
import java.io.FileNotFoundException;

import speerker.inter.PlayerInter;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

public class SpeerkerPlayer{
	
	String songPath;
    
    BasicPlayer player;
    PlayerListener listener;
    
    Playlist playlist;
  
    PlayerInter inter;
    
    
    public SpeerkerPlayer() {
    	inter = null;
    	playlist = null;
	}
    
    public void loadSong(String song) throws FileNotFoundException, JavaLayerException{
    	
    	songPath = song;
		
		player = new BasicPlayer();
		
		listener = new PlayerListener(this);
		player.addBasicPlayerListener(listener);
		
		try {
			player.open(new File(songPath));
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		inter.setEnableScale(true);

    }
    
    public void play(){
    	
    	try {
    		if (player.getStatus()==BasicPlayer.OPENED||player.getStatus()==BasicPlayer.STOPPED) player.play();
    		else if (player.getStatus()==BasicPlayer.PAUSED) player.resume();
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
    
    public void pause(){
    	
    	try {
			player.pause();
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    public void move(int pos){
    	
    	boolean restart = false;
    	if (player.getStatus()==BasicPlayer.PLAYING) restart = true;
    	player.removeBasicPlayerListener(listener);
		try {
			player.stop();
			loadSong(songPath);
			player.seek(pos);
			if (restart) player.play();
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JavaLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void stop(){
  
    	try {
    		player.removeBasicPlayerListener(listener);
    		player.stop();
    		if (inter!=null) inter.setScaleValue(0);
    		loadSong(songPath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JavaLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

	public void setPlaylist(Playlist p){
		playlist = p;
	}
	
	public void setInter(PlayerInter i){
		inter = i;
	}

	public void setMaximumScale(int bytesLength) {
		if (inter!=null) inter.setMaximumScale(bytesLength);
		
	}

	public void setScaleValue(int i) {
		if (inter!=null) inter.setScaleValue(i);
		
	}

	public void next() {
		player.removeBasicPlayerListener(listener);
		try {
			player.stop();
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (inter!=null) inter.setScaleValue(0);
		if (playlist != null) playlist.next();
		
	}

	public void prev() {
		player.removeBasicPlayerListener(listener);
		try {
			player.stop();
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (inter!=null) inter.setScaleValue(0);
		if(playlist != null) playlist.prev();
		
	}

	public void setEnableControls(boolean set) {
		
		if (inter != null) {
			inter.setEnablePlay(set);
			inter.setEnablePause(set);
			inter.setEnableStop(set);
		}
		
	}

	public void setEnablePrev(boolean set) {
		
		if (inter != null) inter.setEnablePrev(set);
		
	}
	
	public void setEnableNext(boolean set) {
		
		if (inter != null) inter.setEnableNext(set);
		
	}
    
}