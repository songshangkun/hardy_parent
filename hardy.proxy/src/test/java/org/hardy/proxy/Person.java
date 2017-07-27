package org.hardy.proxy;


public class Person implements SayInf{
	
	public String name = "zhangsan";
	
	public String say(String hua){
		   return name+": "+hua;
	  }
	  
	   
	public String sayHello(String shui){
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   return name+" say hello to "+shui;
	  }
	
	
}
