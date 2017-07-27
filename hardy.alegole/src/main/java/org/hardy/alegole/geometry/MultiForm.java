package org.hardy.alegole.geometry;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.hardy.statics.exception.NotSupportException;
import org.hardy.util.arrays.ArrayTools;
import org.hardy.util.arrays.ArraysUtils;

/**
 * 多边形,多条线段组成的封闭图形
 * @author songshangkun
 *
 */
public class MultiForm extends MultiLine{

	public MultiForm() {
		super();
	}

	public MultiForm(Line[] lines) {
		super(lines);
	}

	public MultiForm(Point[] vertexs) {
		super(vertexs);
	}

	 

	@Override
	public void initProp(Point[] vertexs) {
		//1-把重复的顶点排除，但要记得顶点的排列次序
				Point[] nvertexs = ArrayUtils.clone(vertexs);
				nvertexs = ArraysUtils.hashArray(nvertexs,Point.class);
				//1-根据vertexs的顺序画出相应的线段
				Set<Line> sline = new HashSet<>();
				for(int i=0;i<vertexs.length;i++){
					Point p1 = ArrayTools.seacherHash_(nvertexs, vertexs[i%vertexs.length],null);
					Point p2 = ArrayTools.seacherHash_(nvertexs, vertexs[(i+1)%vertexs.length],null);
					sline.add(new Line(p1,p2));
				}
				this.line_count = sline.size();
				this.lines = new Line[line_count];
				sline.toArray(lines);
				this.vertexs = nvertexs;
				this.vertexs_count = nvertexs.length;
	}
	
	/**
	 * 根据点获取相邻的两条边
	 * @param p
	 * @return
	 */
	public Line[] getAdjacentLine(Point p){
		if(!ArrayUtils.contains(this.vertexs, p))throw new NotSupportException("点不是三角形顶点");
		 Line[] ls = new Line[2];
		 int i=0;
		 for(Line l:this.lines){
			 if(l.hasVertexs(p)){
				 ls[i++] = l;
				 if(i==2)break;
			 }
		 }
		 return ls;
	}
    
	/**
	 * 根据点获取角度
	 * @param p
	 * @return
	 */
	public double getAngle(Point p){
		 Line[] ls = getAdjacentLine(p);
		return GeometryUtil.getDegreeIntersection(p, ls[0].getOtherPoint(p), ls[1].getOtherPoint(p));   
	}
    
	  
}
