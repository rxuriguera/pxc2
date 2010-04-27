package speerker.inter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;

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
    
    Shell shell;
    Display display;
    
    SpeerkerPlayer player;
    
    public PlayerInter(Shell s, Display d) {
    	
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

}
