package org.hardy.cachehelper.ehcache;

/**
 * Ehcache中获取缓存唯一键值
 * @author 09
 *
 */
public interface EhcacheUniqueKey {
       /**
        * 获取ehcache缓存的唯一key值
        * @param objects
        * @return
        */
	   String KEY(Object...objects);
}
