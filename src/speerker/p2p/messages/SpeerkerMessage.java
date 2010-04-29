/*
 * Speerker 
 * Copyright (C) 2010  Jordi Bartrolí, Hector Mañosas i Ramon Xuriguera
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

package speerker.p2p.messages;

import java.io.Serializable;

import peerbase.PeerMessage;
import speerker.util.Serializer;

public class SpeerkerMessage extends PeerMessage {
	public static final String JOIN = "JOIN";
	public static final String LIST = "LIST";
	public static final String INFO = "INFO";
	public static final String QUERY = "QUER";
	public static final String RESPONSE = "RESP";
	public static final String PARTREQ = "PREQ";
	public static final String PARTRSP = "PRSP";
	public static final String QUIT = "QUIT";

	public static final String REPLY = "REPL";
	public static final String ERROR = "ERRO";

	public SpeerkerMessage(String type) {
		this(type, "");
	}

	public SpeerkerMessage(String type, Serializable data) {
		super(type.getBytes(), Serializer.serialize(data));
	}

	public SpeerkerMessage(byte[] type, byte[] data) {
		super(type, data);
	}

	public SpeerkerMessage(PeerMessage msg) {
		super(msg.getMsgTypeBytes(), msg.getMsgDataBytes());
	}

	public Serializable getMsgContent() throws ClassNotFoundException {
		return Serializer.deserialize(super.getMsgDataBytes());
	}

	public static SpeerkerMessage valueOf(PeerMessage msg) {
		return new SpeerkerMessage(msg);
	}
}
