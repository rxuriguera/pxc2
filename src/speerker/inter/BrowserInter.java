package speerker.inter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class BrowserInter {
	
	Composite compoBrowser;
	Display display;
	GridData gridComposite;
    GridLayout browserLayout;
    Browser browser;
	private GridData gridButton;
	private Button buttonInicio;
    
    public BrowserInter(Composite c, Display d) {
    	
    	compoBrowser = c;
    	display = d;
    	
    	browserLayout = new GridLayout(1, false);
    	compoBrowser.setLayout(browserLayout);
    	
    	GridData gridBrowser = new GridData(GridData.FILL_BOTH);
    	browser = null;
    	try {
			browser = new Browser(compoBrowser, SWT.NONE);
		} catch (SWTError e) {
			
		}
		if (browser != null) {
			browser.setUrl("file:///Users/bartru/workspace/Speerker/web/html/main.html");
		}
    	browser.setLayoutData(gridBrowser);
    	
    	gridButton = new GridData(GridData.BEGINNING);
    	buttonInicio = new Button(compoBrowser, SWT.PUSH);
    	buttonInicio.setText("Inicio");
    	buttonInicio.pack();
    	buttonInicio.setLayoutData(gridButton);
    	
    }
}
