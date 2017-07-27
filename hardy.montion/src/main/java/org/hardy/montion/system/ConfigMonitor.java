package org.hardy.montion.system;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hardy.montion.system.annotation.MethodBug;
import org.hardy.montion.system.annotation.Monitor;
import org.hardy.springutil.AutoWaveBean;
import org.hardy.springutil.AutoWaveBean.AutoWaveBeanTrait;

 
/**
 * 作者签名和解释的封装
 * @author ssk
 *
 */
class AuthorDesc{
	 
	public AuthorDesc(String author, String description) {
		super();
		this.author = author;
		this.description = description;
	}

	public String author;
	
	public String description;
 
	@Override
	public String toString() {
		return "AuthorDesc [author=" + author + ", description=" + description
				+ "]";
	}
	
	
}

/**
 * 这个类主要用于设置读取方法签名和运行注册方法
 * @author ssk
 *
 */
public class ConfigMonitor {
   

	  private static Map<Class<?>,Map<Method,AuthorDesc>> registeMethods = new HashMap<>();
	  
	  private static Map<String, AutoWaveBean.AutoWaveBeanTrait> map = new HashMap<String, AutoWaveBean.AutoWaveBeanTrait>();
	  
	  private static AutoWaveBeanTrait methodsAbt = new AutoWaveBeanTrait() {
		@Override
		public void raiter(Class<?> clazz) {
			String clAuthor = null;
			String clDescip = null;
			if(clazz.isAnnotationPresent(MethodBug.class)){
				MethodBug mbc = clazz.getAnnotation(MethodBug.class);
				clAuthor = mbc.author();
				clDescip = mbc.description();
			}
			 for(Method method:clazz.getDeclaredMethods()){
				 if(method.isAnnotationPresent(MethodBug.class)){
					 MethodBug mbm = method.getAnnotation(MethodBug.class);
					 String meAuthor = mbm.author();
					 String meDescip = mbm.description();
					 if(!registeMethods.containsKey(clazz)){
						 Map<Method,AuthorDesc> mma = new HashMap<Method, AuthorDesc>();
						 mma.put(method, new AuthorDesc(meAuthor, meDescip));
						 registeMethods.put(clazz, mma);
					 }else{
						 registeMethods.get(clazz).put(method, new AuthorDesc(meAuthor, meDescip)); 
					 }
				 }else if(clAuthor!=null){
					 if(!registeMethods.containsKey(clazz)){
						 Map<Method,AuthorDesc> mma = new HashMap<Method, AuthorDesc>();
						 mma.put(method, new AuthorDesc(clAuthor, clDescip));
						 registeMethods.put(clazz, mma);
					 }else{
						 registeMethods.get(clazz).put(method, new AuthorDesc(clAuthor, clDescip)); 
					 }
				 }
			 }
			
   
		}
	};
	/**
	 * 注册包中的全部类
	 * @param classLoaders
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	  @SuppressWarnings("unchecked")
	public static void registerMethod(String... classLoaders) throws ClassNotFoundException, IOException{
		  map.put("www.song.monitor.system.annotation.Monitor", methodsAbt);
		  new AutoWaveBean(classLoaders, map, Monitor.class);  
	  }
	  /**
	   * 获取作者和解释
	   * @param clazz
	   * @param method
	   * @return
	   */
	  public static String[] getAuthorDescrip(Class<?> clazz,Method method){
		    if(registeMethods.containsKey(clazz)&&registeMethods.get(clazz).containsKey(method)) 
		    return new String[]{registeMethods.get(clazz).get(method).author,registeMethods.get(clazz).get(method).description};
		    else return null;
	  }
	  /**
	   * 获取注册的全部类(class类型)-方法(Method类型)-作者-解释
	   * @return
	   */
	  public static List<Object[]> getAuthorDescrip(){
		    List<Object[]> list = new ArrayList<Object[]>();
		    for(Class<?> clazz:registeMethods.keySet()){
		    	 for(Method method:registeMethods.get(clazz).keySet()){
		    		 Object[] cmad = new Object[]{clazz,method,
		    				  registeMethods.get(clazz).get(method).author,
		    				  registeMethods.get(clazz).get(method).description};
		    		  list.add(cmad);
		    	 }
		    }
		    return list;
	  }
	
//	  public static void main(String[] args) throws ClassNotFoundException, IOException {
//		  registerMethod("www.song.monitor.system.test");
//	}
	 
}
 
