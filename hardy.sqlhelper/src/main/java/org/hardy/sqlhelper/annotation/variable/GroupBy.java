package org.hardy.sqlhelper.annotation.variable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 标注在属性上，表示分组依据
 * @author song
 *
 */
@Target({java.lang.annotation.ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface GroupBy
{
	/**
	    * 执行顺序
	    * @return
	    */
	   public int index() default 0;
}