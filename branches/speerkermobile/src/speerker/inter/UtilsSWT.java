package speerker.inter;

import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

public class UtilsSWT {

	public static Point getTextSize(Text text, int columns){

		GC gc = new GC (text);
    	FontMetrics fm = gc.getFontMetrics ();
    	int width = columns * fm.getAverageCharWidth ();
    	int height = fm.getHeight ();
    	gc.dispose ();
    	return new Point (width, height);
    }
	
	public static int getButtonSize(Button button){

		int columns = button.getText().length();
        GC gc = new GC (button);
    	FontMetrics fm = gc.getFontMetrics ();
    	int width = 20 + columns * fm.getAverageCharWidth ();
    	gc.dispose ();
    	return width;
    }
	
}
