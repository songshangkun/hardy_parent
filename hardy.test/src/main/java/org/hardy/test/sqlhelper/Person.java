package org.hardy.test.sqlhelper;

import java.util.Date;

import org.hardy.sqlhelper.annotation.structure.COLUMN;
import org.hardy.sqlhelper.annotation.structure.ID;
import org.hardy.sqlhelper.annotation.structure.Table;
import org.hardy.sqlhelper.annotation.variable.Condition;
import org.hardy.sqlhelper.annotation.variable.GroupBy;
import org.hardy.sqlhelper.annotation.variable.Limit;
import org.hardy.sqlhelper.annotation.variable.OrderBy;
import org.hardy.sqlhelper.annotation.variable.Result;
@Table(name="tb_person")

@Result()
public class Person extends Father{
	@Deprecated
	@ID
	private String userName; 
	@COLUMN(name="friend_id",joinPropValue="uuFather.userName")
	private Person frind;
	@ID(name="father_id",joinPropValue="userName")
	private Father uuFather;
	
//	@Result()
	@COLUMN(name="uuid")
	private Integer id;
	
//	@Result()
	@GroupBy	
	@OrderBy(order="asc",index=1)
    @COLUMN(notnull=true)
//    @Condition(" like '%ssk%'")
	private String name;
	
//	@Result()
//	@Condition(" > 31 ")
	@GroupBy(index=-1)
    @COLUMN 
	private Integer age;
	
//	@Result()
    @COLUMN(name="birth")
	private Date birthday;
	
	@Limit(firstResult=true)
	private Integer firstResult;
	
	@Limit(maxResult=true)
	private Integer rows2;
    
	
	 

	public Person getFrind() {
		return frind;
	}


	public void setFrind(Person frind) {
		this.frind = frind;
	}


	public Father getUuFather() {
		return uuFather;
	}


	public void setUuFather(Father uuFather) {
		this.uuFather = uuFather;
	}


	public Integer getFirstResult() {
		return 0;
	}


	public void setFirstResult(Integer firstResult) {
		this.firstResult = firstResult;
	}


	public Integer getRows2() {
		return 100;
	}


	public void setRows2(Integer rows2) {
		this.rows2 = rows2;
	}


	public String getName() {
		return name;
	}
	

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
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

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	 


	@Override
	public String toString() {
		return "Person [userName=" + userName + ", frind=" + frind
				+ ", uuFather=" + uuFather.getUserName() + ", id=" + id + ", name=" + name
				+ ", age=" + age + ", birthday=" + birthday + ", firstResult="
				+ firstResult + ", rows2=" + rows2 + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
}
