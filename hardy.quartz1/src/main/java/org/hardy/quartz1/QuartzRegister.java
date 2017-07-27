package org.hardy.quartz1;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.hardy.quartz1.config.ScheduleConfig;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import com.song.tool.dom4jutil.XpathUtil;

/**
 */
public class QuartzRegister {
	
	  private Map<String,Scheduler> schedulers = new HashMap<String, Scheduler>() ;
	
      private Map<String, JobDetail>  jobDetailsMap = new HashMap<String, JobDetail>();
      
      private Map<String, Trigger>  triggersMap = new HashMap<String, Trigger>();
      
      private Map<String, ScheduleConfig>  groupsMap = new HashMap<String, ScheduleConfig>();
      
      @SuppressWarnings("unchecked")
	public void registerConfiguration(String... paths) throws ClassNotFoundException, ParseException{
	    for(String path:paths){
	    	Document document = XpathUtil.getDocument(path);
  			Element root = document.getRootElement();
  			//解析job的配置
  			List<Element> jobs = root.elements("jobs");
  			if(jobs!=null&&!jobs.isEmpty()){
  				 for(int i=0;i<jobs.size();i++){
  					List<Element> jobCollect =jobs.get(i).elements("job"); 
  					for(Element job:jobCollect){
  						String jobName = job.attributeValue("id");
  						String groupName = job.attributeValue("group");
  						String className = job.attributeValue("class");
  						Class<?> clazz = Class.forName(className);
  						JobDetail jobDetail = new JobDetail(jobName,groupName,clazz);
  						jobDetailsMap.put(jobName, jobDetail);
  					}
  				 }
  			}
  		   //解析triggers的配置
  			List<Element> triggers = root.elements("triggers");
  			if(triggers!=null&&!triggers.isEmpty()){
  				 for(int i=0;i<triggers.size();i++){
  					List<Element> triggerCollect =triggers.get(i).elements("trigger"); 
  					for(Element trigger:triggerCollect){
  						String triggerName = trigger.attributeValue("id");
  						String cronTime = trigger.attributeValue("cron");
  						String groupName = trigger.attributeValue("group");
  						CronTrigger triggerQ = new CronTrigger(triggerName, groupName, cronTime); 
  						triggersMap.put(triggerName, triggerQ);
  					}
  				 }
  			} 
  			//解析scheduler的配置
  			List<Element> schedulers = root.elements("schedulers");
  			if(triggers!=null&&!schedulers.isEmpty()){
  				 for(int i=0;i<schedulers.size();i++){
  					List<Element> schedulerCollect =schedulers.get(i).elements("scheduler"); 
  					for(Element scheduler:schedulerCollect){
  						String name = scheduler.attributeValue("name");
  						String job_id = scheduler.attributeValue("job_id");
  						String trigger_id = scheduler.attributeValue("trigger_id");
  						long timeOut = 0l;
  						if(scheduler.attributeValue("timeout")!=null)timeOut = Integer.parseInt(scheduler.attributeValue("timeout"));
  						int concurrent = 1;
  						if(scheduler.attributeValue("concurrent")!=null)concurrent = Integer.parseInt(scheduler.attributeValue("concurrent"));
  						boolean enabled = true;
  						if(scheduler.attributeValue("enabled")!=null)enabled = Boolean.parseBoolean(scheduler.attributeValue("enabled"));
  						ScheduleConfig sc = new ScheduleConfig();
  						sc.setConcurrent(concurrent);
  						sc.setEnabled(enabled);
  						sc.setJobDetail(jobDetailsMap.get(job_id));
  						sc.setTrigger(triggersMap.get(trigger_id));
  						sc.setTimeOut(timeOut);
  						groupsMap.put(name, sc);
  					}
  				 }
  			}
	    }
    	  			
      }
      
      public void start() throws SchedulerException{ 
    	   for(String name:groupsMap.keySet()){
    		   ScheduleConfig sc = groupsMap.get(name); 
    		   if(sc.isEnabled()){
    			   Scheduler scheduler = new StdSchedulerFactory().getScheduler();
      		 		scheduler.start();
    			   scheduler.scheduleJob(sc.getJobDetail(),sc.getTrigger());
    			   schedulers.put(name, scheduler);
    		   }
    	   }
      }
      
      public void shutdown() throws SchedulerException{
    	  for(Scheduler scheduler:schedulers.values()){
    		  scheduler.shutdown();
    	  }
      }
      
      public void start(String name) throws SchedulerException{
    	  ScheduleConfig sc = groupsMap.get(name); 
		  if(sc.isEnabled()){
			  Scheduler scheduler = new StdSchedulerFactory().getScheduler();
			  scheduler.start();
			  scheduler.scheduleJob(sc.getJobDetail(), sc.getTrigger());
			  schedulers.put(name, scheduler);
		  }
      }
      
      public void shutdown(String name) throws SchedulerException{
    	  schedulers.get(name).shutdown();
      }
      
      public String asDefaultConfig(){
    	  Document doc=DocumentHelper.createDocument();  
   	      Element ele1=doc.addElement("quartz");
   	      Element jobs = ele1.addElement("jobs");
   	      Element triggers = ele1.addElement("triggers");
   	      Element schedulers = ele1.addElement("schedulers");
   	      Element job = jobs.addElement("job"); 
   	      Element trigger = triggers.addElement("trigger"); 
   	      Element scheduler = schedulers.addElement("scheduler"); 
	   	   
	   	   job.addAttribute("class", "com.song");
	   	   job.addAttribute("group", "group name");
	   	   job.addAttribute("id", "job1");
	   	   trigger.addAttribute("cron", "*/5 * * * * ?");
		   trigger.addAttribute("group", "group name");
		   trigger.addAttribute("id", "tri1");
		   scheduler.addAttribute("concurrent","1");
		   scheduler.addAttribute("timeout","6000");
		   scheduler.addAttribute("trigger_id","tri1");
		   scheduler.addAttribute("job_id","job1");
		   scheduler.addAttribute("name","test1");
		   return doc.asXML();
//		   OutputFormat opf = new OutputFormat();
//		   opf.setEncoding("UTF-8");
//	    	 OutputFormat.createPrettyPrint();
//	    	 try {
//	    		XMLWriter writer=new XMLWriter(new FileOutputStream(new File(path)),opf);
//	    		writer.write(doc);
//	    		writer.close();
//	    	} catch (Exception e) {
//	    		// TODO Auto-generated catch block
//	    		e.printStackTrace();
//	    	}
			 
      }
      
       
}
 
