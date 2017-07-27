package org.hardy.reflex;

import java.text.ParseException;
import java.util.Date;

import com.song.tool.datetime.DateTime;

/**
 * 一个封装用的工具类
 * @author Administrator
 *
 */
public class WrapperConvertor {
   /**
    * 将String值根据class的值转成primary基本类型
    * @param value  String值，如果该String为illegal 抛出异常
    * @param c  class指定类 int float等基本类型
    * @return
    */
	public static Object valuePrivitimeOf(String value,Class<?> c){
		if("int".equals(c.getName())){
			return Integer.parseInt(value);
		}
		if("byte".equals(c.getName())){
			return Byte.parseByte(value);
		}
		if("short".equals(c.getName())){
			return Short.parseShort(value);
		}
		if("float".equals(c.getName())){
			return Float.parseFloat(value);
		}
		if("double".equals(c.getName())){
			return Double.parseDouble(value);
		}
		if("long".equals(c.getName())){
			return Long.parseLong(value);
		}
		if("char".equals(c.getName())){
			return value.charAt(0);
		}
		if("boolean".equals(c.getName())){
			return Boolean.parseBoolean(value);
		}
		return null;	
	}
	
	/**
	 * 默认为string类型输出,将String转化为simpleName标注的对象类型
	 * @param value string字符串
	 * @param simpleName  long,byte,short,float,double,boolean,int,默认string
	 * @return
	 */
	public static Object wrapperOf(String value,String simpleName){
		String className = "java.lang.String";
		if("long".equalsIgnoreCase(simpleName)) className = "java.lang.Long";
		if("byte".equalsIgnoreCase(simpleName)) className = "java.lang.Byte";
		if("short".equalsIgnoreCase(simpleName)) className = "java.lang.Short";
		if("float".equalsIgnoreCase(simpleName)) className = "java.lang.Float";
		if("double".equalsIgnoreCase(simpleName)) className = "java.lang.Double";
		if("char".equalsIgnoreCase(simpleName)) className = "java.lang.Character";
		if("boolean".equalsIgnoreCase(simpleName)) className = "java.lang.Boolean";
		if("int".equalsIgnoreCase(simpleName)) className = "java.lang.Integer";
		Class<?> c = null;
		try {
			 c = Class.forName(className);
		} catch (ClassNotFoundException e) { 
			e.printStackTrace();
		}
		return wrapperOf(value, c);
	}
	/**
	    * 将String值根据class的值转成wrapper基本类型的封装类
	    * @param value  String值，如果该String为illegal 抛出异常
	    * @param c  class指定类 Integer Float等类型
	    * @return
	    */
	public static Object wrapperOf(String value,Class<?> c){
		if("java.lang.Integer".equals(c.getName())){
			return Integer.valueOf(value);
		}
		if("java.lang.Byte".equals(c.getName())){
			return Byte.valueOf(value);
		}
		if("java.lang.Short".equals(c.getName())){
			return Short.valueOf(value);
		}
		if("java.lang.Float".equals(c.getName())){
			return Float.valueOf(value);
		}
		if("java.lang.Double".equals(c.getName())){
			return Double.parseDouble(value);
		}
		if("java.lang.Long".equals(c.getName())){
			return Long.valueOf(value);
		} 
		if("java.lang.Character".equals(c.getName())){
			return Character.valueOf(value.charAt(0));
		}
		if("java.lang.Boolean".equals(c.getName())){
			return Boolean.valueOf(value);
		}
		return null;	
	}
	/**
	    * 将String值根据class的值转成primary基本类型或wrapper基本类型的封装类
	    * 如果c是string类，则直接返回value
	    * @param value  String值，如果该String为illegal 抛出异常
	    * @param c  class指定类 int float Integer Float等 类型
	    * @return
	    */
	 public static Object isRequire(String value,Class<?> c){
		 if(c==java.lang.String.class){
			  return value;
		 }
		 if(isPrimary(c)){
			 return valuePrivitimeOf(value, c);
		  }
		 if(isWrapper(c)){
			 return wrapperOf(value, c);
		 }
		 return value;
	 }
	 
	 public static boolean isWrapper(Class<?> c){
		 if(       "java.lang.Integer".equals(c.getName())||
				    "java.lang.Byte".equals(c.getName())||
				    "java.lang.Short".equals(c.getName())||
				    "java.lang.Long".equals(c.getName())||
				    "java.lang.Boolean".equals(c.getName())||
				    "java.lang.Float".equals(c.getName())||
				    "java.lang.Double".equals(c.getName())||
				    "java.lang.Character".equals(c.getName())	 
				 ){
			 return true;
		 }
		 return false;
	 }
	 
	 public static boolean isPrimary(Class<?> c){
		 if("int".equals(c.getName())||
				    "byte".equals(c.getName())||
				    "short".equals(c.getName())||
				    "long".equals(c.getName())||
				    "boolean".equals(c.getName())||
				    "float".equals(c.getName())||
				    "double".equals(c.getName())||
				    "char".equals(c.getName())
				    ){
			 return true;
		 }
		 return false;
	 }
	 
	 /**
	  * 属于8种基本类型的封包或拆包都返回true
	  * @param c
	  * @return
	  */
	 public static boolean isPrimaryOrWrapper(Class<?> c){
		  return isPrimary(c)||isWrapper(c);
	 }
    /**
     * 如果对象属于Integer,Long,Short,Byte,Float,Double,Boolean,Character,String,Date,StringBuilder,StringBuffer
     * 则返回true
     * @param obj
     * @return
     */
	public static boolean isEssentielData(Object obj) {
		if(obj instanceof Integer||
		   obj instanceof Long||
		   obj instanceof Short||
		   obj instanceof Byte||
		   obj instanceof Float||
		   obj instanceof Double||
		   obj instanceof Boolean||
		   obj instanceof Character||
		   obj instanceof String||
		   obj instanceof Date||
		   obj instanceof StringBuilder||
		   obj instanceof StringBuffer){
			return true;
		}
		return false;
	}
   
	public static boolean isEssentielData(Class<?> clazz) {
		return isPrimaryOrWrapper(clazz)||clazz==String.class||clazz==Date.class||
				clazz==StringBuilder.class||clazz==StringBuffer.class;
		}
   
	public static Object essaentialDataOf(String value,Class<?> clazz,String DatePatten) throws ParseException{
		if(clazz==String.class) return value;
		else if(clazz==StringBuilder.class) return value.toString();
		else if(clazz==StringBuffer.class) return value.toString();
		else if(clazz==Date.class) return DateTime.convertirStringToDate(value, DatePatten);
		else return wrapperOf(value, clazz);
	}
}
