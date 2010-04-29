package speerker.inter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class BrowserInter {
	
	Composite compoBrowser;
	Display display;
	GridData gridComposite;
    GridLayout browserLayout;
    Browser browser;
    
    public BrowserInter(Composite c, Display d) {
    	
    	compoBrowser = c;
    	display = d;
    	
    	browserLayout = new GridLayout(3, false);
    	compoBrowser.setLayout(browserLayout);
    	
    	GridData gridBrowser = new GridData(GridData.FILL_BOTH);
    	browser = null;
    	try {
			browser = new Browser(compoBrowser, SWT.NONE);
		} catch (SWTError e) {
			
		}
		if (browser != null) {
			browser.setUrl("http://www.google.com");
		}
    	browser.setLayoutData(gridBrowser);
    	
    }
}
