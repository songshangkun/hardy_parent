package org.hardy.test.sqlhelper;

import org.hardy.sqlhelper.annotation.structure.COLUMN;
import org.hardy.sqlhelper.annotation.structure.ID;
import org.hardy.sqlhelper.annotation.structure.Table;
import org.hardy.sqlhelper.annotation.variable.GroupBy;
import org.hardy.sqlhelper.annotation.variable.Limit;
import org.hardy.sqlhelper.annotation.variable.OrderBy;
import org.hardy.sqlhelper.annotation.variable.Result;

@Table(name="tb_father")
public class Father {
//	 @Result()
	 @ID(name="user_name")
	 private String userName;
//	 @Result()
     @COLUMN(name="f_name")
	 private String name;
//	 @Result()
     @COLUMN(name="f_age")
	 private Integer age;
	 @OrderBy(order="desc",index=0)
	 @COLUMN(name="fam_name")
	 private String famillyName;
	 
	 
	 
	 
	 private Integer first;
	 @Limit(maxResult=true)
	 private Integer rows;
	 
	 

	public Integer getFirst() {
		return first;
	}

	public void setFirst(Integer first) {
		this.first = first;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getFamillyName() {
		return famillyName;
	}

	public void setFamillyName(String famillyName) {
		this.famillyName = famillyName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	 
	 
}
