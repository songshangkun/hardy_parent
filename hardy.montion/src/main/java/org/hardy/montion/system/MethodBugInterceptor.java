package org.hardy.montion.system;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;

/**
 * 这个类实现MethodInterceptor接口,捕捉方法并记录异常。
 * 因为他是spring实现的方法拦截所以要在spring配置中将其配置为AOP
 * xmlns:aop="http://www.springframework.org/schema/aop" 
 * http://www.springframework.org/schema/aop 
 * http://www.springframework.org/schema/aop/spring-aop-4.0.xsd"
 * 其中必须设置MonitorInterceptor monitor参数否则无任何功能
 * <br>bean id="methodBugInterceptor" class="www.song.monitor.system.MethodBugInterceptor" 
	<br>aop:config
	  <br>aop:pointcut id="methodBugInterceptorPointcut" 
	   		<br>expression="execution(* org.bluesky_dao.mapper.*.*(..))" 
	  <br> aop:advisor pointcut-ref="methodBugInterceptorPointcut" advice-ref="methodBugInterceptor"  
	<br>aop:config
 * @author 09
 *
 */
public class MethodBugInterceptor implements MethodInterceptor{
    
	private static final Logger LOG = Logger.getLogger(MethodBugInterceptor.class);
	
	private MonitorInterceptor monitor;
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object obj = null;
		  Object[] args = invocation.getArguments();
		  Method method = invocation.getMethod();
		  Class<?> clazz = method.getDeclaringClass();
		  LOG.info("class:"+clazz.getName()+";method:"+method.getName());
		  LOG.info("args="+Arrays.toString(args)); 
		  String argsJson = JSONArray.toJSONString(args); 
		  try{
			  obj = invocation.proceed();
			  if(monitor!=null)monitor.recoderExecuteResult(clazz,method,argsJson);
		  }catch(Exception e){
			  if(monitor!=null)monitor.recoderException(clazz, method, argsJson, e);
			  throw e;
		  }
		 
		 return obj;
	}

	public MonitorInterceptor getMonitor() {
		return monitor;
	}

	public void setMonitor(MonitorInterceptor monitor) {
		this.monitor = monitor;
	}
	
	

}

/**
 * 
 * 在spring中使用以下方法配置
 * <bean id="methodBugInterceptor" class="www.song.monitor.system.MethodBugInterceptor" />
	<aop:config>
	   <aop:pointcut id="methodBugInterceptorPointcut" 
	   		expression="execution(* org.bluesky_dao.mapper.*.*(..))" />
	   <aop:advisor pointcut-ref="methodBugInterceptorPointcut" advice-ref="methodBugInterceptor" /> 
	</aop:config>
 * 
 * **/
