package speerker.inter;

import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Text;

public class UtilsSWT {

	public static Point getTextSize(Text text, int columns){

        GC gc = new GC (text);
    	FontMetrics fm = gc.getFontMetrics ();
    	int width = columns * fm.getAverageCharWidth ();
    	int height = fm.getHeight ();
    	gc.dispose ();
    	return text.computeSize (width, height);
    }
	
}
