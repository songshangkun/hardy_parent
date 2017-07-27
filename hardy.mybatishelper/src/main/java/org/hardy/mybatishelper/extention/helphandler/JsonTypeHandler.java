package org.hardy.mybatishelper.extention.helphandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.alibaba.fastjson.JSONObject;

/**
 * 将持久化的类属性转换为JSON字符串
 * 需要ali的JSONObject对象
 * @author 09
 *
 * @param <T>
 */
public class JsonTypeHandler<T> extends BaseTypeHandler<T>{
	
	private Class<T> clazz;
	
	
 
	public Class<T> getClazz() {
		return clazz;
	}

	public void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public T getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		return (T) JSONObject.toJavaObject(JSONObject.parseObject(rs.getString(columnName)),clazz);	 
	}

	@Override
	public T getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		 return (T) JSONObject.toJavaObject(JSONObject.parseObject(rs.getString(columnIndex)),clazz);
	}

	@Override
	public T getNullableResult(CallableStatement callStat, int columnIndex)
			throws SQLException {
		  return (T)JSONObject.toJavaObject(JSONObject.parseObject(callStat.getString(columnIndex)),clazz);
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i,
			T parameter, JdbcType jdbcType) throws SQLException { 
		 ps.setString(i, JSONObject.toJSONString(parameter));  
	}

	 

}
