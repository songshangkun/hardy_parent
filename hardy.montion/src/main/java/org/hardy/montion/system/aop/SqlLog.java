package org.hardy.montion.system.aop;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 专用于LogMethod切面的接口
 * @author songshangkun
 *
 */
public interface SqlLog {
      /**
       * 具体实现接口
       * @param joinPoint
       * @param pentent 使用时间
       */
	   public void recoder(ProceedingJoinPoint joinPoint,long pentent);
	   /**
	    * 
	    * @param joinPoint
	    * @param e
	    */
	   public void exception(ProceedingJoinPoint joinPoint,Throwable e);
}
