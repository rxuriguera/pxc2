package speerker.inter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class MenuInter {
	
	private Composite composite;
	private Display display;
	private SpeerkerInter speerkerInter;
	private GridLayout menuLayout;
	private GridData gridButton;
	private Button buttonInicio;
	private Button buttonPlaylist;

	public MenuInter(Composite c, Display d, SpeerkerInter s){
		
		composite = c;
		display = d;
		speerkerInter = s;
		
		menuLayout = new GridLayout(1, false);
    	composite.setLayout(menuLayout);
    	
    	gridButton = new GridData(GridData.BEGINNING);
    	buttonInicio = new Button(composite, SWT.PUSH);
    	buttonInicio.setText("Inicio");
    	buttonInicio.pack();
    	buttonInicio.setLayoutData(gridButton);
    	buttonInicio.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				speerkerInter.switchCenter("Browser");
			}
		});
		
		gridButton = new GridData(GridData.BEGINNING);
    	buttonPlaylist = new Button(composite, SWT.PUSH);
    	buttonPlaylist.setText("Playlist");
    	buttonPlaylist.pack();
    	buttonPlaylist.setLayoutData(gridButton);
    	buttonPlaylist.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				speerkerInter.switchCenter("Playlist");
			}
		});
		
	}

}
