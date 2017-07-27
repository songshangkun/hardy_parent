package org.hardy.timerhelper;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//initialDelay
//@Scheduled(fixedDelay=5*60*1000) //5分钟
@Documented 
@Retention(RetentionPolicy.RUNTIME) 
@Target(ElementType.METHOD) 
public @interface Scheduled {
	/**
     * 以哪种方式运行
     * <br>0-cron 特定时间定时调度一直执行  
     * <br>1-延迟调度 只执行1次  schedule(task, delay)
     * <br>2-延迟周期调度  可控制执行次数   schedule(task, delay, period)
     * <br>3-精确周期调度  可控制执行次数  scheduleAtFixedRate(task, delay, period)
     * @return
     */
	 public byte schedule() ;
     /**
      * 初始运行时间
      * @return
      */
	  public long initialDelay() default 0;
	   
	  /**
	   * 运行间隔精确时间
	   * @return
	   */
	  public long period() default -1;
	  /**
	   * 运行次数 0为永久运行
	   * @return
	   */
	  public int number() default 0;
	  /**
	   * 使用Cron表达式
	   * @return
	   */
	  public String cron() default "NULL";
	  /**
	   * 确定时区
	   * @return
	   */
	  public String zone() default "LOCAL";
	  /**
	   * abnormal=false 不跳过异常 abnormal=true 可跳过异常 
	   * @return
	   */
	  public boolean abnormal() default false;
	  /**
	   * 标记task的name
	   * @return
	   */
	  public String name() default  "";
}
