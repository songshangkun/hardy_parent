package org.hardy.sqlhelper.holographic.translation.jdbc;

import java.util.Arrays;

import org.hardy.sqlhelper.holographic.translation.inf.Condition;

public class JdbcCondition extends Condition<Object[]>{

	public JdbcCondition(String sql,Object[] params) {
		  this.setPhrase(sql);
		  this.setT(params);
	}

	@Override
	public String toString() {
		return "JdbcCondition [phrase=" + phrase + ", t=" + Arrays.toString(t) + "]";
	}
     
	 
	
}
