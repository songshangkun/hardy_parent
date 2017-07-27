package org.hardy.sqlhelper.helper;

import java.util.List;

/**
 * 包含数据总数量和具体值的一个实体
 * @author song
 *
 * @param <T>
 */
public class EntityCount<T> {
    /**
     * 搜索的总数量
     */
	private Long count;
	/**
	 * 搜索的具体是体值
	 */
	private List<T> entity;
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public List<T> getEntity() {
		return entity;
	}
	public void setEntity(List<T> entity) {
		this.entity = entity;
	}
	@Override
	public String toString() {
		return "EntityCount [count=" + count + ", entity=" + entity + "]";
	}
	
	
}
