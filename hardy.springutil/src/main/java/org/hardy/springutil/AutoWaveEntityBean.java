package org.hardy.springutil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.hardy.springutil.AutoWaveBean.AutoWaveBeanTrait;
import org.hardy.statics.sprintutil.annotation.AutoEntityBean;


/**
 * 该类可设置扫描classpath下的指定包,
 * 然后扫描指定的type级别的AutoEntityBean,
 * 如果发现有AutoEntityBean注释就会将这个class动态设成Spring的bean
 * 每个接口处理一种annotation
 * @author song
 *
 */
public class AutoWaveEntityBean{
     
	  private AutoWaveBean awb ;
	  
	  private Map<String,AutoWaveBeanTrait> taiteNoyeus = new HashMap<String, AutoWaveBean.AutoWaveBeanTrait>();
	  
	  @SuppressWarnings("unchecked")
	public AutoWaveEntityBean(String[] packagesToScan) throws ClassNotFoundException, IOException{
		   taiteNoyeus.put(AutoEntityBean.class.getName(), awbt);
		   awb = new AutoWaveBean(packagesToScan, taiteNoyeus,AutoEntityBean.class);
	  }
	  
	  private static AutoWaveBeanTrait awbt = new AutoWaveBeanTrait() {
		
		@Override
		public void raiter(Class<?> c) {
  	      if(c.isAnnotationPresent(AutoEntityBean.class)){  
	    	   AutoEntityBean ab = c.getAnnotation(AutoEntityBean.class);
	    	   if(ab.name()!=null&&!"".equals(ab.name())){
	    		   BeanManager.addMongoToBeanFactory(c, ab.name());
	    	   }else{
	    	       BeanManager.addMongoToBeanFactory(c, c.getName());
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
