package speerker.inter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

public class MenuInter {
	
	private Composite composite;
	private Display display;
	private SpeerkerInter speerkerInter;
	private GridLayout menuLayout;
	private GridData gridButton;
	private Button buttonInicio;
	private Button buttonPlaylist;
	private Table table;
	private GridData tableGridData;
	private int current;
	private Color colorSelected;

	public MenuInter(Composite c, Display d, SpeerkerInter s){
		
		composite = c;
		display = d;
		speerkerInter = s;
		
		menuLayout = new GridLayout(1, false);
    	composite.setLayout(menuLayout);
    	
    	table = new Table (composite, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
    	//table.setLinesVisible (true);
    	//table.setHeaderVisible (true);
    	tableGridData = new GridData(GridData.FILL_BOTH);
    	tableGridData.heightHint = 200;
    	table.setLayoutData(tableGridData);
    	
    	colorSelected = new Color (display, 176, 217, 12);
    	current = 0;
    	
    	refreshTable();
    	
    	table.addListener (SWT.MouseDown, new Listener () {
			public void handleEvent (Event event) {
				Rectangle clientArea = table.getClientArea ();
				Point pt = new Point (event.x, event.y);
				int index = table.getTopIndex ();
				while (index < table.getItemCount ()) {
					boolean visible = false;
					TableItem item = table.getItem (index);
					for (int i=0; i < 3; i++) {
						Rectangle rect = item.getBounds (i);
						if (rect.contains (pt)) {
							if (index==0) speerkerInter.switchCenter("Browser");
							else if (index==1) speerkerInter.switchCenter("Playlist");
							current = index;
							refreshTable();
							return;
						}
						if (!visible && rect.intersects (clientArea)) {
							visible = true;
						}
					}
					if (!visible) return;
					index++;
				}
			}
		});
    	
		
	}
	
	public void refreshTable(){
		display.syncExec(
				new Runnable() {
					public void run(){
						table.removeAll();
						TableItem web = new TableItem (table, SWT.NONE);
						web.setText (0, "Web");
						TableItem playlist = new TableItem (table, SWT.NONE);
						playlist.setText (0, "Playlist");
						table.getItem(current).setBackground(colorSelected);
				    }
				});
		
	}

}
