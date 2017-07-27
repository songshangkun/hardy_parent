package org.hardy.sqlhelper.holographic.translation.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.hardy.reflex.SpringReflext;
import org.hardy.sqlhelper.datatrans.inf.DataTranslation;
import org.hardy.sqlhelper.holographic.HologResolve;
import org.hardy.sqlhelper.holographic.TableConstruct;
import org.springframework.jdbc.core.RowMapper;

/**
 * 针对jdbcTemplate的RowMapper接口,
 * 保存所有使用过的类和他们对象属性映射的RowMapper接口
 * @author songs
 *
 */
public class RowMapperContext {
    /**
     * 实体类数据库对应试图
     */
	private HologResolve hologResolve;
	/**
	 * 数据转换接口
	 */
	private DataTranslation dataTranslation;
	 
	
	
	private Map<Class<?>,MyRowMapper<?>> rowMappers = new HashMap<>();
	
	public <T>RowMapper<T> saveAndReturn(Class<T> clazz){
		if(!contains(clazz)) addRowMapper(clazz);
		return getRowMapper(clazz);
	}
	/**
	 * 添加一个 rowMapper
	 * @param clazz
	 */
	public void addRowMapper(Class<?> clazz){
		 this.rowMappers.put(clazz, new MyRowMapper<>(clazz));
	}
	/**
	 * 获取rowMapper
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T>RowMapper<T> getRowMapper(Class<?> clazz){
		 return (RowMapper<T>) this.rowMappers.get(clazz);
	}
	/**
	 * 是否包含
	 * @param clazz
	 * @return
	 */
	public boolean contains(Class<?> clazz){
		return this.rowMappers.containsKey(clazz);
	}
	 
	public class MyRowMapper<T> implements RowMapper<T>{
        private Class<T>  clazz;
        private TableConstruct  tc;
        private String[] prop  ;
        
        public MyRowMapper(Class<T> clazz){
        	 this.clazz = clazz;
        	 this.tc = RowMapperContext.this.hologResolve.getStruct(clazz);
        	 this.prop = tc.getAllPropNames();
        }
		@SuppressWarnings("unchecked")
		@Override
		public T mapRow(ResultSet arg0, int arg1)
				throws SQLException {
			Object result = null;
			try {
				result = clazz.newInstance();
			for(String s:prop){
				SpringReflext.set(result, s, RowMapperContext.this.dataTranslation.transler(arg0.getObject(s), tc.getPropColumnByProp(s)));
			 } 
			}catch (NoSuchFieldException | SecurityException
					| InstantiationException | IllegalAccessException
					| ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 } 
			return (T) result;
		}}

	public HologResolve getHologResolve() {
		return hologResolve;
	}

	public void setHologResolve(HologResolve hologResolve) {
		this.hologResolve = hologResolve;
	}

	public DataTranslation getDataTranslation() {
		return dataTranslation;
	}

	public void setDataTranslation(DataTranslation dataTranslation) {
		this.dataTranslation = dataTranslation;
	}
	 
	 
	
}
