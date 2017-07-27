package org.hardy.statics.sprintutil.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 将使用该注解的类或方法注册进自定义webservice方法中去
 * @author 09
 *
 */
@Documented
@Inherited
@Target(value={ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RegistMethod {
	  
	  public String value() default "";
	  
	  public String descrip() default "non description";
	  
	  public String bean_name() default "reference spring beans";
}
