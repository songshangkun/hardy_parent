package org.hardy.alegole.geometry;

/**
 * 代表一个二维平面,三角，四边都是一个平面
 * @author songshangkun
 *
 */
public interface Plane {
      /**
       * 计算精确地面积
       * @return
       */
	  public double measureArea();
	  /**
	   * 图形Form是否位于平面内
	   * @param Form
	   * @return
	   */
	  public boolean surFace(Form form);
	  
	  
	  
}
