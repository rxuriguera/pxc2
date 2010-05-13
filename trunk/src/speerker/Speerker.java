package speerker;

import java.io.FileNotFoundException;

import org.eclipse.swt.widgets.Display;

import speerker.inter.Login;
import speerker.inter.SpeerkerInter;
import speerker.player.Playlist;
import speerker.player.SpeerkerPlayer;
import speerker.types.User;

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
		
		tools = new Tools();
		
		boolean login = false;
		login = Login.show(display, tools);
		
		while(!login){};
		
		tools.iniP2P();
		speerkerInter = new SpeerkerInter(tools, display);
		
		

	}

}