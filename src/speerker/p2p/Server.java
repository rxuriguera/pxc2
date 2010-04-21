package speerker.p2p;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import speerker.App;

import net.jxta.document.AdvertisementFactory;
import net.jxta.id.IDFactory;
import net.jxta.peer.PeerID;
import net.jxta.peergroup.PeerGroup;
import net.jxta.peergroup.PeerGroupID;
import net.jxta.pipe.PipeID;
import net.jxta.pipe.PipeService;
import net.jxta.platform.NetworkManager;
import net.jxta.protocol.PipeAdvertisement;

public class Server extends Thread {

	protected Boolean stop = false;
	protected NetworkManager manager;
	
	public Server() {
		this.setName(App.getProperty("ServerThreadName"));
		NetworkManager.ConfigMode configMode = NetworkManager.ConfigMode.valueOf(App.getProperty("DefaultServerMode"));
		String instanceName = App.getProperty("ServerInstanceNamme");
		String homeDir = App.getProperty("CacheDirectory");
		//this.setNetworkManager(configMode, homeDir, instanceName);
	}

	public Server(NetworkManager.ConfigMode mode, String homeDir, String instanceName)
			throws IOException {
		this.setName(App.getProperty("ServerThreadName"));
		//this.setNetworkManager(mode, homeDir, instanceName);
	}

	public void setNetworkManager(NetworkManager.ConfigMode configMode, String homeDir, String instanceName){
		try {
			this.manager = new NetworkManager(configMode, instanceName, new File(new File(homeDir), instanceName).toURI());
		} catch(IOException e) {
			App.logger.error("Error creating network manager",e);
		}
	}
	
	public static PipeAdvertisement createSocketAdvertisement() {
		PipeID socketID = IDFactory.newPipeID(GroupLauncher.PEERGROUP_ID);

		PipeAdvertisement advertisement = (PipeAdvertisement) AdvertisementFactory
				.newAdvertisement(PipeAdvertisement.getAdvertisementType());

		advertisement.setPipeID(socketID);
		advertisement.setType(PipeService.UnicastType);
		advertisement.setName("Speerker");
		return advertisement;
	}

	public void askToStop() {
		this.stop = true;
	}

	public void run() {
		while (!this.stop) {
			System.out.println("Hey");
		}
	}

}
