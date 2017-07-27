package org.hardy.mybatishelper.sqlTranslation;

/**
 * 记录Map使用当中的字符常量
 * @author song
 *
 */
public interface SQLKeySet {
     /**
      * 代表SQL语句,对应sql语句
      */
	public static final String SQL = "SQL";
	/**
	 * 代表实体类,对应class类
	 */
	public static final String CLASS = "CLASS";
	/**
	 * 代表id,对应id属性名称对应的值
	 */
	public static final String ID = "ID";
 
	/**
	 * 设置关联AND OR
	 */
	public static final String LINKED = "LINKED";
	/**
	 * 设置操作符号
	 */
	public static final String OPERATOR = "OPERATOR";
	/**
	 * 设置是否安全操作
	 */
	public static final String SAFE = "SAFE";
	 
	/**
	 * 目标map的开头字符
	 */
	public static final String TARGET_START = "_T_";
	/**
	 * 条件map的开头字符
	 */
	public static final String CONDITION_START = "_C_";
	/**
	 * 数据库是否使用DISTINCT搜索
	 */
	public static final String DISTINCT = "DISTINCT";
}
