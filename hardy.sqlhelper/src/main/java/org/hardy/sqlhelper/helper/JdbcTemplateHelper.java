package org.hardy.sqlhelper.helper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hardy.sqlhelper.datatrans.inf.DataTranslation;
import org.hardy.sqlhelper.enums.LinkedType;
import org.hardy.sqlhelper.enums.OperatorType;
import org.hardy.sqlhelper.helper.inf.DaoHelper;
import org.hardy.sqlhelper.helper.inf.MapMapper;
import org.hardy.sqlhelper.holographic.translation.inf.SqlTranslation;
import org.hardy.sqlhelper.holographic.translation.jdbc.JdbcCondition;
import org.hardy.sqlhelper.holographic.translation.jdbc.RowMapperContext;
import org.hardy.statics.exception.LogicException;
import org.hardy.statics.exception.NotSupportException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 * SPRING的JDBCTEMPLATE操作接口
 * @author songs
 *
 */
public class JdbcTemplateHelper implements DaoHelper{
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
 
	     
	@Override
	public void createTable(Class<?> clazz) {
		 jdbcTemplate.execute(sqlTranslation.createTable(clazz).getPhrase());
	}

	@Override
	public void dropTable(Class<?> clazz) {
		jdbcTemplate.execute(sqlTranslation.dropTable(clazz).getPhrase());
	}

	@Override
	public void changeSafeModel(int safe) {
		 jdbcTemplate.execute(sqlTranslation.changeSafeModel(safe).getPhrase());
	}

	@Override
	public void cleanTable(Class<?> clazz) {
		jdbcTemplate.execute(sqlTranslation.changeSafeModel(0).getPhrase());
		jdbcTemplate.update(sqlTranslation.cleanTable(clazz).getPhrase());
		jdbcTemplate.execute(sqlTranslation.changeSafeModel(1).getPhrase());
	}

	@Override
	public void insert(Object obj) {
		JdbcCondition jCondition = (JdbcCondition)sqlTranslation.insert(obj);
		jdbcTemplate.update(jCondition.getPhrase(), jCondition.getT());
	}

	@Override
	public void deleteById(Serializable id, Class<?> clazz) {
		JdbcCondition jCondition = (JdbcCondition)sqlTranslation.deleteById(id, clazz);
		jdbcTemplate.update(jCondition.getPhrase(), jCondition.getT());
	}

	@Override
	public void deleteById(Object obj) {
		JdbcCondition jCondition = (JdbcCondition)sqlTranslation.deleteById(obj);
		jdbcTemplate.update(jCondition.getPhrase(), jCondition.getT());
	}

	@Override
	public void deleteByCondition(Object obj, LinkedType link) {
		JdbcCondition jCondition = (JdbcCondition)sqlTranslation.deleteByCondition(obj, link);
		jdbcTemplate.update(jCondition.getPhrase(), jCondition.getT());
	}

	@Override
	public void updateById(Object obj) {
		JdbcCondition jCondition = (JdbcCondition)sqlTranslation.updateById(obj);
		jdbcTemplate.update(jCondition.getPhrase(), jCondition.getT());
	}

	@Override
	public void updateByCondition(Object obj, Map<String, Object> params) {
		JdbcCondition jCondition = (JdbcCondition)sqlTranslation.updateByCondition(obj, params);
		jdbcTemplate.update(jCondition.getPhrase(), jCondition.getT());
	}

	@Override
	public void updateSansCondition(Object obj) {
		JdbcCondition jCondition = (JdbcCondition)sqlTranslation.updateSansCondition(obj);
		jdbcTemplate.update(jCondition.getPhrase(), jCondition.getT());
	}

	@Override
	public Map<String,Object> selectById(Object obj) {
		JdbcCondition jCondition = (JdbcCondition)sqlTranslation.selectById(obj);
		return jdbcTemplate.queryForMap(jCondition.getPhrase(), jCondition.getT());
		 
	}
	
	@Override
	public <T> T selectById(T obj,MapMapper<T> mapMapper) {
		   Map<String,Object> map = selectById(obj);
		  return mapMapper.mapToBean(map);
	}
  
	@Override
	public Map<String,Object> selectById(Serializable id, Class<?> clazz) {
		JdbcCondition jCondition = (JdbcCondition)sqlTranslation.selectById(id, clazz);
		return jdbcTemplate.queryForMap(jCondition.getPhrase(), jCondition.getT());
	}
	
	@Override
	public <T>T selectById(Serializable id, Class<?> clazz,MapMapper<T> mapMapper) {
		JdbcCondition jCondition = (JdbcCondition)sqlTranslation.selectById(id, clazz);
		Map<String, Object> map =  jdbcTemplate.queryForMap(jCondition.getPhrase(), jCondition.getT());
		 return mapMapper.mapToBean(map);  
	}
	
	
	@Override
	public <T> T selectById(T obj, RowMapper<T> rowMapper) {
		JdbcCondition jCondition = (JdbcCondition)sqlTranslation.selectById(obj);
		return jdbcTemplate.queryForObject(jCondition.getPhrase(), jCondition.getT(), rowMapper);
	}

	@Override
	public <T> T selectById(Serializable id, Class<?> clazz, RowMapper<T> rowMapper) {
		JdbcCondition jCondition = (JdbcCondition)sqlTranslation.selectById(id,clazz);
		return jdbcTemplate.queryForObject(jCondition.getPhrase(), jCondition.getT(), rowMapper);
	}

	@Override
	public Map<String, Object> selectUniqueAllSame(Object obj) {
		JdbcCondition jCondition = (JdbcCondition)sqlTranslation.selectAllSame(obj);
		List<Map<String,Object>> list = jdbcTemplate.queryForList(jCondition.getPhrase(), jCondition.getT());
		if(list==null||list.isEmpty()) return null;
		else if(list.size()>1) throw new LogicException("result size "+list.size()); 
		else if(list.size()==1) return list.remove(0);
		return null;
	}

	@Override
	public <T> T selectUniqueAllSame(Object obj, MapMapper<T> mapMapper) {
		JdbcCondition jCondition = (JdbcCondition)sqlTranslation.selectAllSame(obj);
		List<Map<String,Object>> list = jdbcTemplate.queryForList(jCondition.getPhrase(), jCondition.getT());
		if(list==null||list.isEmpty()) return null;
		else if(list.size()>1) throw new LogicException("result size "+list.size()); 
		else if(list.size()==1) return mapMapper.mapToBean(list.remove(0));
		return null;
	}

	@Override
	public <T> T selectUniqueAllSame(Object obj, RowMapper<T> rowMapper) {
		JdbcCondition jCondition = (JdbcCondition)sqlTranslation.selectAllSame(obj);
		List<T> list = jdbcTemplate.query(jCondition.getPhrase(),rowMapper, jCondition.getT());
		if(list==null||list.isEmpty()) return null;
		else if(list.size()>1) throw new LogicException("result size "+list.size()); 
		else if(list.size()==1) return list.remove(0);
		return null;
	}

	@Override
	public Map<String, Object> selectUniqueAnySame(Object obj) {
		JdbcCondition jCondition = (JdbcCondition)sqlTranslation.selectAnyOneSame(obj);
		List<Map<String,Object>> list = jdbcTemplate.queryForList(jCondition.getPhrase(), jCondition.getT());
		if(list==null||list.isEmpty()) return null;
		else if(list.size()>1) throw new LogicException("result size "+list.size()); 
		else if(list.size()==1) return list.remove(0);
		return null;
	}

	@Override
	public <T> T selectUniqueAnySame(Object obj, MapMapper<T> mapMapper) {
		JdbcCondition jCondition = (JdbcCondition)sqlTranslation.selectAnyOneSame(obj);
		List<Map<String,Object>> list = jdbcTemplate.queryForList(jCondition.getPhrase(), jCondition.getT());
		if(list==null||list.isEmpty()) return null;
		else if(list.size()>1) throw new LogicException("result size "+list.size()); 
		else if(list.size()==1) return mapMapper.mapToBean(list.remove(0));
		return null;
	}

	@Override
	public <T> T selectUniqueAnySame(Object obj, RowMapper<T> rowMapper) {
		JdbcCondition jCondition = (JdbcCondition)sqlTranslation.selectAnyOneSame(obj);
		List<T> list = jdbcTemplate.query(jCondition.getPhrase(),rowMapper, jCondition.getT());
		if(list==null||list.isEmpty()) return null;
		else if(list.size()>1) throw new LogicException("result size "+list.size()); 
		else if(list.size()==1) return list.remove(0);
		return null;
	}

	@Override
	public List<Map<String, Object>> selectAllSame(Object obj) {
		JdbcCondition jCondition = (JdbcCondition)sqlTranslation.selectAllSame(obj);
		return jdbcTemplate.queryForList(jCondition.getPhrase(), jCondition.getT());
	}
 
	@Override
	public <T> List<T> selectAllSame(Object obj, RowMapper<T> rowMapper) {
		JdbcCondition jCondition = (JdbcCondition)sqlTranslation.selectAllSame(obj);
		return jdbcTemplate.query(jCondition.getPhrase(), rowMapper,jCondition.getT());
	}

	@Override
	public List<Map<String, Object>> selectAnySame(Object obj) {
		JdbcCondition jCondition = (JdbcCondition)sqlTranslation.selectAnyOneSame(obj);
		return jdbcTemplate.queryForList(jCondition.getPhrase(),jCondition.getT());
	}

	 

	@Override
	public <T> List<T> selectAnySame(Object obj, RowMapper<T> rowMapper) {
		JdbcCondition jCondition = (JdbcCondition)sqlTranslation.selectAnyOneSame(obj);
		return jdbcTemplate.query(jCondition.getPhrase(),rowMapper,jCondition.getT());
	}

	@Override
	public List<Map<String, Object>> selectAllLike(Object obj) {
		JdbcCondition jCondition = (JdbcCondition)sqlTranslation.selectAllLike(obj);
		return jdbcTemplate.queryForList(jCondition.getPhrase(),jCondition.getT());
	}
 
	@Override
	public <T> List<T> selectAllLike(Object obj, RowMapper<T> rowMapper) {
		JdbcCondition jCondition = (JdbcCondition)sqlTranslation.selectAllLike(obj);
		return jdbcTemplate.query(jCondition.getPhrase(),rowMapper,jCondition.getT());
	}

	@Override
	public List<Map<String, Object>> selectAnyLike(Object obj) {
		JdbcCondition jCondition = (JdbcCondition)sqlTranslation.selectAnyOneLike(obj);
		return jdbcTemplate.queryForList(jCondition.getPhrase(),jCondition.getT());
	}

	 

	@Override
	public <T> List<T> selectAnyLike(Object obj, RowMapper<T> rowMapper) {
		JdbcCondition jCondition = (JdbcCondition)sqlTranslation.selectAnyOneLike(obj);
		return jdbcTemplate.query(jCondition.getPhrase(),rowMapper,jCondition.getT());
	}
	@Override
	public <T>T selectById(Object obj, Class<T> requiredType) {
		JdbcCondition jCondition = (JdbcCondition)sqlTranslation.selectById(obj);
		return jdbcTemplate.queryForObject(jCondition.getPhrase(),jCondition.getT(),requiredType);
	}
	@Override
	public <T>T selectUniqueAllSame(Object obj, Class<T> requiredType) {
		JdbcCondition jCondition = (JdbcCondition)sqlTranslation.selectAllSame(obj);
		return jdbcTemplate.queryForObject(jCondition.getPhrase(),jCondition.getT(),requiredType);
	}
	@Override
	public <T>T selectUniqueAnySame(Object obj, Class<T> requiredType) {
		JdbcCondition jCondition = (JdbcCondition)sqlTranslation.selectAnyOneSame(obj);
		return jdbcTemplate.queryForObject(jCondition.getPhrase(),jCondition.getT(),requiredType);
	}
	@Override
	public <T>List<T> selectAllSame(Object obj, Class<T> elementType) {
		JdbcCondition jCondition = (JdbcCondition)sqlTranslation.selectAllSame(obj);
		return jdbcTemplate.queryForList(jCondition.getPhrase(),elementType,jCondition.getT());
	}
	@Override
	public <T>List<T> selectAnySame(Object obj, Class<T> elementType) {
		JdbcCondition jCondition = (JdbcCondition)sqlTranslation.selectAnyOneSame(obj);
		return jdbcTemplate.queryForList(jCondition.getPhrase(),elementType,jCondition.getT());
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

	@Override
	public Long selectCount(Class<?> clazz) {
		JdbcCondition jCondition = (JdbcCondition)sqlTranslation.selectAllCount(clazz);
		return jdbcTemplate.queryForObject(jCondition.getPhrase(), null, Long.class);
	}

	@Override
	public Long selectCount(Object obj, LinkedType link, OperatorType ope) {
		JdbcCondition jCondition = (JdbcCondition)sqlTranslation.selectCount(obj, link, ope);
		return jdbcTemplate.queryForObject(jCondition.getPhrase(), jCondition.getT(), Long.class);
	}
	
	@Override
	public List<Map<String,Object>> selectListMap(Object obj, LinkedType link, OperatorType ope) {
		JdbcCondition jCondition = (JdbcCondition)sqlTranslation.selectByCondition(obj, link, ope);
		return  jdbcTemplate.queryForList(jCondition.getPhrase(),jCondition.getT());
	}

	@Override
	public EntityCount<Map<String, Object>> selectEntityCount(Object obj,LinkedType link, OperatorType ope) {
		EntityCount<Map<String,Object>> ec = new EntityCount<Map<String,Object>>();
		Long count = selectCount(obj, link, ope);
		List<Map<String,Object>> lms = selectListMap(obj, link, ope);
		ec.setCount(count);
		ec.setEntity(lms);
		return ec;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> selectT(Object obj, LinkedType link, OperatorType ope) {
		if(this.rowMapperContext==null) throw new NotSupportException("use method<selectT> must dependens RowMapperContext ");
		JdbcCondition jCondition = (JdbcCondition)sqlTranslation.selectByCondition(obj, link, ope);
		return  (List<T>) jdbcTemplate.query(jCondition.getPhrase(),this.rowMapperContext.saveAndReturn(obj.getClass()),jCondition.getT());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> EntityCount<T> selectTEntityCount(Object obj, LinkedType link, OperatorType ope) {
		if(this.rowMapperContext==null) throw new NotSupportException("use method<selectT> must dependens RowMapperContext ");
		EntityCount<T> ec = new EntityCount<T>();
		Long count = this.selectCount(obj, link, ope);
		ec.setCount(count);
		JdbcCondition jCondition = (JdbcCondition)sqlTranslation.selectByCondition(obj, link, ope);
		List<T> list = jdbcTemplate.query(jCondition.getPhrase(),this.rowMapperContext.saveAndReturn((Class<T>)obj.getClass()),jCondition.getT());
		ec.setEntity(list); 
		return ec;
	 }
 

	public RowMapperContext getRowMapperContext() {
		return rowMapperContext;
	}

	public void setRowMapperContext(RowMapperContext rowMapperContext) {
		this.rowMapperContext = rowMapperContext;
	}

	 

	 
	 
}

/**
 * 
 *  
 *  <!-- sqlhelper测试 -->
	<!-- jdbc的帮助类实例 -->
	<bean id="jdbcObjectHelper" class="org.hardy.sqlhelper.helper.JdbcObjectHelper">
		 <property name="jdbcTemplate" ref="jdbcTemp"/>
		  <property name="sqlTranslation" ref="sqlTranslation"/>
		  <property name="rowMapperContext" ref="rowMapperContext"/>
		  
	</bean>
	<!-- jdbc的帮助类实例 -->
	<bean id="jdbcHelper" class="org.hardy.sqlhelper.helper.JdbcTemplateHelper">
		 <property name="jdbcTemplate" ref="jdbcTemp"/>
		  <property name="sqlTranslation" ref="sqlTranslation"/>	
		  <property name="rowMapperContext" ref="rowMapperContext"/>	  
	</bean>
 <!-- 数据库数据到属性对象转换接口  -->
	 <bean id="rowMapperContext" class="org.hardy.sqlhelper.holographic.translation.jdbc.RowMapperContext">
		 <property name="hologResolve" ref="hologResolve"/>
		  <property name="dataTranslation" ref="dataTranslation"/>		  
	</bean>
	
	<!-- sql翻译接口  -->
	<bean id="sqlTranslation" class="org.hardy.sqlhelper.holographic.translation.jdbc.JdbcTranslation">
		 <property name="hologResolve" ref="hologResolve"/>
	</bean>
	<!-- 解析式图与处理接口  -->
	<bean id="hologResolve" class="org.hardy.sqlhelper.holographic.HologResolve">
		<property name="packageNames" value="org.bluesky_model.exrel.model" />
	</bean>
	<!-- 数据库数据到属性对象转换接口 -->
	<bean id="dataTranslation" class="org.hardy.sqlhelper.datatrans.JdbcDataTransfer">
	</bean>
 
 * 
 * **/
