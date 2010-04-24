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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Scale;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

public class Player{
	
	String songPath;
    
    BasicPlayer player;
    PlayerListener listener;
    
    Composite comp;
    GridData gridComposite;
    GridLayout playerLayout;
    GridData gridButtonPlay;
    GridData gridButtonPause;
    GridData gridButtonStop;
    GridData gridButtonNext;
    GridData gridButtonPrev;
    GridData gridScale;
    Button buttonPlay;
    Button buttonStop;
    Button buttonPause;
    Button buttonNext;
    Button buttonPrev;
    Scale scale;
    
    Shell shell;
    Display display;
    
    
    public Player(Shell s, Display d) {
    	
    	shell = s;
    	display = d;
    	
    	comp = new Composite(shell, SWT.NONE);
    	gridComposite = new GridData(GridData.FILL_HORIZONTAL);
    	comp.setLayoutData(gridComposite);
    	
    	playerLayout = new GridLayout(6, false);
    	comp.setLayout(playerLayout);
    	
    	gridButtonPlay = new GridData();
    	gridButtonPause = new GridData();
    	gridButtonStop = new GridData();
    	gridButtonNext = new GridData();
    	gridButtonPrev = new GridData();
    	
    	buttonPrev = new Button(comp, SWT.PUSH);
    	buttonPrev.setText("<<");
    	buttonPrev.setEnabled(false);
    	buttonPrev.pack();
    	buttonPrev.setLayoutData(gridButtonPrev);
    	
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
    	
    	buttonNext = new Button(comp, SWT.PUSH);
    	buttonNext.setText(">>");
    	buttonNext.setEnabled(false);
    	buttonNext.pack();
    	buttonNext.setLayoutData(gridButtonNext);
    	
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
    	scale.setPageIncrement (1);
    	scale.setEnabled(false);
    	gridScale = new GridData(GridData.FILL_HORIZONTAL);
    	scale.setLayoutData(gridScale);
    	
    	scale.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				move();
			}
		});
 
	}
    
    public void loadSong(String song) throws FileNotFoundException, JavaLayerException{
    	
    	songPath = song;
    	
		buttonPlay.setEnabled(true);
		buttonPause.setEnabled(true);
		buttonStop.setEnabled(true);
		scale.setEnabled(true);
		
		player = new BasicPlayer();
		
		listener = new PlayerListener(this,display);
		player.addBasicPlayerListener(listener);
		
		try {
			player.open(new File(songPath));
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

    }
    
    private void play(){
    	
    	try {
    		if (player.getStatus()==BasicPlayer.OPENED||player.getStatus()==BasicPlayer.STOPPED) player.play();
    		else if (player.getStatus()==BasicPlayer.PAUSED) player.resume();
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
    
    private void pause(){
    	
    	try {
			player.pause();
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    private void move(){
    	
    	int pos = scale.getSelection();
    	boolean restart = false;
    	if (player.getStatus()==BasicPlayer.PLAYING) restart = true;
    	player.removeBasicPlayerListener(listener);
		try {
			player.stop();
			loadSong(songPath);
			player.seek(pos);
			//if (restart) player.play();
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
    
    private void stop(){
  
    	try {
    		player.removeBasicPlayerListener(listener);
    		player.stop();
    		scale.setSelection(0);
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

	public void setScaleValue(final int i) {
		display.syncExec(
				new Runnable() {
					public void run(){
						scale.setSelection(i);
				    }
				});
		
		
		
	}

	public void setMaximumScale(final int bytesLength) {
		display.syncExec(
				new Runnable() {
					public void run(){
						scale.setMaximum(bytesLength);
				    }
				});
		
	}
    
}