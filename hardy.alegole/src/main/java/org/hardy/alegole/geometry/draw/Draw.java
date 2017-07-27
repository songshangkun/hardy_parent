package org.hardy.alegole.geometry.draw;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import org.hardy.alegole.geometry.Line;
import org.hardy.alegole.geometry.Line.LinePe;
import org.hardy.alegole.geometry.Point;
import org.hardy.alegole.geometry.Point.Pe;

/**
 * 专门针对geometry画图的工具
 * @author songshangkun
 *
 */
public class Draw {
	
	public static void drawPoint(Graphics g,Point... points){
		   for(Point p:points){
			   Pe pe = p.pix();
			   g.fillOval(pe.x, pe.y, 5, 5);
		   }
	  }
	  public static void drawLine(Graphics g,Line... lines){		  
		   for(Line l:lines){
			   LinePe lp = l.pix();
			   g.drawLine(lp.start.x, lp.start.y,lp.end.x, lp.end.y);
		   }
	  }
}
