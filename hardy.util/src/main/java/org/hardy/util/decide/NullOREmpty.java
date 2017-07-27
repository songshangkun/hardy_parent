package org.hardy.util.decide;

public class NullOREmpty {
     /**
      * 判断字符串是否为空或者是空字符,如果是返回true,否则false
      * @param value
      * @return
      */
	  public static boolean isEmptyString(String value){
		   if(value==null) return true;
		   if(value.trim().equals(""))return true;
		   return false;
	  }
	  
	  /**
	   * 如果字符串是空或者空字符返回NULL,否则返回字符串的值
	   * @param value
	   * @return
	   */
	  public static String returnStringValue(String value){
		   if(value==null) return null;
		   if(value.trim().equals(""))return null;
		   return value;
	  }
}
