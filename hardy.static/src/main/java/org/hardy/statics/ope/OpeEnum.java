package org.hardy.statics.ope;
/**
 * 操作类型枚举
 * @author 09
 *
 */
public enum OpeEnum {
	/**
	 * 查找
	 */
	FIND,  
	/**
	 * 修改
	 */
	UPDATE,  
	/**
	 * 添加
	 */
	ADD,  
	/**
	 * 删除
	 */
	DELETE,  
	/**
	 * 移除全部
	 */
	REMOUVEAll,  
	/**
	 * 查找全部
	 */
	FINDAll;
	private OpeEnum(){}
}
