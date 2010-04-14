package speerker.p2p;

import net.jxta.document.Advertisement;
import net.jxta.document.AdvertisementFactory;
import net.jxta.document.Attribute;
import net.jxta.document.Document;
import net.jxta.document.Element;
import net.jxta.document.ExtendableAdvertisement;
import net.jxta.document.MimeMediaType;
import net.jxta.document.StructuredDocument;
import net.jxta.document.XMLElement;
import net.jxta.id.ID;
import net.jxta.logging.Logging;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.jxta.document.Attributable;

/**
 * Defines Sepeerker Service configuration parameters.
 * <p/>
 * A typical SpeerkerServiceConfigAdv : <tt><pre>
 *      &lt;jxta:SpeerkerServiceConfigAdv showOwn="true">
 *          &lt;Gossip>
 *              Bonjour!
 *          &lt;/Gossip>
 *      &lt;/jxta:SpeerkerServiceConfigAdv>
 * </pre></tt>
 */
public final class SpeerkerServiceConfigAdv extends ExtendableAdvertisement
		implements Cloneable {

	/**
	 * Logger
	 */
	private static final Logger LOG = Logger
			.getLogger(SpeerkerServiceConfigAdv.class.getName());

	/**
	 * The advertisement index fields. (currently none).
	 */
	private static final String[] INDEX_FIELDS = {};

	/**
	 * The DOCTYPE
	 */
	private static final String advType = "jxta:SpeerkerServiceConfigAdv";

	/**
	 * The name of the attribute which controls whether the gossip service
	 * should show message from the local peer.
	 */
	private static final String SHOW_OWN_ATTR = "showOwn";

	/**
	 * The name of the tag which we use to store the gossip text.
	 */
	private static final String GOSSIP_TEXT_TAG = "gossip";

	/**
	 * Instantiator for GossipServiceConfigAdv
	 */
	public static class Instantiator implements
			AdvertisementFactory.Instantiator {

		/**
		 * {@inheritDoc}
		 */
		public String getAdvertisementType() {
			return advType;
		}

		/**
		 * {@inheritDoc}
		 */
		public Advertisement newInstance() {
			return new SpeerkerServiceConfigAdv();
		}

		/**
		 * {@inheritDoc}
		 */
		public Advertisement newInstance(Element root) {
			if (!XMLElement.class.isInstance(root)) {
				throw new IllegalArgumentException(getClass().getName()
						+ " only supports XLMElement");
			}

			return new SpeerkerServiceConfigAdv((XMLElement) root);
		}
	}

	/**
	 * If {@code true} then the gossip service should show it's own gossips. If
	 * {@code null} then the gossip service will use it's default.
	 */
	private Boolean showOwn = null;

	/**
	 * The text we will "gossip". If {@code null} then the gossip service will
	 * use it's default.
	 */
	private String gossip = null;

	/**
	 * Returns the identifying type of this Advertisement.
	 * <p/>
	 * <b>Note:</b> This is a static method. It cannot be used to determine the
	 * runtime type of an advertisement. ie.
	 * </p>
	 * <code><pre>
	 *      Advertisement adv = module.getSomeAdv();
	 *      String advType = adv.getAdvertisementType();
	 *  </pre></code>
	 * <p/>
	 * <b>This is wrong and does not work the way you might expect.</b> This
	 * call is not polymorphic and calls Advertisement.getAdvertisementType() no
	 * matter what the real type of the advertisement.
	 * 
	 * @return String the type of advertisement
	 */
	public static String getAdvertisementType() {
		return advType;
	}

	/**
	 * Use the Instantiator through the factory
	 */
	private SpeerkerServiceConfigAdv() {
	}

	/**
	 * Use the Instantiator method to construct Peer Group Config Advs.
	 * 
	 * @param doc
	 *            the element
	 */
	private SpeerkerServiceConfigAdv(XMLElement doc) {
		String doctype = doc.getName();

		String typedoctype = "";
		Attribute itsType = doc.getAttribute("type");

		if (null != itsType) {
			typedoctype = itsType.getValue();
		}

		if (!doctype.equals(getAdvertisementType())
				&& !getAdvertisementType().equals(typedoctype)) {
			throw new IllegalArgumentException("Could not construct : "
					+ getClass().getName() + "from doc containing a "
					+ doc.getName());
		}

		/* Process attributes from root element */
		Enumeration<Attribute> eachAttr = doc.getAttributes();

		while (eachAttr.hasMoreElements()) {
			Attribute aConfigAttr = eachAttr.nextElement();

			if (super.handleAttribute(aConfigAttr)) {
				// nothing to do
			} else if (SHOW_OWN_ATTR.equals(aConfigAttr.getName())) {
				setShowOwn(Boolean.valueOf(aConfigAttr.getValue().trim()));
			} else {
				if (Logging.SHOW_WARNING && LOG.isLoggable(Level.WARNING)) {
					LOG
							.warning("Unhandled Attribute: "
									+ aConfigAttr.getName());
				}
			}
		}

		/* process child elements of root */
		Enumeration<XMLElement> elements = doc.getChildren();

		while (elements.hasMoreElements()) {
			XMLElement elem = elements.nextElement();

			if (!handleElement(elem)) {
				if (Logging.SHOW_WARNING && LOG.isLoggable(Level.WARNING)) {
					LOG.warning("Unhandled Element: " + elem.toString());
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean handleElement(Element raw) {
		if (super.handleElement(raw)) {
			return true;
		}

		XMLElement elem = (XMLElement) raw;

		if (GOSSIP_TEXT_TAG.equals(elem.getName())) {
			String value = elem.getTextValue();

			if (null == value) {
				return false;
			}

			value = value.trim();
			if (0 == value.length()) {
				return false;
			}

			gossip = value;

			return true;
		}

		return false;
	}

	/**
	 * Make a clone of this GossipServiceConfigAdv.
	 * 
	 * @return A copy of this GossipServiceConfigAdv.
	 */
	@Override
	public SpeerkerServiceConfigAdv clone() {
		try {
			SpeerkerServiceConfigAdv clone = (SpeerkerServiceConfigAdv) super
					.clone();

			clone.setShowOwn(getShowOwn());
			clone.setGossip(getGossip());

			return clone;
		} catch (CloneNotSupportedException impossible) {
			throw new Error("Object.clone() threw CloneNotSupportedException",
					impossible);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getAdvType() {
		return getAdvertisementType();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String getBaseAdvType() {
		return getAdvertisementType();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ID getID() {
		return ID.nullID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StructuredDocument getDocument(MimeMediaType encodeAs) {
		StructuredDocument adv = (StructuredDocument) super
				.getDocument(encodeAs);

		if (!(adv instanceof Attributable)) {
			throw new IllegalArgumentException(
					"Only document types supporting atrributes are allowed");
		}

		if (null != getShowOwn()) {
			((Attributable) adv).addAttribute(SHOW_OWN_ATTR, Boolean
					.toString(getShowOwn()));
		}

		if (null != getGossip()) {
			Element e = adv.createElement(GOSSIP_TEXT_TAG, getGossip());

			adv.appendChild(e);
		}

		return adv;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String[] getIndexFields() {
		return INDEX_FIELDS;
	}

	/**
	 * Returns the gossip text which should be used by the gossip service.
	 * 
	 * @return The gossip text which should be used by the gossip service or
	 *         {@code null} if the service should use it's default value.
	 */
	public String getGossip() {
		return gossip;
	}

	/**
	 * Sets the gossip text which should be used by the gossip service.
	 * 
	 * @param gossip
	 *            The gossip text which should be used by the gossip service or
	 *            {@code null} to use service default.
	 */
	public void setGossip(String gossip) {
		this.gossip = gossip;
	}

	/**
	 * Returns whether the gossip service should show it's own gossip text.
	 * 
	 * @return If {@code true} then we should show our own gossip text, {@code
	 *         false} to omit it or {@code null} to use service default.
	 */
	public Boolean getShowOwn() {
		return showOwn;
	}

	/**
	 * Sets whether the gossip service should show it's own gossip text.
	 * 
	 * @param showOwn
	 *            If {@code true} then we should show our own gossip text,
	 *            {@code false} to omit it or {@code null} to use service
	 *            default.
	 */
	public void setShowOwn(Boolean showOwn) {
		this.showOwn = showOwn;
	}
}