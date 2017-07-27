package org.hardy.util.hashutil;

import java.io.ByteArrayInputStream;

import org.hardy.cryptogram.des.DES;
import org.hardy.util.io.conversion.ConvertBS;
import org.hardy.util.io.conversion.ObjectConversion;

public class UrlParamCryp {
      /**
       * 密码长度必须是8的倍数
       */
	   public static String  SECRITE_KEY = "12345678";
	   
	   private static ObjectConversion conversion = new ObjectConversion();
	   /**
	    * 将字符串加密
	    * @param urlOrParam
	    * @param password
	    * @return
	    * @throws Exception
	    */
	   public static String encodeDataString(String urlOrParam,String password) throws Exception{
		   password = (password==null)?SECRITE_KEY:password;
		    byte[] bs = DES.encrypt(urlOrParam.getBytes("utf-8"), password);
		    return ConvertBS.bytesToHexString(bs);
	   }

	/**
	 * 将一个对象加密
	 * @param obj
	 * @param password
	 * @return
	 * @throws Exception
	 */
	   public static String encodeObj(Object obj,String password) throws Exception{
		    password = (password==null)?SECRITE_KEY:password;
		    byte[] bs = conversion.objToByteArray(obj);
		    bs = DES.encrypt(bs, password);
		    return ConvertBS.bytesToHexString(bs);
	   }
	   /**
	    * 将字符串解密
	    * @param cryptString
	    * @param password
	    * @return
	    * @throws Exception
	    */
	   public static String decodeDataString(String cryptString,String password) throws Exception{
		   password = (password==null)?SECRITE_KEY:password;
		    byte[] bs = ConvertBS.hexStringToBytes(cryptString);
		    bs = DES.decrypt(bs, password);
		    return new String(bs, "utf-8");
	   }

	/**
	 * 根据字符串和密码反编译对象
	 * @param cryptString
	 * @param password
	 * @param <T>
	 * @return
	 * @throws Exception
	 */
	   public static <T>T decodeObj(String cryptString,String password) throws Exception{
		   password = (password==null)?SECRITE_KEY:password;
		    byte[] bs = ConvertBS.hexStringToBytes(cryptString);
		    bs = DES.decrypt(bs, password);
		    return conversion.streamToObject(new ByteArrayInputStream(bs));
	   }
 

	   
}
