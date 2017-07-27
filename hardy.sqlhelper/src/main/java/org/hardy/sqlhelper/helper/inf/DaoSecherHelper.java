package org.hardy.sqlhelper.helper.inf;

import java.io.Serializable;
import java.util.List;

import org.hardy.sqlhelper.enums.LinkedType;
import org.hardy.sqlhelper.enums.OperatorType;
import org.hardy.sqlhelper.helper.EntityCount;
/**
 * 数据库操作接口
 * @author song
 *
 */
public interface DaoSecherHelper{
	/**
	  * 根据id查找对象
	  * @param obj
	  * @return
	  */
	 <T>T selectById(Object obj);
	 
	 /**
	  * 根据id查找对象
	  * @param id
	  * @param clazz
	  * @return
	  */
	 <T>T selectById(Serializable id, Class<?> clazz);
 
	 /**
	  *  根据相同条件and搜索
	  * @param obj
	  * @return
	  */
	 <T>List<T> selectAllSame(Object obj);
	 
	 <T>T selectUniqueAllSame(Object obj);
	 /**
	  * 根据相同条件or搜索
	  * @param obj
	  * @return
	  */
	 <T>List<T> selectAnyOneSame(Object obj);
	 
	 
	 <T>T selectUniqueAnyOneSame(Object obj);
	 /**
	  * 
	  * @param clazz
	  * @param firstResult
	  * @param maxResult
	  * @return
	  */
	 <T>List<T> selectAll(Class<?> clazz,Integer firstResult,Integer maxResult);
	  
	  
	 /**
	  * 根据相同条件and模糊搜索
	  * @param obj
	  * @return
	  */
	 <T>List<T> selectAllLike(Object obj);
	 /**
	  * 根据相同条件or模糊搜索
	  * @param obj
	  * @return
	  */
	 <T>List<T> selectAnyOneLike(Object obj);
	 /**
	  * 根据条件搜索
	  * @param obj
	  * @param link
	  * @param ope
	  * @return
	  */
	 <T>EntityCount<T> selectEntityCount(Object obj,LinkedType link,OperatorType ope);
}
