package org.hardy.springutil.webservice.terminal;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.hardy.springutil.webservice.Interceptor;
import org.hardy.springutil.webservice.MethodRepository;
import org.hardy.springutil.webservice.ParamJson;
import org.springframework.stereotype.Component;

@Component
@WebService(serviceName="webServiceRemoting")
public class WebServiceRemotingServer {
	 /**
	   * 自定义拦截器
	   */
	   private static List<Interceptor> interceptors = new ArrayList<Interceptor>();
      
	   public static List<Interceptor> getInterceptors() {
		return WebServiceRemotingServer.interceptors;
	}
	public static void setInterceptors(List<Interceptor> interceptors) {
		WebServiceRemotingServer.interceptors = interceptors;
	}
	    @WebMethod
	    public String getResult(@WebParam(name="params") String params) throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{  
	    	  ParamJson pj = new ParamJson(params); 
	    	  if(!WebServiceRemotingServer.interceptors.isEmpty()){
				   for(Interceptor interceptor:WebServiceRemotingServer.interceptors){
					    if(!interceptor.valider(pj.getParam("$token")))
					    	throw new RuntimeException("voutre request ne peux pas passer le interceptors "+interceptor.getClass().getName());
				   }
			  }
	    	  return MethodRepository.runMethodPourJson(pj);
	    }
}

/**
 * use method in spring.xml
 * <!-- web service -->
	<bean class="com.song.ssh.springutil.SpringBeanUtil" />
    <bean class="com.song.ssh.jsonhelper.extention.webservice.WebServiceRemotingServer" >
    </bean>  
        <bean class="com.song.ssh.springutil.AutoWaveMethod">    
		    <constructor-arg>  
		        <list>  
		            <value>com.sundyn.bluesky.service</value>  
		        </list>  
		    </constructor-arg>  
		</bean> 
		
	<bean class="org.springframework.remoting.jaxws.SimpleJaxWsServiceExporter" 
	    p:baseAddress="http://172.16.11.4:6001/" />
 * 
 * **/
