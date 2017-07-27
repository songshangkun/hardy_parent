package org.hardy.alegole.geometry;

import org.apache.commons.lang3.ArrayUtils;
import org.hardy.alegole.geometry.Point.Pe;
import org.hardy.statics.exception.NotSupportException;

/**
 * 代表一条直线,线由无数个点组成,这里指记录端点.
 * 一条线两个端点
 * @author song
 *
 */
public class Line implements Form{
   /**
    * 代表定义的起点
    */
	 private Point start;
	 /**
	  * 代表定义的终点
	  */
	 private Point end;
	 /**
	  * 直线距离
	  */
	 private Double distance;
	 /**
	  * 线条的tan
	  */
	 private Double slope_tan;
	 /**
	  * 线条的cot
	  */
	 private Double slope_cot;
	 /**
	  * 线条的sin
	  */
	 private Double slope_sin;
	 /**
	  * 线条的cos
	  */
	 private Double slope_cos;
	 /**
	  * 是否在改变任一个点时自动计算其他属性
	  */
	 private boolean calAuto = false;
	 /**
	  * 是否为垂直X轴
	  */
	 private Boolean isVertical = false;
	 /**
	  * 是否为垂直Y轴
	  */
	 private Boolean isHorizon = false;
	 
	 /**
	  * 代表斜率精度在小数点后?位,如果精确度设为null则是最精确情况
	  */
	 private Integer precision = 6;
	 /**
	  * 创建一条直线
	  */
	 public Line(){} 
	 /**
	  *  创建一条直线 
	  * @param calAuto  每次设置自动计算其他属性,且线上所有的设置点不可更改
	  */
	 public Line(boolean calAuto){
		 this.calAuto = calAuto;
	 } 
	 /**
	  *  创建一条直线
	  * @param calAuto 每次设置自动计算其他属性,且线上所有的设置点不可更改
	  * @param start
	  * @param end
	  */
	 public Line(boolean calAuto,Point start,Point end){
		  this(calAuto);
		  start.setLock(calAuto);
		  end.setLock(calAuto);
		  this.start = start;
		  this.end = end;
		  initProp();
	 } 
	 /**
	  *  创建一条直线
	  * @param start
	  * @param end
	  */
	 public Line(Point start,Point end){
		  this(false, start, end);
	 }
	 /**
	  * 创建一条直线 
	  * @param calAuto 每次设置自动计算其他属性,且线上所有的设置点不可更改
	  * @param x1
	  * @param y1
	  * @param x2
	  * @param y2
	  */
	 public Line(boolean calAuto,double x1,double y1,double x2,double y2){
		 this(calAuto);
		  this.start = new Point(calAuto,x1,y1);
		  this.end = new Point(calAuto,x2,y2);
		  initProp();
	 } 
	 /**
	  * 创建一条直线 
	  * @param x1
	  * @param y1
	  * @param x2
	  * @param y2
	  */
	 public Line(double x1,double y1,double x2,double y2){
		  this(false, x1, y1, x2, y2);
	 }
	 
	 
	  /**
	   * 设置精度值
	   * @param precision
	   */
	 public void setPrecision(Integer precision) {
		this.precision = precision;
	}
	/**
	  * 从新计算其他属性
	  */
	 public void initProp(){
		 this.distance = GeometryUtil.distance(start, end);
		 this.slope_tan = GeometryUtil.tan_slop(this);
		 this.slope_cot = 1/slope_tan;
		 this.slope_sin = (this.end.getY()-this.start.getY())/this.distance;
		 this.slope_cos = (this.end.getX()-this.start.getX())/this.distance;
		 if(Double.isInfinite(this.slope_tan)&&this.slope_cot==0d) this.isVertical = true;
		 if(Double.isInfinite(this.slope_cot)&&this.slope_tan==0d) this.isHorizon = true;
	 }
	 /**
	  * 获取长度
	  * @return
	  */
	public Double getDistance() {
		return distance;
	}
	/**
	 * 获取tan
	 * @return
	 */
	public Double getSlope_tan() {
		return slope_tan;
	}
	/**
	 * 获取cot
	 * @return
	 */
	public Double getSlope_cot() {
		return slope_cot;
	}
	/**
	 * 获取sin
	 * @return
	 */
	public Double getSlope_sin() {
		return slope_sin;
	}
	/**
	 * 获取cos
	 * @return
	 */
	public Double getSlope_cos() {
		return slope_cos;
	}
	 
	public Point getStart() {
		return start;
	}
    /**
     * 是否为x轴的垂直线
     * @return
     */
	public Boolean getIsVertical() {
		return isVertical;
	}
	/**
     * 是否为x轴的水平线
     * @return
     */
	public Boolean getIsHorizon() {
		return isHorizon;
	}
	public void setStart(Point start) {
		this.start = start;
		if(this.calAuto){
			  this.start.setLock(true);
		  }
		if(this.end!=null&&this.calAuto) initProp();
	}
	
	 /**
	  * 变幻位置 平移
	  * @param x x轴偏移量
	  * @param y y轴偏移量
	  */
	   @Override
	   public void transform(double x,double y){
		       this.start=new Point(this.start.getX()+x,this.start.getY()+y);
		       this.end=new Point(this.end.getX()+x,this.end.getY()+y);
	   }
	   /**
	    * 以线段开始点延长或缩短
	    * @param f 线段长度倍数
	    */
	   public void prolonger(double f){
		   double newdistance = this.distance*f;
	       this.end=new Point(this.start.getX()+this.slope_cos*newdistance,this.start.getY()+this.slope_sin*newdistance);
	   }
	   /**
	    * 获取分段标志点,比如partition=2,将线段氛围两节，会多出一个中心标志点,
	    * 如果partition=3,则会多处两个1/3点位,结果从起点到终点排列
	    * @param partition
	    * @return
	    */
	   public Point[] getPartitionMarkedPoint(int partition){
		   if(partition<1) throw new RuntimeException("partition must > 0");
		   if(partition==1) return new Point[]{this.start,this.end};
		   Point[] points = new Point[partition+1];
		   double add_x = (this.end.getX() - this.start.getX())/(double)partition;
		   double add_y = (this.end.getY() - this.start.getY())/(double)partition;
		   for(int i=0;i<points.length;i++){
			    points[i] = new Point(this.start.getX()+(add_x*i),this.start.getY()+(add_y*i));
		   }
		     return  points;
	   }
	   /**
	    * 获取线段的中点
	    * @return
	    */
	   public  Point getMiddlePoint(){
		   Point[] points = getPartitionMarkedPoint(2);
		   return points[1];
	   }
	   /**
	    * 以线段中心点延长或缩短,
	    * 在prolonger的基础上把变幻后的中心点平移到原来的中心点
	    * @param f
	    */
	   public void prolonger_centre(double f){
		   Point middle = getMiddlePoint(); //先求中心点(变幻之前求,否则变幻后中心点位置也改变了)
		   prolonger(f); //从原点放大
		   Point middle2 = getMiddlePoint();
		   //变幻后中心点平移到原来中心点就是原来中心点坐标减去变幻后中点坐标
		   transform(middle.getX()-middle2.getX(), middle.getY()-middle2.getY());
	   }
	   /**
	    * 根据线段起点 旋转一个角度
	    * @param degree  旋转角度 30代表增加30度
	    */
	   public void rotateStart(double degree){
		     //1-sin是sin斜率的值,这里使用 Math.asin求出度数
		     double deg1 = GeometryUtil.getDegree(this.slope_sin, this.slope_cos);
		     //4-更新角度根据distance计算新的端点位置
		     double newSlope_sin = Math.sin((deg1+degree)/180*Math.PI);
		     double newSlope_cos = Math.cos((deg1+degree)/180*Math.PI);
		     Point endPoint = new Point(distance*newSlope_cos,distance*newSlope_sin);
		     //因为开始点被当作0,0原点计算得到新的结束点,所以要处理结束点的平移
	         //注意这里的开始点还是记录成原来的开始点,所以开始点不变,所以此处使用点的平移
		     endPoint.transform(this.start.getX(), this.start.getY());
		     this.setEnd(endPoint);
	   }
	   
	   /**
	    * 根据线段起点 旋转一个角度
	    * @param degree  旋转角度 0.5 代表0.5/Math.PI*180的度数,即degree时以PI表示的弧度
	    */
	   public void rotateStartPI(double degree){
		     //4-更新角度根据distance计算新的端点位置
		    double newDegree = GeometryUtil.getDegreePI(this.slope_sin, this.slope_cos)+degree;
		     double newSlope_sin = Math.sin(newDegree);
		     double newSlope_cos = Math.cos(newDegree);
		     Point endPoint = new Point(distance*newSlope_cos,distance*newSlope_sin);
	         this.setEnd(endPoint);
		     //因为开始点被当作0,0原点处理所以要在平移,开始点减去现在的原点
		     transform(this.start.getX()-0, this.start.getY()-0);
	   }
	   /**
	    * 根据指定点 旋转一个角度
	    * @param x
	    * @param y
	    * @param degree 旋转角度 30代表增加30度
	    */
	   public void  rotate(double x,double y,double degree){
		   rotate(new Point(x, y), degree);
	   }
	   
	   /**
	    * 根据指定点 旋转一个角度
	    * @param point 指定点
	    * @param degree 旋转角度 30代表增加30度
	    */
	   @Override
	   public void  rotate(Point point,double degree){
		      //1-把线段理解成两个由指定点连接到开始点和结束点的两条线段
		      //分别根据rotateStart求值
		     Line startLine = new Line(point,this.start);
		     Line endLine = new Line(point,this.end);
		     startLine.rotateStart(degree);
		     Point newStart = startLine.getEnd();
		     endLine.rotateStart(degree);
		     Point newEnd = endLine.getEnd();
		     this.setStart(newStart);
		     this.setEnd(newEnd);
		     this.initProp();
	   }
	    /**
	     * 顺时针旋转90度
	     * @param point
	     */
	   @Override
	    public void  rotate90(Point point){
	    	rotate(point, -90);
	    }
	    /**
	     * 顺时针旋转180度
	     * @param point
	     */
	    @Override
	    public void  rotate180(Point point){
	    	rotate(point, -180);
	    }
	    /**
	     * 顺时针旋转270度
	     * @param point
	     */
	    @Override
		public void  rotate270(Point point){
			rotate(point, 90);  
		}
		
		
	   /**
	    * 围绕中心点旋转
	    * @param degree
	    */
	   public void rotate_centre(double degree){
		   rotate(this.getMiddlePoint(), degree);
	   }
	   /**
	    * 围绕原点旋转 0,0
	    * @param degree
	    */
	   public void rotate_zero(double degree){
		   rotate(new Point(0,0), degree);
	   }
	   
	   /**
	    * 判断一个点是否在这条线段上
	    * @return
	    */
	   public boolean surLine(Point point){
		   Point[] minMax_x = GeometryUtil.sortX(this);
		   Point[] minMax_y = GeometryUtil.sortY(this);
		   if(point.getX()>=minMax_x[0].getX()&&
				   point.getX()<=minMax_x[1].getX()&&
				   point.getY()>=minMax_y[0].getY()&&
				   point.getY()<=minMax_y[1].getY()){
			     if(this.start.equals(point)) return true;
			     Line line = new Line(this.start,point);
			     return precision_equals(line.slope_sin, this.slope_sin);
		   }
		   return false;
	   }
	   
    public boolean precision_equals(Double dou1,Double dou2){
    	if(this.precision==null) return dou1.equals(dou2);
    	else {
    		double p = Math.pow(10, precision);
    		double d1 = Math.round(dou1*p)/p;
    		double d2 = Math.round(dou2*p)/p;
    		return d1 == d2;
    	}
    }
	   
	public Point getEnd() {
		return end;
	}

	public void setEnd(Point end) {
		this.end = end;
		if(this.calAuto){
			  this.end.setLock(true);
		  }
		if(this.start!=null&&this.calAuto) initProp();
	}
 
	public boolean isCalAuto() {
		return calAuto;
	}
	/**
	 * 设置是否改变点时自动计算其他属性
	 * @param calAuto
	 */
	public void setCalAuto(boolean calAuto) {
		this.calAuto = calAuto;
	}

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
	 
	@Override
	public Point[] getVertexs() {
		return new Point[]{this.start,this.end};
	}
	
	@Override
	public boolean hasVertexs(Point point) {
		return (this.start.equals(point)||this.end.equals(point));
	}
	@Override
	public void flip(Line line) {
		 this.start.flip(line);
		 this.end.flip(line);
		 initProp();
	}
	
	public LinePe pix(){
		return new LinePe(this.start.pix(), this.end.pix());
	}
	
	public static class LinePe{
		
		 public LinePe(Pe start, Pe end) {
			super();
			this.start = start;
			this.end = end;
		}
		 public Pe start;
		 public Pe end;
		@Override
		public String toString() {
			return "LinePe [start=" + start + ", end=" + end + "]";
		}
		 
	}
	/**
	 * 根据一个点获取另外一个点
	 * @param p
	 * @return
	 */
	public Point getOtherPoint(Point p){
		 if(this.getStart().equals(p)) return this.end;
		 else if(this.getEnd().equals(p)) return this.start;
		 else throw new NotSupportException("点不是直线端点");
	}
	 
	
	 public static void main(String[] args) {
		Line l1 = new Line(0,0,45,45);
		Line lw = new Line(0,10,600,10);
		l1.flip(lw);
		System.out.println(l1.pix());
	}
}
