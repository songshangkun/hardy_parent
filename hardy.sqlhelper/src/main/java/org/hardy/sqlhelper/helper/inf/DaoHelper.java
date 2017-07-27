package org.hardy.sqlhelper.helper.inf;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hardy.sqlhelper.enums.LinkedType;
import org.hardy.sqlhelper.enums.OperatorType;
import org.hardy.sqlhelper.helper.EntityCount;
import org.hardy.sqlhelper.holographic.translation.jdbc.JdbcCondition;
import org.springframework.jdbc.core.RowMapper;

/**
 * 数据库操作接口
 * @author song
 *
 */
public interface DaoHelper {
	/**
	 * 创建数据库表
	 * @param clazz
	 */
	void createTable(Class<?> clazz);
	/**
	 * 删除数据库表
	 * @param clazz
	 */
	void dropTable(Class<?> clazz);
	/**
	 * 调整数据库安全模式 0为可清空表，1为不能清空表
	 * @param safe
	 * @return
	 */
	public void changeSafeModel(int safe);
	/**
	 * 清空整个实体类的表
	 * @param clazz
	 * @return
	 */
	 void cleanTable(Class<?> clazz);
	/**
	 * 增加一个对象实体
	 * @param obj
	 * @return
	 */
	 void insert(Object obj);
	 /**
	  * 根据id删除一个对象实体
	  * @param id
	  * @param clazz
	  * @return
	  */
	 void deleteById(Serializable id,Class<?> clazz);
	 /**
	  * 根据id删除一个对象实体
	  * @param obj
	  * @return
	  */
	 void deleteById(Object obj);
	 /**
	  * 根据条件删除
	  * @param obj
	  * @param link  连接关系
	  * @return
	  */
	 void deleteByCondition(Object obj,LinkedType link);
	 
	 /**
	  * 通过id更改一个对象
	  * @param obj
	  * @return
	  */
	 void updateById(Object obj);
	 /**
	  * 根据条件修改一个对象实体,id永远不能改变
	  * @param obj  修改的对象
	  * @param params  筛选的条件  map.key  属性名称, map.value 属性的值
	  * @return
	  */
	 void updateByCondition(Object obj,Map<String, Object> params);
	 
	 /**
	  * 无条件更改
	  * @param obj
	  * @return
	  */
	 void updateSansCondition(Object obj);
	 
	 /**
	  * 按id查找对象返回Map
	  * @param obj
	  * @return
	  */
	 Map<String,Object> selectById(Object obj);
	 /**
	  * 根据id查找对象
	  * @param id
	  * @param clazz
	  * @return
	  */
	 Map<String,Object> selectById(Serializable id,Class<?> clazz);
	 /**
	  * 根据id查找对象
	  * @param obj
	  * @param mapMapper
	  * @return
	  */
	 <T> T selectById(T obj,MapMapper<T> mapMapper);
	 /**
	  * 根据id查找对象
	  * @param obj
	  * @param rowMapper
	  * @return
	  */
	 <T> T selectById(T obj,RowMapper<T> rowMapper);
	 
	 /**
	  * 根据id查找对象
	  * @param id
	  * @param clazz
	  * @param mapMapper
	  * @return
	  */
	 <T>T selectById(Serializable id, Class<?> clazz,MapMapper<T> mapMapper);
	 /**
	  * 根据id查找对象
	  * @param id
	  * @param clazz
	  * @param rowMapper
	  * @return
	  */
	 <T>T selectById(Serializable id, Class<?> clazz,RowMapper<T> rowMapper);
	 
	 /**
	  * 
	  * @param obj
	  * @return
	  */
	 Map<String,Object> selectUniqueAllSame(Object obj);
	 
	 /**
	  * 查找一样的对象,当返回值大于1时抛出异常
	  * @param obj
	  * @return
	  */
	 <T>T  selectUniqueAllSame(Object obj,MapMapper<T> mapMapper);
	 /**
	  * 查找一样的对象,当返回值大于1时抛出异常
	  * @param obj
	  * @param rowMapper
	  * @return
	  */
	 <T>T  selectUniqueAllSame(Object obj,RowMapper<T> rowMapper);
	 /**
	  * 查找任意属性一样的对象,当返回值大于1时抛出异常
	  * @param obj
	  * @return
	  */
	 Map<String,Object> selectUniqueAnySame(Object obj);
	 
	 /**
	  * 查找任意属性一样的对象,当返回值大于1时抛出异常
	  * @param obj
	  * @return
	  */
	 <T>T  selectUniqueAnySame(Object obj,MapMapper<T> mapMapper);
	 /**
	  * 查找任意属性一样的对象,当返回值大于1时抛出异常
	  * @param obj
	  * @param rowMapper
	  * @return
	  */
	 <T>T  selectUniqueAnySame(Object obj,RowMapper<T> rowMapper);
	 /**
	  * 查找一样的对象
	  * @param obj
	  * @return
	  */
	 List<Map<String,Object>>  selectAllSame(Object obj);
	 
	 /**
	  * 查找一样的对象
	  * @param obj
	  * @param rowMapper
	  * @return
	  */
	 <T>List<T>  selectAllSame(Object obj,RowMapper<T> rowMapper);
	 /**
	  * 查找任意属性一致的值
	  * @param obj
	  * @return
	  */
	 List<Map<String, Object>>  selectAnySame(Object obj);
	
	 /**
	  * 
	  * @param obj
	  * @param rowMapper
	  * @return
	  */
	 <T>List<T>  selectAnySame(Object obj,RowMapper<T> rowMapper);
	 /**
	  * 
	  * @param obj
	  * @return
	  */
	 List<Map<String, Object>>selectAllLike(Object obj);
	/**
	 * 
	 * @param obj
	 * @param rowMapper
	 * @return
	 */
	 <T> List<T> selectAllLike(Object obj, RowMapper<T> rowMapper);
	 /**
	  * 
	  * @param obj
	  * @return
	  */
	 List<Map<String, Object>>selectAnyLike(Object obj);
	 
	 /**
	  * 
	  * @param obj
	  * @param rowMapper
	  * @return
	  */
	 <T>List<T>selectAnyLike(Object obj,RowMapper<T> rowMapper);
	
	 
	 <T>T selectById(Object obj, Class<T> requiredType) ;
			
		
	 <T>T selectUniqueAllSame(Object obj, Class<T> requiredType);
		
	 <T>T selectUniqueAnySame(Object obj, Class<T> requiredType) ;
		
	 <T>List<T> selectAllSame(Object obj, Class<T> elementType) ;
		
	 <T>List<T> selectAnySame(Object obj, Class<T> elementType) ;
	 /**
	  * 查找数量
	  * @param clazz
	  * @return
	  */
	 Long selectCount(Class<?> clazz) ;
	 /**
	  * 查找数量
	  * @param obj
	  * @param link
	  * @param ope
	  * @return
	  */
	 Long selectCount(Object obj,LinkedType link,OperatorType ope) ;
	 /**
	  * 搜索EntityCount
	  * @param obj
	  * @param link
	  * @param ope
	  * @return
	  */
	 EntityCount<Map<String,Object>> selectEntityCount(Object obj,LinkedType link,OperatorType ope) ;
	 /**
	  * 
	  * @param obj
	  * @param link
	  * @param ope
	  * @return
	  */
	 List<Map<String,Object>> selectListMap(Object obj, LinkedType link, OperatorType ope);
	 
	 <T>List<T> selectT(Object obj, LinkedType link, OperatorType ope);
	 
	 <T> EntityCount<T> selectTEntityCount(Object obj, LinkedType link, OperatorType ope);
}
