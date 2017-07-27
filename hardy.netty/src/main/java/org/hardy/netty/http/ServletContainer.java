package org.hardy.netty.http;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.hardy.statics.web.ContentType;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class ServletContainer {
      /**
       * 用于记录bean名称,和bean的URL映射信息
       * <br>如果使用spring 且使用@AutoEntity,意味着使用了spring的bean。
       * <br>这是bean以北spring容器保存。不用再次保存spring的bean实例。只需保存bean的id引用
       * <br>如果没用使用spring或者没有使用@AutoEntity，则意味着直接使用反射调取所以需要在自定义容器sigltonBean中定义保存这些类名称和类实例
       * 
       */
	  private static Map<String, UrlMethodInfo> servletMap = new HashMap<>();
	  /**
	   * 用于记录bean名称,和bean实例
	   * 如果不用IOC key 类的全路径 value 类的实例
	   */
	  private static Map<String, Object> sigltonBean = new HashMap<>();
	  
	public static void addSingletoBean(String name, Object bean){
		sigltonBean.put(name, bean);
	}
	
	public static Object getSingletoBean(String name){
		   return sigltonBean.get(name);
	}

	public static Map<String, Object> getSigltonBean() {
		return sigltonBean;
	}

	public static void setSigltonBean(Map<String, Object> sigltonBean) {
		ServletContainer.sigltonBean = sigltonBean;
	}

	public static Map<String, UrlMethodInfo> getServletMap() {
		return servletMap;
	}

	public static void setServletMap(Map<String, UrlMethodInfo> servletMap) {
		ServletContainer.servletMap = servletMap; 
	}
	
	public static boolean containUrl(String url){
		  return ServletContainer.servletMap.containsKey(url);
	}
	
	public static boolean containSingletonBean(String beanName){
		  return ServletContainer.servletMap.containsKey(beanName);
	}
	
	/**
	 * 
	 * @param url  
	 * @param method
	 * @param beanName  
	 * @param requestMethods
	 * @param springBean
	 * @param clazzs
	 */
	 public static void addUrl(String url,Method method,String beanName,RequestMethod[] requestMethods,
			 boolean springBean,ContentType resultType,Class<?>[] clazzs){
		 if(servletMap.containsKey(url)) throw new RuntimeException("the url->"+url+" exsit 2 same url");
		 UrlMethodInfo urlMethodInfo = new UrlMethodInfo(beanName, requestMethods, springBean, method,resultType, clazzs);
		 servletMap.put(url, urlMethodInfo);
	 }
	  
	  public static  UrlMethodInfo getMethodByUrl(String url){
		     return servletMap.get(url);
	  }
	  /**
	   * 这个类主要用于记录与URL相关的bean名称,bean方法，访问方法等信息
	   * @author 09
	   *
	   */
	  public static class  UrlMethodInfo{
		  
		  public UrlMethodInfo() {
			super();
			// TODO Auto-generated constructor stub
		}

		public UrlMethodInfo(String beanName, RequestMethod[] requestMethods, boolean springBean, Method method,
				ContentType resultType,	Class<?>[] clazzs) {
			super();
			this.beanName = beanName;
			this.requestMethods = requestMethods;
			this.springBean = springBean;
			this.method = method;
			this.clazzs = clazzs;
			this.resultType = resultType;
		}

		/**
		   * si le springBean est vrai ,c'est à dire le beanName est un spring bean
		   * sinon on doit prendre le class utiler Class:forName;
		   */
		     private String beanName;
		     
		     private RequestMethod[] requestMethods;
		     
		     private boolean springBean = false;
		     
		     private Method method;
		     
		     private Class<?>[] clazzs;
		     
		     private ContentType resultType;
		     
		     

			public RequestMethod[] getRequestMethods() {
				return requestMethods;
			}

			public void setRequestMethod(RequestMethod[] requestMethods ){
				this.requestMethods = requestMethods;
			}

			public String getBeanName() {
				return beanName;
			}

			public void setBeanName(String beanName) {
				this.beanName = beanName;
			}

			public boolean isSpringBean() {
				return springBean;
			}

			public void setSpringBean(boolean springBean) {
				this.springBean = springBean;
			}

			public Method getMethod() {
				return method;
			}

			public void setMethod(Method method) {
				this.method = method;
			}

			public Class<?>[] getClazzs() {
				return clazzs;
			}

			public void setClazzs(Class<?>[] clazzs) {
				this.clazzs = clazzs;
			}
			
			
 
			public ContentType getResultType() {
				return resultType;
			}

			public void setResultType(ContentType resultType) {
				this.resultType = resultType;
			}

			public void setRequestMethods(RequestMethod[] requestMethods) {
				this.requestMethods = requestMethods;
			}

			@Override
			public String toString() {
				return "UrlMethodInfo [beanName=" + beanName + ", requestMethods=" + Arrays.toString(requestMethods)
						+ ", springBean=" + springBean + ", method=" + method + ", clazzs=" + Arrays.toString(clazzs)
						+ ", resultType=" + resultType + "]";
			}
 
	  }
	  
	  public static String[] getMethodParameterNamesByAsm4(final Method method) {  
	      Class<?> clazz = method.getDeclaringClass(); 
		  final Class<?>[] parameterTypes = method.getParameterTypes();  
	       if (parameterTypes == null || parameterTypes.length == 0) {  
	           return null;  
	       }  
	       final Type[] types = new Type[parameterTypes.length];  
	       for (int i = 0; i < parameterTypes.length; i++) {  
	           types[i] = Type.getType(parameterTypes[i]);  
	       }  
	       final String[] parameterNames = new String[parameterTypes.length];  
	       String className = clazz.getName();  
	       int lastDotIndex = className.lastIndexOf(".");  
	       className = className.substring(lastDotIndex + 1) + ".class";  
	       InputStream is = clazz.getResourceAsStream(className);  
	       try {  
	           ClassReader classReader = new ClassReader(is);  
	           classReader.accept(new ClassVisitor(Opcodes.ASM4) {  
	               @Override  
	               public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {  
	                   // 只处理指定的方法  
	                   Type[] argumentTypes = Type.getArgumentTypes(desc);  
	                   if (!method.getName().equals(name) || !Arrays.equals(argumentTypes, types)) {  
	                       return null;  
	                   }  
	                   return new MethodVisitor(Opcodes.ASM4) {  
	                       @Override  
	                       public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {  
	                    	   if(index<=method.getParameterTypes().length){
	                    		// 静态方法第一个参数就是方法的参数，如果是实例方法，第一个参数是this  
		                           if (Modifier.isStatic(method.getModifiers())) {  
		                               parameterNames[index] = name;  
		                           }  
		                           else if (index > 0) {  
		                               parameterNames[index - 1] = name;  
		                           }  
	                    	   }      
	                       }  
	                   };  
	 
	               }  
	           }, 0);  
	       } catch (IOException e) {  
	           e.printStackTrace();  
	       }  
	       return parameterNames;  
	   }  
}
