package speerker.p2p;

import speerker.App;

import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;

import org.jdom.Element;

public class P2PFacade {

	List<Thread> threads = new LinkedList<Thread>();

	public P2PFacade() {
		this.threads.add(new Client());
		this.threads.add(new Server());
	}

	public void start() {
		Iterator<Thread> threadIterator = this.threads.iterator();
		Thread currentThread;
		while (threadIterator.hasNext()) {
			currentThread = threadIterator.next();
			currentThread.start();
		}
	}
	
	public void stop() {
		Iterator<Thread> threadIterator = this.threads.iterator();
		Thread currentThread;
		while (threadIterator.hasNext()) {
			currentThread = threadIterator.next();
			try {
				this.stopThread(currentThread);
			} catch (InterruptedException e) {
				App.logger.info("Thread " + currentThread.getName()
						+ " has been" + "interrupted.");
			}
		}
	}

	protected void stopThread(Thread thread) throws InterruptedException {
		while (thread.isAlive()) {
			App.logger.info("Waiting for thread: " + thread.getName()
					+ " to finish");

			long startTime = System.currentTimeMillis();
			thread.join(Long.parseLong(App.getProperty("JoinWaitingTime")));
			if ((System.currentTimeMillis() - startTime) > Long.parseLong(App
					.getProperty("TimeToInterrupt"))
					&& thread.isAlive()) {
				App.logger.info("Tired of waiting. Interrupting "
						+ thread.getName());
				thread.interrupt();
				thread.join();
			}
		}
	}
	
	public Element search(String query) {
		return null;
	}
	
	public String getFile(String hash) {

		String path = "path to file";
		return path;
	}
}
