package org.hardy.statics.sprintutil.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自动将注册这个注解的对象，加入到spring的bean中去，
 * 和spring的@Component类似，但需要自己实现处理注解的引擎
 * @author song
 *
 */
@Documented
@Inherited
@Target(value={ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoEntityBean {
     String name() default "";
}
