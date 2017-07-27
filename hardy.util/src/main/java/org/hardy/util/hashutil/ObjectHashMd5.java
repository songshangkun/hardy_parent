package org.hardy.util.hashutil;

import java.util.ArrayList;
import java.util.List;

import org.hardy.cryptogram.md5.MD5Object;

/**
 * 通过md5验证一组对象的hash值
 * @author 09
 *
 */
public class ObjectHashMd5 {
     /**
      * 获取一组对象的md5值,这个值并不是对象的md5,而是对象被置于Array中的md5
      * 顺序会影响hash值
      * @param objs
      * @return
      */
	public static String getHashOrder(Object... objs){
		 return MD5Object.getMD5(objs);
	}
	/**
	 * 获取一组对象的md5值,这个值并不是对象的md5,而是对象被置于Array中的md5
     * 顺序不会影响hash值 (64位16进制数字)
	 * @param objs
	 * @return
	 */
	public static String getHash(Object... objs){
		List<char[]> list32 = new ArrayList<char[]>();
		 StringBuilder result = new StringBuilder();
		 for(Object obj:objs){
			 String md5 = MD5Object.getMD5(obj); 
			 char[] chars = md5.toCharArray();
			 list32.add(chars);
		 }
		 for(int i=0;i<32;i++){
			 Integer indexNum = 0;
			 for(char[] ch:list32){
				 indexNum +=ch[i];
			 }
			 result.append(Integer.toHexString(indexNum));
		 }
		 return result.toString();
	}
	
	public static void main(String[] args) {
		String s = "a";
		String d = "2";
		String r = getHashOrder(s);
		System.out.println(r);
		 r = getHash(d);
		System.out.println(r);
		 r = getHash(s,d);
		System.out.println(r);
		 r = getHash(d,s);
		System.out.println(r);
		 r = getHash(s,d);
		System.out.println(r);
	}
}
