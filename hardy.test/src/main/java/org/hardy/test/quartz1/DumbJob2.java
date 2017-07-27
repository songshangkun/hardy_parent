package org.hardy.test.quartz1;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class DumbJob2 implements Job{
    
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
 
			System.out.println("zhengzaiyunixng222222");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		}
		
//		 String instName = context.getJobDetail().getName();
//	      String instGroup = context.getJobDetail().getGroup();
//	      JobDataMap dataMap = context.getJobDetail().getJobDataMap();
//	      String jobSays = dataMap.getString("jobSays");
//	      float myFloatValue = dataMap.getFloat("myFloatValue");
//	      ArrayList state = (ArrayList)dataMap.get("myStateData");
//	      state.add(new Date());
//	      System.err.println("Instance " + instName + " of DumbJob says: " +jobSays);
//	}

}
