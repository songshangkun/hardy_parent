package org.hardy.alegole.geometry;

/**
 * 四条边的封闭图形
 * @author songshangkun
 *
 */
public class Quadrilateral extends MultiForm{
    
	public Quadrilateral(Point p1,Point p2,Point p3,Point p4) {
		super(new Point[]{p1,p2,p3,p4});
	}
	
	
}
