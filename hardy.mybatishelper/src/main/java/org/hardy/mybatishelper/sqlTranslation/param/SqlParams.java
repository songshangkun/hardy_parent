package org.hardy.mybatishelper.sqlTranslation.param;

import java.util.HashMap;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.hardy.mybatishelper.sqlTranslation.SQLKeySet;
import org.hardy.reflex.SpringReflext;
import org.hardy.sqlhelper.enums.LinkedType;
import org.hardy.sqlhelper.enums.OperatorType;

public class SqlParams extends HashMap<String, Object> implements SQLKeySet{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	  * 内置条件对象
	  */
	 private Object conditionObject = null;
	 
	 /**
	  * 内置目标对象
	  */
	 private Object targetObject = null;
	 
	 /**
	  * 内置SQL对象
	  */
	 private SQL interfaceSql;

	public SqlParams(){this.SAFE(true);}
	/**
	 * 构造设置sql语句
	 * @param sql sql语句
	 */
	public SqlParams(String sql){ this();this.SQL(sql);}
	/**
	 * 构造设置实体类
	 * @param clazz 实体类
	 */
	public SqlParams(Class<?> clazz){this();this.CLASS(clazz); }
	/**
	 * 构造
	 * @param obj  CRUD操作对象实例
	 */
	public SqlParams(Object obj){this();this.OBJECT(obj); }
	
	/**
	 * 设置目标对象和内置对象
	 * @param target
	 * @param condition
	 */
	public SqlParams(Object target,Object condition){this();this.OBJECT(target); this.CONDITION(condition);}
	/**
	 * 设置目标对象和条件对象
	 * @param clazz
	 * @param target
	 * @param condition
	 */
	public SqlParams(Class<?> clazz,Map<String, Object> target,Map<String, Object> condition){ 
		 this(clazz);
		 for(String ts:target.keySet()){
			 this.put(TARGET_START+ts, target.get(ts));
		 }
		 for(String cs:condition.keySet()){
			 this.put(CONDITION_START+cs, condition.get(cs));
		 }
	}
	
	/**
	 * 设置目标对象和内置条件对象
	 * @param target  targetObject
	 * @param propName
	 * @param param
	 */
	public SqlParams(Object target,String propName,Object param){this();this.OBJECT(target); this.CONDITION(propName,param);}
	/**
	 * 
	 * @param clazz 实体类
	 * @param id   id对应的值
	 */
	public SqlParams(Class<?> clazz,Object id){ 
		 this();
		 this.CLASS(clazz);
		 this.ID(id);
	}
	/**
	 * 
	 * @param obj   CRUD操作对象实例
	 * @param link   关联
	 * @param ope  操作符号
	 */
	public SqlParams(Object obj,LinkedType link,OperatorType ope){ 
		 this();
		 this.OBJECT(obj);
		 this.LINKED(link);
		 this.OPERATOR(ope);
	}
	/**
	 * 构造设置
	 * @param sql SQL语句
	 * @param params 使用参数
	 */
	public SqlParams(String sql,KeyValue params){ 
		 this(sql);
		 this.putAll(params);
	}
	/**
	 * 设置org.apache.ibatis.jdbc.SQL对象
	 * @param sql
	 */
	public SqlParams(SQL sql){
		 this.interfaceSql = sql;
	}
	
 //******* 开始普通方法******
	/**
	 * 获取SQL对象
	 * @return
	 */
	public SQL getInterfaceSql(){
		return this.interfaceSql;
	}
	/**
	 * 设置SQL对象
	 * @param sql
	 */
	public void setInterfaceSql(SQL sql){
		 this.interfaceSql = sql;
	}
	/**
	 * 设置SQL语句
	 * @param sql
	 * @return
	 */
	 public SqlParams SQL(String sql){
		  this.put(SQL, sql);
		  return this;
	 }
	 /**
	  * 设置实体类
	  * @param clazz
	  * @return
	  */
	 public SqlParams CLASS(Class<?> clazz){
		  this.put(CLASS, clazz);
		  return this;
	 }
	 /**
	  * 设置ID的值
	  * @param obj
	  * @return
	  */
	 public SqlParams ID(Object obj){
		  this.put(ID, obj);
		  return this;
	 }
	 /**
	  * 数据库是否使用distinct搜索
	  * @param distinct
	  * @return
	  */
	 public SqlParams DISTINCT(Boolean distinct){
		  this.put(DISTINCT, distinct);
		  return this;
	 }
	 
	 /**
	  * 设置数据库操作是否属于安全操作
	  * @param safe
	  * @return
	  */
	 public SqlParams SAFE(Boolean safe){
		  this.put(SAFE, safe);
		  return this;
	 }
	 /**
	  * 设置CRUD操作的实体类实例targetObject
	  * @param obj
	  * @return
	  */
	 public SqlParams OBJECT(Object obj){
		 this.targetObject = obj;
		  return this;
	 }
	 /**
	  * 设置关联
	  * @param link
	  * @return
	  */
	 public SqlParams LINKED(LinkedType link){
		 this.put(LINKED, link);
		  return this;
	 }
	 /**
	  * 设置操作符号
	  * @param ope
	  * @return
	  */
	 public SqlParams OPERATOR(OperatorType ope){
		 this.put(OPERATOR, ope);
		  return this;
	 }
	  /**
	   * 设置条件对象
	   * @param obj
	   * @return
	   */
	 public SqlParams CONDITION(Object obj){
		 this.conditionObject = obj;
		  return this;
	 }
	 
	 /**
	  * 设置条件对象的属性,直接将propName的值设置到内置conditionObject条件对象中
	  * @param propName
	  * @param obj
	  * @return
	  */
	 public SqlParams CONDITION(String propName,Object obj){
		 if(this.conditionObject==null){
			  try {
				this.conditionObject = this.targetObject.getClass().newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 SpringReflext.set(this.conditionObject, propName, obj);
		  return this;
	 }
	 /**
	  * 设置目标map
	  * @param name
	  * @param obj
	  * @return
	  */
	 public SqlParams TARGET(String name,Object obj){
		  this.put(TARGET_START+name, obj);
		  return this;
	 }
	 /**
	  * 设置条件map
	  * @param name
	  * @param obj
	  * @return
	  */
	 public SqlParams CONDITION_MAP(String name,Object obj){
		  this.put(CONDITION_START+name, obj);
		  return this;
	 }
	 /**
	  * 删除内置条件对象
	  */
	 public SqlParams clean(){
		 this.targetObject = null;
		 this.conditionObject=null;
		 super.clear();
		 return this;
	 }
	/**
	 * 添加条件
	 * @param key
	 * @param value
	 */
	 public SqlParams add(String key,Object value){
		  this.put(key, value);
		  return this;
	 }
  
	 public Object getConditionObject() {
		return conditionObject;
	}
	public void setConditionObject(Object conditionObject) {
		this.conditionObject = conditionObject;
	}
	public Object getTargetObject() {
		return targetObject;
	}
	public void setTargetObject(Object targetObject) {
		this.targetObject = targetObject;
	}
	
	

	public static class KeyValue extends HashMap<String, Object>{
		  /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
 
		  
		  public KeyValue(){}
		  
		  public KeyValue(String name,Object value){
			   this.put(name, value); 
		  }

		 public KeyValue $(String name,Object value){
			  this.put(name, value); 
			  return this;
		 }
		  
		  
	 }

	@Override
	public String toString() {
		return "SqlParams [conditionObject=" + conditionObject + ", targetObject=" + targetObject + ","+super.toString()+"]";
	}

 
}
