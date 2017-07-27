package org.hardy.montion.system.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记要记录的方法，并根据接口配置记录信息
 * @author 09
 *
 */
@Target({java.lang.annotation.ElementType.METHOD,java.lang.annotation.ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodBug {
      /**
       * 方法作者
       * @return
       */
	   public abstract String author();
	   /**
	    * 解释说明 可为空
	    * @return
	    */
	   public String description() default"";
	   
	   
}
