package org.hardy.quartz1;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * quartz有线程池所以同一job如未运行完,可能会被其他线程调用
 * <br>为了解决同一时间值运行一次Job的需求
 * <br>1-使用spring设置属性concurrent=false
 * <br>2-使用StateFul接口
 * <br>3-2.0使用某个annotation
 * <br>4-使用这个OnceJob,它实现普通job接口，但含有一个全局静态变量。当运行时
 * <br>静态变量状态改变,所以在该类中的所有实际job方法体,当有一个正在运行则其余可以调用但都无法运行方法实体
 * @author song
 *
 */
public class OnceJob  implements Job{
    private static boolean isRun = false;
    private OnceJobDetail Detail; 
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		         if(!isRun){
		        	 	isRun = true;
		        	 		Detail.execute(context);
		        	 	isRun = false;
		         }
	}
	public OnceJobDetail getDetail() {
		return Detail;
	}
	public void setDetail(OnceJobDetail detail) {
		Detail = detail;
	}
	public static boolean isRun() {
		return isRun;
	}
	public static void setRun(boolean isRun) {
		OnceJob.isRun = isRun;
	}
	
	

}
