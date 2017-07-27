package org.hardy.alegole.geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


public class GeometryUtil {
	
	 private GeometryUtil(){}
	 /**
	  * 计算2维两个点距离
	  * @param line
	  * @return
	  */
	 public static double distance(Point start,Point end){
		  return Math.sqrt(Math.pow(end.getX()-start.getX(),2)+Math.pow(end.getY()-start.getY(), 2));
	  }
   /**
    * 计算线条的tan斜率
    * @param line
    * @return
    */
	  public static double tan_slop(Line line){
		  return (double)(line.getEnd().getY()-line.getStart().getY())/(double)(line.getEnd().getX()-line.getStart().getX());
	  }
	  /**
	    * 计算线条的cot斜率
	    * @param line
	    * @return
	    */
	  public static double cot_slop(Line line){
		  return 1/tan_slop(line);
	  }
	  /**
	    * 计算线条的sin正弦
	    * @param line
	    * @return
	    */
	  public static double sin_slop(Line line){
		  return (line.getEnd().getY()-line.getStart().getY())/distance(line.getStart(), line.getEnd());
	  }
	  /**
	   * 计算线条的cos余弦
	   * @param line
	   * @return
	   */
	  public static double cos_slop(Line line){
		  return (line.getEnd().getX()-line.getStart().getX())/distance(line.getStart(), line.getEnd());
	  }
	  /**
	   * 同一直线,根据sin求cos的绝对值, 
	   * 或者根据cos求sin绝对值,
	   * sin平方+cos平方=1
	   * @param sin
	   * @return
	   */
	   public static double transABSSinCos(double sinOrCos){
		    return Math.sqrt(1-Math.pow(sinOrCos, 2));
	   }
	   /**
	    * 根据正弦和余弦计算出度数  返回30代表30度 
	    * @param sin   正弦   a/c
	    * @param cos  余弦   b/c
	    * @return
	    */
	   public static double getDegree(double sin,double cos){
		         double degree = Math.asin(sin)/Math.PI*180; //可能在1,4象限, 这里在1象限
		         if(cos<0) degree = 180-degree;
		         return degree;
	   }
	   /**
	    * 根据正弦和余弦计算出度数  返回 0.3 相当于 0.3/Math.PI*180的度数
	    * @param sin 正弦   a/c
	    * @param cos b/c
	    * @return
	    */
	   public static double getDegreePI(double sin,double cos){
	         double degree = Math.asin(sin); //可能在1,4象限, 这里在1象限
	         if(cos<0) degree = Math.PI-degree;
	         return degree;
	   }
	   /**
	    * 将度数转化为Math.PI的数值显示   30度-->Math.PI*30/180
	    * @param degree
	    * @return
	    */
	   public static double transDegreeToPI(double degree){
		    return  degree/180*Math.PI;
	   }
	   
	   /**
	    * 将Math.PI的数值转化为显示度数 Math.PI*30/180-->30度
	    * @param degree
	    * @return
	    */
	   public static double transPIToDegree(double degree){
		     return  degree/Math.PI*180;
	   }
	   
	   /**
		 * 对一组点point在x轴进行排序,x轴坐标由小到大
		 * @param lines
		 * @return
		 */
		public static Point[] sortX(Point... points){
			 Arrays.sort(points,new Comparator<Point>() {
				@Override
				public int compare(Point o1, Point o2) {
					if(o1.getX()-o2.getX()>0) return 0;
					else if(o1.getX()-o2.getX()==0) return -1;
					else return -1;
				}
			});
			return points; 
		}
		/**
		 * 计算一堆图形的端点,按x轴排序
		 * @param form
		 * @return
		 */
		public static Point[] sortX(Form... forms){
			List<Point> lPoints = new ArrayList<>(100);
			for(Form f:forms){
				 for(Point p:f.getVertexs()){
					 lPoints.add(p);
				 }
			}
			Point[] points = new Point[lPoints.size()];
			 lPoints.toArray(points);
			 return sortX(points);
		}
		/**
		 * 对一组点point在y轴进行排序,y轴坐标由小到大
		 * @param lines
		 * @return
		 */
		public static Point[] sortY(Point... points){
			Arrays.sort(points,new Comparator<Point>() {
				@Override
				public int compare(Point o1, Point o2) {
					if(o1.getY()-o2.getY()>0) return 0;
					else if(o1.getY()-o2.getY()==0) return -1;
					else return -1;
				}
			});
			return points; 
		}
		/**
		 * 计算一堆图形的端点,按y轴排序
		 * @param form
		 * @return
		 */
		public static Point[] sortY(Form... forms){
			List<Point> lPoints = new ArrayList<>(100);
			for(Form f:forms){
				 for(Point p:f.getVertexs()){
					 lPoints.add(p);
				 }
			}
			Point[] points = new Point[lPoints.size()];
			 lPoints.toArray(points);
			 return sortY(points);
		}
		
		/**
		 * 求两条直线的交点,没有交点返回null,有一个焦点返回Point[1],如果有一部分线段重合
		 * 则返回重合的起始点和中点point[2]
		 * @param line1
		 * @param line2
		 * @return
		 */
		public static Point[] intersection(Line line1,Line line2){
			if(line1.equals(line2)) return line1.getVertexs();
			if(isSeparation(line1, line2)) return null;
			//1-分析斜率tan是否相同 如果想可能平行 或者 在一个延长线上
			//1-1 两条线都是水平的
			if(line1.getIsHorizon()&&line2.getIsHorizon()){
				 if(line1.getStart().getY()!=line2.getStart().getY()) return null;
				 else if(isInterlocks(line1, line2)){
					  Point[] points = sortX(line1,line2);
					  if(points[1].equals(points[2])) return new Point[]{points[1]};
					  else return new Point[]{points[1],points[2]};
				 }
				 return null;
			}
			//1-1 两条线都是垂直的
			else if(line1.getIsVertical()&&line2.getIsVertical()){
				 if(line1.getStart().getX()!=line2.getStart().getX()) return null;
				 else if(isInterlocks(line1, line2)){
					  Point[] points = sortY(line1,line2);
					  if(points[1].equals(points[2])) return new Point[]{points[1]};
					  else return new Point[]{points[1],points[2]};
				 }
				 return null;
			}
			//1-3 两条线都不水平或垂直,但斜率一样
			else if(line1.getSlope_tan().equals(line2.getSlope_tan())){
				 //因为斜率一样,所以只要一个线段的端点在另一个线段延长线上就说明他们不是平行,否则平行无交点
				 Line nLine = new Line(line1.getStart(),line2.getStart());
				 if(!nLine.getSlope_tan().equals(line1.getSlope_tan())&&!line1.getStart().equals(line2.getStart())) return null;
				 else if(isInterlocks(line1, line2)){
					  Point[] points = sortY(line1,line2);
					  if(points[1].equals(points[2])) return new Point[]{points[1]};
					  else return new Point[]{points[1],points[2]};
				 }
				 return null;
			}
			//2-斜率不同的情况
			//2-1  一条垂直,另一条水平
			else if((line1.getIsHorizon()&&line2.getIsVertical())||(line2.getIsHorizon()&&line1.getIsVertical())){
				if(isInterlocks(line1, line2)){
					  Point[] points_x = sortX(line1,line2);
					  Point[] points_y = sortY(line1,line2);
					  return new Point[]{ new Point(points_x[1].getX(),points_y[1].getY())};
				 } 
				return null;
			}
			//2-2  一条水平,另一条正常(不水平,不垂直)
			else if(line1.getIsHorizon()||line2.getIsHorizon()){
				double foucs_y = 0d;
				double foucs_x = 0d;
				Point point = null;
				if(line1.getIsHorizon()){
					 foucs_y = line1.getStart().getY();
					 foucs_x = (line1.getStart().getY()-line2.getStart().getY())*(1/line2.getSlope_tan())+line2.getStart().getX(); 
				}else if(line2.getIsHorizon()){
					 foucs_y = line2.getStart().getY();
					 foucs_x = (line2.getStart().getY()-line1.getStart().getY())*(1/line1.getSlope_tan())+line1.getStart().getX(); 
				}
				 point = new Point(foucs_x,foucs_y);
				if(line1.surLine(point)&&line2.surLine(point)) 
					return  new Point[]{new Point(foucs_x,foucs_y)};
				else return null;

			}
			//2-3  一条垂直,另一条正常(不水平,不垂直)
			else if(line1.getIsVertical()||line2.getIsVertical()){
				double foucs_y = 0d;
				double foucs_x = 0d;
				Point point = null;
				 if(line1.getIsVertical()){
					    foucs_x = line1.getStart().getX();
						foucs_y = line2.getSlope_tan()*(line1.getStart().getX()-line2.getStart().getX())+line2.getStart().getY();
				 }else if(line2.getIsVertical()){
					   foucs_x = line2.getStart().getX();
					   foucs_y = line1.getSlope_tan()*(line2.getStart().getX()-line1.getStart().getX())+line1.getStart().getY();
				 }
				 if(line1.surLine(point)&&line2.surLine(point)) 
						return  new Point[]{new Point(foucs_x,foucs_y)};
					else return null;
			}
			//2-4 两条都正常
			else {
				double foucs_y = ((-(line1.getSlope_tan()/line2.getSlope_tan()))*
						line2.getStart().getY()+line1.getSlope_tan()*
						(line2.getStart().getX()-line1.getStart().getX())+line1.getStart().getY())/
						(1-line1.getSlope_tan()/line2.getSlope_tan());
				double foucs_x = (foucs_y-line1.getStart().getY())/line1.getSlope_tan()+line1.getStart().getX();
				Point point = new Point(foucs_x,foucs_y);
				System.out.println(line1.surLine(point));
				System.out.println(line2.surLine(point));
				if(line1.surLine(point)&&line2.surLine(point)) 
					return  new Point[]{point};
				else return null;
			}
		}
		//Encompasses interlocks separation
		/**
		 * 两条直线是否为包裹关系,
		 * 即一条直线的两个端点都包含在另一直线两个端点的范围内。
		 * 如果是返回true.
		 * 不能计算的十分精确,在有些情况中会错误
		 * @param line1
		 * @param line2
		 * @return
		 */
		@Deprecated
		public static boolean isEncompasses(Line line1,Line line2){
			  Point[] px = sortX(line1,line2) ;
			  Point[] py = sortY(line1,line2) ;
			  if (((line1.hasVertexs(px[1])&&line1.hasVertexs(px[2]))||(line2.hasVertexs(px[1])&&line1.hasVertexs(px[2])))&&
					  ((line1.hasVertexs(py[1])&&line1.hasVertexs(py[2]))||(line2.hasVertexs(py[1])&&line2.hasVertexs(py[2])))){
				    return true;
			  }
			  return false;
		}
		/**
		 * 两条直线是否交错,交错指的是一条直线的某个端点在
		 * 另一条直线两个端点的范围内。
		 * 但并不表明他们相交,它包含encompasses的情况
		 * @param line1
		 * @param line2
		 * @return
		 */
		public static boolean isInterlocks(Line line1,Line line2){
			  Point[] px = sortX(line1,line2) ;
			  Point[] py = sortY(line1,line2) ;
			  return ((line1.hasVertexs(px[0])&&line2.hasVertexs(px[1]))||(line2.hasVertexs(px[0])&&line1.hasVertexs(px[1])))&&
					  ((line1.hasVertexs(py[0])&&line2.hasVertexs(py[1]))||(line2.hasVertexs(py[0])&&line1.hasVertexs(py[1])));
		}
		
		 
		/**
		 * 表明两条直线是否为不相交的分离线段,注意这只是个充分条件。
		 *  如果分离是true,他们一定不相交,但如果是false他们也不一定相交。
		 *  分离指一条直线的两个端点完全不在另一个直线两个端点的范围内。
		 * @param line1
		 * @param line2
		 * @return
		 */
		public static boolean isSeparation(Line line1,Line line2){
			Point[] px = sortX(line1,line2);
			Point[] py = sortY(line1,line2);
			if(line1.getStart().equals(line2.getStart())||line1.getStart().equals(line2.getEnd())) return false;
			if(line2.getStart().equals(line1.getStart())||line2.getStart().equals(line1.getEnd())) return false;
			return ((line1.hasVertexs(px[0])&&line1.hasVertexs(px[1]))||
					    (line2.hasVertexs(px[0])&&line2.hasVertexs(px[1]))||
					    (line1.hasVertexs(py[0])&&line1.hasVertexs(py[1]))||
					    (line2.hasVertexs(py[0])&&line2.hasVertexs(py[1])));
		}
 
		
		/**
		 * 获取3个点连线的角度 ,结果是度数 30代表30度
		 * @param p1 顶点(要求角度的点)
		 * @param p2 定点相邻两边其中一边上的点
		 * @param p3 定点相邻两边其中一边上的点
		 * @return
		 */
		public static double getDegreeIntersection(Point p1,Point p2,Point p3){
			double dis1 = getDistanceVerticalLine(p2, new Line(p1,p3));
			double dis2 = new Line(p1,p2).getDistance();
			 double degree = Math.asin(dis1/dis2)/Math.PI*180;
	           if(!plus90(p1, p2, p3)){
	        	   return degree;
	           }else{
	        	   return 180-degree;
	           }
		}
		
		/**
		 * 获取两条交线的角度 
		 * @param line1
		 * @param line2
		 * @return
		 */
		public static Double getDegreeIntersection(Line line1,Line line2){
			Point[] pos = intersection(line1, line2);
			if(pos!=null&&pos.length==1){
				Point p1 = null;
				Point p2 = null;
				if(!line1.getStart().equals(pos[0]))p1 = line1.getStart();
				else p1 = line1.getEnd();
				if(!line2.getStart().equals(pos[0]))p2 = line2.getStart();
				else p2 = line2.getEnd();
				return getDegreeIntersection(pos[0], p1, p2);
			}
			return null;
		}
		
		
		
		/**
		 * 一个点链接直线一个端点,在连接直线上另一个点。使图形成为等腰三角形，定点是直线的连接端点。
		 * 然后可求出两个要的夹角，两个底的距离。这里求基础条件 通过计算得到的一个底坐标和底的距离,等腰边的距离
		 * @param point
		 * @param line
		 * @return   double[0] 底坐标x ,double[1] 底坐标y ,double[2] 底长度 , double[3] 等腰边长度 
		 * 
		 */
		public static double[] getEgalLengthCordonne(Point point,Line line){
			double[] result = new double[4];
			//1-点根据反转直线上的任意一点连线新的直线
			  Line nline = new Line(line.getStart(),point);
			  //2-求新的直线和翻转直线的夹角
			  //2-1 得到交点 交点就是刚才连接的点
			  Point fouces1 = line.getStart();
			  //2-2 从交点沿着翻转直线延伸到新线段的长度 可得到公式
			  double y = nline.getDistance()*line.getSlope_sin()+fouces1.getY();
			  double x = nline.getDistance()*line.getSlope_cos()+fouces1.getX(); 
			  result[0] = x;
			  result[1] = y;
			  result[2] = Math.sqrt(Math.pow(point.getX()-x, 2)+Math.pow(point.getY()-y, 2));
			  result[3] = nline.getDistance();
			  return result;
		}
		/**
		 * 根据sin,cos判断象限,注意判断象限实际是判断角度的问题
		 * @return
		 */
		public static int areaBySlop(double sin,double cos){
			 if(sin>=0&&cos>=0)return 1;
			 if(sin>=0&&cos<=0)return 2;
			 if(sin<=0&&cos>=0)return 4;
			 if(sin<=0&&cos<=0)return 3;
			 return 0;
		}
		/**
		 * p1顶点和两边(p1,p2),(p1,p3)的夹角是否大于90度
		 * @param p1 顶点
		 * @param p2
		 * @param p3
		 * @return
		 */
		public static boolean plus90(Point p1,Point p2,Point p3){
			  Line l1 = new Line(p1,p2);
			  Line l2 = new Line(p1,p3);
			  Line l3 = new Line(p2,p3);
			  double d = Math.pow(l1.getDistance(), 2)+Math.pow(l2.getDistance(), 2);
			  if(d<Math.pow(l3.getDistance(), 2)) return true;
			  else return false;
		}
		
		
		/**
		 * 获取点与直线的垂直距离
		 * @param line
		 * @return
		 */
		public static double getDistanceVerticalLine(Point point,Line line){
			  double[] ds = getEgalLengthCordonne(point, line);
			  //初步判断夹角是否大于90度
	           double verticalLine = 0d;
	           if(plus90(line.getStart(), point, line.getEnd())){
	        	 //3 可以知道 新的距离的1/2<dist2/2> 除以distance边长的cos值 可求出角度。这个角度的sin = 垂直距离/dist2的sin值
	 			   verticalLine = Math.sqrt(1-Math.pow((ds[2]/2/ds[3]), 2))*ds[2];
	           }else{
	        	  double a = Math.acos(ds[2]/2/ds[3]);
	        	  verticalLine = Math.cos(Math.PI/2-2*a)*ds[3];
	           }
			  return verticalLine;
		}
		
		/**
		 * 获取点与直线的垂点
		 * @param point
		 * @param line
		 * @return
		 */
		public static Point getPointVerticalLine(Point point,Line line){
			 //只需要计算垂线除以底边的距离得到sin值
			double[] ds = getEgalLengthCordonne(point, line);
			  //初步判断夹角是否大于90度
	           double verticalLine = 0d;
	           if(!plus90(line.getStart(), point, line.getEnd())){
	        	 //3 可以知道 新的距离的1/2<dist2/2> 除以distance边长的cos值 可求出角度。这个角度的sin = 垂直距离/dist2的sin值
	 			   verticalLine = Math.sqrt(1-Math.pow((ds[2]/2/ds[3]), 2))*ds[2];
	           }else{
	        	  double a = Math.acos(ds[2]/2/ds[3]);
	        	  verticalLine = Math.cos(Math.PI/2-2*a)*ds[3];
	           }
			   //计算线段的延伸距离
			   double dre_dis = Math.sqrt(Math.pow(ds[2], 2)-Math.pow(verticalLine, 2));
			   return new Point(ds[0]-dre_dis*line.getSlope_cos(),ds[1]-dre_dis*line.getSlope_sin());
		}
		
	 
	   public static void main(String[] args) {
		  
		   Point p = new Point(45,0);
		   Line line1 = new Line(256,113,12,918);
		  
		   double d = getDistanceVerticalLine(p, line1);
		   Point pp = getPointVerticalLine(p, line1);
		   System.out.println("垂涎距离==="+d);
		   System.out.println("垂dian==="+pp);
//		   
//		   System.out.println(22.5*22.5*2*2);
//		   System.out.println(45*45);
//		   System.out.println(plus90(line1.getStart(),line1.getEnd(),line2.getEnd()));
//		   System.out.println(getDegreeIntersection(line1, line2));
//		   Point[] points = intersection(line1, line2);
//           System.out.println(Arrays.toString(points));

	}
	   
	 
}


