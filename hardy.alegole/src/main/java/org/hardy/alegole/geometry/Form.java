package org.hardy.alegole.geometry;

/**
 * 表示图形的接口,这是关于图形图像学的几何抽象。
 * 结合实际,因为图像在计算机上最小单位是一个像素点。
 * 所以这里不是 理想的数学模型,而是近似的实际模型
 * @author song
 *
 */
public interface Form {
    /**
     * 获取图形端点
     * @return
     */
	   Point[] getVertexs();
	   /**
	    * 点Point是否是所在图形的一个端点
	    * @param point
	    * @return
	    */
	    boolean hasVertexs(Point point);
	   /**
	    * 顺时针旋转90度
	    * @param point
	    */
	    public void  rotate90(Point point);
	    /**
	     * 顺时针旋转180度
	     * @param point
	     */
	    public void  rotate180(Point point);
	    /**
	     * 顺时针旋转270度
	     * @param point
	     */
		public void  rotate270(Point point);
		/**
		 * 根据坐标线翻转
		 * @param line  此处的line是可延长的线,不是线段.虽然给出的线一定有端点,
		 * <br>但他只代表显得位置和斜率,端点可无限延伸
		 */
		public void flip(Line line); 
		/**
		 * 根据指定点 旋转一个角度
		 * @param point 指定点
	     * @param degree 旋转角度 30代表增加30度
		 */
		public void  rotate(Point point,double degree);
		/**
		 * 变幻位置 平移
	     * @param x x轴偏移量
	     * @param y y轴偏移量		 
		 */
		public void transform(double x,double y);
}
