package org.hardy.mybatishelper.extention.helphandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import com.song.tool.constant.DatePatten;
import com.song.tool.dom4jutil.XMLObject;

public class XmlTypeHandler<T> extends BaseTypeHandler<T>{
	private Class<T> clazz;
	
	public Class<T> getClazz() {
		return clazz;
	}

	public void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, T parameter,
			JdbcType jdbcType) throws SQLException {
		String xml = new XMLObject().xmlObject((Object)parameter, DatePatten.ZH.toString()).asXML().trim();
		ps.setString(i, xml);
		
	}

	@Override
	public T getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
