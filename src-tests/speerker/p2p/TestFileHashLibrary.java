package speerker.p2p;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import speerker.Song;

public class TestFileHashLibrary {
	protected FileHashLibrary library;

	@Before
	public void setUp() throws Exception {
		this.library = new FileHashLibrary("someId");
	}

	@Test
	public void testGetMatchingSongs() {
		List<Song> songs = this.library.getMatchingSongs(".*seventeen.*");
		assertEquals(1,songs.size());
		assertEquals("Seventeen",songs.get(0).getTitle());
	}

	@Test
	public void testGetMatchingHashes() {
		List<String> hashes = this.library.getMatchingHashes(".*money.*");
		assertEquals("One hash match", 1, hashes.size());
	}

}
