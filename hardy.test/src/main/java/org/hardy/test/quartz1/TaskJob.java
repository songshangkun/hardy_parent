package org.hardy.test.quartz1;

import org.hardy.timerhelper.Scheduled;
import org.hardy.timerhelper.SchedulerUtil;

public class TaskJob {
      private static int flag = 1;
      @Scheduled(schedule=2,initialDelay=500,period=2000,number=3)
	  public void print(){
		   System.out.println(flag);
		   flag++;
	  }
      
      public static void main(String[] args) throws InstantiationException, IllegalAccessException {
    	  SchedulerUtil su = new SchedulerUtil();
    	  su.execute(TaskJob.class);
	}
}
