package speerker.inter;



import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.*;

public class Login {
	
	static boolean login;
 
	public static String[] show(final Display display) {
		
		Shell shell = new Shell(display, SWT.DIALOG_TRIM);
		login = false;
		
		shell.setText("Login");
		shell.setSize(300, 300);
		shell.setLocation(200, 200);
		GridLayout shellLayout = new GridLayout(2, false);
		shellLayout.marginLeft = 30;
		shellLayout.marginRight = 30;
		shellLayout.marginBottom = 35;
		shellLayout.marginTop = 20;
		shell.setLayout(shellLayout);
		
		GridData gridTitle = new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL);
		gridTitle.horizontalSpan = 2;
		Label title = new Label(shell, SWT.CENTER);
		ImageData dataLogo = new ImageData("images/logoLogin.png");
		Image imageLogo = new Image(display, dataLogo);
	    title.setImage(imageLogo); 
		title.setLayoutData(gridTitle);
		
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_END);
		GridData gridText = new GridData(GridData.VERTICAL_ALIGN_END);
		GridData gridButton = new GridData(GridData.VERTICAL_ALIGN_END | GridData.HORIZONTAL_ALIGN_CENTER | GridData.FILL_HORIZONTAL);
		gridButton.verticalIndent = 10;
		
		
		Label userLabel = new Label (shell, SWT.LEFT);
		userLabel.setText("User: ");
		userLabel.setLayoutData(gridData);
		
		Text userText = new Text (shell, SWT.BORDER);
		Point sizeText20 = UtilsSWT.getTextSize(userText, 20);
		userText.setSize(sizeText20);
		gridText.heightHint = sizeText20.y;
		gridText.widthHint = sizeText20.x;
		userText.setLayoutData(gridText);
		
		Label passwordLabel = new Label (shell, SWT.LEFT);
		passwordLabel.setText("Password: ");
		passwordLabel.setLayoutData(gridData);
		
		Text passwordText = new Text (shell, SWT.BORDER | SWT.PASSWORD);
		passwordText.setLayoutData(gridText);
		
		Button button = new Button(shell, SWT.PUSH);
		button.setText("Register");
		button.setLayoutData(gridButton);
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Register.show(display);
			}
		});
		
		Button buttonLogin = new Button(shell, SWT.PUSH);
		buttonLogin.setText("Login");
		buttonLogin.setLayoutData(gridButton);
		buttonLogin.addSelectionListener(new SelectionAdapter() {
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
		
		shell.dispose();
		
		return data;
	}
            
} 