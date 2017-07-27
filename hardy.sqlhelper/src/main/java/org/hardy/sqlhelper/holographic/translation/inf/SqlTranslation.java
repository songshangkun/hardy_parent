package org.hardy.sqlhelper.holographic.translation.inf;

import java.io.Serializable;
import java.util.Map;

import org.hardy.sqlhelper.enums.LinkedType;
import org.hardy.sqlhelper.enums.OperatorType;
import org.hardy.sqlhelper.holographic.HologResolve;

/**
 * 全部sql的翻译接口
 * @author ssk
 *
 */
public interface SqlTranslation {
	
	 static class printSQL{
		  public static boolean show;
	}
	public static printSQL finalObject = new printSQL();
	/**
	 * 返回设置的HologResolve对象
	 * @return
	 */
	public HologResolve getHologResolve();
	
	/**
	 * 设置HologResolve解析式图
	 * @param hologResolve
	 */
	public void setHologResolve(HologResolve hologResolve);
	/**
	 * 创建数据库表
	 * @param clazz
	 */
	Condition<? extends Object> createTable(Class<?> clazz);
	/**
	 * 删除数据库表
	 * @param clazz
	 */
	Condition<? extends Object> dropTable(Class<?> clazz);
	/**
	 * 调整数据库安全模式 0为可清空表，1为不能清空表
	 * @param safe
	 * @return
	 */
	public Condition<? extends Object> changeSafeModel(int safe);
	/**
	 * 清空整个实体类的表
	 * @param clazz
	 * @return
	 */
	 Condition<? extends Object> cleanTable(Class<?> clazz);
	/**
	 * 增加一个对象实体
	 * @param obj
	 * @return
	 */
	 Condition<? extends Object> insert(Object obj);
	 /**
	  * 根据id删除一个对象实体
	  * @param id
	  * @param clazz
	  * @return
	  */
	 Condition<? extends Object> deleteById(Serializable id,Class<?> clazz);
	 /**
	  * 根据id删除一个对象实体
	  * @param obj
	  * @return
	  */
	 Condition<? extends Object> deleteById(Object obj);
	 /**
	  * 根据条件删除
	  * @param obj
	  * @param link  连接关系
	  * @return
	  */
	 Condition<? extends Object> deleteByCondition(Object obj,LinkedType link);
	 
	 /**
	  * 通过id更改一个对象
	  * @param obj
	  * @return
	  */
	 Condition<? extends Object> updateById(Object obj);
	 /**
	  * 根据条件修改一个对象实体,id永远不能改变
	  * @param obj  修改的对象
	  * @param params  筛选的条件  map.key  属性名称, map.value 属性的值
	  * @return
	  */
	 Condition<? extends Object> updateByCondition(Object obj,Map<String, Object> params);
	 
	 /**
	  * 无条件更改
	  * @param obj
	  * @return
	  */
	 Condition<? extends Object> updateSansCondition(Object obj);
	 /**
	  * 根据id搜索
	  * @return
	  */
	 Condition<? extends Object> selectById(Object obj);
	 /**
	  * 根据id查找对象
	  * @param id
	  * @param clazz
	  * @return
	  */
	 Condition<? extends Object> selectById(Serializable id,Class<?> clazz);
	 /**
	  * 获取相同结果集
	  * @param obj
	  * @param link
	  * @return
	  */
	 Condition<? extends Object> selectSame(Object obj,LinkedType link);
	 /**
	  *  根据相同条件and搜索
	  * @param obj
	  * @return
	  */
	 Condition<? extends Object> selectAllSame(Object obj);
	 /**
	  * 根据相同条件or搜索
	  * @param obj
	  * @return
	  */
	 Condition<? extends Object> selectAnyOneSame(Object obj);
	 /**
	  * 
	  * @param clazz
	  * @param firstResult
	  * @param maxResult
	  * @return
	  */
	 Condition<? extends Object> selectAll(Class<?> clazz,Integer firstResult,Integer maxResult);
	 /**
	  * 获取全部的数量
	  * @param clazz
	  * @return
	  */
	 Condition<? extends Object> selectAllCount(Class<?> clazz);
	 /**
	  * 根据条件模糊搜索
	  * @param obj
	  * @param link
	  * @return
	  */
	 Condition<? extends Object> selectLike(Object obj,LinkedType link);
	 /**
	  * 根据相同条件and模糊搜索
	  * @param obj
	  * @return
	  */
	 Condition<? extends Object> selectAllLike(Object obj);
	 /**
	  * 根据相同条件or模糊搜索
	  * @param obj
	  * @return
	  */
	 Condition<? extends Object> selectAnyOneLike(Object obj);
	 /**
	  * 查找实体类表数据数量
	  * @param obj
	  * @return
	  */
	 Condition<? extends Object> selectCount(Object obj,LinkedType link,OperatorType ope);
	 /**
	  * 根据条件搜索
	  * @param obj
	  * @param link
	  * @param ope
	  * @return
	  */
	 Condition<? extends Object> selectByCondition(Object obj,LinkedType link,OperatorType ope);
	 
	 //*****************************以下是直接搜索对象的方法****************************
	 /**
	  * 根据id查找对象
	  * @param obj
	  * @return
	  */
	 Condition<? extends Object> selectEntityById(Object obj);
	 /**
	  * 根据id查找对象
	  * @param id
	  * @param clazz
	  * @return
	  */
	 Condition<? extends Object> selectEntityById(Serializable id,Class<?> clazz);
	 /**
	  * 获取相同结果集
	  * @param obj
	  * @param link
	  * @return
	  */
	 Condition<? extends Object> selectEntitySame(Object obj,LinkedType link);
	 /**
	  *  根据相同条件and搜索
	  * @param obj
	  * @return
	  */
	 Condition<? extends Object> selectEntityAllSame(Object obj);
	 /**
	  * 根据相同条件or搜索
	  * @param obj
	  * @return
	  */
	 Condition<? extends Object> selectEntityAnyOneSame(Object obj);
	 /**
	  * 
	  * @param clazz
	  * @param firstResult
	  * @param maxResult
	  * @return
	  */
	 Condition<? extends Object> selectEntityAll(Class<?> clazz,Integer firstResult,Integer maxResult);
	  
	 /**
	  * 根据条件模糊搜索
	  * @param obj
	  * @param link
	  * @return
	  */
	 Condition<? extends Object> selectEntityLike(Object obj,LinkedType link);
	 /**
	  * 根据相同条件and模糊搜索
	  * @param obj
	  * @return
	  */
	 Condition<? extends Object> selectEntityAllLike(Object obj);
	 /**
	  * 根据相同条件or模糊搜索
	  * @param obj
	  * @return
	  */
	 Condition<? extends Object> selectEntityAnyOneLike(Object obj);
	 
	 
	 Condition<? extends Object> selectEntityByCondition(Object obj,LinkedType link,OperatorType ope);
}
