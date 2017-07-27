package org.hardy.reflex;


import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.hardy.statics.cache.reflex.ReflectType;

/**
 * 一个使用反射的工具类，具体的查询父类及接口的方法
 * @author Administrator
 *
 */
public class RefletClazzUtil {
	/**
	 * @说明 一个对象是否实现某个接口
	 * @param obj  对象实例
	 * @param interface_Name  接口名称
	 * @return boolean 若对象实现了该接口返回true,为实现返回false
	 */
	public static boolean isExistInterface(Object obj,String interface_Name){
		Class<?> clazz = obj.getClass();
		return isExistInterface(clazz, interface_Name);
	}
	/**
	 * @说明 一个类是否实现某个接口
	 * @param clazz  类
	 * @param interface_Name  接口名称
	 * @return boolean 若类实现了该接口返回true,为实现返回false
	 */
	public static boolean isExistInterface(Class<?> clazz,String interface_Name){
		boolean flag = false;
		Class<?> [] ifaces = clazz.getInterfaces();
		if(ifaces.length==0){
			return false;
		}
		for(int i=0;i<ifaces.length;i++){
			if(interface_Name.equals(ifaces[i].getName())){
				return true;
			}
		}
		return flag;
	}
	/**
	 *  @说明 
	 *  类所关联的所有接口是否存在参数表示的类(接口或类) ALLINF
	 *  类所关联的所有父类是否存在参数表示的类(接口或类) ALLSUP
	 *  类所关联的所有父类或接口是否存在参数表示的类(接口或类) ALLSAI
	 *  类所关联的所有父类或接口或父类的接口是否存在参数表示的类(接口或类) ALL
	 *  @param clazz1 是参数类(操作类)
	 *  @param clazz2 是要搜索的类
	 *  @return clazz1的接口和父类包含clazz2返回true，否则返回false
	 * */
	public static boolean isExistClass(Class<?> clazz1,Class<?> clazz2,ReflectType type){
		boolean flag = false;
		try{
		ReflectType.valueOf(type.name());
		if(ReflectType.ALL.equals(type)){
			 flag = getAllClass(clazz1).contains(clazz2);
		}
		if(ReflectType.ALLSUP.equals(type)){
			 flag = allSupperClass(clazz1).contains(clazz2);
		}
		if(ReflectType.ALLINF.equals(type)){
			 flag = allInterface(clazz1).contains(clazz2);
		}
		if(ReflectType.ALLSAI.name().equals(type)){
			 flag = allSupperClass(clazz1).contains(clazz2)||
					 allInterface(clazz1).contains(clazz2);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}
	
	
	
	/** 
	 *  @说明 
	 *  得到全部的interface以及这些interface所继承的父interface
	 *  @param clazz 类
	 *  @return Set<Class<?>> 类的所有接口以及接口的父接口
	 * */
	public static Set<Class<?>> allInterface(Class<?> clazz){
		 Set<Class<?>> set = new HashSet<Class<?>>();
		 Class<?> [] ifaces = clazz.getInterfaces(); 
		 set.addAll(Arrays.asList(ifaces));
		 for(Class<?> c:ifaces){
		 set.addAll(allInterface(c));
		 }

		 return set;
	}
	/** 
	 *  @说明 得到全部的superclass以及这些superclass的父类的父类
	 *  @param clazz 类
	 *  @return Set<Class<?>> 类的所有父类 以及父类的父类
	 * */
	public static Set<Class<?>> allSupperClass(Class<?> clazz){
		 Set<Class<?>> set = new LinkedHashSet<Class<?>>();
		 set.add(clazz);
		 while(clazz.getSuperclass()!=null){
			 set.add(clazz.getSuperclass());
			 clazz = clazz.getSuperclass();
		 }
		 return set;
	}

	/** 
	 *   @说明 寻找所有接口所有父类以及所有接口的接口
	 *  父类的父类，父类接口的接口
	 * */
	public static Set<Class<?>> getAllClass(Class<?> clazz){
		Set<Class<?>> set = new HashSet<Class<?>>();
		   set.addAll(allInterface(clazz));
		   set.addAll(allSupperClass(clazz));
		   for(Class<?> c: allSupperClass(clazz)){
			   if(c!= clazz&&c!=null){
				   set.addAll(allInterface(c));
			   }
		   }
		return set;
	}
	
	/***
	 * 据类 和propertis的到属性的class
	 * @param clazz  要测试的类
	 * @param propertis   属性如 xxx.aa.bb
	 * @return bb的class
	 */
	  public static Class<?> getClass(Class<?> clazz,String propertis){ 
                                         if(propertis.trim().equals("")) return getClass(clazz, new String[]{});
	    	 String[] propertys = propertis.split("\\.");  	   	 
	    	 return getClass(clazz, propertys);
	    }
	    /**
	     * 据类 和propertis的到属性的class
	     * @param clazz  要测试的类
	     * @param propertys 属性如 ["xxx","aa","bb"] 
	     * @return
	     */
	    public static Class<?> getClass(Class<?> clazz,String[] propertys){ 
		return getClass(clazz, propertys, propertys.length);		
	    }
	    /**
	     * private 方法 被重载的getClass方法使用，递归运算
	     * @param clazz
	     * @param propertys
	     * @param count
	     * @return 
	     */
	    private static Class<?> getClass(Class<?> clazz,String[] propertys,int count) {  
	    	if(count==0){ 
	    		 return clazz;
	    	 }else{   		
	    		try {
	    			Class<?> tempClazz = clazz.getDeclaredField(propertys[propertys.length-count]).getType();
					return getClass(tempClazz,propertys, count-1);
				} catch (NoSuchFieldException e) {
					 	throw new RuntimeException("["+clazz.getName()+"]'s property ["+ propertys[propertys.length-count]+"] is not found!!");	 
				} catch (SecurityException e) {
					e.printStackTrace();
				}
	    	 }
			return null;
	    	 
	    }
}
