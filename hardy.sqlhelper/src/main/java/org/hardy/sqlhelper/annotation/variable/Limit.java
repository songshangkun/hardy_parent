package org.hardy.sqlhelper.annotation.variable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.hardy.statics.constants.SQLConstant;

/**
 * 标注在属性上 分页
 * @author song
 *
 */
@Target({java.lang.annotation.ElementType.FIELD,java.lang.annotation.ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Limit
{
   public String type() default SQLConstant.MYSQL;
	
   public  boolean firstResult() default false;
   
   public boolean maxResult() default false;
}