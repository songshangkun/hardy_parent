package org.hardy.texthelper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringLocalUtil {
   /**
    *  使用字符数字区间判断字符是否为中文
    * @param ch
    * @return
    */
	public static boolean isChinoisChar(char ch){   
	      return (ch >= 19968) && (ch <= 40869);  
	  }
	/**
	 * 检测字符串是否含有制定的char字符
	 * @param s
	 * @param b
	 * @param cs
	 * @return
	 */
	public static boolean hasSpecial(String s,boolean hasChinise,char... ponctuation ){
		char[] cs = s.toCharArray();
		for(char c:cs){
			if(hasChinise&&(c >= 19968) && (c <= 40869)) return true; 
			for(char c1:ponctuation){
				if(c==c1) return true;
			}
		}
		 return false;
		 
	}
	/**
	    *  使用UnicodeBlock方法判断字符是否为中文
	    * @param ch
	    * @return
	    */
    public static boolean isChineseCharByBlock(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT) {
            return true;
        }  
            return false;
     
    }
    /**
     *  使用UnicodeScript方法判断 字符是否为中文
     * @param ch
     * @return
     */
    public static boolean isChineseCharByScript(char c) {
        Character.UnicodeScript sc = Character.UnicodeScript.of(c);
        if (sc == Character.UnicodeScript.HAN) {
            return true;
        }
        return false;
    }
    
   
    /**
     * 根据UnicodeBlock方法判断中文标点符号
     * @param c
     * @return
     */
    public boolean isChinesePunctuation(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS
                || ub == Character.UnicodeBlock.VERTICAL_FORMS) {
            return true;
        }  
           return false;
    }
    /**
     * 判断是否完全为全中文
     * @param s 字符串
     * @return  ponctuation 排除的标点
     */
	public static boolean isAbsolutChinoisString(String s , char...  ponctuation){
		     char[] cs = s.toCharArray();
		    Label:
		     for(char c:cs){
		    	 for(char p:ponctuation){
		    		   if(c==p)continue Label;
		    	 }
		    		  if(!isChinoisChar(c)){
		    			   return false;
		    		   }
		    	 }  	
		  
		     return true;
	}
	 /**
     * 判断是否完全为全中文
     * @param s 字符串
     *  
     */
	public static boolean isAbsolutChinoisString(String s){
		     char[] cs = s.toCharArray();
		     for(char c:cs){
                if(!isChinoisChar(c))return false;
		     }
		     return true;
	}
	
	
	/**
	 * 是否含有中文
	 * @param s
	 * @return
	 */
	public static boolean hasChinoisChar(String s){
	     char[] cs = s.toCharArray();
	     for(char c:cs){
	    	  if(isChinoisChar(c))return true;
	     }
	     return false;
     }
	/**
	 * 得到全部中文字符的坐标
	 * @param s
	 * @return
	 */
	public static Integer[] allIndexChinoisChar(String s){
		List<Integer> list = new ArrayList<Integer>();
	     char[] cs = s.toCharArray();
	     for(int i=0;i<cs.length;i++){
	    	  if(isChinoisChar(cs[i]))list.add(i);
	     }
	     if(list.size()>0){
	    	 Integer[] result = new Integer[list.size()];
	    	 return list.toArray(result);
	     }else{
	    	 return new Integer[0]; 
	     }
	    
    }
	/**
	 * 获取去字符串中文区间 
	 * 注意区间[5,95] 代表从5到95坐标的字符都是中文 如果截取要用substring(5,96)
	 * @param s
	 * @return
	 */
	public static List<Integer[]> allIntervalChinoisChar(String s){
		boolean iscomplate = true;
		List<Integer[]> list = new ArrayList<Integer[]>();
		Integer[] inval = null;
		StringBuilder sb = new StringBuilder(s);
		sb.append("song");
		 char[] cs = sb.toString().toCharArray();
	     for(int i=0;i<cs.length;i++){
	    	  if(isChinoisChar(cs[i])){
	    		  if(iscomplate){ iscomplate=false;
	    			  inval = new Integer[2]; 
	    			  inval[0] = i;
	    		  }
	    	  }else if(!iscomplate&&!isChinoisChar(cs[i])){
	    		  inval[1] = i-1;
	    		  list.add(inval);
	    		  iscomplate=true;
	    	  }
	    	 
	     }
	      return list;
	}
	/**
	 * 对字符串中中文进行urldecoder转码
	 * 返回map的一个entry的key是result,value是整体转码后的string字符串
	 * 其他的entry的key是转码后的字符,value是原中文字符
	 * @param s
	 * @return
	 */
	public static Map<String,String> URLencoderChinoisChar(String s){
		Map<String,String>  map = new HashMap<String, String>();
		List<Integer[]> ins = allIntervalChinoisChar(s);
		StringBuilder sb = new StringBuilder();
		if(ins.isEmpty()){
			map.put("result", s);
			return map;
		}else{
			int tem = 0;
			for(Integer[] in:ins){  
				if(tem<s.length())sb.append(s.substring(tem, in[0]));
				tem = in[1]+1;
				try {
					String temst = s.substring(in[0], in[1]+1);
					String code = URLEncoder.encode(temst, "UTF-8");
					sb.append(code);
					map.put(code, temst);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			sb.append(s.substring(tem,s.length()));
			map.put("result", sb.toString());
		}
		return map;
	}
	 
	  public static short getGB2312Id(char ch)
	  {
	    try
	    {
	      byte[] buffer = Character.toString(ch).getBytes("GB2312");
	      if (buffer.length != 2)
	      {
	        return -1;
	      }
	      int b0 = (buffer[0] & 0xFF) - 161;
	      int b1 = (buffer[1] & 0xFF) - 161;
	      return (short)(b0 * 94 + b1);
	    } catch (UnsupportedEncodingException e) {
	      e.printStackTrace();
	    }
	    return -1;
	  }
	 
	
	 
}
