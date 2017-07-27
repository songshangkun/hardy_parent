package org.hardy.test.sqlhelper;

import org.hardy.sqlhelper.holographic.OprationalVariable;
import org.hardy.sqlhelper.holographic.TableConstruct;

public class TestTable {
  
	public static void main(String[] args) {
//		  HologResolve hr = new HologResolve();
//		  hr.addStruct(Person.class);
//		  hr.addStruct(Father.class);
//		  System.out.println(hr.getStruct(Person.class)); 
//		  System.out.println(hr.getStruct(Father.class)); 
		TableConstruct tc = new TableConstruct(Person.class);
		System.out.println(tc);
		OprationalVariable ov = new OprationalVariable(Person.class,tc);
		System.out.println(ov);
	}
}
