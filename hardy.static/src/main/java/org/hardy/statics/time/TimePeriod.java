package org.hardy.statics.time;

import java.util.Date;

/**
 * 时间周期类型
 * @author 09
 *
 */
public class TimePeriod {
	  /**
	   * 开始时间
	   */
	  private Date startTime;
	  /**
	   * 结束时间
	   */
	  private Date endTime;
	  
	  public TimePeriod() {}
	  
	  public TimePeriod(Date startTime, Date endTime)
	  {
	    this.startTime = startTime;
	    this.endTime = endTime;
	  }
	  
	  public Date getStartTime()
	  {
	    return this.startTime;
	  }
	  
	  public void setStartTime(Date startTime)
	  {
	    this.startTime = startTime;
	  }
	  
	  public Date getEndTime()
	  {
	    return this.endTime;
	  }
	  
	  public void setEndTime(Date endTime)
	  {
	    this.endTime = endTime;
	  }
	  
	  public String toString()
	  {
	    return "TimePeriod [startTime=" + this.startTime + ", endTime=" + this.endTime + "]";
	  }
}
