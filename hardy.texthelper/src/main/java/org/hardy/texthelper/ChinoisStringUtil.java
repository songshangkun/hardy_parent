package org.hardy.texthelper;

import com.song.tool.text.string.StringLocalUtil;
/**
 * 对于netty传输中文参数对系统有影响,
 * 这里将中文按自定义格式编译成\数字形式
 * 并可进行反编.
 * @author 09
 *
 */
public class ChinoisStringUtil {
	 
	  /**
	   * 按照自定义格式将名称编码成数字字母组成的名称
	   * @param orginal
	   * @return
	   */
	  public static String encode(String orginal,char delim){
		  if(orginal.indexOf(delim)!=-1)throw new RuntimeException(orginal+" not contains \\");
		  StringBuilder sb = new StringBuilder();
		  char[] cs = orginal.toCharArray();
		  for(int i=0;i<cs.length;i++){
			  if(StringLocalUtil.isChinoisChar(cs[i]))
			  sb.append(delim).append((short)cs[i]).append(delim);
			  else sb.append(cs[i]);
		  }
		  return sb.toString();
	  }
	  /*
	   * 按照自定义格式将编码成数字字母组成的名称解码成原始名称
	   */
	  public static String decode(String encodeString,char delim){
		  boolean start = false;
		  StringBuilder inner = new StringBuilder();
		  StringBuilder sb = new StringBuilder();
		  char[] cs = encodeString.toCharArray();
		  for(int i=0;i<cs.length;i++){
			  if(cs[i]==delim&&!start){
				  start = true;
				  inner.setLength(0);
			  }else if(cs[i]==delim&&start){
				  start = false;
				  sb.append((char)Integer.parseInt(inner.toString()));
			  }
			  if(start&&cs[i]!=delim){
				  inner.append(cs[i]);
			  }else if(!start&&cs[i]!=delim){
				  sb.append(cs[i]);
			  }
		  }
		  return sb.toString();
	  }
	  
	  
	  
}
