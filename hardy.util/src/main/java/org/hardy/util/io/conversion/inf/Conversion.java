package org.hardy.util.io.conversion.inf;

import java.io.InputStream;
import java.io.OutputStream;
/**
 * 该接口根据java标准jdk,不加载任何其他包,从新定义了一个
 * 对io操作的工具类.
 * @author 09
 *
 */
public interface Conversion {
	/**
     * 对象转流
     * @param obj 转换对象
     * @param oos 输出流
     */
	 void  objToStream(Object obj,OutputStream os);
	 /**
	  * 流转对象
	  * @param obj
	  * @return
	  */
	 <T> T  streamToObject(InputStream is);
	  /**
	   * byte[] 转输出流
	   * @param bs
	   * @param oos
	   */
	  void byteArrayToStream(byte[] bs,OutputStream os);
	  /**
	   * byte[]转输入流
	   * @param bs
	   * @param oos
	   */
	  InputStream byteArrayToStream(byte[] bs);
	 /**
	  * 输入流转byte[]
	  * @param is
	  * @return
	  */
	 byte[]  streamToByteArray(InputStream is);
	 /**
	  * 输入流转byte[]
	  * @param in
	  * @param cache 缓冲区大小
	  * @return
	  */
	 public  byte[] streamToByteArray(InputStream in,int cache);
}
