package speerker.inter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import speerker.App;

public class BrowserInter {

	Composite compoBrowser;
	Display display;
	GridData gridComposite;
	GridLayout browserLayout;
	Browser browser;

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
		this.setBrowserUrl("%");
		browser.setLayoutData(gridBrowser);
	}

	public void setBrowserUrl(String username) {
		if (browser != null) {
			String StatsUrl = String.format(App.getProperty("StatsUrl"),
					username);
			if (StatsUrl != null) {
				App.logger.debug("Loading url: " + StatsUrl);
				browser.setUrl(StatsUrl);
			} else {
				browser.setUrl("http://hypem.com/");
			}
		}
	}
	
	public void refreshBrowser(){
		if (browser != null) {
			browser.refresh();
		}
	}
}
