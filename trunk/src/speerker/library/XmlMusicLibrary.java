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

package speerker.library;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.farng.mp3.MP3File;
import org.farng.mp3.id3.AbstractID3v2;
import org.farng.mp3.id3.ID3v1;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import speerker.util.MD5Checksum;

public class XmlMusicLibrary {

	Document lib;
	File flib;

	public XmlMusicLibrary(String path) {

		flib = new File(path);
		if (flib.exists()) {
			try {
				SAXBuilder builder = new SAXBuilder();
				lib = builder.build(new File(path));
			} catch (JDOMException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			} catch (java.io.IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				flib.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Element root = new Element("library");
			lib = new Document(root);
			outputDocumentToFile();
		}

	}

	public void saveLibrary() {
		flib.delete();
		try {
			flib.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		outputDocumentToFile();
	}

	public void add(String path) {

		File f = new File(path);
		if (f.isFile()) {
			try {
				Element song = fileToElement(path);
				if (song != null) {
					Element library = lib.getRootElement();
					library.addContent(song);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (f.isDirectory()) {
			String[] listPath = f.list();
			for (int i = 0; i < listPath.length; ++i) {
				add(path + "/" + listPath[i]);
			}
		}
	}

	public Element search(String term) {

		Element result = new Element("search");
		String[] terms = term.split(" ");

		if (terms.length > 0) {

			Element library = lib.getRootElement();
			List songs = library.getChildren();
			Iterator it = songs.iterator();

			Element temporal = null;

			while (it.hasNext()) {
				temporal = (Element) it.next();
				boolean ok = true;

				for (int i = 0; i < terms.length; ++i) {
					boolean temp = false;
					if (temporal.getChild("title").getText().contains(terms[i]))
						temp = true;
					else if (temporal.getChild("album").getText().contains(
							terms[i]))
						temp = true;
					else if (temporal.getChild("artist").getText().contains(
							terms[i]))
						temp = true;
					ok = (ok && temp);
				}

				if (ok) {
					Element res = (Element) temporal.clone();
					res.detach();
					result.addContent(res);
				}
			}
		}

		return result;
	}

	private Element fileToElement(String path) throws Exception {

		File songFile = new File(path);
		Element song = new Element("song");

		if (songFile.exists() && songFile.canRead()) {
			if (path.endsWith(".mp3")) {
				String hash = MD5Checksum.getMD5Checksum(songFile
						.getAbsolutePath());
				if (!existSong(hash)) {
					MP3File mp3 = new MP3File(songFile);
					if (mp3.hasID3v1Tag()) {
						ID3v1 tag = mp3.getID3v1Tag();
						song.addContent(new Element("title").addContent(tag
								.getTitle()));
						song.addContent(new Element("artist").addContent(tag
								.getArtist()));
						song.addContent(new Element("album").addContent(tag
								.getAlbum()));
					} else if (mp3.hasID3v2Tag()) {
						AbstractID3v2 tag = mp3.getID3v2Tag();
						song.addContent(new Element("title").addContent(tag
								.getSongTitle()));
						song.addContent(new Element("artist").addContent(tag
								.getLeadArtist()));
						song.addContent(new Element("album").addContent(tag
								.getAlbumTitle()));
					} else
						return null;

					song.addContent(new Element("hash").addContent(hash));
					song.addContent(new Element("path").addContent(songFile
							.getAbsolutePath()));

				} else
					return null;
			} else
				return null;
		} else
			return null;

		return song;
	}

	private boolean existSong(String hash) {

		Element library = lib.getRootElement();
		List songs = library.getChildren();
		Iterator it = songs.iterator();

		Element temporal = null;

		while (it.hasNext()) {
			temporal = (Element) it.next();
			if (temporal.getChild("hash").getText().equals(hash))
				return true;
		}

		return false;
	}

	private void outputDocumentToFile() {
		try {
			Format format = Format.getPrettyFormat();
			format.setEncoding("ISO-8859-1");
			XMLOutputter outputter = new XMLOutputter(format);
			FileWriter writer = new FileWriter(flib);
			outputter.output(lib, writer);
			writer.close();
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
	}

	public List<Element> getSongs() {
		Element library = lib.getRootElement();
		return library.getChildren();
	}

}
