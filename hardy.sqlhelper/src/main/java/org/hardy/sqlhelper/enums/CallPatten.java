package org.hardy.sqlhelper.enums;
/**
 * 关于variabl的annotation的标注关系
 * @author songs
 *
 */
public enum CallPatten {
	/**
     * 未使用标注
     */
	SANS(0),
    /**
     * 标注在类上
     */
	CLASS(1),
	/**
	 * 标注在数据库字段对应的属性上
	 */
	PROPERTY(2),
	/**
	 * 标注在数据库字段不对应的属性上,采用反射读取
	 */
	METHOD(3);
	/**
	 * 优先级,数字越高优先级越高
	 */
	public int prorite;
	
	private CallPatten(int prorite){
		this.prorite = prorite;
	}

	 
	
	
}
