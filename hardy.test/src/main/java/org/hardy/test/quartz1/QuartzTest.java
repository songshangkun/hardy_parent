package org.hardy.test.quartz1;

import java.text.ParseException;

import org.hardy.quartz1.QuartzRegister;
import org.quartz.SchedulerException;


public class QuartzTest {
 public static void main(String[] args) throws SchedulerException, ClassNotFoundException, ParseException {
//	 try { 
//		 Scheduler scheduler = new StdSchedulerFactory().getScheduler();
//		 	scheduler.start();
//		 	JobDetail jobDetail = new JobDetail("myJob",null,DumbJob.class);
////		 	jobDetail.getJobDataMap().put("jobSays", "Hello World!");
////		 	jobDetail.getJobDataMap().put("myFloatValue", 3.141f);
////		 	jobDetail.getJobDataMap().put("myStateData", new ArrayList());
//		 	Trigger trigger = TriggerUtils.makeMinutelyTrigger(); //每个小时激活一次
//		 	trigger.setStartTime(TriggerUtils.getEvenMinuteDate(new Date()));//在下一个
//		 	trigger.setName("myTrigger");
//		 	scheduler.scheduleJob(jobDetail, trigger);
////	        scheduler.shutdown();
//	} catch (SchedulerException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
	 
	   QuartzRegister qr = new QuartzRegister();
	   System.out.println(qr.asDefaultConfig());;
//	   qr.registerConfiguration("C:/Users/09/Desktop/quartz");
//	   qr.start();
}
}
