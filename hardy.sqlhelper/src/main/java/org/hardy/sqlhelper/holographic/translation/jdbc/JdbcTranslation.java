package org.hardy.sqlhelper.holographic.translation.jdbc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hardy.reflex.SpringReflext;
import org.hardy.sqlhelper.enums.LinkedType;
import org.hardy.sqlhelper.enums.OperatorType;
import org.hardy.sqlhelper.holographic.HologResolve;
import org.hardy.sqlhelper.holographic.OprationalVariable;
import org.hardy.sqlhelper.holographic.TableConstruct;
import org.hardy.sqlhelper.holographic.TableConstruct.PropColumn;
import org.hardy.sqlhelper.holographic.translation.inf.Condition;
import org.hardy.sqlhelper.holographic.translation.inf.MapperType;
import org.hardy.sqlhelper.holographic.translation.inf.SqlTranslation;
import org.hardy.sqlhelper.holographic.translation.util.LogUtil;
import org.hardy.statics.exception.NotImplementException;
import org.hardy.statics.exception.NotSupportException;
import org.hardy.statics.exception.ParamNotNullException;
import org.hardy.util.decide.NullOREmpty;

/**
 * 专用于jdbcTempLate的数据库操作
 * @author songs
 *
 */
public class JdbcTranslation implements SqlTranslation{
	
	private final static Logger log = Logger.getLogger(JdbcTranslation.class);
	/**
	 * 解析的容器
	 */
    private HologResolve hologResolve;
    /**
     * 对属性进行特殊的数据处理
     */
    private MapperTypeContext mapperTypeContext;
     
    @Override
	public HologResolve getHologResolve() {
		return hologResolve;
	}
	@Override
	public void setHologResolve(HologResolve hologResolve) {
		this.hologResolve = hologResolve;
	}
	
	
	@Override
	public  JdbcCondition changeSafeModel(int safe) {
		 return new JdbcCondition("SET SQL_SAFE_UPDATES = "+safe, new Object[]{});
	}

	@Override
	public JdbcCondition cleanTable(Class<?> clazz) {
		TableConstruct tc = this.hologResolve.getStruct(clazz);
		String sql = "DELETE FROM "+tc.getTableName();
		JdbcCondition jCondition = LogUtil.log(log, new JdbcCondition(sql, new Object[]{}));
		if(printSQL.show) LogUtil.print(jCondition);
		return jCondition;
	}

	@Override
	public JdbcCondition insert(Object obj) {
		if(obj==null) throw new ParamNotNullException("insert params is not null");
		TableConstruct tc = this.hologResolve.getStruct(obj.getClass());
		if(tc==null) throw new NotSupportException(obj.getClass().getClass()+" not in context");
		//**此处验证getJoinPropValue情况需优化
		if(!tc.isIdAutoIncrement()&&tc.getId()!=null){
			 if(SpringReflext.get(obj, tc.getId().getPropName())==null)
				 throw new ParamNotNullException(tc.getId().getPropName()+" not be null");
//			 String sousId;
//			 if((sousId=tc.getId().getJoinPropValue())!=null&&sousId.trim().length()>0)
//				 if(SpringReflext.getPropValue(obj, sousId)==null) throw new ParamNotNullException(tc.getId().getPropName()+"."+sousId+" is not null");
		}
		for(PropColumn pc:tc.getColumns()){
			 if(pc.isNotNull()&&SpringReflext.get(obj,pc.getPropName())==null)
				 throw new ParamNotNullException(pc.getPropName()+" not be null");
//			 String sousPc;
//			 if((sousPc=pc.getJoinPropValue())!=null&&pc.getJoinPropValue().trim().length()>0)
//				 if(SpringReflext.getPropValue(obj, pc.getPropName())==null) throw new ParamNotNullException(pc.getPropName()+"."+sousPc+" is not null");
		}
		//**此处验证getJoinPropValue情况需优化
		List<Object> params = new ArrayList<>();
		 StringBuilder sql = new StringBuilder("INSERT  INTO ");
		 sql.append(tc.getTableName()).append("(");
		   if(tc.getId()!=null){
			   Object o = SpringReflext.get(obj, tc.getId().getPropName());
			   if(o!=null){ insertSP(sql, o, tc.getId(), params);
//				   if(MapperType.class.isAssignableFrom(o.getClass())){
//					   sql.append(tc.getId().getColumnName()).append(",");
//					   params.add(((MapperType)o).mapper(o));   
//				   }else if(!NullOREmpty.isEmptyString(tc.getId().getMapperType())){
//					    if(mapperTypeContext==null||mapperTypeContext.getMapperType(tc.getId().getMapperType())==null)
//					    	throw new NotSupportException("mapperTypeContext not find "+tc.getId().getMapperType());
//					    MapperType mapperType = mapperTypeContext.getMapperType(tc.getId().getMapperType());
//					    sql.append(tc.getId().getColumnName()).append(",");
//						 params.add(mapperType.mapper(o)); 
//				   }else{
//					   Object bean = SpringReflext.getPropValueChar(o, tc.getId().getJoinPropValue());
//					   if(bean!=null){
//						   sql.append(tc.getId().getColumnName()).append(",");
//						   params.add(bean);  
//					   }
//				   }
			   }
		   }
		   for(PropColumn pc:tc.getColumns()){
			   Object o = SpringReflext.get(obj, pc.getPropName());
			    if(o!=null){insertSP(sql, o, pc, params);
//					   Object bean = SpringReflext.getPropValueChar(o, pc.getJoinPropValue());
//					    if(bean!=null){
//					    	 sql.append(pc.getColumnName()).append(",");
//						   params.add(bean);  
//					   }
			    }
		   }
		   if(sql.indexOf(",")!=-1) sql.deleteCharAt(sql.lastIndexOf(","));
			 sql.append(") VALUES (");
		    for(int i=0;i<params.size();i++){
		    	sql.append("?");
	    	    if(i<params.size()-1)sql.append(",");
		    }
		    sql.append(")");
		    JdbcCondition jCondition = LogUtil.log(log, new JdbcCondition(sql.toString(), params.toArray()));
		    if(printSQL.show)LogUtil.print(jCondition);
	    return jCondition;
	}

	@Override
	public JdbcCondition deleteById(Serializable id, Class<?> clazz) {
		TableConstruct tc = this.hologResolve.getStruct(clazz);
		if(tc==null) throw new NotSupportException(clazz+" not in context");
		String sql = "DELETE FROM "+tc.getTableName()+" WHERE "+tc.getId().getColumnName()+"=?";
		 JdbcCondition jCondition = LogUtil.log(log, new JdbcCondition(sql,new Object[]{id}));
		 if(printSQL.show)LogUtil.print(jCondition);
		return jCondition;
	}

	@Override
	public JdbcCondition deleteById(Object obj) {
		if(obj==null) throw new ParamNotNullException("insert params is not null");
		TableConstruct tc = this.hologResolve.getStruct(obj.getClass());
		if(tc==null) throw new NotSupportException(obj.getClass().getClass()+" not in context");
		String sql = "DELETE FROM "+tc.getTableName()+" WHERE "+tc.getId().getColumnName()+"=?";
//		Object param = SpringReflext.get(obj, tc.getId().getPropName());
//		if(param==null)throw new ParamNotNullException(tc.getId().getPropName()+" is not null");
//		 param = SpringReflext.getPropValueChar(param, tc.getId().getJoinPropValue());
//		 if(param==null)throw new ParamNotNullException(tc.getId().getPropName()+"."+tc.getId().getJoinPropValue()+" is not null");
		Object param = finalParam(obj, tc.getId());
		if(param==null)throw new ParamNotNullException(tc.getId().getPropName()+"."+tc.getId().getJoinPropValue()+" is not null");
		 JdbcCondition jCondition = LogUtil.log(log, new JdbcCondition(sql,new Object[]{param}));
		 if(printSQL.show)LogUtil.print(jCondition);
		return jCondition;
	}

	@Override
	public JdbcCondition deleteByCondition(Object obj,LinkedType link) {
		if(obj==null) throw new ParamNotNullException("insert params is not null");
		TableConstruct tc = this.hologResolve.getStruct(obj.getClass());
		if(tc==null) throw new NotSupportException(obj.getClass().getClass()+" not in context");
		StringBuilder sBuilder = new StringBuilder("DELETE FROM "+tc.getTableName()+" WHERE ");
		List<Object> params = new ArrayList<>();
		if(tc.getId()!=null){
			Object idObject = finalParam(obj, tc.getId());
			if(idObject!=null) {
				sBuilder.append(tc.getId().getColumnName()+"=?").append(link.val());
				params.add(idObject);
			}
//			Object idObject = SpringReflext.get(obj, tc.getId().getPropName());
//			if(idObject!=null) {
//				idObject = SpringReflext.getPropValueChar(idObject, tc.getId().getJoinPropValue());
//				if(idObject!=null){
//					sBuilder.append(tc.getId().getColumnName()+"=?").append(link.val());
//					params.add(idObject);
//				}
//			}
		}
		for(PropColumn pc:tc.getColumns()){
			Object colObj = finalParam(obj, pc);
			if(colObj!=null){
				 sBuilder.append(pc.getColumnName()+"=?").append(link.val());
				 params.add(colObj);
			}
//			 Object colObj = SpringReflext.get(obj, pc.getPropName());
//			 if(colObj!=null){
//				 colObj = SpringReflext.getPropValueChar(colObj, pc.getJoinPropValue());
//				 if(colObj!=null){
//					 sBuilder.append(pc.getColumnName()+"=?").append(link.val());
//					 params.add(colObj);
//				 }
//			 }
		}
		int index = sBuilder.lastIndexOf(link.val());
		JdbcCondition jCondition = new JdbcCondition(sBuilder.subSequence(0, index).toString(), params.toArray());
		if(printSQL.show) LogUtil.print(jCondition);
		return jCondition;
	}

	 

	@Override
	public JdbcCondition updateById(Object obj) {
		if(obj==null) throw new ParamNotNullException("insert params is not null");
		TableConstruct tc = this.hologResolve.getStruct(obj.getClass());
		if(tc==null) throw new NotSupportException(obj.getClass().getClass()+" not in context");
		if(tc.getId()==null) throw new NotSupportException("this class:"+obj.getClass()+" not has ID");
		Object idObject = finalParam(obj, tc.getId());
		if(idObject==null) throw new ParamNotNullException(tc.getId()+"."+tc.getId().getJoinPropValue()+" is NULL");
//		Object idObject = SpringReflext.get(obj, tc.getId().getPropName());
//		if(idObject==null) throw new ParamNotNullException(tc.getId()+" is NULL");
//		idObject = SpringReflext.getPropValueChar(idObject, tc.getId().getJoinPropValue());
//		if(idObject==null) throw new ParamNotNullException(tc.getId()+"."+tc.getId().getJoinPropValue()+" is NULL");
		List<Object> params = new ArrayList<>();
		StringBuilder sBuilder = new StringBuilder("UPDATE "+tc.getTableName()+" SET ");
		for(int i=0;i<tc.getColumns().length;i++){
			Object colObject =  finalParam(obj, tc.getColumns()[i]);
			if(colObject!=null){
				sBuilder.append(tc.getColumns()[i].getColumnName()).append("=? ").append(",");
				params.add(colObject);
			}
//			Object colObject = SpringReflext.get(obj, tc.getColumns()[i].getPropName());
//			if(colObject!=null) {
//				colObject = SpringReflext.getPropValueChar(colObject, tc.getColumns()[i].getJoinPropValue());
//				if(colObject!=null){
//					sBuilder.append(tc.getColumns()[i].getColumnName()).append("=? ").append(",");
//					params.add(colObject);
//				}
//			} 
		}
		int lastIndex = -1;
		if((lastIndex=sBuilder.lastIndexOf(","))!=-1)sBuilder.deleteCharAt(lastIndex);
		sBuilder.append(" WHERE ").append(tc.getId().getColumnName()).append("=?");
		params.add(idObject);
		JdbcCondition jCondition = LogUtil.log(log, new JdbcCondition(sBuilder.toString(), params.toArray()));
		if(printSQL.show) LogUtil.print(jCondition);
		return jCondition;
	}

	@Override
	public JdbcCondition updateByCondition(Object obj,Map<String, Object> params) {
		if(params==null||params.isEmpty()) throw new ParamNotNullException("params is not null");
		if(obj==null) throw new ParamNotNullException("insert params is not null");
		TableConstruct tc = this.hologResolve.getStruct(obj.getClass());
		if(tc==null) throw new NotSupportException(obj.getClass().getClass()+" not in context");
		StringBuilder sBuilder = new StringBuilder("UPDATE "+tc.getTableName()+" SET ");
		List<Object> innerParams = new ArrayList<>();
		Object idObject = finalParam(obj, tc.getId());
		if(idObject!=null){
			 sBuilder.append(tc.getId().getColumnName()).append("=?,");
			  innerParams.add(idObject);
		}
		for(int i=0;i<tc.getColumns().length;i++){
			Object colObject = finalParam(obj, tc.getColumns()[i]);
			if(colObject!=null){
				 sBuilder.append(tc.getColumns()[i].getColumnName()).append("=?,");
				  innerParams.add(colObject);
			}
//			 Object colObject = SpringReflext.get(obj, tc.getColumns()[i].getPropName());
//			 if(colObject!=null) {
//				 colObject = SpringReflext.getPropValueChar(colObject, tc.getColumns()[i].getJoinPropValue());
//				 if(colObject!=null){
//					  sBuilder.append(tc.getColumns()[i].getColumnName()).append("=?,");
//					  innerParams.add(colObject);
//				 }
//			 }
		}
		int lastIndex = -1;
		if((lastIndex=sBuilder.lastIndexOf(","))!=-1)sBuilder.deleteCharAt(lastIndex);
		sBuilder.append(" WHERE ");
		for(String key:params.keySet()){
			Object  paramObject = params.get(key);
			String colName = tc.getColNameByProp(key);
			if(colName==null) throw new ParamNotNullException(key+" is not property name");
			sBuilder.append(colName).append("=? AND ");
			//特别处理char字符
			if(paramObject instanceof Character||paramObject.getClass()==char.class)
			innerParams.add(paramObject.toString());	
			else innerParams.add(paramObject);	
		}
		JdbcCondition jCondition = LogUtil.log(log, new JdbcCondition(sBuilder.subSequence(0, sBuilder.lastIndexOf("AND")).toString(), innerParams.toArray()));
		if(printSQL.show)LogUtil.print(jCondition);
		return jCondition;
	}
	
	@Override
	public JdbcCondition updateSansCondition(Object obj) {
		if(obj==null) throw new ParamNotNullException("insert params is not null");
		TableConstruct tc = this.hologResolve.getStruct(obj.getClass());
		if(tc==null) throw new NotSupportException(obj.getClass().getClass()+" not in context");
		StringBuilder sBuilder = new StringBuilder("UPDATE "+tc.getTableName()+" SET ");
		List<Object> innerParams = new ArrayList<>();
		for(int i=0;i<tc.getColumns().length;i++){
			Object colObject = finalParam(obj, tc.getColumns()[i]);
			if(colObject!=null){
				 sBuilder.append(tc.getColumns()[i].getColumnName()).append("=?,");
				  innerParams.add(colObject);
			}
//			 Object colObject = SpringReflext.get(obj, tc.getColumns()[i].getPropName());
//			 if(colObject!=null) {
//				 colObject = SpringReflext.getPropValueChar(colObject, tc.getColumns()[i].getJoinPropValue());
//				 if(colObject!=null){
//					  sBuilder.append(tc.getColumns()[i].getColumnName()).append("=?,");
//					  innerParams.add(colObject);
//				 }
//			 }
		}
		int lastIndex = -1;
		if((lastIndex=sBuilder.lastIndexOf(","))!=-1)sBuilder.deleteCharAt(lastIndex);
		JdbcCondition jCondition = LogUtil.log(log, new JdbcCondition(sBuilder.toString(), innerParams.toArray()));
		if(printSQL.show)LogUtil.print(jCondition);
		return jCondition;
	}

	@Override
	public JdbcCondition selectById(Object obj) {	
		if(obj==null) throw new ParamNotNullException("insert params is not null");
		Class<?> clazz = obj.getClass();
		TableConstruct tc = this.hologResolve.getStruct(clazz);
		OprationalVariable oVariable = this.hologResolve.getVariabl(clazz);
		if(tc==null) throw new NotSupportException(clazz.getClass()+" not in context");
		if(tc.getId()==null)throw new ParamNotNullException(clazz.getName()+" @ID is not null");
		Object idObject = SpringReflext.get(obj, tc.getId().getPropName());
		if(idObject!=null){
			idObject = SpringReflext.getPropValueChar(idObject, tc.getId().getJoinPropValue());
			if(idObject==null)
				throw new ParamNotNullException(tc.getId().getPropName()+"."+tc.getId().getJoinPropValue()+" is NULL");
		}else{
			throw new ParamNotNullException(tc.getId().getPropName()+" is NULL");
		}
		StringBuilder sBuilder = new StringBuilder("SELECT ");
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
		sBuilder.append(" ").append(target).append(" FROM ").append(tc.getTableName())
		.append(" WHERE ").append(tc.getId().getColumnName()).append("=?");
		JdbcCondition jCondition = LogUtil.log(log, new JdbcCondition(sBuilder.toString(), new Object[]{idObject}));
		if(printSQL.show) LogUtil.print(jCondition);
		return jCondition;
	}
	@Override
	public JdbcCondition selectById(Serializable id,Class<?> clazz){
		TableConstruct tc = this.hologResolve.getStruct(clazz);
		OprationalVariable oVariable = this.hologResolve.getVariabl(clazz);
		if(tc==null) throw new NotSupportException(clazz.getName()+" not in context");
		if(tc.getId()==null)throw new ParamNotNullException(clazz.getName()+" @ID is not null");
		StringBuilder sBuilder = new StringBuilder("SELECT ");
		String target = "*";
		switch (oVariable.getResult_patten()) {
		case SANS:
		case CLASS:
		case PROPERTY:
			target = oVariable.getResult();
			break;
		case METHOD:
			target =tc.columnPropNames();
			break;
		default:
			target = "*";
			break;
		}
		sBuilder.append(" ").append(target).append(" FROM ").append(tc.getTableName())
		.append(" WHERE ").append(tc.getId().getColumnName()).append("=?");
		JdbcCondition jCondition = LogUtil.log(log, new JdbcCondition(sBuilder.toString(), new Object[]{id}));
		if(printSQL.show) LogUtil.print(jCondition);
		return jCondition;
	}

	@Override
	public JdbcCondition selectSame(Object obj,LinkedType link) {
		 return (JdbcCondition) this.selectByCondition(obj, link, OperatorType.EQ);
	}
	
	@Override
	public JdbcCondition selectAll(Class<?> clazz, Integer firstResult, Integer maxResult) {
		TableConstruct tc = this.hologResolve.getStruct(clazz);
		OprationalVariable oVariable = this.hologResolve.getVariabl(clazz);
		if(tc==null) throw new NotSupportException(clazz.getClass()+" not in context");
		StringBuilder sBuilder = new StringBuilder("SELECT ");
		String target = "*";
		switch (oVariable.getResult_patten()) {
		case SANS:
		case CLASS:
		case PROPERTY:
			target = oVariable.getResult();
			break;
		case METHOD:
			target =tc.columnPropNames();
			break;
		default:
			target = "*";
			break;
		}
		sBuilder.append(target).append(" FROM ").append(tc.getTableName());
		if(firstResult!=null&&maxResult!=null){
			sBuilder.append(" LIMIT ").append(firstResult).append(",").append(maxResult);
		}
		JdbcCondition jCondition = LogUtil.log(log, new JdbcCondition(sBuilder.toString(), new Object[]{}));
		if(printSQL.show) LogUtil.print(jCondition);
		return jCondition;
	}

	@Override
	public JdbcCondition selectAllCount(Class<?> clazz) {
		TableConstruct tc = this.hologResolve.getStruct(clazz);
		if(tc==null) throw new NotSupportException(clazz.getClass()+" not in context");
		StringBuilder sBuilder = new StringBuilder("SELECT COUNT(1) FROM ");
		sBuilder.append(tc.getTableName());
		JdbcCondition jCondition = LogUtil.log(log, new JdbcCondition(sBuilder.toString(), new Object[]{}));
		if(printSQL.show) LogUtil.print(jCondition);
		return jCondition;
	}

	@Override
	public JdbcCondition selectLike(Object obj, LinkedType link) {
		return (JdbcCondition) this.selectByCondition(obj, link, OperatorType.LIKE);
	}
	
	@Override
	public JdbcCondition selectAllSame(Object obj){		
		return selectSame(obj, LinkedType.AND);		
	}

	@Override
	public JdbcCondition selectAnyOneSame(Object obj) {
		// TODO Auto-generated method stub
		return selectSame(obj, LinkedType.OR);
	}

	@Override
	public JdbcCondition selectAllLike(Object obj) {
		
		return selectLike(obj, LinkedType.AND);
	}

	@Override
	public JdbcCondition selectAnyOneLike(Object obj) {
		// TODO Auto-generated method stub
		return selectLike(obj, LinkedType.OR);
	}


	@Override
	public JdbcCondition createTable(Class<?> clazz) {
		throw new NotImplementException("createTable not implement");
	}

	@Override
	public JdbcCondition dropTable(Class<?> clazz) {
		TableConstruct tc = this.hologResolve.getStruct(clazz);
		JdbcCondition jcCondition =  LogUtil.log(log,new JdbcCondition("DROP TABLE "+tc.getTableName(), null));
		if(printSQL.show)LogUtil.print(jcCondition);
		return jcCondition;
				
	}
	 
	
	//***************************************以下为直接搜索对象的方法******************
	@Override
	public Condition<? extends Object> selectEntityById(Object obj) {
		if(obj==null) throw new ParamNotNullException("insert params is not null");
		Class<?> clazz = obj.getClass();
		TableConstruct tc = this.hologResolve.getStruct(clazz);
		if(tc==null) throw new NotSupportException(clazz.getClass()+" not in context");
		if(tc.getId()==null)throw new ParamNotNullException(clazz.getName()+" @ID is not null");
		Object idObject = SpringReflext.get(obj, tc.getId().getPropName());
		if(idObject!=null){
			idObject = SpringReflext.getPropValueChar(idObject, tc.getId().getJoinPropValue());
			if(idObject==null)
				throw new ParamNotNullException(tc.getId().getPropName()+"."+tc.getId().getJoinPropValue()+" is NULL");
		}else{
			throw new ParamNotNullException(tc.getId().getPropName()+" is NULL");
		}
		StringBuilder sBuilder = new StringBuilder("SELECT ");
		String target = tc.columnPropNames();
		sBuilder.append(" ").append(target).append(" FROM ").append(tc.getTableName())
		.append(" WHERE ").append(tc.getId().getColumnName()).append("=?");
		JdbcCondition jCondition = LogUtil.log(log, new JdbcCondition(sBuilder.toString(), new Object[]{idObject}));
		if(printSQL.show) LogUtil.print(jCondition);
		return jCondition;
	}
	@Override
	public Condition<? extends Object> selectEntityById(Serializable id,
			Class<?> clazz) {
		TableConstruct tc = this.hologResolve.getStruct(clazz);
		if(tc==null) throw new NotSupportException(clazz.getName()+" not in context");
		if(tc.getId()==null)throw new ParamNotNullException(clazz.getName()+" @ID is not null");
		StringBuilder sBuilder = new StringBuilder("SELECT ");
		String target = tc.columnPropNames();
		sBuilder.append(" ").append(target).append(" FROM ").append(tc.getTableName())
		.append(" WHERE ").append(tc.getId().getColumnName()).append("=?");
		JdbcCondition jCondition = LogUtil.log(log, new JdbcCondition(sBuilder.toString(), new Object[]{id}));
		if(printSQL.show) LogUtil.print(jCondition);
		return jCondition;
	}
	@Override
	public Condition<? extends Object> selectEntitySame(Object obj,
			LinkedType link) {
		return selectEntityByCondition(obj, link, OperatorType.EQ);
	}
	@Override
	public Condition<? extends Object> selectEntityAllSame(Object obj) {
		return selectEntitySame(obj, LinkedType.AND);	
	}
	
	
	@Override
	public Condition<? extends Object> selectEntityAnyOneSame(Object obj) {
		return selectEntitySame(obj, LinkedType.OR);	
	}
	
	@Override
	public Condition<? extends Object> selectEntityAll(Class<?> clazz,
			Integer firstResult, Integer maxResult) {
		TableConstruct tc = this.hologResolve.getStruct(clazz);
		if(tc==null) throw new NotSupportException(clazz.getClass()+" not in context");
		StringBuilder sBuilder = new StringBuilder("SELECT ");
		String target = tc.columnPropNames(); 
		sBuilder.append(target).append(" FROM ").append(tc.getTableName());
		if(firstResult!=null&&maxResult!=null){
			sBuilder.append(" LIMIT ").append(firstResult).append(",").append(maxResult);
		}
		JdbcCondition jCondition = LogUtil.log(log, new JdbcCondition(sBuilder.toString(), new Object[]{}));
		if(printSQL.show) LogUtil.print(jCondition);
		return jCondition;
	}
	@Override
	public Condition<? extends Object> selectEntityLike(Object obj,
			LinkedType link) {
		return selectEntityByCondition(obj, link, OperatorType.LIKE);
	}
	@Override
	public Condition<? extends Object> selectEntityAllLike(Object obj) {
		return selectEntityLike(obj, LinkedType.AND);
	}
	@Override
	public Condition<? extends Object> selectEntityAnyOneLike(Object obj) {
		return selectEntityLike(obj, LinkedType.OR);
	}
	
	@Override
	public Condition<? extends Object> selectCount(Object obj,LinkedType link,OperatorType ope) {
		if(obj==null) throw new ParamNotNullException("insert params is not null");
		Class<?> clazz = obj.getClass();
		TableConstruct tc = this.hologResolve.getStruct(clazz);
		OprationalVariable oVariable = this.hologResolve.getVariabl(clazz);
		if(tc==null) throw new NotSupportException(clazz.getClass()+" not in context");
		Object idObject = null;
		if(tc!=null){
			idObject =SpringReflext.get(obj, tc.getId().getPropName());
			if(idObject!=null){
				idObject = SpringReflext.getPropValueChar(idObject, tc.getId().getJoinPropValue());
			}
		}	 
		StringBuilder sBuilder = new StringBuilder("SELECT ");
		String target = "COUNT(1)";
		sBuilder.append(" ").append(target).append(" FROM ").append(tc.getTableName()).append(" WHERE ");
		List<Object> params = new ArrayList<>();
		if(idObject!=null){
			 sBuilder.append(tc.getId().getColumnName()).append(ope.getVal()).append("? ").append(link.val());
			 params.add(idObject);
		}
		for(PropColumn pc:tc.getColumns()){
			Object colObject = SpringReflext.get(obj, pc.getPropName());
			if(colObject!=null){
				colObject = SpringReflext.getPropValueChar(colObject, pc.getJoinPropValue());
				if(colObject!=null){
					 sBuilder.append(pc.getColumnName()).append(ope.getVal()).append("?").append(link.val());
					 params.add(colObject);
				}
			}
		}
		switch (oVariable.getCondition_patten()) {
		case SANS: 
			try{
				sBuilder.delete(sBuilder.lastIndexOf(link.val()), sBuilder.length());
			}catch (StringIndexOutOfBoundsException e) {
				e.printStackTrace();
				sBuilder.delete(sBuilder.lastIndexOf("WHERE"), sBuilder.length());
			}
			break;
		case CLASS:
		case PROPERTY:
			sBuilder.append(oVariable.getCondition());
			break;
		case METHOD:
			sBuilder.append(SpringReflext.get(obj, oVariable.getCondition()));
		default:
			break;
		}
		JdbcCondition jCondition = LogUtil.log(log, new JdbcCondition(sBuilder.toString(),params.toArray()));
		if(printSQL.show) LogUtil.print(jCondition);
		return jCondition;
	}
	@Override
	public Condition<? extends Object> selectByCondition(Object obj,
			LinkedType link, OperatorType ope) {
		if(obj==null) throw new ParamNotNullException("insert params is not null");
		Class<?> clazz = obj.getClass();
		TableConstruct tc = this.hologResolve.getStruct(clazz);
		OprationalVariable oVariable = this.hologResolve.getVariabl(clazz);
		if(tc==null) throw new NotSupportException(clazz.getClass()+" not in context");
		Object idObject = null;
		if(tc!=null){
			idObject =SpringReflext.get(obj, tc.getId().getPropName());
			if(idObject!=null){
				idObject = SpringReflext.getPropValueChar(idObject, tc.getId().getJoinPropValue());
			}
		}	 
		StringBuilder sBuilder = new StringBuilder("SELECT ");
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
		sBuilder.append(" ").append(target).append(" FROM ").append(tc.getTableName()).append(" WHERE ");
		List<Object> params = new ArrayList<>();
		if(idObject!=null){
			 sBuilder.append(tc.getId().getColumnName()).append(ope.getVal()).append("? ").append(link.val());
			 params.add(idObject);
		}
		for(PropColumn pc:tc.getColumns()){
			Object colObject = SpringReflext.get(obj, pc.getPropName());
			if(colObject!=null){
				colObject = SpringReflext.getPropValueChar(colObject, pc.getJoinPropValue());
				if(colObject!=null){
					 sBuilder.append(pc.getColumnName()).append(ope.getVal()).append("?").append(link.val());
					 params.add(colObject);
				}
			}
		}
		//sBuilder.delete(sBuilder.lastIndexOf(link.val()), sBuilder.length());
		switch (oVariable.getCondition_patten()) {
		case SANS: 
			try{
				sBuilder.delete(sBuilder.lastIndexOf(link.val()), sBuilder.length());
			}catch (StringIndexOutOfBoundsException e) {
				e.printStackTrace();
				sBuilder.delete(sBuilder.lastIndexOf("WHERE"), sBuilder.length());
			}
			break;
		case CLASS:
		case PROPERTY:
			sBuilder.append(oVariable.getCondition());
			break;
		case METHOD:
			sBuilder.append(SpringReflext.get(obj, oVariable.getCondition()));
		default:
			break;
		}
		switch (oVariable.getGroupBy_patten()) { 
		case CLASS:
		case PROPERTY: 
			sBuilder.append(" GROUP BY ").append(oVariable.getGroupBy());
			break;
		case METHOD: 
			sBuilder.append(" GROUP BY ").append(SpringReflext.get(obj, oVariable.getGroupBy()));
			break;
		default:
			break;
		}
		switch (oVariable.getOrderBy_patten()) {
		case CLASS:
		case PROPERTY:
			sBuilder.append(" ORDER BY ").append(oVariable.getOrderBy());
			break;
		case METHOD: 
			sBuilder.append(" ORDER BY ").append(SpringReflext.get(obj, oVariable.getOrderBy()));
			break;
		default:
			break;
		}
		if(oVariable.getFirstResult()!=null&&oVariable.getMaxResult()!=null){
			Integer firstResult = SpringReflext.get(obj, oVariable.getFirstResult());
			Integer maxResult = SpringReflext.get(obj, oVariable.getMaxResult());
			  if(firstResult!=null&&maxResult!=null){
			   sBuilder.append(" LIMIT ").append("?,?");
			   params.add(firstResult);
			   params.add(maxResult);
			  }
		}
		JdbcCondition jCondition = LogUtil.log(log, new JdbcCondition(sBuilder.toString(),params.toArray()));
		if(printSQL.show) LogUtil.print(jCondition);
		return jCondition;
	}
	@Override
	public Condition<? extends Object> selectEntityByCondition(Object obj,
			LinkedType link, OperatorType ope) {
		if(obj==null) throw new ParamNotNullException("insert params is not null");
		Class<?> clazz = obj.getClass();
		TableConstruct tc = this.hologResolve.getStruct(clazz);
		OprationalVariable oVariable = this.hologResolve.getVariabl(clazz);
		if(tc==null) throw new NotSupportException(clazz.getClass()+" not in context");
		Object idObject = null;
		if(tc!=null){
			idObject =SpringReflext.get(obj, tc.getId().getPropName());
			if(idObject!=null){
				idObject = SpringReflext.getPropValueChar(idObject, tc.getId().getJoinPropValue());
			}
		}	 
		StringBuilder sBuilder = new StringBuilder("SELECT ");
		String target = tc.columnPropNames();
		sBuilder.append(" ").append(target).append(" FROM ").append(tc.getTableName()).append(" WHERE ");
		List<Object> params = new ArrayList<>();
		if(idObject!=null){
			 sBuilder.append(tc.getId().getColumnName()).append(ope.getVal()).append("? ").append(link.val());
			 params.add(idObject);
		}
		for(PropColumn pc:tc.getColumns()){
			Object colObject = SpringReflext.get(obj, pc.getPropName());
			if(colObject!=null){
				colObject = SpringReflext.getPropValueChar(colObject, pc.getJoinPropValue());
				if(colObject!=null){
					 sBuilder.append(pc.getColumnName()).append(ope.getVal()).append("?").append(link.val());
					 params.add(colObject);
				}
			}
		}
		//sBuilder.delete(sBuilder.lastIndexOf(link.val()), sBuilder.length());
		switch (oVariable.getCondition_patten()) {
		case SANS: 
			try{
				sBuilder.delete(sBuilder.lastIndexOf(link.val()), sBuilder.length());
			}catch (StringIndexOutOfBoundsException e) {
				e.printStackTrace();
				sBuilder.delete(sBuilder.lastIndexOf("WHERE"), sBuilder.length());
			}
			break;
		case CLASS:
		case PROPERTY:
			sBuilder.append(oVariable.getCondition());
			break;
		case METHOD:
			sBuilder.append(SpringReflext.get(obj, oVariable.getCondition()));
		default:
			break;
		}
		switch (oVariable.getGroupBy_patten()) { 
		case CLASS:
		case PROPERTY: 
			sBuilder.append(" GROUP BY ").append(oVariable.getGroupBy());
			break;
		case METHOD: 
			sBuilder.append(" GROUP BY ").append(SpringReflext.get(obj, oVariable.getGroupBy()));
			break;
		default:
			break;
		}
		switch (oVariable.getOrderBy_patten()) {
		case CLASS:
		case PROPERTY:
			sBuilder.append(" ORDER BY ").append(oVariable.getOrderBy());
			break;
		case METHOD: 
			sBuilder.append(" ORDER BY ").append(SpringReflext.get(obj, oVariable.getOrderBy()));
			break;
		default:
			break;
		}
		if(oVariable.getFirstResult()!=null&&oVariable.getMaxResult()!=null){
			Integer firstResult = SpringReflext.get(obj, oVariable.getFirstResult());
			Integer maxResult = SpringReflext.get(obj, oVariable.getMaxResult());
			  if(firstResult!=null&&maxResult!=null){
			   sBuilder.append(" LIMIT ").append("?,?");
			   params.add(firstResult);
			   params.add(maxResult);
			  }
		}
		JdbcCondition jCondition = LogUtil.log(log, new JdbcCondition(sBuilder.toString(),params.toArray()));
		if(printSQL.show) LogUtil.print(jCondition);
		return jCondition;
	}
	
	public MapperTypeContext getMapperTypeContext() {
		return mapperTypeContext;
	}
	public void setMapperTypeContext(MapperTypeContext mapperTypeContext) {
		this.mapperTypeContext = mapperTypeContext;
	}
	
	/**
	 * 添加
	 * @param sql
	 * @param o
	 * @param pc
	 * @param params
	 */
     private void  insertSP(StringBuilder sql,Object o,PropColumn pc,List<Object> params){
    	  if(MapperType.class.isAssignableFrom(o.getClass())){
			   sql.append(pc.getColumnName()).append(",");
			   params.add(((MapperType)o).mapper(o));   
		   }else if(!NullOREmpty.isEmptyString(pc.getMapperType())){
			    if(mapperTypeContext==null||mapperTypeContext.getMapperType(pc.getMapperType())==null)
			    	throw new NotSupportException("mapperTypeContext not find "+pc.getMapperType());
			    MapperType mapperType = mapperTypeContext.getMapperType(pc.getMapperType());
			    sql.append(pc.getColumnName()).append(",");
				 params.add(mapperType.mapper(o)); 
		   }else{
			   Object bean = SpringReflext.getPropValueChar(o, pc.getJoinPropValue());
			   if(bean!=null){
				   sql.append(pc.getColumnName()).append(",");
				   params.add(bean);  
			   }
		   }
     }
     /**
      * 获取最终的Param
      * @param obj  操作的实体类
      * @param pc  属性的PropColumn
      * @return
      */
     private  Object finalParam(Object obj,PropColumn pc){
    	 //1-第一次反射属性
    	 Object param = SpringReflext.get(obj, pc.getPropName());
    	//2-不为空的话,获取MapperType
 		 if(param!=null){
 			 if(MapperType.class.isAssignableFrom(param.getClass()))
 	 			param = ((MapperType)param).mapper(param);
 			 else if(!NullOREmpty.isEmptyString(pc.getMapperType())){
 				  if(this.mapperTypeContext==null||this.mapperTypeContext.getMapperType(pc.getMapperType())==null) 
 					  throw new NotSupportException("mapperTypeContext not found "+pc.getMapperType());
 				 param = this.mapperTypeContext.getMapperType(pc.getMapperType()).mapper(param);
 			 }else {
 				 param = SpringReflext.getPropValueChar(param, pc.getJoinPropValue());
 			 }
 		   }
 		  return param;
     }
}
