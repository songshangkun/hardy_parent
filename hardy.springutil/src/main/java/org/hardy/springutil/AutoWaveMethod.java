package org.hardy.springutil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.hardy.springutil.AutoWaveBean.AutoWaveBeanTrait;
import org.hardy.springutil.webservice.MethodRepository;
import org.hardy.statics.sprintutil.annotation.RegistMethod;


 


public class AutoWaveMethod {
      
	 private AutoWaveBean awb ;
	  
	  private Map<String,AutoWaveBeanTrait> taiteNoyeus = new HashMap<String, AutoWaveBean.AutoWaveBeanTrait>();
	  
	  @SuppressWarnings("unchecked")
	public AutoWaveMethod(String[] packagesToScan) throws ClassNotFoundException, IOException{
		 
		   taiteNoyeus.put(RegistMethod.class.getName(), awbt);
		   awb = new AutoWaveBean(packagesToScan, taiteNoyeus,RegistMethod.class);
	  }
	  
	  private static AutoWaveBeanTrait awbt = new AutoWaveBeanTrait() {
		
		@Override
		public void raiter(Class<?> c) {  
			 Method[] methods = c.getDeclaredMethods();
			 for(Method m:methods){  
				 //如果方法标有RegistMethod
				 if(m.isAnnotationPresent(RegistMethod.class)){   
					 //查看是否标注特别的名称id
					 RegistMethod rme = m.getAnnotation(RegistMethod.class);  
		 	    	    	  if(rme.value()!=null&&!"".equals(rme.value())){  
		 	    	    		  if(MethodRepository.containsId(rme.value()))
		 	    	    			   throw new RuntimeException(" method "+m.getName()+"nomer "+rme.value()+" dans "+c.getName()+" deja exsite dans le MethodRepository ");
		 	    	    		  else  MethodRepository.register(rme.value(), m,rme.bean_name(),rme.descrip(),c.getName()+" "+m.getName());	  
		 	  	    	   }else{
		 	  	    		  if(MethodRepository.containsId(c.getName()+"$"+m.getName()))
		    	    			   throw new RuntimeException(" method "+m.getName()+" dans "+c.getName()+" est duplicate Method,pour metter dans le MethodRepository ,vous pouvez nomer un autre nom");
		 	  	    		  else MethodRepository.register(c.getName()+"$"+m.getName(), m,rme.bean_name(),rme.descrip(),c.getName()+" "+m.getName());
		 	  	      }
				 }else if(c.isAnnotationPresent(RegistMethod.class)){
					 RegistMethod rm = c.getAnnotation(RegistMethod.class);
					 if("ALL".equalsIgnoreCase(rm.value())){
						 if(MethodRepository.containsId(c.getName()+"$"+m.getName()))
		  	    			   throw new RuntimeException(" method "+m.getName()+" dans "+c.getName()+" est duplicate Method,pour metter dans le MethodRepository ,vous pouvez nomer un autre nom");
			  	    		  else MethodRepository.register(c.getName()+"$"+m.getName(), m,rm.bean_name(),rm.descrip(),c.getName()+" "+m.getName());
					 }
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
