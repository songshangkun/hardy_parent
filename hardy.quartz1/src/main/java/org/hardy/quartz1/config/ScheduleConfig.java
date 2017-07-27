package org.hardy.quartz1.config;

import org.quartz.JobDetail;
import org.quartz.Trigger;

public class ScheduleConfig {
     private JobDetail jobDetail;
     private Trigger trigger;
     private long timeOut;
     private int concurrent;
     private boolean enabled;
	public JobDetail getJobDetail() {
		return jobDetail;
	}
	public void setJobDetail(JobDetail jobDetail) {
		this.jobDetail = jobDetail;
	}
	public Trigger getTrigger() {
		return trigger;
	}
	public void setTrigger(Trigger trigger) {
		this.trigger = trigger;
	}
	public long getTimeOut() {
		return timeOut;
	}
	public void setTimeOut(long timeOut) {
		this.timeOut = timeOut;
	}
	public int getConcurrent() {
		return concurrent;
	}
	public void setConcurrent(int concurrent) {
		this.concurrent = concurrent;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	@Override
	public String toString() {
		return "ScheduleConfig [jobDetail=" + jobDetail + ", trigger="
				+ trigger + ", timeOut=" + timeOut + ", concurrent="
				+ concurrent + ", enabled=" + enabled + "]";
	}
     
     
	 
     
}
