package org.hardy.netty.http;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.hardy.statics.web.ContentType;
@Documented
@Inherited
@Target(value={ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
 
	  /**
	   * 定义url路径
	   * @return
	   */
	  public String value() default "/";
	   /**
	    * 定义url返回的数据类型
	    * @return
	    */
	   public ContentType resultType() default ContentType.TEXT; 
	   /**
	    * 要求的访问方法
	    * @return
	    */
	   public RequestMethod[] requestMethod() default RequestMethod.ALL;
}
