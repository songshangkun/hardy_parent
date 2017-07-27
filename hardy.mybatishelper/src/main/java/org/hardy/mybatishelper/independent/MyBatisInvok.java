package org.hardy.mybatishelper.independent;

import java.util.Collection;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.hardy.mybatishelper.independent.MyBatisHelper.MapperInvoke;

public abstract class MyBatisInvok<T> implements MapperInvoke<T>{

	@Override
	public Object invoke(T t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object invoke(Object... objs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object invoke(Map<Class<?>, Object> map, Collection<SqlSession> sessions) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
