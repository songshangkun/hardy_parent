package org.hardy.test.reflex;

 
import org.hardy.reflex.SpringReflext;
import org.hardy.test.sqlhelper.Person;

public class Test {
	
	public static void main(String[] args) throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException {
		  Person p = new Person();
		  SpringReflext.setPropValue(p, "uuFather.userName", "zhangsan");
		  System.out.println(p);
	}
}
