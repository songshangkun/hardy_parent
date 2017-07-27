package org.hardy.alegole.geometry;

/**
 * 长方形
 * @author songshangkun
 *
 */
public class Rectangle extends Quadrilateral{
    /**
     * 通过两个点确定一个矩形 
     * @param left_up
     * @param right_down
     */
	public Rectangle(Point left_up, Point right_down) {
		super(left_up, new Point(right_down.getX(),left_up.getY()),
				right_down, new Point(left_up.getX(),right_down.getY()));
	}
	/**
	 * 通过一个点和宽高确定矩形
	 * @param left_up
	 * @param width
	 * @param height
	 */
	public Rectangle(Point left_up, double width,double height){
		super(left_up,new Point(left_up.getX()+width,left_up.getY()) , 
				new Point(left_up.getX()+width,left_up.getY()+height),
				new Point(left_up.getX(),left_up.getY()+height));
	}
	 

}
