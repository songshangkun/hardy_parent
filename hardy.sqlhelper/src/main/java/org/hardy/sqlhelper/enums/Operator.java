package org.hardy.sqlhelper.enums;

/**
 * 表示搜索的模式条件,针对方法调用使用
 * @author 09
 *
 */
public enum Operator {
	/**
	 * 属性名称对应
	 */
	eqName,
	/**
	 * =
	 */
	eq,
	/**
	 * all = 
	 */
	allEq,
	/**
	 * or = 
	 */
	orEq,
	/**
	 *  >
	 */
	gt,
	/**
	 *  <
	 */
	lt,
    /**
	 *  <=
	 */
	 le,
    /**
	 *  >=
	 */
	 ge,
	 /**
	  * BETWEEN
	  */
	 between,
	 /**
	  * LIKE
	  */
	 like,
	 /**
	  * in
	  */
	 in,and,or,isNull,isNotNull,asc,desc,groupby,
}
