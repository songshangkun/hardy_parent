package org.hardy.sqlhelper.enums;

/**
 * 定义常用数据类型
 * length＝0表示数据类型不需要长度
 * length＝10表示数据类型长度是10
 * decimal＝3 表示小数部分长度定义3
 * 当定义decimal时，该长度包含在length长度之内，即length＝10，decimal＝3表示
 * 整数部分是7，小数部分是3
 * @author song
 *
 */
public enum DataType {
     
	   VARCHAR(50),INT(8),BIGINT(20),DATETIME,DATE,TIME,
	   LONGTEXT,TEXT,BIT(1),FLOAT(8,3),DOUBLE(12,6),BLOB,
	   TINYBLOB,TINYTEXT,MEDIUMTEXT,TIMESTAMP,ENUM;
	   
	    private int length;
	    
	    private int decimal;
	    
	    private DataType(){}
	    
	    private DataType(int defaultLength){
		     this.length = defaultLength; 
	   }
	    
	    private DataType(int defaultLength,int defaultdecimal){
		     this.length = defaultLength;
		     this.decimal = defaultdecimal;
	   }

		public int getLength() {
			return length;
		}
		public void setLength(int length) {
			this.length = length;
		}
  
		public int getDecimal() {
			return decimal;
		}

		public void setDecimal(int decimal) {
			this.decimal = decimal;
		}
	    
	
}
