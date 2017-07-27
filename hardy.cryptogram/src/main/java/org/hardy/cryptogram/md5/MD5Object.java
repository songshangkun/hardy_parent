package org.hardy.cryptogram.md5;

import com.song.tool.util.conversion.ObjectConversion;

/**
 * 对对象进行MD5加密,对象属性不同MD5值也就不一样
 * @author 09
 *
 */
public class MD5Object {
	/**
	 * 对对象进行md5加密,对象必须序列化
	 * @param obj
	 * @return
	 */
	 public static String getMD5(Object obj){
		 ObjectConversion ob = new ObjectConversion();
	   return  MD5Util.getMD5String(ob.objToByteArray(obj));
	 }
	 
	  
 
}

