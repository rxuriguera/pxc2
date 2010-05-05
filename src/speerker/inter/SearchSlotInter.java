package speerker.inter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

import speerker.search.SearchManager;

public class SearchSlotInter {
	
	Composite composite;
    GridLayout playerLayout;
    SpeerkerInter speerkerInter;
    
    Display display;
    
    GridData gridText;
    GridData gridButton;
    
    Text search;
    Button button;
	private SearchManager searchManager;
    
    public SearchSlotInter(Composite c, Display d, SpeerkerInter s){
    	
    	speerkerInter = s;
    	searchManager = speerkerInter.getTool().getSearchManager();
    	composite=c;
    	display=d;
   
    	playerLayout = new GridLayout(3, false);
    	composite.setLayout(playerLayout); 	
    	
    	GridData gridText = new GridData(GridData.BEGINNING | GridData.VERTICAL_ALIGN_CENTER | GridData.FILL_HORIZONTAL);
    	search = new Text(composite, SWT.BORDER);
    	search.forceFocus();
    	Point sizeText30 = UtilsSWT.getTextSize(search, 30);
		search.setSize(sizeText30);
		gridText.heightHint = sizeText30.y;
		gridText.widthHint = sizeText30.x;
    	search.setLayoutData(gridText);
    	
    	GridData gridButton = new GridData(GridData.BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
    	Button button = new Button(composite, SWT.PUSH);
		button.setText("Search");
		button.pack();
		button.setLayoutData(gridButton);
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				searchManager.newSearch(search.getText());
				speerkerInter.getMenuInter().refreshTable();
				speerkerInter.getSearchInter().setKey(search.getText());
				speerkerInter.switchCenter("Search");
				search.setText("");
			}
		});
		
    	
    }

}
