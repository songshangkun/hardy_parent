package org.hardy.sqlhelper.holographic;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hardy.reflex.RefletClazzUtil;
import org.hardy.sqlhelper.annotation.structure.COLUMN;
import org.hardy.sqlhelper.annotation.structure.ID;
import org.hardy.sqlhelper.annotation.variable.Condition;
import org.hardy.sqlhelper.annotation.variable.GroupBy;
import org.hardy.sqlhelper.annotation.variable.Limit;
import org.hardy.sqlhelper.annotation.variable.OrderBy;
import org.hardy.sqlhelper.annotation.variable.Result;
import org.hardy.sqlhelper.enums.CallPatten;
import org.hardy.util.annotation.AnnotationTools;

/**
 * 记录每次操作的变量值,对于每次操作这个对象都是新建的多例,多用于搜索使用.
 * 解析子类和父类多个相同Annotation的关系,使用1同类中按CallPatten优先级使用,2:CallPatten优先级相同情况下子类优先于父类,
 * @author 09
 *
 */
public class OprationalVariable {
	
	public OprationalVariable(Class<?> clazz,TableConstruct tc){
		Set<String> innerSuppemerField = new HashSet<String>();	
		Set<Class<?>> set = RefletClazzUtil.allSupperClass(clazz);	
		StringBuilder resultBuilder = null;
//		StringBuilder groupBuilder = null;
//		StringBuilder orderBuilder = null;
		List<InnerIndexObj> groupList = new ArrayList<InnerIndexObj>(); 
		List<InnerIndexObj> orderList = new ArrayList<InnerIndexObj>(); 
		StringBuilder conditionBuilder = null;
		String conditionLastLink = null;
		for(Class<?> c:set){
			 Field[] fs = c.getDeclaredFields();
			 for(Field f:fs){
			 if(!f.isAnnotationPresent(Deprecated.class)&&!innerSuppemerField.contains(f.getName())){
				   String colNname = null;
				   //判断是否为数据库字段
				   ID id = AnnotationTools.findFieldAnnotation(f, ID.class);
				   COLUMN column = AnnotationTools.findFieldAnnotation(f, COLUMN.class);
				   if(id!=null) colNname = id.name().trim().length()>0?id.name():f.getName();
				   else if(column!=null) colNname = column.name().trim().length()>0?column.name():f.getName();
				   //对@Result进行处理
				   Result result = AnnotationTools.findFieldAnnotation(f, Result.class);
				   if(this.result_patten.prorite<CallPatten.METHOD.prorite){
					   if(result!=null){
						   //1-以后使用 String colNname = null 来判断是method模式还是Property模式
						   if(colNname!=null){
							   innerSuppemerField.add(f.getName());
							   if(this.result_patten.prorite<=CallPatten.PROPERTY.prorite){
								   this.result_patten = CallPatten.PROPERTY;
								   if(resultBuilder==null)resultBuilder = new StringBuilder();
								   resultBuilder.append(colNname);
								   String alisCol = f.getName();
								   if(result.value().trim().length()>0) 
									   alisCol = result.value();  
								   resultBuilder.append(" ").append(alisCol).append(",");
							   }  
						   }else{
							   if(this.result_patten.prorite<CallPatten.METHOD.prorite){
								   this.result_patten = CallPatten.METHOD;
								   this.result = f.getName();
							   }   
						   }
	 
					   }
				   }
				   //对Limit进行处理
				   Limit limit = AnnotationTools.findFieldAnnotation(f, Limit.class);
				   if(limit!=null){
					   if(limit.firstResult()&&this.firstResult==null){
						    this.firstResult = f.getName();
						    innerSuppemerField.add(f.getName());
					   }
					   if(limit.maxResult()&&this.maxResult==null){
						   this.maxResult = f.getName();
						   innerSuppemerField.add(f.getName());
					   }
				   }
				   
				   //开始解析@GroupBy
				   GroupBy groupBy = AnnotationTools.findFieldAnnotation(f, GroupBy.class);
				   if(groupBy!=null&&this.groupBy==null&&this.groupBy_patten.prorite<CallPatten.METHOD.prorite){
					   innerSuppemerField.add(f.getName());
					   if(colNname!=null){
//						   if(groupBuilder==null) groupBuilder= new StringBuilder();
//						   groupBuilder.append(colNname).append(",");
						   groupList.add(new InnerIndexObj(colNname, groupBy.index()));
						   this.groupBy_patten = CallPatten.PROPERTY;  
						   innerSuppemerField.add(f.getName());
					   } else{
						   this.groupBy =  f.getName();
						   this.groupBy_patten = CallPatten.METHOD;
					   } 
				   }
				   
				 //开始解析@OrderBy
				   OrderBy orderBy = AnnotationTools.findFieldAnnotation(f, OrderBy.class);
				   if(orderBy!=null&&this.orderBy==null&&this.orderBy_patten.prorite<CallPatten.METHOD.prorite){
					   innerSuppemerField.add(f.getName());
					   if(colNname!=null){
//						   if(orderBuilder==null)orderBuilder=new StringBuilder();
//						   orderBuilder.append(colNname).append(",");
						   orderList.add(new InnerIndexObj(colNname+" "+orderBy.order(), orderBy.index()));
						   this.orderBy_patten = CallPatten.PROPERTY;
						   innerSuppemerField.add(f.getName());
					   } else{
						   this.orderBy =  f.getName();
						   this.orderBy_patten = CallPatten.METHOD;
				   }
				   
			 	}
				 //开始解析@Condition
				   Condition condition = AnnotationTools.findFieldAnnotation(f, Condition.class);
				   if(condition!=null&&this.condition==null&&this.orderBy_patten.prorite<CallPatten.METHOD.prorite){
					   innerSuppemerField.add(f.getName());
					   if(colNname!=null){
						   if(conditionBuilder==null)conditionBuilder = new StringBuilder();
						   conditionBuilder.append(" "+colNname)
						   .append(" ").append(condition.value()).append(" ").append(condition.link());
						   conditionLastLink = condition.link();
						   this.condition_patten = CallPatten.PROPERTY; 
						   innerSuppemerField.add(f.getName());
					   } else{
						   this.condition =  f.getName();
						   this.condition_patten = CallPatten.METHOD;
				   }
				   
			 	}
			 }
		}
  }	
		//完成最终对@Result的处理
		if(this.result_patten.prorite<CallPatten.CLASS.prorite){
			 Result result = AnnotationTools.findClassAnnotation(clazz, Result.class);
			 if(result!=null){
				 this.result_patten = CallPatten.CLASS;
				 if(result.value().trim().length()>0) this.result = result.value();
				 else this.result = tc.columnPropNames();
			 }
		}
		if(this.result_patten.equals(CallPatten.PROPERTY)){
			 this.result = resultBuilder.deleteCharAt(resultBuilder.lastIndexOf(",")).toString();
		}else if(this.result_patten.equals(CallPatten.SANS)){
			 this.result = "*";
		}
		//完成对@GroupBy的最终分析处理
		if(this.groupBy_patten.equals(CallPatten.PROPERTY)){
//			 this.groupBy = groupBuilder.deleteCharAt(groupBuilder.lastIndexOf(",")).toString();
			 Collections.sort(groupList,new Comparator<InnerIndexObj>() {
				@Override
				public int compare(InnerIndexObj o1,InnerIndexObj o2) {	 
					return o1.index - o2.index;
				}
			});
			 StringBuilder sb = new StringBuilder();
			 for(int i=0;i<groupList.size();i++){
				 sb.append(groupList.get(i).colName);
				 if(i<groupList.size()-1)sb.append(",");
				 else sb.append(" ");
			 }
			 this.groupBy = sb.toString(); 
		}
		//完成对@OrderBy的最终分析处理
		if(this.orderBy_patten.equals(CallPatten.PROPERTY)){
//			 this.orderBy = orderBuilder.deleteCharAt(orderBuilder.lastIndexOf(",")).toString();
			 Collections.sort(orderList,new Comparator<InnerIndexObj>() {
					@Override
					public int compare(InnerIndexObj o1,InnerIndexObj o2) {	 
						return o1.index - o2.index;
					}
				});
				 StringBuilder sb = new StringBuilder();
				 for(int i=0;i<orderList.size();i++){
					 sb.append(orderList.get(i).colName);
					 if(i<orderList.size()-1)sb.append(",");
					 else sb.append(" ");
				 }
				 this.orderBy = sb.toString(); 
		}
		//完成对@Condition的最终分析处理
		if(this.condition_patten.equals(CallPatten.PROPERTY)){
			 this.condition = conditionBuilder.substring(0,conditionBuilder.lastIndexOf(conditionLastLink)).toString();
		}
		   
	}
     /**
      * 搜索的标量
      */
	 private String result;
	 /**
	  * 默认为标记在类上
	  */
	 private CallPatten result_patten = CallPatten.SANS;
	 /**
	  * 表别名
	  */
	 private String alias ;
	 /**
	  *  默认为标记在类上
	  */
	 private CallPatten alias_patten = CallPatten.SANS;
	 /**
	  * 第一条分页搜索
	  */
	 private String firstResult;
	 /**
	  * 搜索条数
	  */
	 private String maxResult;
	 /**
	  * 排队规则
	  */
	 private String orderBy;
	 /**
	  * 
	  */
	 private CallPatten orderBy_patten = CallPatten.SANS;
	 /**
	  * 分组规则
	  */
	 private String groupBy;
	 /**
	  * 
	  */
	 private CallPatten groupBy_patten = CallPatten.SANS;
	 /**
	  * 是否使用distinct
	  */
	 private boolean distinct = true;
	 /**
	  * where条件语句
	  */
	 private String condition;
	 /**
	  * CLASS:每次对象搜索都要加上condition的值
	  * PROPERTY:每次对象搜索都要加上属性名称和condition的值
	  * METHOD:每次对象搜索都要加上
	  */
	 private CallPatten condition_patten = CallPatten.SANS;
    
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getFirstResult() {
		return firstResult;
	}

	public void setFirstResult(String firstResult) {
		this.firstResult = firstResult;
	}

	public String getMaxResult() {
		return maxResult;
	}

	public void setMaxResult(String maxResult) {
		this.maxResult = maxResult;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getGroupBy() {
		return groupBy;
	}

	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}

	public boolean isDistinct() {
		return distinct;
	}

	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}
	
	

	public CallPatten getResult_patten() {
		return result_patten;
	}

	public CallPatten getAlias_patten() {
		return alias_patten;
	}

	public CallPatten getOrderBy_patten() {
		return orderBy_patten;
	}

	public CallPatten getGroupBy_patten() {
		return groupBy_patten;
	}

	public CallPatten getCondition_patten() {
		return condition_patten;
	}

	@Override
	public String toString() {
		return "OprationalVariable [result=" + result + ", result_patten="
				+ result_patten + ", alias=" + alias + ", alias_patten="
				+ alias_patten + ", firstResult=" + firstResult
				+ ", maxResult=" + maxResult + ", orderBy=" + orderBy
				+ ", orderBy_patten=" + orderBy_patten + ", groupBy=" + groupBy
				+ ", groupBy_patten=" + groupBy_patten + ", distinct="
				+ distinct + ", condition=" + condition + ", condition_patten="
				+ condition_patten + "]";
	}

	
	private class InnerIndexObj{
		public String colName;
		
		public int index;

		public InnerIndexObj(String colName, int index) {
			super();
			this.colName = colName;
			this.index = index;
		}
		
		
	}
	 
}
