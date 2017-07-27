package org.hardy.texthelper;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;


/**
 * 这个类用于转换string字符串的形体
 * @author songshangkun
 *
 */
public class StringConverter2 {
	/**
	 * string转16进制字符串
	 * @param s
	 * @return
	 */
	 public static String toHexString(String s)   
     {   
     String str="";   
     for (int i=0;i<s.length();i++)   
     {   
     int ch = (int)s.charAt(i);   
     String s4 = Integer.toHexString(ch);   
     str = str + s4; 
     }   
     return str;   
     } 

     /**
      * 16进制字符串转string
      * @param s
      * @return
      */
     public static String toStringHex(String s) 
     { 
     byte[] baKeyword = new byte[s.length()/2]; 
        for(int i = 0; i < baKeyword.length; i++) 
        { 
         try 
         { 
         baKeyword[i] = (byte)(0xff & Integer.parseInt(s.substring(i*2, i*2+2),16)); 
         } 
         catch(Exception e) 
         { 
         e.printStackTrace(); 
         } 
        } 
       
     try 
     { 
     s = new String(baKeyword, "utf-8");//UTF-16le:Not 
     } 
     catch (Exception e1) 
     { 
     e1.printStackTrace(); 
     } 
     return s; 
     } 

     
     
     /* 
     * 16进制数字字符集 
     */ 
     private static String hexString="0123456789ABCDEF"; 
     /* 
     * 将字符串编码成16进制数字,适用于所有字符（包括中文） 
     */ 
     public static String encode(String str) 
     { 
     //根据默认编码获取字节数组 
     byte[] bytes=str.getBytes(); 
     StringBuilder sb=new StringBuilder(bytes.length*2); 
     //将字节数组中每个字节拆解成2位16进制整数 
     for(int i=0;i<bytes.length;i++) 
     { 
     sb.append(hexString.charAt((bytes[i]&0xf0)>>4)); 
     sb.append(hexString.charAt((bytes[i]&0x0f)>>0)); 
     } 
     return sb.toString(); 
     } 
     
     /* 
     * 将16进制数字解码成字符串,适用于所有字符（包括中文） 
     */ 
     public static String decode(String bytes) 
     { 
     ByteArrayOutputStream baos=new ByteArrayOutputStream(bytes.length()/2); 
     //将每2位16进制整数组装成一个字节 
     for(int i=0;i<bytes.length();i+=2) 
     baos.write((hexString.indexOf(bytes.charAt(i))<<4 |hexString.indexOf(bytes.charAt(i+1))));
     return new String(baos.toByteArray()); 
     }
     
     
     
     //转换字符编码
     /**
      * 转换字符串的编码
      */
    
      /** 7位ASCII字符，也叫作ISO646-US、Unicode字符集的基本拉丁块 */
      public static final String US_ASCII = "US-ASCII";

      /** ISO 拉丁字母表 No.1，也叫作 ISO-LATIN-1 */
      public static final String ISO_8859_1 = "ISO-8859-1";

      /** 8 位 UCS 转换格式 */
      public static final String UTF_8 = "UTF-8";

      /** 16 位 UCS 转换格式，Big Endian（最低地址存放高位字节）字节顺序 */
      public static final String UTF_16BE = "UTF-16BE";

      /** 16 位 UCS 转换格式，Little-endian（最高地址存放低位字节）字节顺序 */
      public static final String UTF_16LE = "UTF-16LE";

      /** 16 位 UCS 转换格式，字节顺序由可选的字节顺序标记来标识 */
      public static final String UTF_16 = "UTF-16";

      /** 中文超大字符集 */
      public static final String GBK = "GBK";

      /**
       * 将字符编码转换成US-ASCII码
       */
      public String toASCII(String str) throws UnsupportedEncodingException{
       return this.changeCharset(str, US_ASCII);
      }
      /**
       * 将字符编码转换成ISO-8859-1码
       */
      public String toISO_8859_1(String str) throws UnsupportedEncodingException{
       return this.changeCharset(str, ISO_8859_1);
      }
      /**
       * 将字符编码转换成UTF-8码
       */
      public String toUTF_8(String str) throws UnsupportedEncodingException{
       return this.changeCharset(str, UTF_8);
      }
      /**
       * 将字符编码转换成UTF-16BE码
       */
      public String toUTF_16BE(String str) throws UnsupportedEncodingException{
       return this.changeCharset(str, UTF_16BE);
      }
      /**
       * 将字符编码转换成UTF-16LE码
       */
      public String toUTF_16LE(String str) throws UnsupportedEncodingException{
       return this.changeCharset(str, UTF_16LE);
      }
      /**
       * 将字符编码转换成UTF-16码
       */
      public String toUTF_16(String str) throws UnsupportedEncodingException{
       return this.changeCharset(str, UTF_16);
      }
      /**
       * 将字符编码转换成GBK码
       */
      public String toGBK(String str) throws UnsupportedEncodingException{
       return this.changeCharset(str, GBK);
      }


     //**********
     /**
      * 字符串编码转换的实现方法
      * @param str  待转换编码的字符串
      * @param newCharset 目标编码
      * @return
      * @throws UnsupportedEncodingException
      */
     public String changeCharset(String str, String newCharset)
       throws UnsupportedEncodingException {
      if (str != null) {
       //用默认字符编码解码字符串。
       byte[] bs = str.getBytes();
       //用新的字符编码生成字符串
       return new String(bs, newCharset);
      }
      return null;
     }
     /**
      * 字符串编码转换的实现方法
      * @param str  待转换编码的字符串
      * @param oldCharset 原编码
      * @param newCharset 目标编码
      * @return
      * @throws UnsupportedEncodingException
      */
     public String changeCharset(String str, String oldCharset, String newCharset)
       throws UnsupportedEncodingException {
      if (str != null) {
       //用旧的字符编码解码字符串。解码可能会出现异常。
       byte[] bs = str.getBytes(oldCharset);
       //用新的字符编码生成字符串
       return new String(bs, newCharset);
      }
      return null;
     }
  //*************************************
 	 
 	 public static void main(String[] args) {
 		
 		 System.out.println("zhang".hashCode());
 		 System.out.println(new String("zhang").hashCode());
	}
}
