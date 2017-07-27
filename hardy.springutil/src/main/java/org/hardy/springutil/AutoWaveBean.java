package org.hardy.springutil;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 该类可设置扫描classpath下的指定包,
 * 然后扫描指定的type级别的annotation,
 * 并可植置入一个taiteNoyeus对象,该对象是一个
 * map，key是annotation的全名称，value是AutoWaveBeanTrait接口
 * 每个接口处理一种annotation
 * @author song
 *
 */
public class AutoWaveBean {
	private static Logger logger = LoggerFactory.getLogger(AutoWaveBean.class.getName());
	
	  private LoadPackageClasses loadPackageClass;
      
      @SuppressWarnings("unchecked")
	public AutoWaveBean(String[] packagesToScan,Map<String,AutoWaveBeanTrait> taiteNoyeus, Class<? extends Annotation>... annotationFilter) throws ClassNotFoundException, IOException{ 
    	  loadPackageClass = new LoadPackageClasses(packagesToScan, annotationFilter);
    	      Set<Class<?>> classet = loadPackageClass.getClassSet();
    	       if(annotationFilter!=null&&annotationFilter.length>0)
    	      for(Class<? extends Annotation> a:annotationFilter){
    	    	   if(classet!=null&&classet.size()>0)
	    	    	  for(Class<?> c:classet){
	    	    	      if(c.isAnnotationPresent(a)&&taiteNoyeus!=null){
	    	    	    	  AutoWaveBeanTrait awbt = taiteNoyeus.get(a.getName());
	    	    	    	   if(awbt!=null)awbt.raiter(c);
	    	    	    	   else 	logger.info("not found AutoWaveBeanTrait for " +  a.getName());
	    	    	      }
	    	      }
    	      }
	    	      
	  } 
	  public LoadPackageClasses getLoadPackageClass() {
		return loadPackageClass;
	}

	public void setLoadPackageClass(LoadPackageClasses loadPackageClass) {
		this.loadPackageClass = loadPackageClass;
	}

	public static interface AutoWaveBeanTrait{
		    public void raiter(Class<?> c);
	}
 
}
/**
 *     <bean class="com.song.ssh.springutil.SpringBeanUtil" />
        
        <bean class="com.song.ssh.springutil.AutoWaveEntityBean">  
		    <constructor-arg value="com.sundyn.blueSky.constant" />  
		    <constructor-arg> 
		       <map>................</map>
		    </constructor-arg>   
		    <constructor-arg>  
		        <list>  
		            <value>com.song.ssh.springutil.annotation.AutoEntityBean</value>  
		        </list>  
		    </constructor-arg>  
		</bean> 
**/
