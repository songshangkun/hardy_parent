package org.hardy.servlet.out;

/**
 * 一个被ResourceWrite使用的返回资源接口
 * @author sundyn
 *
 */
public interface ServletResource {
	 /**
      * 返回String数据
      * @return
      */
	   public String excuterS();
	   /**
	    * 返回byte数组
	    * @return
	    */
	   public byte[] excuterB();
}
