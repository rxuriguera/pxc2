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
	public static byte[] serialize(Serializable object) throws IOException{
		byte[] sObject = "".getBytes();
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(bOut);
		out.writeObject(object);
		sObject = bOut.toByteArray();
		return sObject;
	}

	public static Serializable deserialize(byte[] sObject) throws IOException, ClassNotFoundException {
		Object object = null;

		ByteArrayInputStream bIn = new ByteArrayInputStream(sObject);
		ObjectInputStream in = new ObjectInputStream(bIn);
		object = in.readObject();

		return (Serializable) object;
	}
}
