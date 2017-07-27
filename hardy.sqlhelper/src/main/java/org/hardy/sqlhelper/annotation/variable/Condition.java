package org.hardy.sqlhelper.annotation.variable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 标注在属性上，表示ID
 * @author song
 *
 */
@Target({java.lang.annotation.ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Condition {
	 
	/**
	 * 在有@Column属性上标注带属性关联的列名称+标注语句
	 * 在没有@Column属性上无内容
	 * @return
	 */
	  public String value() default "IS NOT NULL";
      /**
       * 关联方式 and和or
       */
	  public String link() default "and";
}
