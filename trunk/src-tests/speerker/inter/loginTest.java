package speerker.inter;
import org.eclipse.swt.widgets.Display;

import speerker.inter.Login;



public class loginTest {
	
	public static void main(String argv[]) {
		Display display = new Display();
		String[] loginData = Login.show(display, null);
		System.out.println(loginData[0]);
		System.out.println(loginData[1]);
	}

}
