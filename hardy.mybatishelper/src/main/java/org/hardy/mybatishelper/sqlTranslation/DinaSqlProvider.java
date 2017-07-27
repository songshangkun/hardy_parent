package org.hardy.mybatishelper.sqlTranslation;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.type.TypeHandler;
import org.apache.log4j.Logger;
import org.hardy.mybatishelper.sqlTranslation.param.SqlParams;
import org.hardy.reflex.SpringReflext;
import org.hardy.sqlhelper.enums.LinkedType;
import org.hardy.sqlhelper.enums.OperatorType;
import org.hardy.sqlhelper.holographic.HologResolve;
import org.hardy.sqlhelper.holographic.OprationalVariable;
import org.hardy.sqlhelper.holographic.TableConstruct;
import org.hardy.sqlhelper.holographic.TableConstruct.PropColumn;
import org.hardy.sqlhelper.holographic.translation.util.LogUtil;
import org.hardy.statics.exception.NotSupportException;
import org.hardy.statics.exception.ParamNotNullException;
import org.hardy.util.decide.NullOREmpty;


public class DinaSqlProvider implements SQLKeySet{
	
	private final static Logger log = Logger.getLogger(DinaSqlProvider.class);
	 
	/**
	 * 直接运行sqlparams对象
	 */
	public static final String excuteSQL = "excuteSQL";
	 
	public static final String insertObj = "insertObj";
	
	public static final String deleteObjectById = "deleteObjectById"; 
	
	public static final String deleteSQLById = "deleteSQLById"; 
	
	public static final String deleteObject = "deleteObject"; 
	
	public static final String deleteSqlParams = "deleteSqlParams"; 
	
	public static final String updateObjectById = "updateObjectById"; 
	
	public static final String updateSqlParams = "updateSqlParams"; 
	
	public static final String updateSqlParamsMap = "updateSqlParamsMap"; 
	
	public static final String selectSameObject = "selectSameObject"; 
	
	public static final String selectObjectById = "selectObjectById"; 
	
	public static final String selectSqlById = "selectSqlById"; 
	
	public static final String selectSqlParams = "selectSqlParams"; 
	
	public static final String excuteInterface = "excuteInterface"; 
     /**
      * 数据库和实体类对照视图
      */
	   protected HologResolve hologResolve = new HologResolve();
	   /**
	    * 是否打印sql语句
	    */
	   public static boolean printSQL = false;
	   /**
	    * 是否执行安全的操作
	    */
	   public static boolean safe = true;
  
	   /**
	    * 运行含有sql的map
	    * @param sql {SQL:sql语句,可使用el语言,其他使用el名称和设置的值}
	    * @return
	    */
	   public String excuteSQL(SqlParams sql){
		   if(printSQL)LogUtil. print(log, sql);
		     return sql.get(SQL).toString();
	   }
	   
	  /**
	   * 根据id删除一个对象
	   * @param obj
	   */
	  public String deleteObjectById(final Object obj){
		   final TableConstruct tc = getTc(obj);
		   final SQL sql = new SQL(){
				{    
					DELETE_FROM(tc.getTableName());
					if(tc.getId()==null) throw new ParamNotNullException("@ID not null");
					Object idObject = SpringReflext.get(obj, tc.getId().getPropName());
					if(TypeHandler.class.isAssignableFrom(idObject.getClass())&&
							"true".equalsIgnoreCase(tc.getId().getMapperType()))
						 WHERE(tc.getId().getColumnName()+"=#{"+tc.getId().getPropName()+"}");
					else {
						String idName = tc.getId().getPropName();
						if(!NullOREmpty.isEmptyString(tc.getId().getJoinPropValue()))
							idName+=("."+tc.getId().getJoinPropValue());
						WHERE(tc.getId().getColumnName()+"=#{"+idName+"}");
					}
				}
			};
			String result = sql.toString();
			if(printSQL)LogUtil. print(log, result,obj);
			return result;
	  }
	 /**
	  * 根据SqlParams删除一个对象 
	  * @param sql  {CLASS:类,ID:id的值}
	  */
	  public String deleteSQLById(SqlParams sqlParams){
		   final TableConstruct tc = hologResolve.getStruct((Class<?>)sqlParams.get(CLASS));
		   if(tc.getId()==null) throw new NotSupportException("@ID NOT NULL");
		    SQL sql = new SQL(){
			    	{
			    		DELETE_FROM(tc.getTableName());
			    		WHERE(tc.getId().getColumnName()+"=#{"+ID+"}");
			    	}
		    };
		    String result = sql.toString();
			if(printSQL)LogUtil. print(log, result,sqlParams);
			return result;
	  }
      /**
       * 删除条件一样的对象
       * @param obj
       * @return
       */
	  public String deleteObject(final Object obj){
		  final TableConstruct tc = getTc(obj);
		  final OprationalVariable oVariable = getOv(obj);
		   final SQL sql = new SQL(){
				{    
					DELETE_FROM(tc.getTableName());
					 if(tc.getId()!=null){
						 conditionFinalPhrase(obj, tc.getId(), this, LinkedType.AND, OperatorType.EQ);
						 }
					for(PropColumn pc:tc.getColumns()){
						 conditionFinalPhrase(obj, pc, this, LinkedType.AND, OperatorType.EQ);
					}
					//设置补充条件
					 switch (oVariable.getCondition_patten()) {
						case SANS: 
							break;
						case CLASS:
						case PROPERTY:
							WHERE(oVariable.getCondition());
							break;
						case METHOD:
							WHERE((String) SpringReflext.get(obj, oVariable.getCondition()));
						default:
							break;
						}
				}
			};
			
			String result = sql.toString();
			if(printSQL)LogUtil. print(log, result,obj);
			if(DinaSqlProvider.safe&&result.indexOf("WHERE")<0) throw new NotSupportException("this ope is not safe");
			return result;
	  }
	  /**
       * 删除 SqlParams中的对象
       * @param obj
       * @return
       */
	  public String deleteSqlParams(final SqlParams sqlParams){ 
		  final TableConstruct tc = getTc(sqlParams.getTargetObject());
		  final OprationalVariable oVariable = getOv(sqlParams.getTargetObject());
		   final SQL sql = new SQL(){
				{    
					DELETE_FROM(tc.getTableName());
					 if(tc.getId()!=null){
						 conditionFinalPhraseSqlParams(sqlParams,"targetObject", tc.getId(),this);
						 }
					for(PropColumn pc:tc.getColumns()){
						conditionFinalPhraseSqlParams(sqlParams,"targetObject", pc, this);
					}
					//设置补充条件
					 switch (oVariable.getCondition_patten()) {
						case SANS: 
							break;
						case CLASS:
						case PROPERTY:
							WHERE(oVariable.getCondition());
							break;
						case METHOD:
							WHERE((String) SpringReflext.get(sqlParams.getTargetObject(), oVariable.getCondition()));
						default:
							break;
						}
				}
			};
			String result = sql.toString();
			if(printSQL)LogUtil. print(log, result,sqlParams);
			if((Boolean)sqlParams.get(SAFE)&&result.indexOf("WHERE")<0) throw new NotSupportException("this ope is not safe");
			return result;
	  }
	  
	  /**
	   * 根据ID更新对象
	   * @param obj
	   * @return
	   */
	  public String updateObjectById(final Object obj){
		  final TableConstruct tc = getTc(obj);
		  final OprationalVariable oVariable = getOv(obj);
		  if(tc.getId()==null||SpringReflext.get(obj, tc.getId().getPropName())==null){
			  throw new NotSupportException("@ID not is NULL");
		  }
		  SQL sql = new SQL(){
			  {
				  UPDATE(tc.getTableName());
				  for(PropColumn pc:tc.getColumns()){
					  updateFinalPhrase(obj, pc, this);
				  }
				  Object idObject = SpringReflext.get(obj, tc.getId().getPropName());
						String pName = tc.getId().getPropName();
						//如果对象实现BaseTypeHandler接口
						if(TypeHandler.class.isAssignableFrom(idObject.getClass())&&
								"true".equalsIgnoreCase(tc.getId().getMapperType())){
						}else {
							if(!NullOREmpty.isEmptyString(tc.getId().getJoinPropValue()))
								pName +=("."+tc.getId().getJoinPropValue());
						}
						WHERE(tc.getId().getColumnName()+"=#{"+pName+"}");
						//补充条件
						switch (oVariable.getCondition_patten()) {
						case SANS: 
							break;
						case CLASS:
						case PROPERTY:
							WHERE(oVariable.getCondition());
							break;
						case METHOD:
							WHERE((String) SpringReflext.get(obj, oVariable.getCondition()));
						default:
							break;
						}
			  }  
		  };
		  String result = sql.toString();
			if(printSQL)LogUtil. print(log, result,obj);
			return result;
	  }
	  
	  /**
	   * 更新 SqlParams中的对象
	   * @param sqlParams
	   * @return
	   */
	  public String updateSqlParams(final SqlParams sqlParams){
		  final TableConstruct tc = getTc(sqlParams.getTargetObject());
		  final OprationalVariable oVariable = getOv(sqlParams.getConditionObject());
		   final SQL sql = new SQL(){
				{    
					UPDATE(tc.getTableName());
					 if(tc.getId()!=null)updateFinalPhraseSqlParams(sqlParams, tc.getId(), this);
					 for(PropColumn pc:tc.getColumns()){
						 updateFinalPhraseSqlParams(sqlParams, pc, this);
					 }
					 if(sqlParams.getConditionObject()!=null){
						 if(tc.getId()!=null){
                                conditionFinalPhraseSqlParams(sqlParams, "conditionObject", tc.getId(), this);
							 }
						for(PropColumn pc:tc.getColumns()){
							 conditionFinalPhraseSqlParams(sqlParams, "conditionObject", pc, this);
						}
					 }
					 //设置补充条件
					 switch (oVariable.getCondition_patten()) {
						case SANS: 
							break;
						case CLASS:
						case PROPERTY:
							WHERE(oVariable.getCondition());
							break;
						case METHOD:
							WHERE((String) SpringReflext.get(sqlParams.getConditionObject(), oVariable.getCondition()));
						default:
							break;
						}
				}
			};
			String result = sql.toString();
			if(printSQL)LogUtil. print(log, result,sqlParams);
			if((Boolean)sqlParams.get(SAFE)&&result.indexOf("WHERE")<0) throw new NotSupportException("this ope is not safe");
			return result;
	  }
	  /**
	   * 利用map完成修改对象操作
	   * @param sqlParams
	   * @return
	   */
	 public  String updateSqlParamsMap(final SqlParams sqlParams){
		  final TableConstruct tc = this.hologResolve.getStruct((Class<?>)sqlParams.get(CLASS));
		  final Map<String, String> map = tc.getNameMap();
		  final Map<String, Object> targetmap = new HashMap<>();
		  final Map<String, Object> conditionmap = new HashMap<>();
		  for(String key:sqlParams.keySet()){
			    if(key.startsWith(TARGET_START)) targetmap.put(key, sqlParams.get(key));
			    else if(key.startsWith(CONDITION_START)) conditionmap.put(key, sqlParams.get(key));
		   }
		   final SQL sql = new SQL(){
			   {
				   UPDATE(tc.getTableName());
				  for(String name:targetmap.keySet()){
					  SET(map.get(name.substring(3))+"=#{"+name+"}");
				  }
				  for(String name:conditionmap.keySet()){
					  if(LinkedType.OR.equals(sqlParams.get(LINKED))) OR();
					  String ope = " = ";
					  if(sqlParams.get(OPERATOR)!=null)ope =((OperatorType) sqlParams.get(OPERATOR)).getVal();
					  WHERE(map.get(name.substring(3))+ope+"#{"+name+"}");
				  }
			   }
		   };
		   String result = sql.toString();
			if(printSQL)LogUtil. print(log, result,sqlParams);
			if((Boolean)sqlParams.get(SAFE)&&result.indexOf("WHERE")<0) throw new NotSupportException("this ope is not safe");
			return result;
	 }
	 
	 /**
	    * 插入一个对象
	    * @param obj
	    * @return
	    */
	  public String insertObj(final Object obj){
		  final TableConstruct tc = getTc(obj);
			 final SQL sql = new SQL(){
					{    
						INSERT_INTO(tc.getTableName());
						if(tc.getId()!=null){
							insertFinalPhrase(obj, tc.getId(), this);
						}
						for(PropColumn pc:tc.getColumns()) {
							insertFinalPhrase(obj, pc, this);
						}	 
					}
				};
				String result = sql.toString();
				if(printSQL)LogUtil. print(log, result,obj);
				return result;
	  }
	  
	 /**
	  * 根据对象搜索属性相同的其他对象
	  * @param obj
	  * @return
	  */
	 public String  selectSameObject(final Object obj){
		 final TableConstruct tc = getTc(obj);
		 final OprationalVariable oVariable = getOv(obj);
		  SQL sql = new SQL(){
			  {
				  String target = "*";
					switch (oVariable.getResult_patten()) {
					case SANS:
					case CLASS:
					case PROPERTY:
						target = oVariable.getResult();
						break;
					case METHOD:
						target = SpringReflext.get(obj, oVariable.getResult());
						//如果食用方法模式,在方法返回空时首选全部属性字段对应
						if(target==null||target.trim().equals("")) target =tc.columnPropNames();
						break;
					default:
						target = "*";
						break;
					}
				  SELECT_DISTINCT(target);
				  FROM(tc.getTableName());
				  if(tc.getId()!=null){
					  conditionFinalPhrase(obj, tc.getId(), this, LinkedType.AND, OperatorType.EQ);
				  }
				  for(PropColumn pc:tc.getColumns()){
					  conditionFinalPhrase(obj,pc, this, LinkedType.AND, OperatorType.EQ);  
				  }
				//设置补充条件
					 switch (oVariable.getCondition_patten()) {
						case SANS: 
							break;
						case CLASS:
						case PROPERTY:
							WHERE(oVariable.getCondition());
							break;
						case METHOD:
							WHERE((String) SpringReflext.get(obj, oVariable.getCondition()));
						default:
							break;
						}
					 //设置分组条件
					 switch (oVariable.getGroupBy_patten()) { 
						case CLASS:
						case PROPERTY: 
							GROUP_BY(oVariable.getGroupBy());
							break;
						case METHOD: 
							GROUP_BY((String) SpringReflext.get(obj, oVariable.getGroupBy()));
							break;
						default:
							break;
						}
					//设置排序条件
						switch (oVariable.getOrderBy_patten()) {
						case CLASS:
						case PROPERTY:
							ORDER_BY(oVariable.getOrderBy());
							break;
						case METHOD: 
							ORDER_BY((String) SpringReflext.get(obj, oVariable.getOrderBy()));
							break;
						default:
							break;
						}
			  }
		  };
		  String result = sql.toString();
		  if(oVariable.getFirstResult()!=null&&oVariable.getMaxResult()!=null){
				Integer firstResult = SpringReflext.get(obj, oVariable.getFirstResult());
				Integer maxResult = SpringReflext.get(obj, oVariable.getMaxResult());
				  if(firstResult!=null&&maxResult!=null){
					  result = result+ " LIMIT "+firstResult+","+maxResult;
				  }
			}
			if(printSQL)LogUtil. print(log, result,obj);
			return result;
	 }
	 /**
	  * 指按照id搜索,注解条件会影响搜索结果
	  * @param obj
	  * @return
	  */
	 public String  selectObjectById(final Object obj){
		 final TableConstruct tc = getTc(obj);
		 final OprationalVariable oVariable = getOv(obj);
		  SQL sql = new SQL(){
			  {
				  String target = "*";
					switch (oVariable.getResult_patten()) {
					case SANS:
					case CLASS:
					case PROPERTY:
						target = oVariable.getResult();
						break;
					case METHOD:
						target = SpringReflext.get(obj, oVariable.getResult());
						//如果食用方法模式,在方法返回空时首选全部属性字段对应
						if(target==null||target.trim().equals("")) target =tc.columnPropNames();
						break;
					default:
						target = "*";
						break;
					}
				  SELECT_DISTINCT(target);
				  FROM(tc.getTableName());
				  if(tc.getId()==null) throw new NotSupportException("@ID is not NULL");
				  conditionFinalPhrase(obj, tc.getId(), this, LinkedType.AND, OperatorType.EQ);
				//设置补充条件
					 switch (oVariable.getCondition_patten()) {
						case SANS: 
							break;
						case CLASS:
						case PROPERTY:
							WHERE(oVariable.getCondition());
							break;
						case METHOD:
							WHERE((String) SpringReflext.get(obj, oVariable.getCondition()));
						default:
							break;
						}
					 //设置分组条件
					 switch (oVariable.getGroupBy_patten()) { 
						case CLASS:
						case PROPERTY: 
							GROUP_BY(oVariable.getGroupBy());
							break;
						case METHOD: 
							GROUP_BY((String) SpringReflext.get(obj, oVariable.getGroupBy()));
							break;
						default:
							break;
						}
					//设置排序条件
						switch (oVariable.getOrderBy_patten()) {
						case CLASS:
						case PROPERTY:
							ORDER_BY(oVariable.getOrderBy());
							break;
						case METHOD: 
							ORDER_BY((String) SpringReflext.get(obj, oVariable.getOrderBy()));
							break;
						default:
							break;
						}
			  }
		  };
		  String result = sql.toString();
			if(printSQL)LogUtil. print(log, result,obj);
			return result;
	 }
	 /**
	  * 根据类和id搜索
	  * @param sqlParams
	  * @return
	  */
	public String selectSqlById(final SqlParams sqlParams){
		 final TableConstruct tc = hologResolve.getStruct((Class<?>) sqlParams.get(CLASS));
		 final OprationalVariable oVariable = hologResolve.getVariabl((Class<?>) sqlParams.get(CLASS));
		 SQL sql = new SQL(){
				{
					String target = "*";
					switch (oVariable.getResult_patten()) {
					case SANS:
					case CLASS:
					case PROPERTY:
						target = oVariable.getResult();
						break;
					case METHOD:
						throw new NotSupportException("this method not support CallPatten[METHOD]");
					default:
						target = "*";
						break;
					}
					if(sqlParams.get(DISTINCT)!=null&&(Boolean)sqlParams.get(DISTINCT)==true){
						 SELECT_DISTINCT(target);
					}else{
						SELECT(target);
					}
					FROM(tc.getTableName());
					if(tc.getId()==null) throw new NotSupportException("@ID is not NULL");
					WHERE(tc.getId().getColumnName()+" = #{"+ID+"}");
					//设置补充条件
					 switch (oVariable.getCondition_patten()) {
						case SANS: 
							break;
						case CLASS:
						case PROPERTY:
							WHERE(oVariable.getCondition());
							break;
						case METHOD:
							throw new NotSupportException("this method not support CallPatten[METHOD]");
						default:
							break;
						}
					 //设置分组条件
					 switch (oVariable.getGroupBy_patten()) { 
						case CLASS:
						case PROPERTY: 
							GROUP_BY(oVariable.getGroupBy());
							break;
						case METHOD: 
							throw new NotSupportException("this method not support CallPatten[METHOD]");
						default:
							break;
						}
					//设置排序条件
						switch (oVariable.getOrderBy_patten()) {
						case CLASS:
						case PROPERTY:
							ORDER_BY(oVariable.getOrderBy());
							break;
						case METHOD: 
							throw new NotSupportException("this method not support CallPatten[METHOD]");
						default:
							break;
						}
				}
			};
			   String result = sql.toString();
				if(printSQL)LogUtil. print(log, result,sqlParams);
				return result;
	}
	 /**
	  * 根据SqlParams的TargetObject搜索对象
	  * @param sqlParams
	  * @return
	  */
	 public String  selectSqlParams(final SqlParams sqlParams){
		 final TableConstruct tc = getTc(sqlParams.getTargetObject());
		 final OprationalVariable oVariable = getOv(sqlParams.getTargetObject());
		SQL sql = new SQL(){
			{
				 String target = "*";
					switch (oVariable.getResult_patten()) {
					case SANS:
					case CLASS:
					case PROPERTY:
						target = oVariable.getResult();
						break;
					case METHOD:
						target = SpringReflext.get(sqlParams.getTargetObject(), oVariable.getResult());
						//如果食用方法模式,在方法返回空时首选全部属性字段对应
						if(target==null||target.trim().equals("")) target =tc.columnPropNames();
						break;
					default:
						target = "*";
						break;
					}
				if(sqlParams.get(DISTINCT)!=null&&(Boolean)sqlParams.get(DISTINCT)==true){
					 SELECT_DISTINCT(target);
				}else{
					SELECT(target);
				}
				FROM(tc.getTableName());
				if(tc.getId()!=null)
					conditionFinalPhraseSqlParams(sqlParams, "targetObject", tc.getId(), this);
				for(PropColumn pc:tc.getColumns()){
					conditionFinalPhraseSqlParams(sqlParams, "targetObject",pc, this);
				}
				//设置补充条件
				 switch (oVariable.getCondition_patten()) {
					case SANS: 
						break;
					case CLASS:
					case PROPERTY:
						WHERE(oVariable.getCondition());
						break;
					case METHOD:
						WHERE((String) SpringReflext.get(sqlParams.getTargetObject(), oVariable.getCondition()));
					default:
						break;
					}
				 //设置分组条件
				 switch (oVariable.getGroupBy_patten()) { 
					case CLASS:
					case PROPERTY: 
						GROUP_BY(oVariable.getGroupBy());
						break;
					case METHOD: 
						GROUP_BY((String) SpringReflext.get(sqlParams.getTargetObject(), oVariable.getGroupBy()));
						break;
					default:
						break;
					}
				//设置排序条件
					switch (oVariable.getOrderBy_patten()) {
					case CLASS:
					case PROPERTY:
						ORDER_BY(oVariable.getOrderBy());
						break;
					case METHOD: 
						ORDER_BY((String) SpringReflext.get(sqlParams.getTargetObject(), oVariable.getOrderBy()));
						break;
					default:
						break;
					}	
			}
		};
		   String result = sql.toString();
		   if(oVariable.getFirstResult()!=null&&oVariable.getMaxResult()!=null){
				Integer firstResult = SpringReflext.get(sqlParams.getTargetObject(), oVariable.getFirstResult());
				Integer maxResult = SpringReflext.get(sqlParams.getTargetObject(), oVariable.getMaxResult());
				  if(firstResult!=null&&maxResult!=null){
					  result = result+ " LIMIT "+firstResult+","+maxResult;
				  }
			}
			if(printSQL)LogUtil. print(log, result,sqlParams);
			return result;
	 }
	 /**
	  * 执行SQL接口
	  * @param sqlParams
	  * @return
	  */
	 public String excuteInterface(SqlParams sqlParams){
		 String result = sqlParams.getInterfaceSql().toString();
		 if(printSQL)LogUtil. print(log, result,sqlParams);
			return result;
	 }
 
	public HologResolve getHologResolve() {
		return hologResolve;
	}

	public void setHologResolve(HologResolve hologResolve) {
		this.hologResolve = hologResolve;
	}
	   
	/**
	 * 组织条件从句
	 * @param obj
	 * @param pc
	 * @param sql
	 * @param link
	 * @param ope
	 */
	private  void conditionFinalPhrase(Object obj,PropColumn pc,SQL sql,LinkedType link,OperatorType ope){
		 Object object = SpringReflext.get(obj, pc.getPropName());
		 if(object!=null){
			 if(TypeHandler.class.isAssignableFrom(object.getClass())&&
						"true".equalsIgnoreCase(pc.getMapperType())){
				    if(LinkedType.OR.equals(link)) sql.OR();
					 sql.WHERE(pc.getColumnName()+ope.getVal()+"#{"+pc.getPropName()+"}");
			 }
				else {
					String propName = pc.getPropName();
					if(!NullOREmpty.isEmptyString(pc.getJoinPropValue()))
						propName+=("."+pc.getJoinPropValue());
					if(LinkedType.OR.equals(link)) sql.OR();
					sql.WHERE(pc.getColumnName()+ope.getVal()+"#{"+propName+"}");
				}
		  }
	}
	/**
	 * 专用于集成在sqlparams中使用的情况
	 * @param obj
	 * @param pc
	 * @param sql
	 * @param link
	 * @param ope
	 */
	private  void conditionFinalPhraseSqlParams(SqlParams sqlParams,String tarOrCon,PropColumn pc,SQL sql){
		 Object object = null;
			if("targetObject".equals(tarOrCon))	object = SpringReflext.get(sqlParams.getTargetObject(), pc.getPropName());
			if("conditionObject".equals(tarOrCon))	object = SpringReflext.get(sqlParams.getConditionObject(), pc.getPropName());
		 if(object!=null){
			 if(TypeHandler.class.isAssignableFrom(object.getClass())&&
						"true".equalsIgnoreCase(pc.getMapperType())){
				    if(LinkedType.OR.equals(sqlParams.get(LINKED))) sql.OR();
				    if("targetObject".equals(tarOrCon)){
						sqlParams.put(TARGET_START+pc.getPropName(), object);
						sql.WHERE(pc.getColumnName()+((OperatorType)sqlParams.get(OPERATOR)).getVal()+"#{"+TARGET_START+pc.getPropName()+"}");
					}
					if("conditionObject".equals(tarOrCon)){
						sqlParams.put(CONDITION_START+pc.getPropName(), object);
						sql.WHERE(pc.getColumnName()+((OperatorType)sqlParams.get(OPERATOR)).getVal()+"#{"+CONDITION_START+pc.getPropName()+"}");
					}
			 }
				else {
					if(LinkedType.OR.equals(sqlParams.get(LINKED))) sql.OR();
					if("targetObject".equals(tarOrCon)){
						sqlParams.put(TARGET_START+pc.getPropName(), SpringReflext.getPropValue(object, pc.getJoinPropValue()));
						sql.WHERE(pc.getColumnName()+((OperatorType)sqlParams.get(OPERATOR)).getVal()+"#{"+TARGET_START+pc.getPropName()+"}");
					}
					if("conditionObject".equals(tarOrCon)){
						sqlParams.put(CONDITION_START+pc.getPropName(), SpringReflext.getPropValue(object, pc.getJoinPropValue()));
						sql.WHERE(pc.getColumnName()+((OperatorType)sqlParams.get(OPERATOR)).getVal()+"#{"+CONDITION_START+pc.getPropName()+"}");
					}
				
				}
		  }
	}
	/**
	 * 插入对象时的参数设置
	 * @param obj
	 * @param pc
	 * @param sql
	 */
	private  void insertFinalPhrase(Object obj,PropColumn pc,SQL sql){
		Object object = SpringReflext.get(obj, pc.getPropName());
		if(object!=null) {
			String pName = pc.getPropName();
			//如果对象实现BaseTypeHandler接口
			if(TypeHandler.class.isAssignableFrom(object.getClass())&&
					"true".equalsIgnoreCase(pc.getMapperType())){
				sql.VALUES(pc.getColumnName(), "#{"+pName+"}");
			}else {
				if(!NullOREmpty.isEmptyString(pc.getJoinPropValue()))
					pName +=("."+pc.getJoinPropValue());
				sql.VALUES(pc.getColumnName(), "#{"+pName+"}");
			}
		}
	}
	/**
	 * 更新语句
	 * @param obj
	 * @param pc
	 * @param sql
	 */
	private  void updateFinalPhrase(Object obj,PropColumn pc,SQL sql){
		Object object = SpringReflext.get(obj, pc.getPropName());
		if(object!=null) {
			String pName = pc.getPropName();
			//如果对象实现BaseTypeHandler接口
			if(TypeHandler.class.isAssignableFrom(object.getClass())&&
					"true".equalsIgnoreCase(pc.getMapperType())){
				sql.SET(pc.getColumnName()+"=#{"+pName+"}");
			}else {
				if(!NullOREmpty.isEmptyString(pc.getJoinPropValue()))
					pName +=("."+pc.getJoinPropValue());
				sql.SET(pc.getColumnName()+"=#{"+pName+"}");
			}
		}
	}
	/**
	 * 专用于集成在sqlparams的环境中使用
	 * @param obj
	 * @param pc
	 * @param sql
	 */
	private  void updateFinalPhraseSqlParams(SqlParams sqlParams,PropColumn pc,SQL sql){
		Object object = SpringReflext.get(sqlParams.getTargetObject(), pc.getPropName());
		if(object!=null) {
			//如果对象实现BaseTypeHandler接口
			if(TypeHandler.class.isAssignableFrom(object.getClass())&&
					"true".equalsIgnoreCase(pc.getMapperType())){
				sqlParams.put(TARGET_START+pc.getPropName(), object);
				sql.SET(pc.getColumnName()+"=#{"+TARGET_START+pc.getPropName()+"}");
			}else {
				sqlParams.put(TARGET_START+pc.getPropName(), SpringReflext.getPropValue(object, pc.getJoinPropValue()));
				sql.SET(pc.getColumnName()+"=#{"+TARGET_START+pc.getPropName()+"}");
			}
		}
	}
    
	/**
	 * 获取TableConstruct
	 * @param obj
	 * @return
	 */
	private TableConstruct getTc(Object obj){
		 Class<?> clazz = obj.getClass();
		  TableConstruct tc = hologResolve.getStruct(clazz);
		  if(tc==null) throw new NotSupportException("hologResolve not find "+clazz.getName());	
	    return tc;
	}
	/**
	 * 获取OprationalVariable
	 * @param obj
	 * @return
	 */
	private OprationalVariable getOv(Object obj){
		 Class<?> clazz = obj.getClass();
		 OprationalVariable ov = hologResolve.getVariabl(clazz);
		  if(ov==null) throw new NotSupportException("hologResolve not find "+clazz.getName());	
	    return ov;
	}
}
