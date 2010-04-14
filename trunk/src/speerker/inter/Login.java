package speerker.inter;

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.*;

public class Login {
	
	static boolean login;
 
	public static String[] show() {
            	
		Display display = new Display();
		Display.setAppName("Speerker");
		 
		Shell shell = new Shell(display, SWT.TITLE | SWT.CLOSE | SWT.BORDER | SWT.ON_TOP);
		login = false;
		
		shell.setText("Login");
		shell.setSize(300, 300);
		shell.setLocation(400, 200);
		GridLayout shellLayout = new GridLayout(1, false);
		shellLayout.marginLeft = 30;
		shellLayout.marginRight = 30;
		shellLayout.marginBottom = 40;
		shellLayout.marginTop = 20;
		shell.setLayout(shellLayout);
		
		GridData gridTitle = new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL);
		Label title = new Label(shell, SWT.CENTER);
		title.setText("Speerker");
		title.setLayoutData(gridTitle);
		
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_END);
		
		Label userLabel = new Label (shell, SWT.LEFT);
		userLabel.setText("User: ");
		userLabel.setLayoutData(gridData);
		
		Text userText = new Text (shell, SWT.BORDER);
		userText.setLayoutData(gridData);
		
		Label passwordLabel = new Label (shell, SWT.LEFT);
		passwordLabel.setText("Password: ");
		passwordLabel.setLayoutData(gridData);
		
		Text passwordText = new Text (shell, SWT.BORDER);
		passwordText.setLayoutData(gridData);
		
		Button button = new Button(shell, SWT.PUSH);
		GridData gridButton = new GridData(GridData.VERTICAL_ALIGN_END | GridData.HORIZONTAL_ALIGN_CENTER);
		button.setText("Login");
		button.setLayoutData(gridButton);
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				login = true;
			}
		});
		
		shell.open();
		while (!login)
			if (!display.readAndDispatch()) display.sleep();
		
		String[] data = new String[2];
		
		data[0] = userText.getText();
		data[1] = passwordText.getText();
		
		shell.close();
		
		return data;
	}
            
} 