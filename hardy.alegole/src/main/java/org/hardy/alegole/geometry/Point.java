package org.hardy.alegole.geometry;

/**
 * 形容一个像素点
 * @author song
 *
 */
public class Point implements Form{
       /**
        * 代表x轴的坐标点
        */
	   private double x;
	   /**
	    * 代表y轴坐标点
	    */
	   private double y;
	   /**
	    * 一旦设置lock=true,则无法改变点的x,y坐标
	    */
	   private boolean lock = false;
	   /**
	    * 是否已设置x
	    */
	   private boolean lock_x = false;
	   /**
	    * 是否已设置y
	    */
	   private boolean lock_y = false;
	   /**
	    * 创建一个点
	    */
	   public Point(){}
	   /**
	    * 创建一个点
	    * @param lock  为true时无法改变点坐标
	    */
	   public Point(boolean lock){
		    this.lock = lock;
	   }
	   /**
	    * 创建一个点
	    * @param lock 为true时无法改变点坐标
	    * @param x
	    * @param y
	    */
	   public Point(boolean lock,double x,double y){
		   this(lock);
		   if(this.lock){
			   this.lock_x = true;
			   this.lock_y = true;
		   }
		   this.x = x;
		   this.y = y; 
	   }
	   /**
	    * 创建一个点
	    * @param x
	    * @param y
	    */
	   public Point(double x,double y){
		   this(false, x, y);
	   }
	  /**
	   * 变幻位置 平移
	   * @param x x轴偏移量
	   * @param y y轴偏移量
	   */
	   @Override
	   public void transform(double x,double y){
		     setX(this.x+x);
		     setY(this.y+y);
	   }
	   /**
	    * 放大或缩小
	    * @param fx  x点倍数
	    * @param fy  y点倍数
	    */
	   public void amplification(double fx,double fy){
		     setX(this.x*fx);
		     setY(this.y*fy);
	   }
	   /**
	    * 放大或缩小
	    * @param f  放大缩小的倍数
	    */
	   public void amplification(double f){
		     setX(this.x*f);
		     setY(this.y*f);
	   }
	   
	   @Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}
	public double getX() {
			return x;
		}
		public void setX(double x) {
			if(!lock||!lock_x){
				if(lock) lock_x = true;
				this.x = x;
			}else{
				throw new RuntimeException(this.toString()+" is locked");
			}
			
		}
		public double getY() {
			return y;
		}
		public void setY(double y) {
			if(!lock||!lock_y){
				if(lock) lock_y = true;
				this.y = y;
			}else{
				throw new RuntimeException(this.toString()+" is locked");
			}
			
		}

		public boolean isLock() {
			return lock;
		}

		public void setLock(boolean lock) {
			this.lock = lock;
		} 
		
		
		@Override
		public Point[] getVertexs() {
			return new Point[]{this};
		}
		@Override
		public boolean hasVertexs(Point point) {
			return this.equals(point);
		}
		@Override
		public void rotate90(Point point) {
			 Line line = new Line(point,this);
			 line.rotate(point, -90);
			 this.setX(line.getEnd().x);
			 this.setY(line.getEnd().y);
		}
		@Override
		public void rotate180(Point point) {
			Line line = new Line(point,this);
			 line.rotate(point, -180);
			 this.setX(line.getEnd().x);
			 this.setY(line.getEnd().y);
		}
		@Override
		public void rotate270(Point point) {
			Line line = new Line(point,this);
			line.rotate(point, 90);
			this.setX(line.getEnd().x);
			this.setY(line.getEnd().y);
		}
		@Override
		public void flip(Line line) {
			 Point p = GeometryUtil.getPointVerticalLine(this, line);
			 System.out.println("垂点=="+p);
			 this.setX(p.getX()*2-this.getX());
			 this.setY(p.getY()*2-this.getY());
		}
		
		@Override
		public void rotate(Point point, double degree) {
			Line line = new Line(point,this);
			line.rotate(point, degree);
			this.setX(line.getEnd().x);
			this.setY(line.getEnd().y);
		}
		
		public Pe pix(){
			return new Pe((int)Math.round(this.x),(int)Math.round(this.y));
		}
		
		public static class Pe{
			
			 public Pe(int x, int y) {
				super();
				this.x = x;
				this.y = y;
			}
			 public int x;
			 public int y;
			@Override
			public String toString() {
				return "Pe [x=" + x + ", y=" + y + "]"; 
			}
			 
		}
	 
		public static void main(String[] args) {
			  Point p = new Point(0,45);
			  Line l = new Line(0,0,45,-45); 
			  p.flip(l);
			  System.out.println(p);
			  System.out.println(p.pix());
		}
}
