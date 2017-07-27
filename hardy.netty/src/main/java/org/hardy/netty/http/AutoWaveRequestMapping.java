package org.hardy.netty.http;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.hardy.springutil.AutoWaveBean;
import org.hardy.springutil.AutoWaveBean.AutoWaveBeanTrait;
import org.hardy.springutil.BeanManager;
import org.hardy.statics.sprintutil.annotation.AutoEntityBean;
import org.hardy.statics.web.ContentType;


/**
 * 该类可设置扫描classpath下的指定包,
 * 然后扫描指定的type级别的AutoEntityBean,
 * 如果发现有@AutoEntityBean注释就会将这个class动态设成Spring的bean
 * 如果SpringBeanUtil支持
 * 如果不使用AutoEntityBean只使用@RequestMapping会使用java反射获取
 * @author song
 * 
 */
public class AutoWaveRequestMapping{
     
	  private AutoWaveBean awb ;
	  
	  private Map<String,AutoWaveBeanTrait> taiteNoyeus = new HashMap<String, AutoWaveBean.AutoWaveBeanTrait>();
	  
	  
	  @SuppressWarnings("unchecked") 
	public AutoWaveRequestMapping(String[] packagesToScan) throws ClassNotFoundException, IOException{
		   taiteNoyeus.put(RequestMapping.class.getName(), awbt);
		   taiteNoyeus.put(AutoEntityBean.class.getName(), awbt);
		   awb = new AutoWaveBean(packagesToScan, taiteNoyeus,
				   RequestMapping.class,
				   AutoEntityBean.class);
	  }
	  
	  private static AutoWaveBeanTrait awbt = new AutoWaveBeanTrait() {
		
		@Override
		public void raiter(Class<?> c) {
  	      if(c.isAnnotationPresent(RequestMapping.class)){  
  	      	RequestMapping mapping = c.getAnnotation(RequestMapping.class);
	    	         if(c.isAnnotationPresent(AutoEntityBean.class)){
	    	        	 AutoEntityBean aBean = c.getAnnotation(AutoEntityBean.class);
	    	        	  if(aBean.name()==null||aBean.name().equals("")) {
	    	        		    BeanManager.addMongoToBeanFactory(c, c.getName());   
	    	        		    addUrlInfo(c, mapping.value(), c.getName(), true);
	    	        	  }else{
	    	        		  BeanManager.addMongoToBeanFactory(c, aBean.name()); 
	    	        		  addUrlInfo(c, mapping.value(), aBean.name(), true);
	    	        	  }	 	    	        	   
	        	    }else{
	        	    	  try {
							Object obj = c.newInstance();
							ServletContainer.addSingletoBean(c.getName(), obj);
							addUrlInfo(c, mapping.value(), c.getName(), false);
						} catch (InstantiationException | IllegalAccessException e) {
							e.printStackTrace();
						}
	        	  }
	    	         
	         } 
    }
		
		private void addUrlInfo(Class<?> clazz,String parentUrl,String beanName,boolean springBean){
			  Method[] methods = clazz.getDeclaredMethods();
			  for(Method method:methods){
				  if(method.isAnnotationPresent(RequestMapping.class)){
					  RequestMapping mapping = method.getAnnotation(RequestMapping.class);
					  
					  String url = null;
					  if(parentUrl.equals("/")){
						   if(mapping.value().startsWith("/")) url = mapping.value();
						   else url = "/"+mapping.value();
					  }else{
						  if(!parentUrl.startsWith("/")) parentUrl = "/"+parentUrl;
						  if(parentUrl.endsWith("/")) parentUrl = parentUrl.substring(0, parentUrl.length()-1);
						  if(mapping.value().startsWith("/")) url = parentUrl+mapping.value();
						   else url = parentUrl+"/"+mapping.value();
					  }
					  ContentType resultType = mapping.resultType();
					  ServletContainer.addUrl(url, method, beanName, mapping.requestMethod(), springBean,resultType, method.getParameterTypes());
				  }
				 
			  }
		}
		  
 };

	public AutoWaveBean getAwb() {
		return awb;
	}

	public Map<String, AutoWaveBeanTrait> getTaiteNoyeus() {
		return taiteNoyeus;
	}

	public static AutoWaveBeanTrait getAwbt() {
		return awbt;
	} 
	
	
}
