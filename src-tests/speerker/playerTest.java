package speerker;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class playerTest {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 * @throws JavaLayerException 
	 */
	public static void main(String[] args) throws FileNotFoundException, JavaLayerException {
		
		String song="/Users/bartru/Music/Metallica - Enter Sandman/Metallica - Enter Sandman.mp3";

		FileInputStream fis     = new FileInputStream(song);
        BufferedInputStream bis = new BufferedInputStream(fis);
		
		AdvancedPlayer player = new AdvancedPlayer(bis);
		player.play(1000,200000);

	}
}