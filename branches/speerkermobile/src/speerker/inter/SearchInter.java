package speerker.inter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import speerker.p2p.SearchResult;



public class SearchInter {
	
	private Composite compoSearch;
	private GridLayout searchLayout;
	private SpeerkerInter speerkerInter;
	
    private Display display;
	private Table table;
	private GridData tableGridData;
	private TableColumn columnTitle;
	private TableColumn columnArtist;
	private TableColumn columnAlbum;
	
	private String key;
	private HashMap<String, SearchResult> searchResults;
	
    public SearchInter(Composite c, Display d, SpeerkerInter s){
    	
		compoSearch = c;
		display = d;
		speerkerInter = s;
		
		searchLayout = new GridLayout(1, false);
		compoSearch.setLayout(searchLayout);
    	
    	table = new Table (compoSearch, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
    	//table.setLinesVisible (true);
    	table.setHeaderVisible (true);
    	tableGridData = new GridData(GridData.FILL_BOTH);
    	//tableGridData.heightHint = 200;
    	table.setLayoutData(tableGridData);
    	
    	columnTitle = new TableColumn (table, SWT.NONE);
		columnTitle.setText ("Title");
		columnTitle.setWidth(175);
		
		columnArtist = new TableColumn (table, SWT.NONE);
		columnArtist.setText ("Artist");
		columnArtist.setWidth(175);
		
		columnAlbum = new TableColumn (table, SWT.NONE);
		columnAlbum.setText ("Album");
		columnAlbum.setWidth(175);

		
		key = "";
		
		new Thread () {
			public void run () {
				while(true){
					refreshTable();
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		}.start ();
		
    }
    
    public void refreshTable(){
		display.syncExec(
				new Runnable() {
					public void run(){
						table.removeAll();
						if (!key.equals("")){
							System.out.println(key);
							searchResults = speerkerInter.getTool().getSearchManager().getResults().get(key);
							
							Iterator<Entry<String, SearchResult>> iter = searchResults.entrySet().iterator();
							while(iter.hasNext()){
								Map.Entry e = iter.next();
								SearchResult r = (SearchResult) e.getValue();
								TableItem s = new TableItem (table, SWT.NONE);
								s.setText (0, r.getSong().getTitle());
								s.setText (1, r.getSong().getArtist());
								s.setText (2, r.getSong().getAlbum());
								
							}
							
						}
				    }
				});
		
	}
    
    public void setKey(String k){
    	this.key = k;
    }

}
