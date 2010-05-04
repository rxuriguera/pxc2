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

	
	public SpeerkerInter(Tools t, Display d){
		
		tools = t;
		
		display = d;
		Display.setAppName("Speerker");
		shell = new Shell(display, SWT.TITLE | SWT.CLOSE | SWT.BORDER | SWT.RESIZE);
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
		
		compoSearch = new Composite(shell, SWT.NONE);
		gridSearch = new GridData(GridData.FILL_HORIZONTAL);
		compoSearch.setLayoutData(gridSearch);
		
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
		
		compoPlayer = new Composite(shell, SWT.NONE);
		gridPlayer = new GridData(GridData.FILL_HORIZONTAL);
		gridPlayer.horizontalSpan = 2;
		compoPlayer.setLayoutData(gridPlayer);
		
		logoInter = new LogoInter(compoLogo, display);
		searchSlotInter = new SearchSlotInter(compoSearch, display, tools.getSearchManager()); 
		playlistInter = new PlaylistInter(compoPlaylist, display, tools.getPlaylist());
		browserInter = new BrowserInter(compoBrowser, display);
		menuInter = new MenuInter(compoMenu, display, this);
		playerInter = new PlayerInter(compoPlayer, display, tools.getPlayer());
		
		gridPlaylist.exclude = true;		
		
		String title0 = "La llave de oro";
		String artist0 = "Los Planetas";
		String album0 = "Una opera egipcia";
		String path0 = "/Users/bartru/Music/Los Planetas - Una opera egipcia/01. La llave de oro.mp3";
		
		String title1 = "Una corona de estrellas";
		String artist1 = "Los Planetas";
		String album1 = "Una opera egipcia";
		String path1 = "/Users/bartru/Music/Los Planetas - Una opera egipcia/02. Una corona de estrellas.mp3";
		
		String title2 = "Soy un pobre granaino";
		String artist2 = "Los Planetas";
		String album2 = "Una opera egipcia";
		String path2 = "/Users/bartru/Music/Los Planetas - Una opera egipcia/03. Soy un pobre granaino.mp3";
		
		String title3 = "Siete Faroles";
		String artist3 = "Los Planetas";
		String album3 = "Una opera egipcia";
		String path3 = "/Users/bartru/Music/Los Planetas - Una opera egipcia/04. Siete Faroles.mp3";
		
		String title4 = "No se como te atreves";
		String artist4 = "Los Planetas";
		String album4 = "Una opera egipcia";
		String path4 = "/Users/bartru/Music/Los Planetas - Una opera egipcia/05. No se como te atreves.mp3";
		
		tools.getPlaylist().add(title0,artist0,album0,path0);
		tools.getPlaylist().add(title1,artist1,album1,path1);
		tools.getPlaylist().add(title2,artist2,album2,path2);
		tools.getPlaylist().add(title3,artist3,album3,path3);
		tools.getPlaylist().add(title4,artist4,album4,path4);
		
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		
		tools.getPlayer().stop();
		shell.dispose();
		
		
		
	}


	public void switchCenter(String center) {
		
        GridData playlistGridData = (GridData) compoPlaylist.getLayoutData();
        GridData broswerGridData = (GridData) compoBrowser.getLayoutData();

        if (center.equals("Playlist")) {
            compoBrowser.setVisible (!(gridBrowser.exclude = true));
            compoPlaylist.setVisible (!(gridPlaylist.exclude = false));
        } else if(center.equals("Browser")){
            compoPlaylist.setVisible (!(gridPlaylist.exclude = true));
            compoBrowser.setVisible (!(gridBrowser.exclude = false));
        }
        compoCenter.layout();
        compoCenter.getParent().layout();
	}
	
}
