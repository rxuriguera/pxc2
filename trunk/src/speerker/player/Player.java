package speerker.player;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
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
import javazoom.jl.player.advanced.AdvancedPlayer;

public class Player{
	
	String status;
	boolean playing;
	
	String songPath;
	FileInputStream fis;
    BufferedInputStream bis;
    AdvancedPlayer player;
    
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
    	
    	status = "ini";
 
	}
    
    public void loadSong(String song) throws FileNotFoundException, JavaLayerException{
    	
    	songPath = song;
    	
		buttonPlay.setEnabled(true);
		buttonPause.setEnabled(true);
		buttonStop.setEnabled(true);
		scale.setEnabled(true);
		
		
		playing = false;
		status = "stop";
		
    }
    
    private void play(){
    	
    	if (status.equals("stop")){
    		try {
				fis = new FileInputStream(songPath);
				bis = new BufferedInputStream(fis);
				player = new AdvancedPlayer(bis);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JavaLayerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	    
			playing = true;
			PlayerThread pt = new PlayerThread();
			pt.start();
			status = "play";
		}else if (status.equals("pause")) {
			playing = true;
			status = "play";
		}
    }
    
    private void pause(){
    	
    	if (status.equals("play")){
    		playing = false;
    		status = "pause";
		}
    	
    }
    
    private void stop(){
    	
    	if (status.equals("play")||status.equals("pause")){
    		
		}
    	
    }
    
    class PlayerThread extends Thread {
		public void run(){
			try {
				while(playing){
					System.out.println("si");
					player.play(50);
				}
			}
			catch( Exception e ) {
			e.printStackTrace();
			}
		}
    }
	

    
}