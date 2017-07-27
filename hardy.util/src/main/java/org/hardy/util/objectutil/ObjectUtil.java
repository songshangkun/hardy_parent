package org.hardy.util.objectutil;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.hardy.reflex.SpringReflext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

/**
 * 对象帮助工具类
 * @author 09
 *
 */
public class ObjectUtil {
	/**
	 * 拷贝一个对象书香到另一个对象
	 * @param source   源对象
	 * @param sourcePropNames 源对象属性名称
	 * @param target  目标对象
	 * @param targetPropNames 目标对象属性名称
	 * @return
	 */
	public static Object copyProperties(Object source, String[] sourcePropNames, Object target, String[] targetPropNames)
	  {
	    if (sourcePropNames.length != targetPropNames.length) {
	      throw new RuntimeException("sourcePropNames length != targetPropNames length");
	    }
	    for (int i = 0; i < sourcePropNames.length; i++) {
	      SpringReflext.set(target, targetPropNames[i], SpringReflext.get(source, sourcePropNames[i]));
	    }
	    return target;
	  }
	  
	/**
	 * 拷贝一个对象书香到另一个对象
	 * @param source  源对象
	 * @param sourcePropNames 源对象属性名称
	 * @param clazz  目标对象的类
	 * @param targetPropNames 目标对象属性名称
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	  @SuppressWarnings("unchecked")
	public static <T> T copyProperties(Object source, String[] sourcePropNames, Class<?> clazz, String[] targetPropNames)
	    throws InstantiationException, IllegalAccessException
	  {
	    if (sourcePropNames.length != targetPropNames.length) {
	      throw new RuntimeException("sourcePropNames length != targetPropNames length");
	    }
	    Object target = clazz.newInstance();
	    for (int i = 0; i < sourcePropNames.length; i++) {
	    	SpringReflext.set(target, targetPropNames[i], SpringReflext.get(source, sourcePropNames[i]));
	    }
	    return (T)target;
	  }
	  
	  
	  /**
		 * 将实体类型转化为Map
		 * 
		 * @param t 需要转化的类型
		 * @return
		 */
		public static <T> Map<String, Object> convertToMap(T t) {
			Map<String, Object> params = new HashMap<String, Object>(0);
			try {
				PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
				PropertyDescriptor[] descriptors = propertyUtilsBean
						.getPropertyDescriptors(t);
				for (int i = 0; i < descriptors.length; i++) {
					String name = descriptors[i].getName(); 
					if (!"class".equals(name)) {
						params.put(name,propertyUtilsBean.getNestedProperty(t, name));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return params;
		}
	    
		 
	    /**
	     * 复制一个新的对象
	     * @param from   对象
	     * @param klass   对象的类
	     * @param excludedProperties  不复制的属性
	     * @return
	     */
		public static <T, R> R copy(T from, Class<R> klass,
				String... excludedProperties) {
			R to = BeanUtils.instantiate(klass);
			return copy(from, to, excludedProperties);
		}

		/**
		 * 浅copy, 不逐级复制.
		 * 
		 * @param from
		 * @param to
		 * @param excludedProperties
		 * @return
		 */
		public static <T, R> R copy(T from, R to, String... excludedProperties) {
			BeanWrapper wrapper = PropertyAccessorFactory
					.forBeanPropertyAccess(from);
			BeanWrapper toWrapper = PropertyAccessorFactory
					.forBeanPropertyAccess(to);

			for (PropertyDescriptor descriptor : wrapper.getPropertyDescriptors()) {
				if (descriptor.getWriteMethod() == null
						|| descriptor.getReadMethod() == null) {
					continue;
				}

				String name = descriptor.getName();
				boolean found = Arrays.binarySearch(excludedProperties, name) >= 0;
				if (found) {
					continue;
				}

				toWrapper.setPropertyValue(name, wrapper.getPropertyValue(name));
			}
			return to;
		}
	    /**
	     * 将一个对象中的属性替换到另一个对象中
	     * @param target   目标对象
	     * @param source   要复制属性的对象
	     * @param includedProperties   需要复制的属性
	     * @return
	     */
		public static <T, R> T extend(T target, R source,
				String... includedProperties) {
			BeanWrapper targetWrapper = PropertyAccessorFactory
					.forBeanPropertyAccess(target);
			BeanWrapper sourceWrapper = PropertyAccessorFactory
					.forBeanPropertyAccess(source);

			for (PropertyDescriptor descriptor : sourceWrapper
					.getPropertyDescriptors()) {
				if (descriptor.getWriteMethod() == null
						|| descriptor.getReadMethod() == null) {
					continue;
				}

				String name = descriptor.getName();
				boolean found = includedProperties.length == 0
						|| Arrays.binarySearch(includedProperties, name) >= 0;
				if (!found) {
					continue;
				}

				if (!targetWrapper.isWritableProperty(name)) {
					continue;
				}
				targetWrapper.setPropertyValue(name,
						sourceWrapper.getPropertyValue(name));
			}
			return target;
		}

		public static String toString(Object o) {
			return ReflectionToStringBuilder.toString(o);
		}
}
