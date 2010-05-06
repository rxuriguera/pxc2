package speerker;

import java.io.FileNotFoundException;

import org.eclipse.swt.widgets.Display;

import speerker.inter.Login;
import speerker.inter.SpeerkerInter;
import speerker.player.Playlist;
import speerker.player.SpeerkerPlayer;

import javazoom.jl.decoder.JavaLayerException;


public class Speerker {

	private static SpeerkerInter speerkerInter;
	private static Tools tools;

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 * @throws JavaLayerException 
	 */
	public static void main(String[] args) throws FileNotFoundException, JavaLayerException {
		
		Display display = new Display();
		
		String[] loginData = Login.show(display);
		
		tools = new Tools();
		
		speerkerInter = new SpeerkerInter(tools, display);
		

	}

}