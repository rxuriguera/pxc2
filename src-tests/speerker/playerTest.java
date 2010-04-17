package speerker;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.jlp;

public class playerTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String[] args1 = new String[1];
		args1[0] = "/Users/bartru/Music/Broken Bells - Broken Bells/02 Vaporize.mp3";
		jlp player = jlp.createInstance(args1);
		try
		{
			player.play();
			//assertTrue("Play",true);	
		}
		catch (JavaLayerException e)
		{
			e.printStackTrace();
			//assertTrue("JavaLayerException : "+e.getMessage(),false);			
		}

	}
}