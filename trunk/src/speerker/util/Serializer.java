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

package speerker.util;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class Serializer {
	/**
	 * Serializes an object and returns a byte array
	 * 
	 * @param object
	 *            A serializable object
	 * @return A byte array
	 */
	public static byte[] serialize(Serializable object) {
		byte[] sObject = "".getBytes();
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		ObjectOutputStream out;
		try {
			out = new ObjectOutputStream(bOut);
			out.writeObject(object);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sObject = bOut.toByteArray();
		return sObject;
	}

	public static Serializable deserialize(byte[] sObject)
			throws ClassNotFoundException {
		Object object = null;

		ByteArrayInputStream bIn = new ByteArrayInputStream(sObject);
		ObjectInputStream in;
		try {
			in = new ObjectInputStream(bIn);
			object = in.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (Serializable) object;
	}
}
