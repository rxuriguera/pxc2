package speerker.player;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class Player {
	
	String songPath;
	FileInputStream fis;
    BufferedInputStream bis;
    AdvancedPlayer player;
    
    public Player() {
	}
    
    public void loadSong(String song) throws FileNotFoundException, JavaLayerException{
    	
    	songPath = song;
    	fis = new FileInputStream(song);
        BufferedInputStream bis = new BufferedInputStream(fis);
		player = new AdvancedPlayer(bis);
    	
    }
    
    
	

}
