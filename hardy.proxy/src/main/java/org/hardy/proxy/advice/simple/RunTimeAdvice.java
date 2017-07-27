package org.hardy.proxy.advice.simple;

import java.lang.reflect.Method;
import java.util.Date;

import org.apache.log4j.Logger;
import org.hardy.proxy.advice.Advice;
import org.hardy.proxy.pointjoint.PointJoint;

import net.sf.cglib.proxy.MethodProxy;

/**
 * 记录程序运行时间如果时间大于阀值,则记录并排序
 * @author song
 *
 */
public class RunTimeAdvice extends Advice{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final  Logger LOG = Logger.getLogger(RunTimeAdvice.class);
	@Override
	public Object around(PointJoint pj) {
		String className = pj.getClass().getName();
		String methodName = null;
		if(pj.getMethod() instanceof Method)methodName = ((Method)pj.getMethod()).getName();
		else if(pj.getMethod() instanceof MethodProxy)methodName = ((MethodProxy)pj.getMethod()).getSignature().getName();
		 long start = new Date().getTime();
		 Object result = super.around(pj);
		 long user_time = new Date().getTime()-start;
		 LOG.info(className+":"+methodName+",use ==>"+user_time+" ns");
		 System.out.println(className+":"+methodName+",use ==>"+user_time+" ns");
		 return result;
				 
	}

	
   
    
    
}
