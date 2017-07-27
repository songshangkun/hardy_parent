package org.hardy.reflex;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.song.tool.runtime.exception.MethodRuntimException;

/***
 * 一个反射操作方法的类
 * @author Administrator
 *
 */
public class RefletMethodUtil {
	 
	private RefletMethodUtil(){}
	/**
     * @说明  调用对象某属性的setter方法，对象属性名与setter方法必须符合约定
     * @param bean 对象实例
     * @param propertyName 属性名
     * @param propValue setter方法参数
     * @
     * */
    public static void setter(Object bean,String propertyName,Object propValue,Class<?> clazz){
    	setter(bean, propertyName, true, propValue, clazz);
    }
     /**
      * @说明  调用对象某属性的setter方法，对象属性名与setter方法必须符合约定
      * @param bean 对象实例
      * @param propertyName 属性名
      * @param security  boolean 是否可调用protected方法或private方法
      * @param propValue setter方法参数
      * @param clazz setter方法参数的类型
      * */
     public static void setter(Object bean,String propertyName,boolean security,Object propValue,Class<?> clazz){
    	 String propNameCamelize = propertyName.substring(0, 1).toUpperCase()+
				 propertyName.substring(1, propertyName.length());
         propNameCamelize = "set"+propNameCamelize;
         callMethod(bean, propNameCamelize, security, new Object[]{propValue}, new Class<?>[]{clazz});
	    }
     /**
      * @说明  调用对象某属性的getter方法，对象属性名与getter方法必须符合约定
      * @param bean 对象实例
      * @param propertyName 属性名
      * @return Object getter方法返回值
      * @
      * */
	    public static Object getter(Object bean,String propertyName){
	    	 return getter(bean, propertyName, true);
	    }
     /**
      * @说明  调用对象某属性的getter方法，对象属性名与getter方法必须符合约定
      * @param bean 对象实例
      * @param propertyName 属性名
      * @param security  boolean 是否可调用protected方法或private方法
      * @return Object getter方法返回值
      * @
      * */
	    public static Object getter(Object bean,String propertyName,boolean security){
	    	String propNameCamelize = propertyName.substring(0, 1).toUpperCase()+
					propertyName.substring(1, propertyName.length());
	    	 try {
				if(bean.getClass().getDeclaredField(propertyName).getType()==boolean.class){
					if(!propNameCamelize.startsWith("is"))
					propNameCamelize = "is"+propNameCamelize; 
					else propNameCamelize = propertyName;
				}else{
					propNameCamelize = "get"+propNameCamelize;
				}
			} catch (NoSuchFieldException e) {	
				e.printStackTrace();
			} catch (SecurityException e) {	
				e.printStackTrace();
			}
	    return callMethod(bean, propNameCamelize, security);
	   }
	    /**
	     * 调用对象某属性的is方法，对象属性名与is方法必须符合约定
	     * @param bean
	     * @param propertyNmae
	     * @param security
	     * @return
	     */
	    public static Object is(Object bean,String propertyNmae,boolean security){
	    	String propNameCamelize = propertyNmae.substring(0, 1).toUpperCase()+
	    			 propertyNmae.substring(1, propertyNmae.length());
	         propNameCamelize = "is"+propNameCamelize;
	    return callMethod(bean, propNameCamelize, security);
	   }
	    /**
	     * @说明  调用对象的某个无参方法
	     * @param bean 对象实例
	     * @param methodName  方法名称
	     * @return Object 方法返回值
	     */
	     
	    public static Object callMethod(Object bean,String methodName){
	    	return callMethod(bean, methodName, true, null, null);
	    }
	    /**
	     * @说明  调用对象的某个无参方法
	     * @param bean 对象实例
	     * @param methodName  方法名称
	     * @param security  boolean 是否可调用protected方法或private方法
	     * @return Object 方法返回值
	     */
	     
	    public static Object callMethod(Object bean,String methodName,boolean security){
	    	return callMethod(bean, methodName, security, null, null);
	   }
	    /**
	     * @说明  调用对象的某个方法
	     * @param bean 对象实例
	     * @param methodName  方法名称
	     * @param params  方法的参数对象
	     * @param parameterTypes  方法的参数对象的类型
	     * @return Object 方法返回值
	     */
	    public static Object callMethod(Object bean,String methodName,Object[] params,Class<?>[]parameterTypes){
	    	 return callMethod(bean, methodName, true, params, parameterTypes);
	    } 
	    /**
	     * @说明  调用对象的某个方法
	     * @param bean 对象实例
	     * @param methodName  方法名称
	     * @param security  boolean 是否可调用protected方法或private方法
	     * @param params  方法的参数对象
	     * @param parameterTypes  方法的参数对象的类型
	     * @return Object 方法返回值
	     */
	    public static Object callMethod(Object bean,String methodName,boolean security,Object[] params,Class<?>[]parameterTypes) {  	 
     	   Object resulta = null;
     	  Method method = null;
	    	try {    
	    		if(security){    		
	    				method = bean.getClass().getMethod(methodName,parameterTypes); 
	    		}else{	    
	    				method = bean.getClass().getDeclaredMethod(methodName,parameterTypes);
	   		            method.setAccessible(true);
	    		}
		     resulta = method.invoke(bean,params);
			} catch (NoSuchMethodException e) {
				throw new MethodRuntimException("RefletMethodUtil warning  NoSuchMethodException ["
						+bean.getClass().getName() +"]'s ["+ methodName +"] is not be find !");
			} catch (IllegalAccessException e) {
				throw new MethodRuntimException("RefletMethodUtil warning  IllegalAccessException ["
						+bean.getClass().getName() +"]'s ["+ methodName +"] is not support access!");
			} catch (IllegalArgumentException e) {
				throw new MethodRuntimException("RefletMethodUtil warning  IllegalArgumentException ["
						+bean.getClass().getName() +"]'s ["+ methodName +"] argument are illegal!");
			} catch (InvocationTargetException e) {
				 System.err.println("实际的错误:"+e.getTargetException().getMessage());
				throw new MethodRuntimException("RefletMethodUtil warning  InvocationTargetException ["
						+bean.getClass().getName() +"]'s ["+ methodName +"] invoke error!");
			} 
	         return resulta;
	    }
	   /**
	    * 
	    * @param bean  操作的对象
	    * @param propertyNames  操作对象的嵌套属性  "aa","bb","cc" ，aa的属性bb的cc属性
	    * @return
	    */
	    public static Object getterLoop(Object bean,String... propertyNames){
			 for(String pn:propertyNames){
				 bean = getter(bean, pn,false);
				 if(null==bean) return null;
			 }
	    	return bean;
	    }
	    /**
		    * 
		    * @param bean  操作的对象
		    * @param propertyNamees  操作对象的嵌套属性  "aa.bb.cc" ，aa的属性bb的cc属性
		    * @return
		    */
	    public static Object getterLoop(Object bean,String propertyNamees){
	    	 String[] strs = propertyNamees.split("\\.");
	    	 return getterLoop(bean, strs);
	    }
	    /**
	     * 判断目标类中是否含有列举的参数方法
	     * @param clazz
	     * @param methods
	     * @return
	     */
	    public static boolean isExsitMethod(Class<?> clazz,Method... methods){
	    	  Method[] ms = clazz.getMethods();
	    	  int i = 0;
	    	  for(Method m1:methods){
	    		 for(Method m2:ms){	  
	    			 if(m1.equals(m2)){i++; break;}
	    		 }
	    	  } 
	    	  if(methods==null||methods.length==i)return true;
	    	  else return false;
	    }
	    /**
	     * 只根据方法名称判断目标类是否含有参数方法
	     * @param clazz
	     * @param methodNames
	     * @return
	     */
	    public static boolean isExsitMethod(Class<?> clazz,String... methodNames){
	    	  Method[] ms = clazz.getMethods();
	    	  int i = 0;
	    	  for(String m1:methodNames){
	    		 for(Method m2:ms){	  
	    			 if(m1.equals(m2.getName())){i++; break;}
	    		 }
	    	  } 
	    	  if(methodNames==null||methodNames.length==i)return true;
	    	  else return false;
	    }
	    
	    public static boolean isExsitMethod(Class<?> clazz,String methodNames,Class<?>... parameterType){
	    	try{
	    		clazz.getMethod(methodNames,parameterType);
	    	}catch(NoSuchMethodException e){
	    		return false;
	    	}
	    	 return true;
	    }
	    
	   
	
}
 