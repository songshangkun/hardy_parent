package org.hardy.statics.cache.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.hardy.statics.ope.OpeEnum;


/**
 * 用于Ehcache缓存的注解
 * @author 09
 *
 */
@Target({java.lang.annotation.ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Ehcache
{ 
 /**
  * 标注ehcahe的缓存文件位置
  * @return
  */
  String config();
  /**
   * 缓存名称
   * @return
   */
  String cacheName();
  /**
   * 缓存操作类型
   * @return
   */
  OpeEnum operator() default OpeEnum.FIND;
}