package org.hardy.texthelper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream; 
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader; 
import java.io.Reader;
import java.io.UnsupportedEncodingException;


/**
 * 本类主要是实现string与stream的转换，
 * <hr> String------------>outputStream
 * <hr> inputStream------------>String
 * @author Administrator
 *
 */
public class StringStreamUtil {
    /**
     * 将inputStrean对象通过BufferedReader转化为String字符串
     * @param in  InputStream
     * @param charsetName  可以为null
     * @return
     */
	public static String streamToStringByBuffered(InputStream in, String charsetName){
		  Reader reader = null;	    
			 if(null!=charsetName){
				 try {
					reader = new InputStreamReader(in, charsetName);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }else{
				 reader = new InputStreamReader(in);
			 }
		   return streamReaderToString(reader); 
	}
	/**
	 * 将inputStreanReader对象通过BufferedReader转化为String字符串
	 * @param reader   
	 * @return
	 */
	public static String streamReaderToString(Reader reader){
		StringBuilder sb = new StringBuilder();
		 String result = null;
		 BufferedReader buf = new BufferedReader(reader);
		 try {
			while((result=buf.readLine())!=null){
				    sb.append(result+System.lineSeparator());
			   }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try{
				if(buf!=null){
					buf.close();
				}
				if(reader!=null){
					reader.close();
				}
			}catch (Exception e) {
				 e.printStackTrace();
				 throw new RuntimeException("close error:"+e.getMessage());
			}
			
		}
		   return sb.toString(); 
	}
	/**
	 * 将InputStream对象通过ByteArrayOutputStream转成String字符串
	 * @param in  InputStream
	 * @param charsetName  字符集名称  可以为null
	 *  @param cache  ByteArray的大小 默认1024
	 * @return
	 */
	 public static String streamToStringByByteArray(InputStream in,String charsetName,int cache){
		 ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		 if(cache==0)cache = 1024;
		 byte[] data = new byte[cache];
		 int count = -1;
		 try {
			while ((count = in.read(data, 0, cache)) != -1) {
			     outStream.write(data, 0, count);
			  }
			data = null;
			if(null!=charsetName)return new String(outStream.toByteArray(), charsetName);
			 else return new String(outStream.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try{
				if(outStream!=null){
					outStream.close();
				}
				if(in!=null){
					in.close();
				}
			}catch (Exception e) {
				 e.printStackTrace();
				 throw new RuntimeException("close error:"+e.getMessage());
			}
		}
		return null;
	 }
	 
	 
	 /**
	  * 将InputStream直接读入byte[]数组，数组大小与InputStream一致
	  * @param in
	  * @return
	  */
	 public static byte[] streamToByteArray(InputStream in){
		 byte[] data = null;
		 try
		     {
		        data = new byte[in.available()];
		        in.read(data);
		        in.close();
		      }
		       catch (IOException e)
		      {
		        e.printStackTrace();
		      }finally{
		    	     try{
						if(in!=null){
							in.close();
						}
					}catch (Exception e) {
						 e.printStackTrace();
						 throw new RuntimeException("close error:"+e.getMessage());
					}
		      }
		  
		      return data;
	 }
	 /**
	  * 将string转化成InputStream
	  * @param content
	  * @param charsetName  可以为null
	  * @return
	  */
	 public static InputStream getInputStream(String content,String charsetName){
		 byte[] bytes = null;
		 if(null!=charsetName){
			 try {
				bytes = content.getBytes(charsetName);
			} catch (UnsupportedEncodingException e) { 
				 bytes = content.getBytes();  //如果因为charsetName出现异常就再使用getBytes()
			}
		 }else{
			  bytes = content.getBytes(); 
		 }
		 
		 return new ByteArrayInputStream(bytes); 
	 }
	 /**
	  * 将byte[]数组转化为16进制字符串
	  * @param buffer
	  * @return
	  */
	 public static String toHex(byte[] buffer) {
			StringBuffer sb = new StringBuffer(buffer.length * 2);
			for (int i = 0; i < buffer.length; i++) {
			sb.append(Character.forDigit((buffer[i] & 0xf0) >> 4, 16));
			sb.append(Character.forDigit(buffer[i] & 0x0f, 16));
			}
			return sb.toString();
			}
}
