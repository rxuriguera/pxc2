package speerker.player;

import java.util.Map;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Scale;

import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerListener;

public class PlayerListener implements BasicPlayerListener{

	
	SpeerkerPlayer player;
	private double bytesLength;
	
	public PlayerListener(SpeerkerPlayer p){
		player=p;
		
	}
	
	public void opened(Object arg0, Map arg1) {
		// TODO Auto-generated method stub
		if (arg1.containsKey("audio.length.bytes")) {
			  bytesLength = Double.parseDouble(arg1.get("audio.length.bytes").toString());
			  player.setMaximumScale((int) bytesLength);
		}
		
	}

	@Override
	public void progress(int bytesread, long microseconds, byte[] pcmdata,  Map properties) {
		// TODO Auto-generated method stub
		float progressUpdate = (float) (bytesread * 1.0f / bytesLength * 1.0f);
		player.setScaleValue( (int) (bytesLength * progressUpdate));
	}

	@Override
	public void setController(BasicController arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stateUpdated(BasicPlayerEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getCode()==BasicPlayerEvent.EOM) player.next();
		
	}

}
