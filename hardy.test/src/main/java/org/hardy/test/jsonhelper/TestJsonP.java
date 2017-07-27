package org.hardy.test.jsonhelper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.hardy.jsonhelper.param.JSONP;


public class TestJsonP {
   public static void main(String[] args) {
	   Person p = new Person();
	    p.setName("songshangkun");
	    p.setAge(30);
	    
	    Map<String, Object> map = new HashMap<>();
	    map.put("a", Integer.valueOf(1));map.put("sss", "bbb");
	    p.setObjs(new Object[] { Double.valueOf(2.23D), Double.valueOf(3.201D), Integer.valueOf(4), Integer.valueOf(5), new Date(), map });
	    String s = JSONP.beanToJson(p);
	    System.out.println(s);
}
}
