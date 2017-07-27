package org.hardy.reflex;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
/**
 * 利用spring的方法进行setter,getter操作
 * @author 09
 *
 */
public class SpringReflext {
	/**
	   * 设置对象setter方法
	   * @param bean
	   * @param property
	   * @param value
	   */
	  public static void set(Object bean, String property, Object value)
	  {
	    BeanWrapper wrapper = null;
	    wrapper = PropertyAccessorFactory.forBeanPropertyAccess(bean);
	    wrapper.setPropertyValue(property, value);
	  }
	  
	  /**
	   * 设置对象getter方法
	   * @param bean
	   * @param property
	   * @return
	   */
	  @SuppressWarnings("unchecked")
	public static <R> R get(Object bean, String property) {
	    BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(bean);
	    return (R)wrapper.getPropertyValue(property);
	  }
	    /**
	     * 通过属性xxx.xx的形式反射出属性对象
	     * @param bean
	     * @param prop
	     * @return
	     */
	  public  static  Object getPropValue(Object bean,String prop){
		  if(bean==null) return null;
		  if(prop==null||prop.trim().equals("")) return bean;
		   String[] pops = prop.split("\\.");
		   Object result = bean;
		   for(String s:pops){
			   result = get(result, s);
			   if(result==null) return null;
		   }
		   return result;
	  }
	  /**
	   * 通过属性xxx.xx的形式反射出属性对象
	   * 注意:char类型会转为String
	   * @param bean
	   * @param prop
	   * @return
	   */
	  public  static  Object getPropValueChar(Object bean,String prop){
		  if(bean==null) return null;
		  if(prop==null||prop.trim().equals("")){
			  if(bean instanceof	Character||bean.getClass()==char.class){
				  return bean.toString();
			  }else{
				  return bean;
			  }
		  }
		   String[] pops = prop.split("\\.");
		   Object result = bean;
		   for(String s:pops){
			   result = get(result, s);
			   if(result==null) return null;
		   }
		   if(result instanceof	Character||result.getClass()==char.class) return result.toString();
		   return result;
	  }
	  
	  /**
	   * 通过属性xxx.xx的形式反射出属性对象,并设置到最终的结果对象当中
	   * @param bean    最终对象
	   * @param prop   joinPropValue关联的属性
	   * @param object   得到的根属性值
	   * @return
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	   */
	  public  static void setPropValue(Object bean,String prop,Object object) throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException{
		  if(bean==null||object==null) return ;
		  if(prop==null||prop.trim().equals(""))return;
		   String[] pops = prop.split("\\.");
		   Object result1 = bean;
		   Object result2 = null;
		   for(int i=0;i<pops.length;i++){
			   if(i<pops.length-1)result2 = result1.getClass().getDeclaredField(pops[i]).getType().newInstance();
			   else result2 = object;
			   set(result1,pops[i],result2);
			   result1 = result2;
		   }
	  }
	  
	  
}
