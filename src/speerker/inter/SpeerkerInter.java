package speerker.inter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import speerker.Tools;


public class SpeerkerInter {

	private Display display;
	private Shell shell;
	private GridLayout shellLayout;
	private GridData gridCenter;
	private GridData gridPlayer;
	private GridData gridSearch;
	private GridData gridMenu;
	private Composite compoSearch;
	private Composite compoMenu;
	private Composite compoCenter;
	private Composite compoPlayer;
	private Tools tools;
	private SearchSlotInter searchSlotInter;
	private BrowserInter browserInter;
	private MenuInter menuInter;
	private PlayerInter playerInter;
	private PlaylistInter playlistInter;
	private Composite compoLogo;
	private GridData gridLogo;
	private LogoInter logoInter;
	private GridLayout centerLayout;
	private Composite compoBrowser;
	private GridData gridBrowser;
	private Composite compoPlaylist;
	private GridData gridPlaylist;
	private Composite compoSearchSlot;
	private GridData gridSearchSlot;
	private SearchInter searchInter;
	private UpMenuInter upMenuInter;

	
	public SpeerkerInter(Tools t, Display d){
		
		tools = t;
		
		display = d;
		Display.setAppName("Speerker");
		shell = new Shell(display, SWT.TITLE | SWT.CLOSE | SWT.BORDER | SWT.RESIZE | SWT.MIN | SWT.MAX);
		shell.setMinimumSize(800, 500);
		
		shell.setText("Speerker");
		shell.setSize(800, 500);
		shell.setLocation(200, 150);
		shellLayout = new GridLayout(2, false);
		shell.setLayout(shellLayout);
		
		compoLogo = new Composite(shell, SWT.NONE);
		gridLogo = new GridData();
		gridLogo.widthHint = 175;
		compoLogo.setLayoutData(gridLogo);
		
		compoSearchSlot = new Composite(shell, SWT.NONE);
		gridSearchSlot = new GridData(GridData.FILL_HORIZONTAL);
		compoSearchSlot.setLayoutData(gridSearchSlot);
		
		compoMenu = new Composite(shell, SWT.NONE);
		gridMenu = new GridData(GridData.FILL_VERTICAL);
		gridMenu.widthHint = 175;
		compoMenu.setLayoutData(gridMenu);
		
		compoCenter = new Composite(shell, SWT.NONE);
		gridCenter = new GridData(GridData.FILL_BOTH);
		compoCenter.setLayoutData(gridCenter);
		centerLayout = new GridLayout(1, false);
		centerLayout.marginBottom = 0;
		centerLayout.marginHeight = 0;
		centerLayout.marginLeft = 0;
		centerLayout.marginRight = 0;
		centerLayout.marginTop = 0;
		centerLayout.marginWidth = 0;
		compoCenter.setLayout(centerLayout);
		
		compoBrowser = new Composite(compoCenter, SWT.NONE);
		gridBrowser = new GridData(GridData.FILL_BOTH);
		compoBrowser.setLayoutData(gridBrowser);
		
		compoPlaylist = new Composite(compoCenter, SWT.NONE);
		gridPlaylist = new GridData(GridData.FILL_BOTH);
		compoPlaylist.setLayoutData(gridPlaylist);
		
		compoSearch = new Composite(compoCenter, SWT.NONE);
		gridSearch = new GridData(GridData.FILL_BOTH);
		compoSearch.setLayoutData(gridSearch);
		
		compoPlayer = new Composite(shell, SWT.NONE);
		gridPlayer = new GridData(GridData.FILL_HORIZONTAL);
		gridPlayer.horizontalSpan = 2;
		compoPlayer.setLayoutData(gridPlayer);
		
		logoInter = new LogoInter(compoLogo, display);
		searchSlotInter = new SearchSlotInter(compoSearchSlot, display, this); 
		playlistInter = new PlaylistInter(compoPlaylist, display, tools.getPlaylist());
		searchInter = new SearchInter(compoSearch, display, this);
		browserInter = new BrowserInter(compoBrowser, display);
		menuInter = new MenuInter(compoMenu, display, this);
		playerInter = new PlayerInter(compoPlayer, display, tools.getPlayer());
		upMenuInter = new UpMenuInter(shell, display, this);
		
		switchCenter("Browser");		
		
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		
		tools.getPlayer().stop();
		shell.dispose();
		
		
	
	}


	public void switchCenter(String center) {

        if (center.equals("Playlist")) {
        	compoSearch.setVisible (!(gridSearch.exclude = true));
            compoBrowser.setVisible (!(gridBrowser.exclude = true));
            compoPlaylist.setVisible (!(gridPlaylist.exclude = false));
        } else if(center.equals("Browser")){
        	compoSearch.setVisible (!(gridSearch.exclude = true));
            compoPlaylist.setVisible (!(gridPlaylist.exclude = true));
            compoBrowser.setVisible (!(gridBrowser.exclude = false));
        } else if(center.equals("Search")){
        	compoSearch.setVisible (!(gridSearch.exclude = false));
            compoPlaylist.setVisible (!(gridPlaylist.exclude = true));
            compoBrowser.setVisible (!(gridBrowser.exclude = true));
        }
        compoCenter.layout();
        compoCenter.getParent().layout();
	}
	
	public Tools getTool(){
		return this.tools;
	}
	
	public MenuInter getMenuInter(){
		return this.menuInter;
	}
	
	public SearchInter getSearchInter(){
		return this.searchInter;
	}
	
}
