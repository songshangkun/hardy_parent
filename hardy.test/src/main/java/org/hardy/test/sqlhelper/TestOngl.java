
package org.hardy.test.sqlhelper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.hardy.sqlhelper.enums.LinkedType;
import org.hardy.sqlhelper.holographic.HologResolve;
import org.hardy.sqlhelper.holographic.translation.inf.SqlTranslation;
import org.hardy.sqlhelper.holographic.translation.jdbc.JdbcTranslation;

public class TestOngl {
	public static void main(String[] args) {
		SqlTranslation.printSQL.show = true;
		JdbcTranslation jdbcTranslation = new JdbcTranslation();
		HologResolve hr = new HologResolve();
		hr.addStruct(Person.class);
		jdbcTranslation.setHologResolve(hr);
		  Person p = new Person();
		  Father father = new Father();father.setUserName("IdFather");
		  p.setUuFather(father);
		  p.setFrind(p);
		  p.setId(1);
		  p.setAge(22);
		  p.setName("zhangs");
		  p.setBirthday(new Date());
		  p.setUserName("song");
		  Map<String, Object> map = new HashMap<>();
		  map.put("frind", "aaa");
		 jdbcTranslation.selectAnyOneSame(p);
		 jdbcTranslation.selectAll(Person.class, null, null)  ;
		 System.out.println("**********************************");
		 jdbcTranslation.selectAnyOneLike(p)  ;
	}
	
	
}
