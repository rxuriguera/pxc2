package speerker.p2p;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import speerker.App;
import speerker.p2p.SpeerkerService;
import speerker.p2p.SpeerkerServiceConfigAdv;

import net.jxta.document.AdvertisementFactory;
import net.jxta.document.MimeMediaType;
import net.jxta.document.XMLDocument;
import net.jxta.document.XMLElement;
import net.jxta.exception.PeerGroupException;
import net.jxta.id.IDFactory;
import net.jxta.impl.peergroup.StdPeerGroupParamAdv;
import net.jxta.peergroup.PeerGroup;
import net.jxta.peergroup.PeerGroupID;
import net.jxta.platform.ModuleSpecID;
import net.jxta.platform.NetworkManager;
import net.jxta.protocol.ModuleImplAdvertisement;
import net.jxta.protocol.PeerGroupAdvertisement;

@SuppressWarnings("deprecation")
public class Client {
	/*
	 * public void run(String peerName, String groupName) throws
	 * PeerGroupException { PeerGroup peerGroup =
	 * GroupLauncher.launchGroup(peerName, groupName); }
	 */
	private final static PeerGroupID PEERGROUP_ID = PeerGroupID.create(URI
			.create("urn:jxta:uuid-098353991A6E455E804B85ED40BD1A"
					+ "B459616261646162614E5047205032503302"));

	private final static ModuleSpecID PEERGROUP_MSID = ModuleSpecID
			.create(URI
					.create("urn:jxta:uuid-8A465DEB9C6942618194F30089B447EFC2B6E9ADD0C041DD9D7CA73EB5BF534206"));

	public PeerGroup getSpeekerPeerGroup(PeerGroup group) throws Exception {

		// Register advertisement
		AdvertisementFactory.registerAdvertisementInstance(
				SpeerkerServiceConfigAdv.getAdvertisementType(),
				new SpeerkerServiceConfigAdv.Instantiator());

		//
		// Create the Module Impl Advertisement for our group.
		//
		// Start with a standard peer group impl advertisement.
		ModuleImplAdvertisement groupImplAdv = group
				.getAllPurposePeerGroupImplAdvertisement();

		// Set custom module spec ID.
		groupImplAdv.setModuleSpecID(PEERGROUP_MSID);

		// StdPeerGroup uses the ModuleImplAdvertisement param section to
		// describe what services to run.
		StdPeerGroupParamAdv customGroupParamAdv = new StdPeerGroupParamAdv(
				groupImplAdv.getParam());

		// Add our service to the group
		customGroupParamAdv.addService(SpeerkerService.SERVICE_MCID,
				SpeerkerService.SERVICE_MSID);

		// update the groupImplAdv by updating its param
		groupImplAdv.setParam((XMLElement) customGroupParamAdv
				.getDocument(MimeMediaType.XMLUTF8));

		// publish service module implementation advertisement
		group.getDiscoveryService().publish(groupImplAdv);

		//
		// Create the module Impl Advertisement for our service.
		//
		// Create a new ModuleImplAdvertisement instance.
		ModuleImplAdvertisement speerkerImplAdv = (ModuleImplAdvertisement) AdvertisementFactory
				.newAdvertisement(ModuleImplAdvertisement
						.getAdvertisementType());

		// Our implementation implements the given ModuleSpecID.
		speerkerImplAdv.setModuleSpecID(SpeerkerService.SERVICE_MSID);

		// Copy compatibility statement from the peer group impl advertisement.
		speerkerImplAdv.setCompat(groupImplAdv.getCompat());

		// Implemented in SpeerkerService
		speerkerImplAdv.setCode(SpeerkerService.class.getName());

		// The provider of the implementation.
		speerkerImplAdv.setProvider("Speerker");

		// A description of the service. (optional)
		speerkerImplAdv.setDescription("Speerker Service");

		// publish the gossip service module implementation advertisement.
		group.getDiscoveryService().publish(speerkerImplAdv);

		//
		// Create the Peer Group Advertisement.
		//
		// Crete a new PeerGroupAdvertisement instance.
		PeerGroupAdvertisement groupAdv = (PeerGroupAdvertisement) AdvertisementFactory
				.newAdvertisement(PeerGroupAdvertisement.getAdvertisementType());

		// Set our chosen peer group ID.
		groupAdv.setPeerGroupID(PEERGROUP_ID);

		// Set the peer group name.
		groupAdv.setName("Speerker Peer Group");

		// The custom group uses our custom Module Specification.
		groupAdv.setModuleSpecID(groupImplAdv.getModuleSpecID());

		// Add Speerker Service configuration parameters to the group adv.
		SpeerkerServiceConfigAdv serviceConfig = (SpeerkerServiceConfigAdv) AdvertisementFactory
				.newAdvertisement(SpeerkerServiceConfigAdv
						.getAdvertisementType());

		serviceConfig.setGossip("Custom Peer Group Services are fun!");

		// Save the service config into the peer group advertisement.
		XMLDocument asDoc = (XMLDocument) serviceConfig
				.getDocument(MimeMediaType.XMLUTF8);
		groupAdv.putServiceParam(SpeerkerService.SERVICE_MCID, asDoc);

		// publish the peer group advertisement.
		group.getDiscoveryService().publish(groupAdv);

		return group.newGroup(groupAdv);
	}

	public void run(String peerName, String groupName) {
		try {
			File cacheDir = new File(App.getProperty("CacheDirectory"));
			String instance = App.getProperty("ClientInstanceName");

			NetworkManager manager = new NetworkManager(
					NetworkManager.ConfigMode.EDGE, instance, new File(
							cacheDir, instance).toURI());

			// PeerGroup group = manager.getNetPeerGroup();
			manager.setPeerID(IDFactory
					.newPeerID(PeerGroupID.defaultNetPeerGroupID));

			System.out.println("Starting JXTA");

			PeerGroup netPeerGroup = manager.startNetwork();
			PeerGroup speekerPeerGroup = this.getSpeekerPeerGroup(netPeerGroup);

			speekerPeerGroup.startApp(new String[0]);

			// manager.setInfrastructureID(peerGroup.getPeerGroupID());
			// manager.setPeerID(peerGroup.getPeerID());
			App.logger.info("Started app " + speekerPeerGroup);
			speekerPeerGroup.stopApp();

			manager.stopNetwork();

			// manager.setPeerID(peerGroup.getPeerID());
			// manager.setInfrastructureID(peerGroup.getPeerGroupID());
			//
		} catch (IOException e) {
			App.logger.error("Error loading cache directory: ", e);
		} catch (PeerGroupException e) {
			App.logger.error("Error creating peer group: ", e);
		} catch (Exception e) {
			App.logger.error("Error: ", e);
		}
	}

}
