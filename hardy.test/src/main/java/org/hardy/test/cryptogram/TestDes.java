package org.hardy.test.cryptogram;

import org.hardy.cryptogram.des.DES;

public class TestDes {
   
	  public static void main(String[] args) throws Exception {
		 String s = "这是一条秘闻吗";
		 
		 byte[] bs= DES.encrypt(s.getBytes(), "12345678songshangkunaids");
		 System.out.println("==============");
		 System.out.println(new String(bs,"UTF-8"));
		 
		 String gg = "���ַ��5{��8֢�&3�|�";
		 
		 byte[] bs2 = DES.decrypt(bs, "12345678songshangkunaids");
	     System.out.println(new String(bs2,"UTF-8"));
	  }
}
