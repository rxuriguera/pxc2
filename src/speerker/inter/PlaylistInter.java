package speerker.inter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import speerker.player.Playlist;

public class PlaylistInter {
	
	Playlist playlist;
	Composite compoPlaylist;
	GridData gridComposite;
    GridLayout playerLayout;
	
	Display display;
	TableColumn columnTitle;
	TableColumn columnArtist;
	TableColumn columnAlbum;
	
	Table table;
	GridData tableGridData;
	
	public PlaylistInter (Composite c, Display d, Playlist p) {
		
		playlist = p;
		compoPlaylist = c;
		display = d;
		
		GridData gridComposite = new GridData(GridData.FILL_BOTH);
    	compoPlaylist.setLayoutData(gridComposite);
    	
    	playerLayout = new GridLayout(1, false);
    	compoPlaylist.setLayout(playerLayout);
    	
    	table = new Table (compoPlaylist, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
    	table.setLinesVisible (true);
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
    	
	}
	
	public void refreshTable(){
		
		table.removeAll();
		
		for (int i=0; i<playlist.getSize(); i++) {
			TableItem item = new TableItem (table, SWT.NONE);
			item.setText (0, playlist.getTitle(i));
			item.setText (1, playlist.getArtist(i));
			item.setText (2, playlist.getAlbum(i));
			
		}
		
		
		
	}

}
