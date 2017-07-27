package org.hardy.alegole;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import org.hardy.alegole.geometry.MultiLine;
import org.hardy.alegole.geometry.Quadrilateral;
import org.hardy.alegole.geometry.Rectangle;
import org.hardy.alegole.geometry.Square;
import org.hardy.alegole.geometry.Triangle;
import org.hardy.alegole.geometry.draw.Draw;

public class DrowPanel extends JPanel{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static Quadrilateral sj ;
	
 
	@Override
	public void paint(Graphics g) {	
		super.paint(g);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Draw.drawLine(g, sj.getLines());
		g.drawString(String.valueOf(sj.getAngle(sj.getVertexs()[2])), 500, 500);
		g.setColor(Color.RED);
		Draw.drawPoint(g, sj.getVertexs()[2]);
		this.validate();
	}
   
	  
	
}
