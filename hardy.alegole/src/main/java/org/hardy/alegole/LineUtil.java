package org.hardy.alegole;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class LineUtil {
	
	public static boolean isIntersection(Line line1,Line line2){
		double tan1=0,tan2=0;
		Point[] posX = sortX(line1,line2); 
		   if(posX[0].key.equals(posX[1].key))return false;
		   Point[] posY = sortY(line1,line2); 
		   if(posY[0].key.equals(posY[1].key))return false;
		if(posX[1].key.equals(posX[2].key)){ //说明一条线完全包裹另一条
			double tan = 0d;
			if(posX[1].key.equals(line1.key)){
				tan = tan2;
			}else{
				tan = tan1;
			}
			int x1 = posX[1].x - posX[0].x;
			int x2 = posX[2].x - posX[0].x;
			double y1 = x1*tan;
			double y2 = x2*tan;
			if(y1+posX[0].y>posX[1].y&&y2+posX[0].y>posX[2].y) return false;
			if(y1+posX[0].y<posX[1].y&&y2+posX[0].y<posX[2].y) return false;
		}
		if(!posX[1].key.equals(posX[2].key)){//说明一条线包裹另一条一部分
			int x1 = posX[1].x - posX[0].x;
			int x2 = posX[2].x - posX[1].x;
			double y1 = 0d;
				if(posX[1].key.equals(line1.key))y1 = x1*tan2;
				else y1 = x1*tan1;
			double y2 = 0d;
				if(posX[2].key.equals(line1.key))y2 = x2*tan2;
				else y2 = x2*tan1;
			if(y1+posX[0].y>posX[1].y&&posX[2].y>y2+posX[1].y) return false;
			if(y1+posX[0].y<posX[1].y&&posX[2].y<y2+posX[1].y) return false;
		}
		return true;
	}
	/**
	 * 计算两线断相交交点
	 * @param line1
	 * @param line2
	 * @return
	 */
	public static Point[] intersection(Line line1 ,Line line2){  
	try{	
	    int f1,f2;
		double tan1=0,tan2=0;
		if(line1.end.y==line1.start.y) {
			f1 = 0;
		}
		else if(line1.end.x==line1.start.x) {
			f1 = 1;
		}
		else {
			tan1 = ((double)line1.end.y-line1.start.y)/((double)line1.end.x-line1.start.x);
			f1 = 2;
		}
		if(line2.end.y==line2.start.y) {
			f2 = 0;
		}
		else if(line2.end.x==line2.start.x) {
			f2 = 1;
		}
		else {
			tan2 = ((double)line2.end.y-line2.start.y)/((double)line2.end.x-line2.start.x);
			f2 = 2;
		}
		//0 与x轴平行  1 与y轴平行  2 有倾斜角度
		//0-0   
		if(f1==0&&f2==0){
		   if(line1.end.y!=line2.end.y) return null;
           Point[] pos = sortX(line1,line2);
		   if(pos[0].key.equals(pos[1])) return null;
		   else{
			   Point[] vs = new Point[pos[2].x-pos[1].x+1];
			   for(int i=0;i<vs.length;i++){
				   vs[i] = new Point(null, pos[1].x+i, pos[1].y);
			   }
			   return vs;
		   }
		} 
		//1-1
		if(f1==1&&f2==1){
			   if(line1.end.x!=line2.end.x) return null;
	           Point[] pos = sortY(line1,line2);
			   if(pos[0].key.equals(pos[1])) return null;
			   else{
				   Point[] vs = new Point[pos[2].y-pos[1].y+1];
				   for(int i=0;i<vs.length;i++){
					   vs[i] = new Point(null, pos[1].x, pos[1].y+i);
				   }
				   return vs;
			   }
			} 
		   Point[] posX = sortX(line1,line2); 
		   if(posX[0].key.equals(posX[1].key))return null;
		   Point[] posY = sortY(line1,line2); 
		   if(posY[0].key.equals(posY[1].key))return null;
		//0-1 1-0
		if(f1==0&&f2==1||f1==1&&f2==0){
			return new Point[]{new Point(null,posX[1].x,posY[1].y)};
		} 
		//其他情况 先看会不会有交点
		if(posX[1].key.equals(posX[2].key)){ //说明一条线完全包裹另一条
			double tan = 0d;
			if(posX[1].key.equals(line1.key)){
				tan = tan2;
			}else{
				tan = tan1;
			}
			int x1 = posX[1].x - posX[0].x;
			int x2 = posX[2].x - posX[0].x;
			double y1 = x1*tan;
			double y2 = x2*tan;
			if(y1+posX[0].y>posX[1].y&&y2+posX[0].y>posX[2].y) return null;
			if(y1+posX[0].y<posX[1].y&&y2+posX[0].y<posX[2].y) return null;
		}
		if(!posX[1].key.equals(posX[2].key)){//说明一条线包裹另一条一部分
			int x1 = posX[1].x - posX[0].x;
			int x2 = posX[2].x - posX[1].x;
			double y1 = 0d;
				if(posX[1].key.equals(line1.key))y1 = x1*tan2;
				else y1 = x1*tan1;
			double y2 = 0d;
				if(posX[2].key.equals(line1.key))y2 = x2*tan2;
				else y2 = x2*tan1;
			if(y1+posX[0].y>posX[1].y&&posX[2].y>y2+posX[1].y) return null;
			if(y1+posX[0].y<posX[1].y&&posX[2].y<y2+posX[1].y) return null;
		}
		 //计算焦点
		 //0-2 2-0
		if(f1==0||f2==0){
			 double foucs_x = 0d;
			 double foucs_y = 0d;
			if(f1==0) {
				foucs_y = line1.start.y;
				foucs_x = (line1.start.y-line2.start.y)*(1/tan2)+line2.start.x; 
				return new Point[]{new Point(null,(int)Math.round(foucs_x),(int)Math.round(foucs_y))};
			}
			else{
				foucs_y = line2.start.y;
				foucs_x = (line2.start.y-line1.start.y)*(1/tan1)+line1.start.x; 
				return new Point[]{new Point(null,(int)Math.round(foucs_x),(int)Math.round(foucs_y))};
			}	 
		}
		//1-2 2-1
		double foucs_x = 0d;
		double foucs_y = 0d;
		if(f1==1||f2==1){
			if(f1==1) {
				foucs_x = line1.start.x;
				foucs_y = tan2*(line1.start.x-line2.start.x)+line2.start.y;
				return new Point[]{new Point(null,(int)Math.round(foucs_x),(int)Math.round(foucs_y))};
			}
			else{
				foucs_x = line2.start.x;
				foucs_y = tan1*(line2.start.x-line1.start.x)+line1.start.y;
				return new Point[]{new Point(null,(int)Math.round(foucs_x),(int)Math.round(foucs_y))};
			}	 
		}
		//2-2
		foucs_y = ((-(tan1/tan2))*line2.start.y+tan1*(line2.start.x-line1.start.x)+line1.start.y)/(1-tan1/tan2);
		foucs_x = (foucs_y-line1.start.y)/tan1+line1.start.x;
		return new Point[]{new Point(null,(int)Math.round(foucs_x),(int)Math.round(foucs_y))};
	  }finally {
		 
	  }
	}
	
	/**
	 * 计算线的角度(斜率)
	 * @param line
	 * @return
	 */
	public static double slope(Line line){
		if(line.end.y==line.start.y)return 0d;
		else if(line.end.x==line.start.x)return 90d;
		else return Math.atan((line.end.y-line.start.y)/(line.end.x-line.start.x))/Math.PI*180;
	}
	/**
	 * 排列线端X轴坐标
	 * @param lines
	 * @return
	 */
	private static Point[] sortX(Line... lines){
		Point[] points = new Point[lines.length*2];
		int i=0;
		for(Line l:lines){
			points[i] = l.start;
			points[++i] = l.end;
			i++;
		}
		 Arrays.sort(points,new Comparator<Point>() {

			@Override
			public int compare(Point o1, Point o2) {
				 
				return o1.x-o2.x;
			}
		});
		return points; 
	}
	/**
	 * 排列线端X轴坐标
	 * @param lines
	 * @return
	 */
	private static Point[] sortY(Line... lines){
		Point[] points = new Point[lines.length*2];
		int i=0;
		for(Line l:lines){
			points[i] = l.start;
			points[++i] = l.end;
			i++;
		}
		 Arrays.sort(points,new Comparator<Point>() {

			@Override
			public int compare(Point o1, Point o2) {
				 
				return o1.y-o2.y;
			}
		});
		return points; 
	}
	
	/**
	 * 代表一个点
	 * @author songshangkun
	 *
	 */
	public static class Point{
		 
		public Point(Object key){this.key = key;}
		public Point(Object key,int x,int y){
			this(key);
			this.x = x;
			this.y = y;
		}
		public Point(int x,int y){
			this(UUID.randomUUID(), x, y);
		}
		public Object key;
		public int x;
		public int y;
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((key == null) ? 0 : key.hashCode());
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Point other = (Point) obj;
			if (key == null) {
				if (other.key != null)
					return false;
			} else if (!key.equals(other.key))
				return false;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}
		@Override
		public String toString() {
			return "Point [key=" + key + ", x=" + x + ", y=" + y + "]";
		}
		
		
	}
	
	public static class Line{
		
		public Line(Object key){
			 this.key = key;
		} 
		
		
		
		public Line(Object key,int start_x, int start_y, int end_x, int end_y) {
			 this(key,new Point(key,start_x,start_y), new Point(key, end_x,end_y));
		}
		public Line(Object key,Point start, Point end) {
			this(key);
			this.start = start;
			this.end = end;
			this.start.key =  key;
			this.end.key = key;
			
		}
		
		public Line(int start_x, int start_y, int end_x, int end_y) {		 
			 this(UUID.randomUUID(),new Point(null,start_x,start_y), new Point(null, end_x,end_y));
		}
        public Object key;
        
		public Point start;
		
		public Point end;
		
		

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((end == null) ? 0 : end.hashCode());
			result = prime * result + ((start == null) ? 0 : start.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Line other = (Line) obj;
			if (end == null) {
				if (other.end != null)
					return false;
			} else if (!end.equals(other.end))
				return false;
			if (start == null) {
				if (other.start != null)
					return false;
			} else if (!start.equals(other.start))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "Line [start=" + start + ", end=" + end + "]";
		}
		
		
	}
	 /**
	  * 
	  * 代表一个矩形
	  * @author songshangkun
	  *
	  */
	 public static class Rect{
		 
		 public Point left_up_1 = new Point(null);
		 
		 public Point left_down_4 = new Point(null);
		 
		 public Point right_up_2 = new Point(null);
		 
		 public Point right_down_3 = new Point(null);
		 
		 public List<Line> getAllLine(){
			  List<Line> ls = new ArrayList<>();
			  ls.add(new Line("line1",left_up_1,right_up_2));
			  ls.add(new Line("line2",right_up_2,right_down_3));
			  ls.add(new Line("line3",right_down_3,left_down_4));
			  ls.add(new Line("line4",left_down_4,left_up_1));
			  return ls;
		 }
	 }
}
