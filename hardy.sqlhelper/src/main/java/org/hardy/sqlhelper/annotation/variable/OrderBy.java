package org.hardy.sqlhelper.annotation.variable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.FIELD,java.lang.annotation.ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OrderBy
{
	/**
	 * 排序规则
	 * @return
	 */
   public String order() default "ASC";
   /**
    * 执行顺序
    * @return
    */
   public int index() default 0;
}