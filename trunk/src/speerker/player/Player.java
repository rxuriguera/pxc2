package speerker.player;

import java.io.File;
import java.io.FileNotFoundException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Scale;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

public class Player{
	
	String songPath;
    
    BasicPlayer player;
	BasicController control;
    
    Composite comp;
    GridData gridComposite;
    GridLayout playerLayout;
    GridData gridButtonPlay;
    GridData gridButtonPause;
    GridData gridButtonStop;
    GridData gridScale;
    Button buttonPlay;
    Button buttonStop;
    Button buttonPause;
    Scale scale;
    
    
    public Player(Shell shell) {
    	
    	comp = new Composite(shell, SWT.NONE);
    	gridComposite = new GridData(GridData.FILL_HORIZONTAL);
    	comp.setLayoutData(gridComposite);
    	
    	playerLayout = new GridLayout(4, false);
    	comp.setLayout(playerLayout);
    	
    	gridButtonPlay = new GridData();
    	gridButtonPause = new GridData();
    	gridButtonStop = new GridData();
    	
    	buttonPlay = new Button(comp, SWT.PUSH);
    	buttonPlay.setText("Play");
    	buttonPlay.setEnabled(false);
    	buttonPlay.pack();
    	buttonPlay.setLayoutData(gridButtonPlay);
    	
    	buttonPause = new Button(comp, SWT.PUSH);
    	buttonPause.setText("Pause");
    	buttonPause.setEnabled(false);
    	buttonPause.pack();
    	buttonPause.setLayoutData(gridButtonPause);
    	
    	buttonStop = new Button(comp, SWT.PUSH);
    	buttonStop.setText("Stop");
    	buttonStop.setEnabled(false);
    	buttonStop.pack();
    	buttonStop.setLayoutData(gridButtonStop);
    	
    	buttonPlay.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				play();
			}
		});
    	
    	buttonPause.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				pause();
			}
		});
    	
    	buttonStop.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				stop();
			}
		});
    	
    	scale = new Scale(comp, SWT.NONE);
    	scale.setMaximum (100);
    	scale.setPageIncrement (1);
    	scale.setEnabled(false);
    	gridScale = new GridData(GridData.FILL_HORIZONTAL);
    	scale.setLayoutData(gridScale);
 
	}
    
    public void loadSong(String song) throws FileNotFoundException, JavaLayerException{
    	
    	songPath = song;
    	
		buttonPlay.setEnabled(true);
		buttonPause.setEnabled(true);
		buttonStop.setEnabled(true);
		scale.setEnabled(true);
		
		player = new BasicPlayer();
		control = (BasicController) player;
		
		try {
			control.open(new File(songPath));
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
    
    private void play(){
    	
    	try {
			control.play();
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
    
    private void pause(){
    	
    	try {
			control.pause();
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    private void stop(){
    	
    	try {
			control.stop();
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
}