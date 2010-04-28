package speerker;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import speerker.inter.Login;
import speerker.inter.PlayerInter;
import speerker.inter.PlaylistInter;
import speerker.inter.SearchSlotInter;
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
		
		String title0 = "La llave de oro";
		String artist0 = "Los Planetas";
		String album0 = "Una opera egipcia";
		String path0 = "/Users/bartru/Music/Los Planetas - Una opera egipcia/01. La llave de oro.mp3";
		
		String title1 = "Una corona de estrellas";
		String artist1 = "Los Planetas";
		String album1 = "Una opera egipcia";
		String path1 = "/Users/bartru/Music/Los Planetas - Una opera egipcia/02. Una corona de estrellas.mp3";
		
		String title2 = "Soy un pobre granaino";
		String artist2 = "Los Planetas";
		String album2 = "Una opera egipcia";
		String path2 = "/Users/bartru/Music/Los Planetas - Una opera egipcia/03. Soy un pobre granaino.mp3";
		
		String title3 = "Siete Faroles";
		String artist3 = "Los Planetas";
		String album3 = "Una opera egipcia";
		String path3 = "/Users/bartru/Music/Los Planetas - Una opera egipcia/04. Siete Faroles.mp3";
		
		String title4 = "No se como te atreves";
		String artist4 = "Los Planetas";
		String album4 = "Una opera egipcia";
		String path4 = "/Users/bartru/Music/Los Planetas - Una opera egipcia/05. No se como te atreves.mp3";
		
		Playlist playlist = new Playlist();
		SpeerkerPlayer player = new SpeerkerPlayer();
		
		playlist.setPlayer(player);
		player.setPlaylist(playlist);

		Display display = new Display();
		Display.setAppName("Speerker");
		Shell shell = new Shell(display, SWT.TITLE | SWT.CLOSE | SWT.BORDER | SWT.ON_TOP);
		
		shell.setText("Login");
		shell.setSize(700, 500);
		shell.setLocation(400, 500);
		GridLayout shellLayout = new GridLayout(1, false);
		shell.setLayout(shellLayout);
		
		Composite compoSearch = new Composite(shell, SWT.NONE);
		Composite compoPlaylist = new Composite(shell, SWT.NONE);
		Composite compoInter = new Composite(shell, SWT.NONE);
	
		SearchSlotInter searchSlotInter = new SearchSlotInter(compoSearch, display); 
		PlaylistInter playlistInter = new PlaylistInter(compoPlaylist, display, playlist);
		PlayerInter playerInter = new PlayerInter(compoInter, display, player);
		
		playlist.add(title0,artist0,album0,path0);
		playlist.add(title1,artist1,album1,path1);
		playlist.add(title2,artist2,album2,path2);
		playlist.add(title3,artist3,album3,path3);
		playlist.add(title4,artist4,album4,path4);
			
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		shell.dispose();

	}
}