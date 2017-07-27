package org.hardy.montion.system.aop;

import java.lang.reflect.Method;
import java.util.Date;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.hardy.montion.system.annotation.MethodBug;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogMethod {
	  /**
	   * 是否system控制台输出
	   */
	  public static boolean consol = false;
	  /**
	   * 是否log4j记录
	   */
	  public static boolean log = false;
	  /**
	   * 是否使用数据库实现记录
	   */
	  public static SqlLog slog = null;
	  
	  
	  private static Logger logger = Logger.getLogger(LogMethod.class);
	
	  @Pointcut("@annotation(org.hardy.montion.system.annotation.MethodBug)")
	  public void annotationPoinCut(){}
	  
	  @Around("annotationPoinCut()")
	  public Object around(ProceedingJoinPoint joinPoint){
		    Object result = null;
		    MethodSignature signature = (MethodSignature)joinPoint.getSignature();
		    Method method = signature.getMethod();
		    MethodBug mbug = method.getAnnotation(MethodBug.class);		   
		    try {
		    	 if(consol){
					 System.out.println("method==>"+method.getName());
					 System.out.println("author==>"+mbug.author());
				 }
				 if(log){
					 logger.info("method==>"+signature.getMethod().getName());
					 logger.info("author==>"+mbug.author());
				 }
		    	 Long stratTime = new Date().getTime();
				 result =  joinPoint.proceed();
				 Long prentent = new Date().getTime() - stratTime;
				 if(consol){
					 System.out.println("use time==>"+(prentent/1000.00)+":s");
				 }
				 if(log){
					 logger.info("use time==>"+(prentent/1000.00)+":s");
				 } 
				 if(slog!=null)slog.recoder(joinPoint,prentent);
			} catch (Throwable e) {
				 if(consol){
					 System.out.println("exception==>"+e);
				 }
				 if(log){
					 logger.info("exception==>"+e);
				 }
				 if(slog!=null)slog.exception(joinPoint, e);
				e.printStackTrace();
			}finally {
				logger.info("finir==>"+method.getName());
			}
		    return result;
	  }
}
