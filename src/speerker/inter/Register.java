package speerker.inter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Register {
	
	public static final void show(Display display){
		
		final Shell shell = new Shell(display, SWT.DIALOG_TRIM);
		
		shell.setText("Register");
		shell.setSize(600, 300);
		shell.setLocation(500, 300);
		GridLayout shellLayout = new GridLayout(1, false);
		shellLayout.marginLeft = 30;
		shellLayout.marginRight = 30;
		shellLayout.marginBottom = 20;
		shellLayout.marginTop = 20;
		shell.setLayout(shellLayout);
		
		Browser browser = null;
		try {
			browser = new Browser(shell, SWT.NONE);
		} catch (SWTError e) {
			
		}
		if (browser != null) {
			browser.setUrl("http://www.eclipse.org");
		}
		GridData gridBrowser = new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL);
		browser.setLayoutData(gridBrowser);
		
		GridData gridButton = new GridData(GridData.VERTICAL_ALIGN_END | GridData.HORIZONTAL_ALIGN_END);
		
		Button button = new Button(shell, SWT.PUSH);
		button.setText("Close");
		button.setLayoutData(gridButton);
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		shell.dispose();
		
	}

}
