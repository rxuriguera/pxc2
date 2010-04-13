package speerker.library;
import java.util.Iterator;
import java.util.List;

import org.jdom.Element;

import speerker.library.xmlMusicLibrary;


public class xmlMusicLibraryTest {

    public static void main(String argv[]) {
    	
    	System.out.println("Test xmlMusicLibrary:");
    	System.out.println("Load/create library from file... xml/lib.xml");
    	xmlMusicLibrary lib = new xmlMusicLibrary("xml/lib.xml");
    	System.out.println("Done");
    	System.out.println();
    	
    	System.out.println("Adding mp3 file/directory...");
    	try {
    		System.out.println("/Users/bartru/Music");
			lib.add("/Users/bartru/Music");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Done");
		System.out.println();
		
		System.out.println("Searching: Cullum");
		Element search = lib.search("Cullum");
		System.out.println("Search results:");
		showSearch(search);
		System.out.println("Done");
		System.out.println();
		
		System.out.println("Searching: Here I Come");
		search = lib.search("Here I Come");
		showSearch(search);
		System.out.println("Done");
		System.out.println();
		
		System.out.println("Searching: The Coco");
		search = lib.search("The Coco");
		showSearch(search);
		System.out.println("Done");
		System.out.println();
		
		System.out.println("Saving library");
		lib.saveLibrary();
		System.out.println("Done");
		System.out.println();
    }
    
    private static void showSearch(Element search){
    	List list = search.cloneContent();
		Iterator it = list.iterator();
		Element temporal;
		while (it.hasNext()){
			temporal = (Element) it.next();
			System.out.println();
			System.out.println("Title: " + temporal.getChildText("title"));
			System.out.println("Artist: " + temporal.getChildText("artist"));
			System.out.println("Album: " + temporal.getChildText("album"));
		}
    }
}
