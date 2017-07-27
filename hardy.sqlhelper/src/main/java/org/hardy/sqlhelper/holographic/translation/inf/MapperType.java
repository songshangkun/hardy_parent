package org.hardy.sqlhelper.holographic.translation.inf;

/**
 * 属性对象转换类型接口,
 * 如果一个实体类实现这个接口,在保存时会按照接口制定方式保存
 * 当实体类对象实现MapperType接口,处理优先级最高
 * 当在@COLUMN注解指定了mapperType处理优先级低
 * 
 * @author song
 *
 */
public interface MapperType {
     /**
      * 处理属性对象并得到返回值,用于添加到方法参数中
      * @param obj
      * @return
      */
	  public Object mapper(Object obj);
	  /**
	   * 将数据返回成对象属性
	   * @param obj
	   * @return
	   */
	  public Object retour(Object obj);
}
