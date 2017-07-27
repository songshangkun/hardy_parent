package org.hardy.sqlhelper.enums;

/**
 * SQL 连接关系符
 * @author song
 *
 */
public enum LinkedType {
   
	  AND("AND"),OR("OR");
	  
	  private String stringVal;
	
	  private LinkedType(String stringVal){
		   this.stringVal = stringVal;
	  }
	  
	  public String val(){
		   return " "+this.stringVal+" ";
	  }
}
