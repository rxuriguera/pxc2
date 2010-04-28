package speerker.inter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;

import speerker.player.SpeerkerPlayer;

public class PlayerInter {
	
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
    
    Boolean scaleMove;
    
    Shell shell;
    Display display;
    
    SpeerkerPlayer player;
    
    public PlayerInter(Composite compoInter, Display d, SpeerkerPlayer p) {
    	
    	player = p;
    	display = d;
    	
    	comp = compoInter;
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
				player.play();
			}
		});
    	
    	buttonPause.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				player.pause();
			}
		});
    	
    	buttonStop.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				player.stop();
			}
		});
    	
    	buttonNext.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				player.next();
			}
		});
    	
    	buttonPrev.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				player.prev();
			}
		});
    	
    	scale = new Scale(comp, SWT.NONE);
    	scale.setPageIncrement (1);
    	scale.setEnabled(false);
    	gridScale = new GridData(GridData.FILL_HORIZONTAL);
    	scale.setLayoutData(gridScale);
    	
    	scaleMove = true;
    	
    	scale.addListener (SWT.MouseDown, new Listener () {
			public void handleEvent (Event event) {
				scaleMove = false;
			}
		});
    	
    	scale.addListener (SWT.MouseUp, new Listener () {
			public void handleEvent (Event event) {
				player.move(scale.getSelection());
				scaleMove = true;
			}
		});
    	
    	player.setInter(this);
 
	}
    
    public void setScaleValue(final int i) {
		display.syncExec(
				new Runnable() {
					public void run(){
						if (scaleMove) scale.setSelection(i);
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
	
	public void setEnablePlay(final boolean set) {
		display.syncExec(
				new Runnable() {
					public void run(){
						buttonPlay.setEnabled(set);
				    }
				});
		
	}

	public void setEnablePause(final boolean set) {
		display.syncExec(
				new Runnable() {
					public void run(){
						buttonPause.setEnabled(set);
				    }
				});
		
	}
	
	public void setEnableStop(final boolean set) {
		display.syncExec(
				new Runnable() {
					public void run(){
						buttonStop.setEnabled(set);
				    }
				});
		
	}
	
	public void setEnableNext(final boolean set) {
		display.syncExec(
				new Runnable() {
					public void run(){
						buttonNext.setEnabled(set);
				    }
				});
		
	}
	
	public void setEnablePrev(final boolean set) {
		display.syncExec(
				new Runnable() {
					public void run(){
						buttonPrev.setEnabled(set);
				    }
				});
		
	}

	public void setEnableScale(final boolean set) {
		display.syncExec(
				new Runnable() {
					public void run(){
						scale.setEnabled(set);
				    }
				});
		
	}
}
