package org.hardy.mybatishelper.extention.helphandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.hardy.reflex.SpringReflext;
/**
 * 将对象按照持久化操作某个属性
 * <br>只保存持久化对象的某个属性,搜索时只将该属性设置到空实体类中
 * <br>因为使用这个类会造成无法保存模板类的情况
 * <br>所以这个类只能用来保存不会被持久化的对象的某个属性所以不建议使用(基本无用)
 * @author ssk
 *
 * @param <T>
 */
@Deprecated
public class PropPersistenceTypeHander <T> extends BaseTypeHandler<T>{
    
	private Class<T> clazz ;
	
	private String propName ;
	
	public Class<T> getClazz() {
		return clazz;
	}

	public void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	

	public String getPropName() {
		return propName;
	}

	public void setPropName(String propName) {
		this.propName = propName;
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, T parameter,
			JdbcType jdbcType) throws SQLException {
		    if(clazz==null||propName==null)
		    throw new RuntimeException("Specify property clazz and propName");
		    ps.setObject(i, SpringReflext.get(parameter, propName));  
               
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		    Object obj = null;
		try {
			obj = clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  SpringReflext.set(obj, propName, rs.getObject(columnName));
		return (T)obj;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		   Object obj = null;
			try {
				obj = clazz.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			SpringReflext.set(obj, propName, rs.getObject(columnIndex));
			return (T)obj;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		   Object obj = null;
			try {
				obj = clazz.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			SpringReflext.set(obj, propName, cs.getObject(columnIndex));
			return (T)obj;
	}

}
