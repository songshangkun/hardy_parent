package org.hardy.util.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Annotation 工具类,封装了找寻class注解,方法注解,属性注解的封装
 * @author huoleihu
 *
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class AnnotationTools {
	private AnnotationTools(){}
	
	/**
	 * 找到class类上的某个Annotation,如果未标注返回NULL
	 * @param clazz
	 * @param ann
	 * @return  
	 */
	public static <T extends Annotation>T findClassAnnotation(Class<?> clazz,
			Class<? extends Annotation> ann) {
		if (clazz.isAnnotationPresent(ann)) {
			Annotation s = clazz.getAnnotation(ann);
			return (T) s;
		}
		return null;
	}
	
	 
    /**
     * 找到class类的某个方法上的某个Annotation
     * @param clazz
     * @param ann
     * @return
     */
	public static <T extends Annotation>Map<Method, T> findMethodAnnotation(
			Class<?> clazz, Class<? extends Annotation> ann) {
		Map result = new HashMap();
		for (Method m : clazz.getDeclaredMethods()) {
			if(m.isAnnotationPresent(ann)){
				result.put(m, m.getAnnotation(ann));
			} 
		}
		return result;
	}
    /**
     * 找到class类上的某个属性上的Annotation
     * @param clazz
     * @param ann
     * @return
     */
	
	public static <T extends Annotation>Map<Field, T> findFieldAnnotation(Class<?> clazz,Class<? extends Annotation> ann) {
		Map result = new HashMap();
		for (Field field : clazz.getDeclaredFields()) {
			if(field.isAnnotationPresent(ann)){
				result.put(field, field.getAnnotation(ann));
			} 
		}
		return result;
	}
	/**
	 * 获取属性上的Annotation
	 * @param field
	 * @param ann
	 * @return
	 */
	public static <T extends Annotation> T findFieldAnnotation(Field field,Class<? extends Annotation> ann) {
		if(field.isAnnotationPresent(ann)){
			return (T) field.getAnnotation(ann);
		} 
		return null;
	}
	
	/**
	 * 获取属性上的Annotation
	 * @param field
	 * @param ann
	 * @return
	 */
	public static <T extends Annotation> T findMethodAnnotation(Method method,Class<? extends Annotation> ann) {
		if(method.isAnnotationPresent(ann)){
			return (T) method.getAnnotation(ann);
		} 
		return null;
	}
 
}