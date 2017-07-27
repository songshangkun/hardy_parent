package org.hardy.sqlhelper.helper;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.hardy.reflex.SpringReflext;
import org.hardy.sqlhelper.datatrans.inf.DataTranslation;
import org.hardy.sqlhelper.enums.LinkedType;
import org.hardy.sqlhelper.enums.OperatorType;
import org.hardy.sqlhelper.helper.inf.DaoSecherHelper;
import org.hardy.sqlhelper.holographic.TableConstruct;
import org.hardy.sqlhelper.holographic.translation.inf.SqlTranslation;
import org.hardy.sqlhelper.holographic.translation.jdbc.JdbcCondition;
import org.hardy.sqlhelper.holographic.translation.jdbc.RowMapperContext;
import org.hardy.sqlhelper.holographic.translation.util.MapBeanHelper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class JdbcObjectHelper implements DaoSecherHelper{
	/**
     * jdbcTemplate数据库操作对象
     */
	   private JdbcTemplate jdbcTemplate;
	   /**
	    * sqlTranslation 数据库实体类的翻译对象
	    */
	   private SqlTranslation sqlTranslation;
	   /**
	     * 记录类对象属性名称和数据映射的上下文
	     */
	   private RowMapperContext rowMapperContext;
 
	   
	@SuppressWarnings("unchecked")
	@Override
	public <T> T selectById(Object obj) {
		final Class<?> clazz = obj.getClass();
		final TableConstruct tc = sqlTranslation.getHologResolve().getStruct(clazz);
		final String[] prop = tc.getAllPropNames();
		JdbcCondition jCondition = (JdbcCondition)sqlTranslation.selectEntityById(obj);
		return jdbcTemplate.queryForObject(jCondition.getPhrase(),this.rowMapperContext.saveAndReturn((Class<T>)clazz), jCondition.getT());
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T selectById(Serializable id, final Class<?> clazz) {
		JdbcCondition jCondition = (JdbcCondition)sqlTranslation.selectEntityById(id, clazz);
		return jdbcTemplate.queryForObject(jCondition.getPhrase(),this.rowMapperContext.saveAndReturn((Class<T>)clazz), jCondition.getT());
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> selectAllSame(Object obj) {
		final Class<?> clazz = obj.getClass();
		JdbcCondition jCondition = (JdbcCondition)sqlTranslation.selectEntityAllSame(obj);
		return jdbcTemplate.query(jCondition.getPhrase(),this.rowMapperContext.saveAndReturn((Class<T>)clazz), jCondition.getT());
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T selectUniqueAllSame(Object obj) {
		final Class<?> clazz = obj.getClass();
		JdbcCondition jCondition = (JdbcCondition)sqlTranslation.selectEntityAllSame(obj);
		return jdbcTemplate.queryForObject(jCondition.getPhrase(),this.rowMapperContext.saveAndReturn((Class<T>)clazz), jCondition.getT());
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> selectAnyOneSame(Object obj) {
		final Class<?> clazz = obj.getClass();
		JdbcCondition jCondition = (JdbcCondition)sqlTranslation.selectEntityAnyOneSame(obj);
		return jdbcTemplate.query(jCondition.getPhrase(),this.rowMapperContext.saveAndReturn((Class<T>)clazz), jCondition.getT());
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T selectUniqueAnyOneSame(Object obj) {
		final Class<?> clazz = obj.getClass();
		JdbcCondition jCondition = (JdbcCondition)sqlTranslation.selectEntityAnyOneSame(obj);
		return jdbcTemplate.queryForObject(jCondition.getPhrase(),this.rowMapperContext.saveAndReturn((Class<T>)clazz) , jCondition.getT());
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> selectAll(final Class<?> clazz, Integer firstResult,
			Integer maxResult) {
		JdbcCondition jCondition = (JdbcCondition)sqlTranslation.selectEntityAll(clazz, firstResult, maxResult);
		return jdbcTemplate.query(jCondition.getPhrase(),this.rowMapperContext.saveAndReturn((Class<T>)clazz) , jCondition.getT());
	}
  
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> selectAllLike(Object obj) {
		final Class<?> clazz = obj.getClass();
		JdbcCondition jCondition = (JdbcCondition)sqlTranslation.selectEntityAllLike(obj);
		return jdbcTemplate.query(jCondition.getPhrase(),this.rowMapperContext.saveAndReturn((Class<T>)clazz), jCondition.getT());
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> selectAnyOneLike(Object obj) {
		final Class<?> clazz = obj.getClass();
		JdbcCondition jCondition = (JdbcCondition)sqlTranslation.selectEntityAnyOneLike(obj);
		return jdbcTemplate.query(jCondition.getPhrase(),this.rowMapperContext.saveAndReturn((Class<T>)clazz), jCondition.getT()); 
	}
	@SuppressWarnings("unchecked")
	@Override
	public <T> EntityCount<T> selectEntityCount(Object obj,LinkedType link,OperatorType ope) {
		final Class<?> clazz = obj.getClass();
		EntityCount<T>  ec = new EntityCount<T>();
		JdbcCondition jCondition = (JdbcCondition)sqlTranslation.selectCount(obj, link, ope);
		Long count = jdbcTemplate.queryForObject(jCondition.getPhrase(), jCondition.getT(), Long.class);
		ec.setCount(count);
		jCondition = (JdbcCondition)sqlTranslation.selectEntityByCondition(obj, link, ope);
		List<T> list = jdbcTemplate.query(jCondition.getPhrase(),this.rowMapperContext.saveAndReturn((Class<T>)clazz), jCondition.getT());
		ec.setEntity(list);
		return ec; 
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public SqlTranslation getSqlTranslation() {
		return sqlTranslation;
	}

	public void setSqlTranslation(SqlTranslation sqlTranslation) {
		this.sqlTranslation = sqlTranslation;
	}

	public RowMapperContext getRowMapperContext() {
		return rowMapperContext;
	}

	public void setRowMapperContext(RowMapperContext rowMapperContext) {
		this.rowMapperContext = rowMapperContext;
	}
	
	

//	public DataTranslation getDataTranslation() {
//		return dataTranslation;
//	}
//
//	public void setDataTranslation(DataTranslation dataTranslation) {
//		this.dataTranslation = dataTranslation;
//	}
	
	

	 
//	public class MyRowMapper<T> implements RowMapper<T>{
//        private Class<T>  clazz;
//        private TableConstruct  tc;
//        private String[] prop  ;
//        
//        public MyRowMapper(Class<T> clazz){
//        	 this.clazz = clazz;
//        	 this.tc = JdbcObjectHelper.this.sqlTranslation.getHologResolve().getStruct(clazz);
//        	 this.prop = tc.getAllPropNames();
//        }
//		@SuppressWarnings("unchecked")
//		@Override
//		public T mapRow(ResultSet arg0, int arg1)
//				throws SQLException {
//			Object result = null;
//			try {
//				result = clazz.newInstance();
//			for(String s:prop){
//				SpringReflext.set(result, s, dataTranslation.transler(arg0.getObject(s), tc.getPropColumnByProp(s)));
//			 } 
//			}catch (NoSuchFieldException | SecurityException
//					| InstantiationException | IllegalAccessException
//					| ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			 } 
//			return (T) result;
//		}}
 
}
