package hardy.securite.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Inherited
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthCheck {
	/**
	 * param标注用户访问时斜带参数的名称
	 * 当时用这个标注时,系统检查容器中是否
	 * 含有random指定标签携带的值
	 * 
	 */
	String param() default "";
	 
	
	/**
	 * 当用户成功登陆,系统验证在用户session中是否存在标识的Attribute属性
	 * @return
	 */
	String[] sessionAttr() default "";
	/**
	 * 当用户成功登陆,系统验证在用户requestAttr中是否存在标识的Attribute属性
	 * @return
	 */
	String[] requestAttr() default "";
	 
	 
	/**
	 * 当标注el时,过滤器会访问一个实现el方法的接口
	 * 来处理el所标注的字符串内容
	 * @return
	 */
	String[] el() default "";
	
	/**
	 * 允许通过的ip
	 * @return
	 */
	String[] ip() default "";
	/**
	 *  不允许通过的ip
	 * @return
	 */
	String[] refuseIp() default "";
	/**
	 * 对接口不进行过滤,权限高于其他标注
	 * 默认进行过滤
	 * 如果在类上使用此标注则该类所有接口过滤失效
	 * @return
	 */
	boolean exclude() default false;
	
	/**
	 * 是否继承上级class标注的Annotation.
	 * 默认进行对上级的继承
	 * 在class上的注解会忽略这个属性 不需要继承
	 * @return
	 */
	boolean inherit() default true;
}
