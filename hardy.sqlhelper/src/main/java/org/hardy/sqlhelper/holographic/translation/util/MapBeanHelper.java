package org.hardy.sqlhelper.holographic.translation.util;

import java.text.ParseException;
import java.util.Map;

import org.apache.commons.lang.NotImplementedException;
import org.hardy.reflex.SpringReflext;
import org.hardy.sqlhelper.datatrans.JdbcDataTransfer;
import org.hardy.sqlhelper.datatrans.inf.DataTranslation;
import org.hardy.sqlhelper.holographic.TableConstruct;

/**
 * map数据与实体类转化工具
 * @author song
 *
 */
public class MapBeanHelper {
	/**
     * 将map转化成对象，这里的map的key是属性名称 value查询结果
     * @param clazz 要转换的对象
     * @param map  map的key必须是和类的属性名称相对应
     * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ParseException 
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 */
	  @SuppressWarnings("unchecked")
	public static <T>T mapPropToBean(Class<?> clazz,Map<String,Object> map,TableConstruct tc,DataTranslation dataTranslation) throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException, ParseException{
		  if(dataTranslation==null)throw new NotImplementedException("DataTranslation 接口未设置"); 
		  Object obj = clazz.newInstance();
		  for(String key:map.keySet()){ 
			  SpringReflext.set(obj, key, dataTranslation.transler(map.get(key), tc.getPropColumnByProp(key))); 
		  } 
		return (T)obj; 
	  }
	  @Deprecated
	  @SuppressWarnings("unchecked")
	public static <T>T mapColToBean(Class<T> clazz,Map<String,Object> map) throws InstantiationException, IllegalAccessException{
			 Object obj = clazz.newInstance();
			  for(String key:map.keySet()){
				  SpringReflext.set(obj, key, map.get(key)); 
			  }
			return (T)obj; 
		  }

	 
}
