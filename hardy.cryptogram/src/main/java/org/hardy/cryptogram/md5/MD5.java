package org.hardy.cryptogram.md5;

 
/*
 * 利用java的MessageDigest 计算
 * */
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.hardy.texthelper.StringStreamUtil;



/**
 * 使用了java的MD5加密算法,主要是字符串转MD5加密字符串
 * @author 宋尚堃
 *
 */
public class MD5 {
	/**
	 * 加密字符串成byte数组
	 * @param msg
	 * @return
	 */
	 public static byte[] encrypt(String msg){
		  MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
			byte[] srcBytes = msg.getBytes();
			  md5.update(srcBytes);
			  byte[] resultBytes = md5.digest();
			  return resultBytes;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;  
 }
	  
	 /**
	  * @注意： 该方法输出的md5加密不符合规范统一的md5加密所以不建议使用
	  * 输出byte数组转16进制后的string形式
	  * 内部使用MD5Util.getMD5String方法转换byte[]到String16
	  * @param s  加密前的字符串
	  * @return
	  */
	 @Deprecated
	 public static String toMd5String16_1(String s){		  
		return  MD5Util.getMD5String(encrypt(s));
	 }
	 
	 /**
	  * 输出byte数组转16进制后的string形式
	  * 内部使用StringStreamUtil.toHex方法转换byte[]到String16
	  * @param s  加密前的字符串
	  * @return
	  */
	 public static String toMd5String16_2(String s){		  
		   return  StringStreamUtil.toHex(encrypt(s));
		 }
	 
		 
 
	 
}
