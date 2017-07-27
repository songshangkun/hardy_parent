package org.hardy.sqlhelper.enums;
/**
 * 操作类型枚举
 * @author songs
 *
 */
public enum OperatorType {
   
	 EQ("="),LIKE("LIKE"),LT("<"),LG("<="),GT(">"),GE(">="),
	 BETWEEN("BETWEEN"),IN("IN"),NULL("IS NULL"),NOTNULL("IS NOT NULL");
	 
	  private String val;
	  
	 private OperatorType(String val){
		  this.val = val; 
	  }

	public String getVal() {
		return " "+val+" ";
	}

	public void setVal(String val) {
		this.val = val;
	}
	  
	  
}
