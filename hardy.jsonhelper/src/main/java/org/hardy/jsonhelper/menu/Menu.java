package org.hardy.jsonhelper.menu;

/**
 * 针对数据库的菜单实体类
 * 设置parentCode为查找父菜单的口径
 * 是为了在hibernate设置时可以删除父菜单而保留子菜单
 * 这里不适用对象的嵌套设计，就是为了更灵活的删除
 */
public class Menu {
	
	 
	private int id;
	private String menuCode; //菜单代码
	private String menuName; //菜单名称
	private String menuClass; //菜单样式
	private String menuUrl; //菜单URL路径
	private int sequence; //排序
	private String parentCode; //父亲节点
	private String remark; //备注
	//private List<Authority> subAuthorityList;

	public Menu() {

	}
	

	/**
	 * @param menuCode
	 * @param menuName
	 * @param menuClass
	 * @param menuUrl
	 * @param sequence
	 * @param parentCode
	 */
	public Menu(Integer id,String menuCode, String menuName, String menuClass,
			String menuUrl, int sequence, String parentCode,String remark) {
		super();
		this.id=id;
		this.menuCode = menuCode;
		this.menuName = menuName;
		this.menuClass = menuClass;
		this.menuUrl = menuUrl;
		this.sequence = sequence;
		this.parentCode = parentCode;
		this.remark=remark;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuClass() {
		return menuClass;
	}

	public void setMenuClass(String menuClass) {
		this.menuClass = menuClass;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public int getSequence() {
		return sequence;
	}
	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}
	

	@Override
	public String toString() {
		return "Authority [id=" + id + ", menuCode=" + menuCode + ", menuName="
				+ menuName + ", menuClass=" + menuClass + ", menuUrl="
				+ menuUrl + ", sequence=" + sequence + ", parentCode="
				+ parentCode + "]";
	}

}

