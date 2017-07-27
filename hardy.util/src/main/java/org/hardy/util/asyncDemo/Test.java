package org.hardy.util.asyncDemo;

import java.util.Date;

public class Test {
	  
	public String getLongtime1(String name){
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   return "longtime1=="+name;
	}
	
	public String getLongtime2(String name2){
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   return "longtime2=="+name2;
	}
	   
//	public static void main(String[] args) {
//		final Test t = new Test();
//		 long s = new Date().getTime();
//		  System.out.println(t.getLongtime1("111122222"));
//		  System.out.println(t.getLongtime2("333334444"));
//		  System.out.println("end===>"+(new Date().getTime()-s)/1000);
//	}
	public static void main(String[] args) {
		final Test t = new Test();
		 long s = new Date().getTime();
			 Awaite<String> aw = new Awaite<>();
		 aw.setProduice(new Fonction<String>() {
			@Override
			public String produice(long index) {
				 String s1 = t.getLongtime1("zhangsan");
				 String s2 = t.getLongtime2("zhangs222an");
				 return  s1+s2;
			}
		}.range("0:1"));
		  System.out.println(aw.next());
		  System.out.println("end===>"+(new Date().getTime()-s)/1000);
	}
	
	

}
