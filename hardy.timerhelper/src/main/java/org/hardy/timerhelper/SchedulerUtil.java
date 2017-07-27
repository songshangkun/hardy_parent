package org.hardy.timerhelper;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.song.tool.reflex.RefletMethodUtil;

/**
 * 启动java Timer工具类,一个task使用一个线程,
 * 每个task是唯一的线程
 * @author song
 *
 */
public class SchedulerUtil {
	
	private static final Logger LOGGER = Logger.getLogger(SchedulerUtil.class);
	
	 private RunNumberSuffisant rns;
      
	 private Map<String,Timer> register = new HashMap<String, Timer>();
	 
	 
      public void execute(final Class<?> clazz) throws InstantiationException, IllegalAccessException{
    	       Object object = clazz.newInstance();
    	       execute(object);
      }
      /**
       * 执行业务调度
       * @param t
       */
	  public <T>void execute(final T t){
		  Class<?> clazz = t.getClass();
		  Method[] methods = clazz.getDeclaredMethods();
		  for(final Method m :methods){
			 if(m.isAnnotationPresent(Scheduled.class)){
				 if(m.getParameterTypes().length>0) 
				 throw new RuntimeException("scheduler.helper.timer.Scheduled "
				 		+ "not support method contains params");
				 final Scheduled scheduled = m.getAnnotation(Scheduled.class); 
				 final String timerName = (scheduled.name()!=null&&!"".equals(scheduled.name()))?
						 scheduled.name():clazz.getName()+"#"+m.getName();
				 final Timer timer = new Timer(timerName);
				 register.put(timerName, timer);
				  switch (scheduled.schedule()) {
				case 0:
					throw new RuntimeException("not implement scheduled.schedule = 0");
//					break;
				case 1:
					timer.schedule(new TimerTask() {	
						@Override
						public void run() {
							refletMethod(t, m.getName(), scheduled.abnormal(),timerName);	
						}
					}, scheduled.initialDelay());
					break;
				case 2:
					timer.schedule(new TimerTask() {
						int number = 0;
						@Override
						public void run() {
							refletMethod(t, m.getName(), scheduled.abnormal(),timerName);	
							number++; 
							if(rns!=null)rns.executeQuantNum(timerName,number);
							if(scheduled.number()!=0&&number>=scheduled.number())timer.cancel();
						}
						
					}, scheduled.initialDelay(), scheduled.period());
					break;
				case 3:
					timer.scheduleAtFixedRate(new TimerTask() { 
						int number = 0;
						@Override
						public void run() {
							refletMethod(t, m.getName(), scheduled.abnormal(),timerName);	
							number++;
							if(rns!=null)rns.executeQuantNum(timerName,number);
							if(scheduled.number()!=0&&number>=scheduled.number())timer.cancel();
						}
					},scheduled.initialDelay(), scheduled.period());
					break;
				default:
					break;
				}
			 } 
		  }
	  }
	  
	  
	  private void refletMethod(Object object,String name,boolean skipException,String taskName) {
		  if(skipException){
			  try{
				  RefletMethodUtil.callMethod(object, name,false); 
				}catch(Exception e){
					LOGGER.error("task error .... task name = ["+taskName+"]", e);
					e.printStackTrace();
				}
		  }else{
			  RefletMethodUtil.callMethod(object, name,false); 
		  }
		  
	}
	  
	  /**
	   * 结束业务调度
	   * @param clazz
	   * @param methodName
	   */
	  public void stopTask(Class<?> clazz,String methodName){
		   String taskName = clazz.getName()+"#"+methodName;
		   register.get(taskName).cancel();
	  }
	  /**
	   * 从注册中移除注册调度
	   * @param clazz
	   * @param methodName
	   */
	  public void unRegister(Class<?> clazz,String methodName){
		   String taskName = clazz.getName()+"#"+methodName;
		   register.remove(taskName);
	  }
	  
	 /**
	  * 设置RunNumberSuffisant接口,当执行此书到达某个
	  * <br>数字式调用接口
	  * @param rns
	  */
	public void setRns(RunNumberSuffisant rns) {
		this.rns = rns;
	}
	public RunNumberSuffisant getRns() {
		return rns;
	}

	 /**
	  * 递归调度执行某个任务,并将结果输入下次调度中
	  * @param params  初始参数
	  * @param date   初始日期
	  * @param jobDetail  具体方法接口
	  * @param period  周期 毫秒
	  */
	public static void execute(final Object params,final Date date,final Long period,final JobDetail jobDetail){
		  final Timer timer  = new Timer();
		   timer.schedule(new TimerTask() {
			@Override
			public void run() {
				  Object result =jobDetail.execute(params);
				   if(jobDetail.isContinu(result))execute(result,new Date(date.getTime()+period),period, jobDetail);
				   timer.cancel();
			}
		}, date);
	}
	
	 
	  
	   
}
