package org.hardy.sqlhelper.annotation.variable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 未标注时搜索为数据库的列名称 *
 * <br>标注在类上会返回全部属性名称(无参数时返回全部,否则返回参数内容)
 * <br>标注在多个属性上会返回多个属性名称(参数是别名)
 * <br>标注在方法上会使用方法返回的String字符串(方法有返回值时返回方法指定内容,无参数时返回Result默认内容,否则返回参数内容)
 * <br>优先级标注方法上 -> 标注属性上 ->标注在类上
 * @author song
 *
 */
@Target({java.lang.annotation.ElementType.FIELD,java.lang.annotation.ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Result {
       /**
        * 如果有Column的标签,标注的是别名
        * 否则会搜索@Result标注的属性的返回字符。
        * @return
        */
	   public String value() default "";

}
