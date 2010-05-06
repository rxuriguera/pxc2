package speerker.inter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

public class LogoInter {
	
	private Composite composite;
	private Display display;
	private GridData gridTitle;
	private Label title;
	private GridLayout playerLayout;

	public LogoInter(Composite c, Display d){
		
		composite = c;
		display = d;
		
		playerLayout = new GridLayout(1, false);
    	composite.setLayout(playerLayout); 	
		
		gridTitle = new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_CENTER);
		title = new Label(composite, SWT.RIGHT);
		ImageData dataLogo = new ImageData("images/logoApp.png");
		Image imageLogo = new Image(display, dataLogo);
	    title.setImage(imageLogo); 
		title.setLayoutData(gridTitle);
		
	}

}
