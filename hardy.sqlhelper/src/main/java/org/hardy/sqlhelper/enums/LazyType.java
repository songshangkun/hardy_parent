package org.hardy.sqlhelper.enums;
/**
 * 懒加载的模式
 * @author songs
 *
 */
public enum LazyType {
	/**
     * 根据全局设置使用LazyType
     */
	  NONE,
      /**
       * 对于复合对象,不执行加载
       */
	  SANS,
	  /**
	   * 对于符合对象只加载关联数据到对象
	   */
	  SUELKEY,
	  /**
	   * 对于符合对象进行搜索,并全部加载
	   */
	  LINKED;
}
