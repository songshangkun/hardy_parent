package org.hardy.texthelper;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 这是一个字符转化工具类，
 * convert(String str)将字符串转成unicode，
 * revert(String str)将unicode转成正常字符串，
 * 还有数字类型转化字符串与二进制，16进制
 * 
 * ***/
public class StringConvertor {
	/** 
	 * <h1>将字符串转成 unicode字符串</h1> 
	 * <hr>
	 * @param str 待转字符串 
	 * @return unicode字符串 
	 */ 
	 public static String convert(String str) 
	 { 
	 str = (str == null ? "" : str); 
	 String tmp; 
	 StringBuffer sb = new StringBuffer(1000); 
	 char c; 
	 int i, j; 
	 sb.setLength(0); 
	 for (i = 0; i < str.length(); i++) 
	 { 
	 c = str.charAt(i); 
	 sb.append("\\u"); 
	 j = (c >>>8); //取出高 8位 
	 tmp = Integer.toHexString(j); 
	 if (tmp.length() == 1) 
	  
	 sb.append("0"); 
	 sb.append(tmp); 
	 j = (c & 0xFF); //取出低 8位 
	 tmp = Integer.toHexString(j); 
	 if (tmp.length() == 1) 
	 sb.append("0"); 
	 sb.append(tmp); 
	 
	 } 
	 return (new String(sb)); 
	 }  
	 /** 
	 * 将 unicode 字符串 转化为 正常字符串 
	 * @注意 在转化是需要将要转化的字符串酒家一个空格,否则转化后的字符串将少一个字符
	 * @param str 待转字符串 
	 * @return 普通字符串 
	 */ 
	 public static String revert(String str) 
	 { 
	 str = (str == null ? "" : str); 
	 if (str.indexOf("\\u") == -1)//如果不是 unicode码则原样返回 
	  return str; 
	  
	   StringBuffer sb = new StringBuffer(1000); 
	   
	   for (int i = 0; i < str.length() - 6;) 
	   { 
	    String strTemp = str.substring(i, i + 6); 
	    String value = strTemp.substring(2); 
	    int c = 0; 
	    for (int j = 0; j < value.length(); j++) 
	    { 
	     char tempChar = value.charAt(j); 
	     int t = 0; 
	     switch (tempChar) 
	     { 
	      case 'a': 
	      t = 10; 
	      break; 
	      case 'b': 
	      t = 11; 
	      break; 
	      case 'c': 
	      t = 12; 
	      break; 
	      case 'd': 
	      t = 13; 
	      break; 
	      case 'e': 
	      t = 14; 
	      break; 
	      case 'f': 
	      t = 15; 
	      break; 
	      default: 
	      t = tempChar - 48; 
	      break; 
	      } 
	     
	    c += t * ((int) Math.pow(16, (value.length() - j - 1))); 
	    } 
	   sb.append((char) c); 
	   i = i + 6; 
	   } 
	  return sb.toString(); 
	 }
	 /**
	  *  @说明 将sourceUrl转化为url的String
	  *  在网上传输中文时会碰到在url地址传参数是发生乱码
	  *  如: XXX/xxx?path=${sourcePath} 这里的sourcePath如果包含中文则会乱码
	  *  如果参数直接是中文我们可以使用javascript的
	  *  UserVerify?userName=" + encodeURI(encodeURI(userName))；
	  *  如果参数是class类传来的属性可使用此方法转换
	  *  @注意 此方法中使用encode两次才能转换，但解码时却只解一次。
	  * @param sourceUrl 未编码的字符串
	  * @param codeType 编码字符集 一般为UTF-8，如果中文也可能是GBK 需要与解码方法字符集对应
	  * @return  String 编码后的字符串
	  */
	 public static String StringToUrlCode(String sourceUrl,String codeType){
		 try {
			return URLEncoder.encode(URLEncoder.encode(sourceUrl,codeType),codeType);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sourceUrl;
	 }
	 
	 /**
	  *  @说明 将url的String解码
	  *  在网上传输中文时会碰到在url地址传参数是发生乱码
	  *  如: XXX/xxx?path=${sourcePath} 这里的sourcePath如果包含中文则会乱码
	  * 所以传输时会编码，此方法用于对编码的code解码  decode。
	  *  @注意 此方法中编码使用encode两次才能转换，但解码时却只解一次。
	  * @param urlCode 编码后的字符串
	  * @param codeType 编码字符集 一般为UTF-8，如果中文也可能是GBK
	  *  需要与编码方法字符集对应
	  * @return  String 还原的字符串
	  */
	 public static String UrlCodeToString(String urlCode,String codeType){
		 try {
			return URLDecoder.decode(urlCode,codeType);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return urlCode;
		
	 }
	 /**
	  * 这个方法类似Integer.toBinaryString(i);
	  * 他可以将2转化为10,8转化为1000,2转化1100
	  * 这个方法可以定义一个长度，为了将空位补充为0.
	  * 如binaryString(5,2),2转化为00010,
	  * binaryString(10,8)8转化为0000001000.
	  * @param n 要显示的位数
	  * @param j 要转化的数字
	  * **/
	 public static String binaryString(int n,int j)
	 { 
		 StringBuffer sb = new StringBuffer(); 
		 String r1 = Integer.toBinaryString(j);
		 for(int i=0;i<n-r1.length();i++){
			sb.append("0");
		 }
		 sb.append(r1);
		 return sb.toString();
	 }
	 
	 /**
	  * 将字符串转化为字符数组的数字 ,对于‘/’将转成47
	  * @param name 要转换字符串
	  * @param c 分隔符号  ex:'/',',','*','!','~' .....
	  * @return
	  */
	public static String changStringToNum(String name,char c){
  	   StringBuffer sb=new StringBuffer("");
    	   char[] chars=name.toCharArray();
    	   for(int i=0;i<chars.length;i++){
    		 int j= (int)chars[i]; 
    		 
    		 sb.append(j);
    		 sb.append((int)c);
    	   }
    	   return sb.toString();
     }
     
 	 /**
 	  * 将数字转回字符串   
 	  * @注意 可能产生错误  比如 86 46 两个字符连起来变为8 64 6 
 	  * @param name 要转换字符串
	  * @param c 分隔符号  ex:'/',',','*','!','~' .....
 	  * @return
 	  */
 	 public static String changNumToString(String num,char c){
 		System.out.println((int)c);
   	   String[] nums = num.split(String.valueOf((int)c));
   	   StringBuffer sb=new StringBuffer("");
     	   for(int i=0;i<nums.length;i++){
     		 char j= (char)Integer.parseInt(nums[i]); 
     		 sb.append(j);
     	   }
     	   return sb.toString();
      }
 	 
 	/**
 	 * 此方法避免文件名过长  但无法反编文件名 
 	 * @param name
 	 * @return
 	 */
     public static String changStringToNum(String name){
   	  StringBuffer sb=new StringBuffer("");
     	   char[] chars=name.toCharArray();
     	   for(int i=0;i<chars.length;i++){
     		 int j= (int)chars[i]; 
     		 sb.append(j);
     	   }     	    
     	   return sb.toString();         
    } 
}
