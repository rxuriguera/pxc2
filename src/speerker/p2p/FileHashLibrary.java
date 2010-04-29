package speerker.p2p;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.jdom.Element;

import speerker.App;
import speerker.Song;
import speerker.library.XmlMusicLibrary;

public class FileHashLibrary {

	// Maps from file hash to peer-id
	private HashMap<String, String> owners;
	// Maps from file hash to local path
	private HashMap<String, String> paths;
	// Maps from file info to hash
	private HashMap<String, String> searchInfo;

	protected XmlMusicLibrary library;

	public FileHashLibrary(String peerId) {
		this.library = new XmlMusicLibrary(App.getProperty("MusicLibrary"));
		this.loadFiles(peerId);
	}

	public HashMap<String, String> getOwners() {
		return owners;
	}

	public void setOwners(HashMap<String, String> owners) {
		this.owners = owners;
	}

	public HashMap<String, String> getPaths() {
		return paths;
	}

	public void setPaths(HashMap<String, String> paths) {
		this.paths = paths;
	}

	public HashMap<String, String> getSearchInfo() {
		return searchInfo;
	}

	public void setSearchInfo(HashMap<String, String> searchInfo) {
		this.searchInfo = searchInfo;
	}
	
	public String getFilePath(String hash){
		return this.paths.get(hash);
	}

	/**
	 * Registers all the files from the default library as being locally
	 * available. Stores their info and hash so they can later be found by other
	 * peers. The default library can be set using the "MusicLibrary" property
	 * in the Speerker configuration file.
	 */
	public void loadFiles(String peerId) {
		owners = new HashMap<String, String>();
		paths = new HashMap<String, String>();
		searchInfo = new HashMap<String, String>();

		List<Element> music = this.library.getSongs();

		Element song;
		String str, hash, path;
		Iterator<Element> it = music.iterator();
		while (it.hasNext()) {
			song = it.next();
			str = song.getChildText("title").toLowerCase() + " "
					+ song.getChildText("artist").toLowerCase() + " "
					+ song.getChildText("album").toLowerCase();
			hash = song.getChildText("hash");
			path = song.getChildText("path");
			this.owners.put(hash, peerId);
			this.paths.put(hash, path);
			this.searchInfo.put(str, hash);
		}
	}


	/**
	 * Returns a list of songs whose title, artist or album match the given
	 * query.
	 * 
	 * @param query
	 * @return
	 */
	public List<Song> getMatchingSongs(String query) {
		List<Song> songs = new LinkedList<Song>();
		Song song = null;
		Iterator<String> iterator = this.getMatchingHashes(query).iterator();
		while (iterator.hasNext()) {
			song = this.library.getSong(iterator.next());
			if (song != null) {
				songs.add(song);
			}
		}
		return songs;
	}

	/**
	 * Get all the hashes of the files that match the query.
	 * 
	 * @param query
	 * @return
	 */
	public List<String> getMatchingHashes(String query) {
		List<String> hashes = new LinkedList<String>();
		Set<String> songInfoSet = this.searchInfo.keySet();
		Iterator<String> iterator = songInfoSet.iterator();

		String searchInfo = "";

		while (iterator.hasNext()) {
			searchInfo = iterator.next();
			if (Pattern.matches(query, searchInfo)) {
				hashes.add(this.searchInfo.get(searchInfo));
			}
		}
		return hashes;
	}

}
