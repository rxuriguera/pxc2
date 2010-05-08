package speerker.inter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Display;

import speerker.App;
import speerker.library.XmlMusicLibrary;

public class UpMenuInter {
	
	private Shell shell;
	private Display display;
	private SpeerkerInter SpeerkerInter;

	public UpMenuInter(Shell sh, Display d, SpeerkerInter s){
		
		shell = sh;
		display = d;
		SpeerkerInter = s;
		
		Menu m = new Menu(shell, SWT.BAR);
	    // create a file menu and add an exit item
	    final MenuItem file = new MenuItem(m, SWT.CASCADE);
	    file.setText("&File");
	    final Menu filemenu = new Menu(shell, SWT.DROP_DOWN);
	    file.setMenu(filemenu);
	    final MenuItem separator = new MenuItem(filemenu, SWT.SEPARATOR);
	    final MenuItem exitItem = new MenuItem(filemenu, SWT.PUSH);
	    exitItem.setText("E&xit");
	    
	    final MenuItem library = new MenuItem(m, SWT.CASCADE);
	    library.setText("Library");
	    final Menu libraryMenu = new Menu(shell, SWT.DROP_DOWN);
	    library.setMenu(libraryMenu);
	    final MenuItem setPathLibrary = new MenuItem(libraryMenu, SWT.PUSH);
	    setPathLibrary.setText("Set Library Folder");
	    
	    exitItem.addSelectionListener(new SelectionAdapter() {
	        public void widgetSelected(SelectionEvent e) {
	          MessageBox messageBox = new MessageBox(shell, SWT.YES | SWT.NO);
	          messageBox.setMessage("Do you really want to exit?");
	          messageBox.setText("Speerker - Exit");
	          int response = messageBox.open();
	          if (response == SWT.YES)
	            System.exit(0);
	        }
	      });
	    
	    setPathLibrary.addSelectionListener(new SelectionAdapter() {
	        public void widgetSelected(SelectionEvent e) {
	        	DirectoryDialog fd = new DirectoryDialog(shell);
	            fd.setText("Set Library Folder");
	            String selected = fd.open();
	            XmlMusicLibrary lib = new XmlMusicLibrary (App.getProperty("MusicLibrary"));
	            lib.clear();
	            lib.add(selected);
	            lib.saveLibrary();
	          }
	      });
	    
	    shell.setMenuBar(m);

		
		
		
		
	}

}
