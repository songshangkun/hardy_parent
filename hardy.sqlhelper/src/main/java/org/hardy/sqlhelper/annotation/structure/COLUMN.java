package org.hardy.sqlhelper.annotation.structure;

import java.awt.Component;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.hardy.sqlhelper.enums.DataType;
/**
 * 标注在属性上表示属性为数据库的一个列
 * @author song
 *
 */
import org.hardy.sqlhelper.enums.LazyType;
@Target({java.lang.annotation.ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface COLUMN
{
  
	/**
	 * 标注表列名称
	 * @return
	 */
   public  String name() default "";
   /**
    * 是否不为空,默认可以为空
    * @return
    */
   public boolean notnull() default false;
   /**
    * 是否唯一,默认不唯一
    * @return
    */
   public boolean unique() default false;
   /**
    * 注释
    * @return
    */
   public String commit() default "";
   /**
    * 数据库对应的类型
    * @return
    */
   public DataType type() default DataType.VARCHAR ;
   /**
	 * 数据库字段是否记录属性的子属性 xx.xx
	 * @return
	 */
	public String joinPropValue() default "";
	/**
	 * 懒加载模式,默认不加载对象
	 * @return
	 */
	public LazyType lazy() default LazyType.SANS;
	/**
	 * 属性对象转换类型,优先级大于joinPropValue
	 * @return
	 */
	public String mapperType() default "";
}