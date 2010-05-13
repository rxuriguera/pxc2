package speerker.inter;
import org.eclipse.swt.widgets.Display;

import speerker.inter.Login;



public class loginTest {
	
	public static void main(String argv[]) {
		Display display = new Display();
		boolean loginData = Login.show(display, null);
		
	}

}
