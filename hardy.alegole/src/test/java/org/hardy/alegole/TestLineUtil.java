package org.hardy.alegole;

import java.util.Arrays;

import org.hardy.alegole.GeotudeMetre.GeoPointDDD;
import org.hardy.alegole.GeotudeMetre.GeoRectDDD;
import org.hardy.alegole.LineUtil.Line;
import org.hardy.alegole.LineUtil.Point;

public class TestLineUtil {
    
	 public static void main(String[] args) {
//			 LineUtil.Line line1 = new Line(0, 0, 100, 100);
//			 LineUtil.Line line2 = new Line(80, 80, 400, 400);
//			Point[] ps =  LineUtil.intersection(line1, line2);			
//			System.out.println(Arrays.toString(ps));
		 
		 GeoPointDDD d1 = new GeoPointDDD(113.4479232946,34.8721061049);
		 GeoPointDDD d2 = new GeoPointDDD(113.8769853562 ,34.7061109509);
		 
		 GeoRectDDD[] geotudeMetres =  GeotudeMetre.netGridLL(d1, d2, 3, 3) ;
		 for(GeoRectDDD g:geotudeMetres){
			 System.out.println(g);
		 }
	}
}
/**
//System.out.println(Math.sin(Math.PI/6));
//System.out.println(Math.asin(0.5)/Math.PI*180);
**/