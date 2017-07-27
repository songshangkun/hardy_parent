package org.hardy.sqlhelper.helper.inf;

import java.util.Map;

/**
 * map转化实体接口对象
 * @author song
 *
 * @param <T>
 */
public interface MapMapper<T> {
    /**
     * 将map数据转为实体对象数据
     * @param map
     * @return
     */
	T mapToBean(Map<String,Object> map);
}
