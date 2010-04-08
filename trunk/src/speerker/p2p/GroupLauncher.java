/*
 * Speerker 
 * Copyright (C) 2010  Jordi Bartrolí, Hector Mañosas i Ramon Xuriguera
 * 
 * This file is largely based on MultiNetGroupLaucher from the JXTA
 * documentation.
 * Copyright (c) 2006-2007 Sun Microsystems, Inc.  All rights reserved.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package speerker.p2p;

import java.io.File;
import java.net.URI;

import net.jxta.document.MimeMediaType;
import net.jxta.document.XMLDocument;
import net.jxta.exception.PeerGroupException;
import net.jxta.peer.PeerID;
import net.jxta.peergroup.NetPeerGroupFactory;
import net.jxta.peergroup.PeerGroup;
import net.jxta.peergroup.PeerGroupID;
import net.jxta.peergroup.WorldPeerGroupFactory;
import net.jxta.platform.NetworkConfigurator;
import net.jxta.protocol.ModuleImplAdvertisement;

import net.jxta.id.IDFactory;
import net.jxta.impl.endpoint.mcast.McastTransport;
import net.jxta.impl.peergroup.StdPeerGroupParamAdv;


public class GroupLauncher {
    /**
     * Configure and start the World Peer Group.
     *
     * @param storeHome The location JXTA will use to store all persistent data.
     * @param peername  The name of the peer.
     * @throws PeerGroupException Thrown for errors creating the world peer group.
     * @return the world peergroup
     */
    public static PeerGroup startJXTA(URI storeHome, String peername) throws PeerGroupException {
        NetworkConfigurator worldGroupConfig = NetworkConfigurator.newAdHocConfiguration(storeHome);

        PeerID peerid = IDCreator.createPeerID(PeerGroupID.worldPeerGroupID, peername); 
        worldGroupConfig.setName(peername);
        worldGroupConfig.setPeerID(peerid);
        // Disable multicast because we will be using a separate multicast in each group.
        worldGroupConfig.setUseMulticast(false);

        // Instantiate the world peer group
        WorldPeerGroupFactory wpgf = new WorldPeerGroupFactory(worldGroupConfig.getPlatformConfig(), storeHome);

        PeerGroup wpg = wpgf.getInterface();

        System.out.println("JXTA World Peer Group : " + wpg + " started!");

        return wpg;
    }

    /**
     * Configure and start a separate top-level JXTA domain.
     *
     * @param wpg        The JXTA context into which the domain will run.
     * @param domainName The name of the domain.
     * @throws PeerGroupException Thrown for errors creating the domain.
     * @return the net peergroup
     */
    public static PeerGroup startDomain(PeerGroup wpg, String domainName) throws PeerGroupException {
        assert wpg.getPeerGroupID().equals(PeerGroupID.worldPeerGroupID);

        ModuleImplAdvertisement npgImplAdv;
        try {
            npgImplAdv = wpg.getAllPurposePeerGroupImplAdvertisement();
            npgImplAdv.setModuleSpecID(PeerGroup.allPurposePeerGroupSpecID);

            StdPeerGroupParamAdv params = new StdPeerGroupParamAdv(npgImplAdv.getParam());
            params.addProto(McastTransport.MCAST_TRANSPORT_CLASSID, McastTransport.MCAST_TRANSPORT_SPECID);
            npgImplAdv.setParam((XMLDocument) params.getDocument(MimeMediaType.XMLUTF8));
        } catch (Exception failed) {
            throw new PeerGroupException("Could not construct domain ModuleImplAdvertisement", failed);
        }

        // Configure the domain
        NetworkConfigurator domainConfig = NetworkConfigurator.newAdHocConfiguration(wpg.getStoreHome());

        PeerGroupID domainID = IDCreator.createPeerGroupID(domainName);
        domainConfig.setInfrastructureID(domainID);
        domainConfig.setName(wpg.getPeerName());
        domainConfig.setPeerID(wpg.getPeerID());
        domainConfig.setUseMulticast(true);
        int domainMulticastPort = 1025 + (domainName.hashCode() % 60000);
        domainConfig.setMulticastPort(domainMulticastPort);

        // Instantiate the domain net peer group
        NetPeerGroupFactory npgf1 = new NetPeerGroupFactory(wpg, domainConfig.getPlatformConfig(), npgImplAdv);
        PeerGroup domain = npgf1.getInterface();
        System.out.println("Peer Group : " + domain + " started!");

        return domain;
    }
    
    
    public static PeerGroup launchGroup(String peerName, String groupName) throws PeerGroupException{
        final URI storeHome = new File(".cache/").toURI();
        PeerGroup wpg = startJXTA(storeHome, peerName);
        return startDomain(wpg, groupName);
    }

}
