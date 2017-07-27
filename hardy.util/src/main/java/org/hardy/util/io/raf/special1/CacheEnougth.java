package org.hardy.util.io.raf.special1;

/**
 * 缓冲区都满后,是否进行下一步操作接口
 * 如网络传输等
 * @author 09
 *
 */
public interface CacheEnougth {
   
	  boolean transferData(ConfigMessage message,byte[] byts);
}
