package org.hardy.montion.system.db.support;

import org.hardy.sqlhelper.annotation.structure.COLUMN;
import org.hardy.sqlhelper.annotation.structure.ID;
import org.hardy.sqlhelper.annotation.structure.Table;
import org.hardy.sqlhelper.annotation.variable.Result;

@Table(name="monitor_statistic")
public class MonitorStatistic {
	/**
	 * 从外部指定
	 */
	 @ID
	 private String uuid ;
	 @COLUMN
	 private String className;
	 @COLUMN
	 private String methodName;
	 @COLUMN
	 private String author;
	 @Result
	 @COLUMN
	 private Integer callNum;
	 @Result
	 @COLUMN
	 private Integer exceptionNum;

	 

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Integer getCallNum() {
		return callNum;
	}

	public void setCallNum(Integer callNum) {
		this.callNum = callNum;
	}

	public Integer getExceptionNum() {
		return exceptionNum;
	}

	public void setExceptionNum(Integer exceptionNum) {
		this.exceptionNum = exceptionNum;
	}

	@Override
	public String toString() {
		return "MonitorStatistic [uuid=" + uuid + ", className=" + className
				+ ", methodName=" + methodName + ", author=" + author
				+ ", callNum=" + callNum + ", exceptionNum=" + exceptionNum
				+ "]";
	}
	 
	 
}
