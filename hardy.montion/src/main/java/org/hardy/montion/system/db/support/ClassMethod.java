package org.hardy.montion.system.db.support;

import java.util.UUID;

import org.hardy.sqlhelper.annotation.structure.COLUMN;
import org.hardy.sqlhelper.annotation.structure.ID;
import org.hardy.sqlhelper.annotation.structure.Table;

/**
 * 对应monitor_method表的实体类
 * @author 09
 *
 */
@Table(name="monitor_method")
public class ClassMethod {
	/**
	 * 从外部指定
	 */
      @ID
	  private String uuid ;
      @COLUMN
	  private String eid = UUID.randomUUID().toString();
	  @COLUMN
	  private String className;
	  @COLUMN
	  private String methodName;
	  @COLUMN
	  private String argJson;
	  
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
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
	public String getArgJson() {
		return argJson;
	}
	public void setArgJson(String argJson) {
		this.argJson = argJson;
	}
	@Override
	public String toString() {
		return "ClassMethod [uuid=" + uuid + ", className=" + className
				+ ", methodName=" + methodName + ", argJson=" + argJson + "]";
	}
	  
	  
}
