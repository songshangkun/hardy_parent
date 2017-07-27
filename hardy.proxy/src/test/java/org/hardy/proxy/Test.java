package org.hardy.proxy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.hardy.proxy.advice.Advice;
import org.hardy.proxy.advice.simple.RunTimeAdvice;
import org.hardy.proxy.cglib.CGlibProxyFactory;
import org.hardy.proxy.dynproxy.MyProxy;
import org.hardy.proxy.pointjoint.PointJoint;



public class Test {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
//		Advice advice = new MyAdvice();
//		MyProxy proxy = new MyProxy();
//		proxy.setAdvice(advice);
//		SayInf sayInf = (SayInf)proxy.getProxy(new Person());
//		String hString = sayInf.say("kkkkk");
//		System.out.println(hString);
    	
//    	Advice advice = new MyAdvice();
//    	CGlibProxyFactory<Person> proxy = new CGlibProxyFactory<Person>();
//		proxy.setAdvice(advice);
//		String hString = proxy.getProxy(new Person()).say("kkkkk");
//		System.out.println(hString);
    	
    	    Person person = 	new Person();
      	person.name = "wangjiayu";
	    Proxy<SayInf> proxy = new Proxy<SayInf>(person,new RunTimeAdvice());
	    SayInf sa1 = proxy.dynProxy();
	    sa1.say("aaaassss");
	    person.name = "song";
	    sa1.say("aaaassss");
	    
	    Person person2 = 	new Person();
	    person2.name = "hahahahahah";
	    proxy.setBean(person2);
	    proxy.dynProxy().sayHello("wwwwwwwwwww");
		
	    Proxy.cglib(person2,new RunTimeAdvice()).say("mmmmmmmmm");
    }
    
    
     
}


	
	


 
 
