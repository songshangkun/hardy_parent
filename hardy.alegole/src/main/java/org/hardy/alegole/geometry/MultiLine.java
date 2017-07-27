package org.hardy.alegole.geometry;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.ObjectUtils.Null;
import org.apache.commons.lang3.ArrayUtils;
import org.hardy.util.arrays.ArrayTools;
import org.hardy.util.arrays.ArrayUtil;
import org.hardy.util.arrays.ArraysUtils;

/**
 * 多边线,多条线段组成线条
 * @author songshangkun
 *
 */
public class MultiLine implements Form{
	/**
	 * 端点和节点
	 */
	public Point[] vertexs;
	/**
	 * 多个直线边
	 */
	public Line[] lines;
	/**
	 * 端点和节点数量
	 */
	public int vertexs_count;
	/**
	 * 边的数量
	 */
	public int line_count;
	
	public MultiLine(){}
    /**
     * 新建一个多线图形
     * @param vertexs vertexs顶点坐标不能包含相同序列相同坐标
     */
	public MultiLine(Point[] vertexs) {
		super();
		initProp(vertexs);
	}
	
	
	/**
     * 新建一个多线图形
     * @param lines lines线段,不允许有开始点到结束点有向线段的相同线段
     */
	public MultiLine(Line[] lines) {
		super();
		initProp(lines);
	}
	/**
	 * 初始化线段,不能保证顶点序列
	 * @param lines
	 */
	public void initProp(Line[] lines){
		 this.lines = lines;
		 this.line_count = lines.length;
		 Set<Point> sVer = new HashSet<>();
		 for(Line line:this.lines){
			 sVer.add(line.getStart());
			 sVer.add(line.getEnd());
		 }
		 for(Point p:sVer){
			 for(Line line:this.lines){
				 if(p.equals(line.getStart())) line.setStart(p);
				 if(p.equals(line.getEnd())) line.setEnd(p);
			 } 
		 }
		 this.vertexs_count = sVer.size();
		 this.vertexs = new Point[this.vertexs_count];
		 
	}
    /**
     * 初始化顶点和关联点
     * @param vertexs
     */
	public void initProp(Point[] vertexs){
		//1-把重复的顶点排除，但要记得顶点的排列次序
		Point[] nvertexs = ArrayUtils.clone(vertexs);
		nvertexs = ArraysUtils.hashArray(nvertexs,Point.class);
		//1-根据vertexs的顺序画出相应的线段
		Set<Line> sline = new HashSet<>();
		for(int i=0;i<vertexs.length-1;i++){
			Point p1 = ArrayTools.seacherHash_(nvertexs, vertexs[i],null);
			Point p2 = ArrayTools.seacherHash_(nvertexs, vertexs[i+1],null);
			sline.add(new Line(p1,p2));
		}
		this.line_count = sline.size();
		this.lines = new Line[line_count];
		sline.toArray(lines);
		this.vertexs = nvertexs;
		this.vertexs_count = nvertexs.length;
	}


	@Override
	public Point[] getVertexs() {
		return vertexs;
	}

	@Override
	public boolean hasVertexs(Point point) {		 
		return ArrayUtils.contains(this.vertexs, point);
	}

	@Override
	public void rotate90(Point point) {
		rotate(point, -90);
	}

	@Override
	public void rotate180(Point point) {
		rotate(point, -180);
	}

	@Override
	public void rotate270(Point point) {
		rotate(point, 90);
	}

	@Override
	public void flip(Line line) {
		//这里不能改变线,应为先变化后,会打乱点的顺序并使点也跟着变化所以这里要改变点
		for(Point p:this.vertexs){
			 p.flip(line);
		 }
	}

	@Override
	public void rotate(Point point, double degree) {
		 for(Line l:this.lines){
			 l.rotate(point, degree);
		 }
	}

	@Override
	public void transform(double x, double y) {
		for(Point p:this.vertexs){
			 p.transform(x, y);
		 }
	}

	public Line[] getLines() {
		return lines;
	}

	public void setLines(Line[] lines) {
		this.lines = lines;
	}

	public void setVertexs(Point[] vertexs) {
		this.vertexs = vertexs;
	}
    /**
     * 追加一组个点,图形最后一个点和新的点连成一个线段
     * @param point
     */
	public void addPoint(Point... points){
		this.vertexs = ArraysUtils.concatAll(this.vertexs,points);
		initProp(this.vertexs);
	} 
    /**
     * 追加一个线段
     * @param line
     */
	public void addLine(Line... lines){
		this.lines = ArraysUtils.concatAll(this.lines,lines);
		initProp(this.lines);
	} 
	
	public static void main(String[] args) {
		MultiLine m = new MultiLine();
		Point[] a1 = new Point[]{new Point(0,0),new Point(1,3),new Point(0,0),new Point(2,4)};	 
		m.initProp(a1);
		m.addPoint(new Point(100,100));
		System.out.println(Arrays.toString(m.getVertexs()));
		

		
		
	}
}
