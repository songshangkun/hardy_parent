package org.hardy.sqlhelper.holographic.translation.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

import org.hardy.reflex.SpringReflext;
import org.hardy.sqlhelper.annotation.structure.COLUMN;
import org.hardy.sqlhelper.annotation.structure.ID;
import org.hardy.sqlhelper.annotation.structure.Table;
import org.hardy.sqlhelper.holographic.HologResolve;
import org.hardy.sqlhelper.holographic.TableConstruct;
import org.hardy.sqlhelper.holographic.TableConstruct.PropColumn;
import org.hardy.util.objectutil.ObjectUtil;

import com.song.tool.reflex.RefletClazzUtil;
import com.song.tool.reflex.RefletMethodUtil;

/**
 * 一个为了简化使用mybatisHelper时，初始化POJO对象的方法
 * 
 * @author sundyn
 *
 */
public class PojoUtil {
      /**
       * 清除对象setter方法涉及到的所有属性
       * @param obj
       */
	  public static void initPojoAllNull(Object obj){
		   Class<?> clazz = obj.getClass();
		   Method[] methods = clazz.getMethods();
		   for(Method method:methods){
			    if(method.getName().startsWith("set")){
			    	RefletMethodUtil.callMethod(obj, method.getName(), new Object[]{null}, method.getParameterTypes());
			    }
			    
		   }
	  }
	  /**
	   * 清除使用Annotation(@Id,@Column)的所有属性
	   * @param obj
	   */
	  public static void initPojoAllNullAnnotation(Object obj){
		   Class<?> clazz = obj.getClass();
		   Set<Class<?>> set = RefletClazzUtil.allSupperClass(clazz);
		   for(Class<?> cl:set){
			   if(cl.isAnnotationPresent(Table.class)){
				   Field[] fs = cl.getDeclaredFields();
				   for(Field f:fs){
					   if(f.isAnnotationPresent(ID.class)||f.isAnnotationPresent(COLUMN.class)){
						   SpringReflext.set(obj, f.getName(), null);
					   }
					    
				   }
			   }
			  
		   }
		  
	  }
  
	  /**
	   * 只copy @ID 和 @COLUMN 标注的属性,并产生新的对象
	   * @param from
	   * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	   */
	  @SuppressWarnings("unchecked")
	public static <T>T copyPropForMainAnnotation(HologResolve hr,T from) throws InstantiationException, IllegalAccessException {
		   Class<T> clazz = (Class<T>) from.getClass();
		   TableConstruct tConstruct = hr.getStruct(clazz);
		   int n = 0;
		   PropColumn pColumn = tConstruct.getId();
		   if(pColumn!=null){
			   n++;
		   }
		   String[] propNames = new String[n+tConstruct.getColumns().length];
		   for(int i=0;i<propNames.length;i++){
			   if(pColumn!=null){
				    if(i==0)propNames[i] = pColumn.getPropName();
				    else propNames[i] = tConstruct.getColumns()[i-1].getPropName();
			   }else{
				   propNames[i] = tConstruct.getColumns()[i].getPropName();
			   }
			 
		   }
		   return (T)ObjectUtil.copyProperties(from, propNames, clazz, propNames);
		   
	  }
}
