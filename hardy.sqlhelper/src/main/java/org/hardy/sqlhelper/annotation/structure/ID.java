package org.hardy.sqlhelper.annotation.structure;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.hardy.sqlhelper.enums.DataType;
import org.hardy.sqlhelper.enums.LazyType;
/**
 * 标注在属性上，表示ID
 * @author song
 *
 */
@Target({java.lang.annotation.ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ID {
	/**
	 * 标注id列名称
	 * @return
	 */
	public String name() default "";
	/**
	 * 数据库表中的数据类型
	 * @return
	 */
	public DataType type() default DataType.BIGINT ;
    /**
     * 注释
     * @return
     */
	public String commit() default "";
	/**
	 * 是否自动增长
	 * @return
	 */
	boolean automatic() default false;  //是否自动生成
	/**
	 * 数据库字段是否记录属性的子属性 xx.xx
	 * @return
	 */
	public String joinPropValue() default "";
	/**
	 * 懒加载模式,默认不加载对象
	 * @return
	 */
	public LazyType lazy() default LazyType.NONE;
	
	/**
	 * 属性对象转换类型,优先级大于joinPropValue
	 * @return
	 */
	public String mapperType() default "";
}
