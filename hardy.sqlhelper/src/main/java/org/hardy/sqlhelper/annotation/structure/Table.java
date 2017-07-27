package org.hardy.sqlhelper.annotation.structure;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Table
{
	/**
	 * 标注表名称
	 * @return
	 */
  public  String name() default "" ;
  /**
   * 是否为抽象的表
   * @return
   */
  public  boolean abstraction() default false ;
   
}