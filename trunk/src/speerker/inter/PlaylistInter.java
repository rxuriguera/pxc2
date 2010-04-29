package speerker.inter;

import org.eclipse.swt.SWT;
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
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import speerker.player.Playlist;

public class PlaylistInter {
	
	Playlist playlist;
	Composite compoPlaylist;
	GridData gridComposite;
    GridLayout playlistLayout;
	
	Display display;
	TableColumn columnTitle;
	TableColumn columnArtist;
	TableColumn columnAlbum;
	
	Table table;
	GridData tableGridData;
	Color colorPlay;
	
	public PlaylistInter (Composite c, Display d, Playlist p) {
		
		playlist = p;
		compoPlaylist = c;
		display = d;
    	
		
		
    	playlistLayout = new GridLayout(1, false);
    	compoPlaylist.setLayout(playlistLayout);
    	
    	table = new Table (compoPlaylist, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
    	//table.setLinesVisible (true);
    	table.setHeaderVisible (true);
    	tableGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
    	tableGridData.heightHint = 200;
    	table.setLayoutData(tableGridData);
    	
    	columnTitle = new TableColumn (table, SWT.NONE);
		columnTitle.setText ("Title");
		columnTitle.setWidth(200);
		
		columnArtist = new TableColumn (table, SWT.NONE);
		columnArtist.setText ("Artist");
		columnArtist.setWidth(200);
		
		columnAlbum = new TableColumn (table, SWT.NONE);
		columnAlbum.setText ("Album");
		columnAlbum.setWidth(200);
		
		table.addListener (SWT.MouseDoubleClick, new Listener () {
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
							playlist.play(index);
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
		
		colorPlay = new Color (display, 176, 217, 12);
		
		playlist.setInter(this);
    	
	}
	
	public void refreshTable(){
		display.syncExec(
				new Runnable() {
					public void run(){
						table.removeAll();
						
						for (int i=0; i<playlist.getSize(); i++) {
							TableItem item = new TableItem (table, SWT.NONE);
							item.setText (0, playlist.getTitle(i));
							item.setText (1, playlist.getArtist(i));
							item.setText (2, playlist.getAlbum(i));
							
							
							if (i==playlist.getCurrent()){
								item.setBackground(colorPlay);
							}
							
						}
				    }
				});
		
		
		
		
		
	}

}
