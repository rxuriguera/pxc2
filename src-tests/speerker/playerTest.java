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
import speerker.player.Player;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class playerTest {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 * @throws JavaLayerException 
	 */
	public static void main(String[] args) throws FileNotFoundException, JavaLayerException {
		
		String song="/Users/bartru/Music/Crystal Castles - Crystal Castles [2010]/01 - Fainting Spells.mp3";

		Display display = new Display();
		Display.setAppName("Speerker");
		Shell shell = new Shell(display, SWT.TITLE | SWT.CLOSE | SWT.BORDER | SWT.ON_TOP);
		
		shell.setText("Login");
		shell.setSize(500, 500);
		shell.setLocation(400, 200);
		GridLayout shellLayout = new GridLayout(1, false);
		shell.setLayout(shellLayout);
		
		Player player = new Player(shell);
		
		player.loadSong(song);
		
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		shell.dispose();

	}
}