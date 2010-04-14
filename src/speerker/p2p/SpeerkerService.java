package speerker.p2p;

import java.io.IOException;
import java.net.URI;
import java.util.NoSuchElementException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import speerker.App;
import net.jxta.document.Advertisement;
import net.jxta.document.AdvertisementFactory;
import net.jxta.document.XMLDocument;
import net.jxta.endpoint.EndpointAddress;
import net.jxta.endpoint.EndpointService;
import net.jxta.endpoint.Message;
import net.jxta.endpoint.MessageElement;
import net.jxta.endpoint.StringMessageElement;
import net.jxta.exception.PeerGroupException;
import net.jxta.id.ID;
import net.jxta.logging.Logging;
import net.jxta.platform.ModuleSpecID;
import net.jxta.peergroup.PeerGroup;
import net.jxta.platform.Module;
import net.jxta.platform.ModuleClassID;
import net.jxta.protocol.ConfigParams;
import net.jxta.protocol.ModuleImplAdvertisement;
import net.jxta.service.Service;

/**
 * A very simple Peer Group Service.
 * <p/>
 * This service sends JXTA an annoucement message every few seconds via JXTA
 * endpoint service <tt>propagate()</tt>. It also listens for announcement
 * messages from other peers and prints a message on the console whenever it
 * receives one.
 * <p/>
 * The protocol for this service consists of JXTA messages sent via Endpoint
 * propagation. This gossip service implementation uses the <tt>assignedID</tt>
 * which is initialized in the <tt>init()</tt> method as the endpoint address
 * for the messages it sends and receives. Use of the <tt>assignedID</tt> as the
 * <tt>serviceParam</tt> is a common choice because it is gauranteed to be
 * unique within the PeerGroup and the <tt>assignedID</tt> <tt>serviceParam</tt>
 * is informally reserved for the service with that <tt>assignedID</tt>.
 * <p/>
 * The messages exchanged by the gossip service contain two message elements in
 * the "<tt>gossip</tt>" namespace. "<tt>sender</tt>" contains a <tt>String</tt>
 * of the peer id of the message sender. "<tt>gossip</tt>" contains a
 * <tt>String</tt> of the gossip text which is being shared by the sender.
 */
public class SpeerkerService implements net.jxta.service.Service,
		net.jxta.endpoint.EndpointListener {

	/**
	 * The module class ID for Gossip services. All Gossip services regardless
	 * of the protocol used share this same module class id.
	 */
	public static final ModuleClassID SERVICE_MCID = ModuleClassID
			.create(URI
					.create("urn:jxta:uuid-8A465DEB9C6942618194F30089B447EF05"));
	/**
	 * The module spec ID for our Gossip service. The module spec id contains
	 * the {@code GOSSIP_SERVICE_MCID}. All implementations which use the same
	 * messaging protocol as this implementation will share this same module
	 * spec id.
	 */
	public static final ModuleSpecID SERVICE_MSID = ModuleSpecID
			.create(URI
					.create("urn:jxta:uuid-8A465DEB9C6942618194F30089B447EFC2B6E9ADD0C041DD9D7CA73EB5BF534206"));
	/**
	 * The default gossip text we will send to other peers.
	 */
	public static final String DEFAULT_GOSSIP_TEXT = "JXTA is cool. Pass it on!";
	/**
	 * Whether we should show our own gossip text default.
	 */
	public static final boolean DEFAULT_SHOW_OWN = false;
	/**
	 * The default interval in milliseconds at which we will send our gossip
	 * text.
	 */
	public static final long GOSSIP_INTERVAL_DEFAULT = 10 * 1000L;
	/**
	 * The name of the message namespace for all gossip service messages.
	 */
	public static final String GOSSIP_NAMESPACE = "gossip";
	/**
	 * The name of the message element identifying the gossip sender.
	 */
	public static final String GOSSIP_SENDER_ELEMENT_NAME = "sender";
	/**
	 * The name of the message element containing the gossip text.
	 */
	public static final String GOSSIP_GOSSIP_ELEMENT_NAME = "gossip";
	/**
	 * A Timer shared between all Gossip service instances that we use for
	 * sending our gossip messages.
	 */
	public static final Timer SHARED_TIMER = new Timer("Gossip Services Timer",
			true);
	/**
	 * The peer group in which this instance is running.
	 */
	private PeerGroup group;
	/**
	 * Our assigned service ID. Usually this is our MCID but may also be our
	 * MCID with a role id if there are multiple gossip services within the peer
	 * group.
	 */
	private ID assignedID;
	/**
	 * The module implementation advertisement for our instance.
	 */
	private ModuleImplAdvertisement implAdv;
	/**
	 * The "gossip" message we read from our configuration.
	 */
	private String gossip = DEFAULT_GOSSIP_TEXT;
	/**
	 * If {@code true} then we show our own gossip messages;
	 */
	private boolean showOwn = DEFAULT_SHOW_OWN;
	/**
	 * The interval in milliseconds at which we will send our gossip message.
	 */
	private long gossip_interval = GOSSIP_INTERVAL_DEFAULT;
	/**
	 * The endpoint service with which we send our gossips and register our
	 * listener.
	 */
	private EndpointService endpoint = null;
	/**
	 * The timer task we use to send our gossip messages.
	 */
	private TimerTask sendTask = null;

	/**
	 * {@inheritDoc}
	 * <p/>
	 * This implementation doesn't currently use interface objects so it just
	 * returns itself. We would use an interface object if we needed to maintain
	 * state for each caller of the Gossip Service or wished to attach a
	 * security context to the callers of this service. ie. different callers
	 * might have different sercurity privleges.
	 */
	public Service getInterface() {
		return this;
	}

	/**
	 * Return our assigned ID.
	 * 
	 * @return Our assigned ID.
	 */
	public ID getAssignedID() {
		return assignedID;
	}

	/**
	 * {@inheritDoc}
	 */
	public Advertisement getImplAdvertisement() {
		return implAdv;
	}

	/**
	 * {@inheritDoc}
	 */
	public void init(PeerGroup group, ID assignedID,
			Advertisement implAdvertisement) throws PeerGroupException {
		this.group = group;
		this.assignedID = assignedID;
		this.implAdv = (ModuleImplAdvertisement) implAdvertisement;

		// Get our configuration parameters.
		SpeerkerServiceConfigAdv gossipConfig = null;
		ConfigParams confAdv = group.getConfigAdvertisement();

		if (confAdv != null) {
			Advertisement adv = null;

			try {
				XMLDocument configDoc = (XMLDocument) confAdv
						.getServiceParam(getAssignedID());

				if (null != configDoc) {
					adv = AdvertisementFactory.newAdvertisement(configDoc);
				}
			} catch (NoSuchElementException failed) {
				// ignored
			}

			if (adv instanceof SpeerkerServiceConfigAdv) {
				gossipConfig = (SpeerkerServiceConfigAdv) adv;
			}
		}

		if (null == gossipConfig) {
			// Make a new advertisement for defaults.
			gossipConfig = (SpeerkerServiceConfigAdv) AdvertisementFactory
					.newAdvertisement(SpeerkerServiceConfigAdv
							.getAdvertisementType());
		}

		// If the config has a non-null gossip then use that.
		if (null != gossipConfig.getGossip()) {
			gossip = gossipConfig.getGossip();
		}

		// If the config has a non-null showOwn then use that.
		if (null != gossipConfig.getShowOwn()) {
			showOwn = gossipConfig.getShowOwn();
		}


		StringBuilder configInfo = new StringBuilder(
				"Configuring Gossip Service : " + assignedID);

		configInfo.append("\n\tImplementation :");
		configInfo.append("\n\t\tModule Spec ID: ").append(
				implAdv.getModuleSpecID());
		configInfo.append("\n\t\tImpl Description : ").append(
				implAdv.getDescription());
		configInfo.append("\n\t\tImpl URI : ").append(implAdv.getUri());
		configInfo.append("\n\t\tImpl Code : ").append(implAdv.getCode());

		configInfo.append("\n\tGroup Params :");
		configInfo.append("\n\t\tGroup : ").append(group);
		configInfo.append("\n\t\tPeer ID : ").append(group.getPeerID());

		configInfo.append("\n\tConfiguration :");
		configInfo.append("\n\t\tShow own gossips : ").append(showOwn);
		configInfo.append("\n\t\tGossip : ").append(gossip);
		App.logger.info(configInfo.toString());
	}

	/**
	 * {@inheritDoc}
	 */
	public synchronized int startApp(String[] args) {
		/*
		 * We require the peer group Endpoint service. Since the order in which
		 * services are initialized is random the Endpoint might not yet be
		 * initialized when we are first called. If the Endpoint service is not
		 * available then we tell our caller that we can not yet start. The peer
		 * group implementation will continue to start other services and call
		 * our <tt>startApp()</tt> method again.
		 */
		endpoint = group.getEndpointService();

		if (null == endpoint) {
			App.logger.warn("Stalled until there is an endpoint service");
			return Module.START_AGAIN_STALLED;
		}

		/*
		 * Register our listener for gossip messages. The registered address is
		 * our assigned ID as a String as the <tt>serviceName</tt> and nothing
		 * as the <tt>serviceParam</tt>.
		 */
		boolean registered = endpoint.addIncomingMessageListener(this,
				getAssignedID().toString(), null);

		if (!registered) {
			App.logger.error("Failed to regiser endpoint listener.");
			return -1;
		}

		// Create our timer task which will send our gossip messages.
		sendTask = new TimerTask() {

			/**
			 * {@inheritDoc}
			 */
			public void run() {
				sendGossip();
			}
		};

		// Register the timer task.
		SHARED_TIMER.schedule(sendTask, gossip_interval, gossip_interval);

		App.logger.info("[" + group + "] Gossip Serivce (" + getAssignedID()
					+ ") started");

		return Module.START_OK;
	}

	/**
	 * {@inheritDoc}
	 */
	public synchronized void stopApp() {
		/*
		 * We have to assume that <tt>stopApp()</tt> might be called before
		 * <tt>startApp()</tt> successfully completes. This means that fields
		 * initialized in the <tt>startApp()</tt> method might not be
		 * initialized.
		 */
		if (null != endpoint) {
			endpoint.removeIncomingMessageListener(getAssignedID().toString(),
					null);
		}
		endpoint = null;

		// Cancel our sending timer task.
		TimerTask currentTask = sendTask;
		if (null != currentTask) {
			currentTask.cancel();
		}
		sendTask = null;

		App.logger.info("[" + group + "] Gossip Serivce (" + getAssignedID()
					+ ") stopped.");
	}

	/**
	 * {@inheritDoc}
	 */
	public void processIncomingMessage(Message message,
			EndpointAddress srcAddr, EndpointAddress dstAddr) {
		MessageElement sender = message.getMessageElement(GOSSIP_NAMESPACE,
				GOSSIP_SENDER_ELEMENT_NAME);
		MessageElement text = message.getMessageElement(GOSSIP_NAMESPACE,
				GOSSIP_GOSSIP_ELEMENT_NAME);

		// Make sure that the message contains the required elements.
		if ((null == sender) || (null == text)) {
			System.err.println("Someone sent us an incomplete message.");
			return;
		}

		// Check if the message is from ourself and should be ignored.
		if (!showOwn && group.getPeerID().toString().equals(sender.toString())) {
			// It's from ourself and we are configured to ignore it.
			return;
		}

		// Print the message's gossip text along with who it's from.
		System.out.println(sender.toString() + " says : " + text.toString());
	}

	/**
	 * Send a gossip message using the endpoint propagate method.
	 */
	public void sendGossip() {
		try {
			EndpointService currentEndpoint = endpoint;

			if (null == currentEndpoint) {
				return;
			}

			// Create a new message.
			Message gossipMessage = new Message();

			// Add a "sender" element containing our peer id.
			MessageElement sender = new StringMessageElement(
					GOSSIP_SENDER_ELEMENT_NAME, group.getPeerID().toString(),
					null);
			gossipMessage.addMessageElement(GOSSIP_NAMESPACE, sender);

			// Add a "gossip" element containing our gossip text.
			MessageElement text = new StringMessageElement(
					GOSSIP_GOSSIP_ELEMENT_NAME, gossip, null);
			gossipMessage.addMessageElement(GOSSIP_NAMESPACE, text);

			// Send the message to the network using endpoint propagation.
			currentEndpoint.propagate(gossipMessage,
					getAssignedID().toString(), null);
		} catch (IOException ex) {
			App.logger.error("Failed sending gossip message.", ex);
		}
	}
}