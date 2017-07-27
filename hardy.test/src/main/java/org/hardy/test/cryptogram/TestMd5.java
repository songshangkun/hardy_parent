package org.hardy.test.cryptogram;

import org.hardy.cryptogram.md5.MD5Object;
 
import org.hardy.test.jsonhelper.Person;


public class TestMd5 {
    
	
	public static void main(String[] args) {
		Person p = new Person();
		String s = MD5Object.getMD5(p);
		System.out.println(s);
		p.setAge(100);
	    s = MD5Object.getMD5(p);
		System.out.println(s);
	}
}
