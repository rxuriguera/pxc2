package speerker.rmi;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import speerker.Song;
import speerker.types.Play;
import speerker.types.User;

public class TestSpeerkerRMIClient {
	protected SpeerkerRMIClient client;
	
	@Before
	public void setUp(){
		client = new SpeerkerRMIClient();
	}
	
	@Test
	public void testLogin() {
		User user = new User("abc","def");
		user = this.client.login(user);
		assertEquals(true, user.getValid());
	}
	
	@Test
	public void testSendStats() {
		Play play = new Play("some_user", new Song("a","b","c","d",33l));
		this.client.sendPlay(play);
	}
	

}
