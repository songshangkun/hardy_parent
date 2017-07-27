package org.hardy.montion.system.db.support;

import java.util.Date;

import org.hardy.sqlhelper.annotation.structure.COLUMN;
import org.hardy.sqlhelper.annotation.structure.ID;
import org.hardy.sqlhelper.annotation.structure.Table;



@Table(name="monitor_exception")
public class MonitorException {
	/**
	 * 
	 *从外部指定,关联ClassMethod的eid
	 **/
    @ID
	private String eid ;
	@COLUMN
	private String exceptionCause ;
	@COLUMN
	private Date callTime = new Date();

	

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getExceptionCause() {
		return exceptionCause;
	}

	public void setExceptionCause(String exceptionCause) {
		this.exceptionCause = exceptionCause;
	}

	public Date getCallTime() {
		return callTime;
	}

	public void setCallTime(Date callTime) {
		this.callTime = callTime;
	}

	@Override
	public String toString() {
		return "MonitorException [eid=" + eid + ", exceptionCause="
				+ exceptionCause + ", callTime=" + callTime + "]";
	}
	
	
}
