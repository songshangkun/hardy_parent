package org.hardy.statics.sql;

/**
 * 数据库搜索数值类型
 * @author 09
 *
 */
public enum TargetDataType {
	/**
	 * 原始数据
	 */
	NORMAL,  
	/**
	 * 最大数值
	 */
	MAX,  
	/**
	 * 最小数值
	 */
	MIN,  
	/**
	 * 平均值
	 */
	AVG,  
	/**
	 * 总和
	 */
	SUM;
	  
	  private TargetDataType() {}
}
