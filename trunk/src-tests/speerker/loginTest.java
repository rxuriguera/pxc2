package speerker;
import speerker.inter.Login;



public class loginTest {
	
	public static void main(String argv[]) {
		String[] loginData = Login.show();
		System.out.println(loginData[0]);
		System.out.println(loginData[1]);
	}

}
