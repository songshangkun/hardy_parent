package hardy.jsonhelper;

import java.io.Serializable;

import org.hardy.jsonhelper.form.JsonBean;
import org.hardy.statics.json.RequiredPolicy;
import org.hardy.statics.json.ToJson;

public class Test {
	 public static void main(String[] args) {
		 Dog dog=new Dog();
		 dog.setName("dogA");
		 dog.setAge(10);
		 Person person = new Person();
		 person.setAge(30);
		 person.setName("witf");
		 dog.setOwen(person);
		 person.setMydod(dog);
		 JsonBean jBean = new JsonBean(dog, new String[]{"uuu"}, RequiredPolicy.ALL,RequiredPolicy.GET,RequiredPolicy.ANNOTATION);
		    System.out.println(jBean);
		}
}


class Person{
	private String name;
	
	private int age;
	
	private Dog mydod;
	
	

	public Dog getMydod() {
		return mydod;
	}

	public void setMydod(Dog mydod) {
		this.mydod = mydod;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	  
}

class Dog implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    @ToJson
	private String name;
	
	private int age;
	 @ToJson(policy="uuu")
	private Person owen;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Person getOwen() {
		return owen;
	}

	public void setOwen(Person owen) {
		this.owen = owen;
	}
	
	
	
}