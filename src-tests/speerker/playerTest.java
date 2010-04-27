package speerker;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import speerker.inter.Login;
import speerker.player.SpeerkerPlayer;
import speerker.player.Playlist;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class playerTest {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 * @throws JavaLayerException 
	 */
	public static void main(String[] args) throws FileNotFoundException, JavaLayerException {
		
		String song0="/Users/bartru/Music/Crystal Castles - Crystal Castles [2010]/01 - Fainting Spells.mp3";
		String song1="/Users/bartru/Music/Crystal Castles - Crystal Castles [2010]/03 - Doe Deer.mp3";
		String song2="/Users/bartru/Music/Crystal Castles - Crystal Castles [2010]/05 - Year of Silence.mp3";
		String song3="/Users/bartru/Music/Crystal Castles - Crystal Castles [2010]/11 - Pap Smear.mp3";
		
		Playlist playlist = new Playlist(null);

		Display display = new Display();
		Display.setAppName("Speerker");
		Shell shell = new Shell(display, SWT.TITLE | SWT.CLOSE | SWT.BORDER | SWT.ON_TOP);
		
		shell.setText("Login");
		shell.setSize(700, 500);
		shell.setLocation(400, 500);
		GridLayout shellLayout = new GridLayout(1, false);
		shell.setLayout(shellLayout);
		
		SpeerkerPlayer player = new SpeerkerPlayer(shell,display);
		playlist.setPlayer(player);
		
		playlist.add(song0);
		playlist.add(song1);
		playlist.add(song2);
		playlist.add(song3);
		
		
		
		
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		shell.dispose();

	}
}