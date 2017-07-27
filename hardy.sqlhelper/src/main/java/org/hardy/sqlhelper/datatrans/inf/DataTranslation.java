package org.hardy.sqlhelper.datatrans.inf;

import java.text.ParseException;

import org.hardy.sqlhelper.holographic.TableConstruct;
import org.hardy.sqlhelper.holographic.TableConstruct.PropColumn;


/**
 * 负责数据库数据类型和java对象类型的转换
 * @author songs
 *
 */
public interface DataTranslation {
     /**
      * 
   * 转化数据库数据类型到java对象属性类型
      * @param data
      * @param targetClass
      * @param tc 数据转换所在表的视图
      * @param propName 对象属性名称
      * @return
      * @throws ParseException
      * @throws NoSuchFieldException
      * @throws SecurityException
      * @throws InstantiationException
      * @throws IllegalAccessException
      */
	  <T>T transler(Object data,PropColumn pc) throws ParseException, NoSuchFieldException, 
			  SecurityException, InstantiationException, IllegalAccessException;
	  
	   
}
