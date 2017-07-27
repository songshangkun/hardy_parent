package org.hardy.sqlhelper.hql;


import static com.song.ssh.hibernate3.templateutil.impl.HqlUtil.toSqlInForm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.song.ssh.hibernate3.templateutil.iface.ADUSBaseDao;
import com.song.ssh.hibernate3.templateutil.iface.BaseDAO;
import com.song.ssh.hibernate3.templateutil.iface.SimpleBaseDao;
import com.song.ssh.hibernate3.templateutil.impl.EntityCount;
import com.song.ssh.hibernate3.templateutil.impl.HqlUtil;
import com.song.ssh.hibernate3.templateutil.impl.HqlUtil.PairKV;
import com.song.ssh.hibernate3.templateutil.impl.HqlUtil.SpecialOptionSB;
import com.song.ssh.hibernate3.templateutil.impl.SelfLinkedQuery;
import com.song.tool.array.util.ArraysUtils;

public class HqlQueryUtil {
	/**
	 * 该方法集成了HqlUtil工具类解析HQL语句的两种情况,不包含自关联查询,查询不处理group by和聚合函数等操作
	 * @param condition  不为空时采取预编译，否则直接注入HQL
	 * @param clazzName
	 * @param id
	 * @param tableProperties 
	 * @param attributes
	 * @param orderBy
	 * @param ssb
	 * @param target
	 * @return
	 */
	public static StringBuilder query(Map<String,Object> condition,String clazzName,String id,Map<String,Object> tableProperties ,Map<String,Object[]> attributes,String orderBy,SpecialOptionSB ssb,String target){  
  	  StringBuilder sb = new StringBuilder("select distinct a"+target+" from "+clazzName+" as a ");
  	  boolean[] flags = new boolean[null==tableProperties?0:tableProperties.size()];
  	  int n = 0;
  	 if(null!=tableProperties&&tableProperties.size()>0){
  		 for(String propertieName:tableProperties.keySet()){
  		 if(tableProperties.get(propertieName) instanceof Object[]){
  			 Object[] tempObjs = (Object[])tableProperties.get(propertieName);
  			if(null!=tempObjs&&tempObjs.length>0){
			    flags[n] = true;
			    sb.append(clazzName+" as a").append(n).append(" inner join a").append(n).append(".").append(propertieName).
			    append(" as ").append(propertieName).append(", ");
		   }
  		 }
  		 if(tableProperties.get(propertieName) instanceof PairKV){
  			PairKV tempPKV = (PairKV)tableProperties.get(propertieName);
  			if(null!=tempPKV&&null!=tempPKV.getObjs()&&tempPKV.getObjs().length>0){
 			    flags[n] = true;
 			    sb.append(clazzName+" as a").append(n).append(" inner join a").append(n).append(".").append(propertieName).
 			    append(" as ").append(propertieName).append(", ");
 		   } 
  		 }   
    		   n++;
    	 }
  		 sb.append("where ");
  		 if(ArraysUtils.getObjectCount(flags, true)>0)
  		 sb.delete(sb.indexOf("as a")+5, sb.indexOf("inner")).delete(sb.indexOf("join")+6, sb.indexOf("join")+7).delete(sb.indexOf("where")-2, sb.indexOf("where")-1);
  		 for(String propertieName:tableProperties.keySet()){
  			if(tableProperties.get(propertieName) instanceof Object[]){ 
  			Object[] tempObjs = (Object[])tableProperties.get(propertieName);
  			if(condition!=null){
  				 if(null!=tempObjs&&tempObjs.length>0){
        			 sb.append("(");
        			 for(int i=0;i<tempObjs.length;i++){
        				 sb.append(propertieName).append(" = :").append(propertieName).append(i);
        					 condition.put(propertieName+i, tempObjs[i]);
        				  if(i<tempObjs.length-1) sb.append(" or ");
        			 }
        			 sb.append(") and "); 	
        		 }
  			}else{
  				 if(null!=tempObjs&&tempObjs.length>0){
  	      			 sb.append("(").append(propertieName).append(" ");
  	      			 sb.append(toSqlInForm(tableProperties.get(propertieName)));
  	      			 sb.append(") and "); 	
  	      		 }
  				}
  			}
	  			if(tableProperties.get(propertieName) instanceof PairKV){	
	  			PairKV tempPKV = (PairKV)tableProperties.get(propertieName);
	  			if(condition!=null){
	  				if(null!=tempPKV&&null!=tempPKV.getObjs()&&tempPKV.getObjs().length>0){
 	        			 sb.append("(");
 	        			 for(int i=0;i<tempPKV.getObjs().length;i++){
 	        				if(null!=tempPKV.getKey()) sb.append(propertieName+"."+tempPKV.getKey());
 	        				else sb.append(propertieName);
 	        				sb.append(" = :").append(propertieName).append(i);
 	        					 condition.put(propertieName+i, tempPKV.getObjs()[i]);
 	        				  if(i<tempPKV.getObjs().length-1) sb.append(" or ");
 	        			 }
 	        			 sb.append(") and "); 	
 	        		 }
	  			}else{
	  				 if(null!=tempPKV&&null!=tempPKV.getObjs()&&tempPKV.getObjs().length>0){
		    			 sb.append("(");
		    			 if(null!=tempPKV.getKey()) 
		    			 sb.append(propertieName+"."+tempPKV.getKey());
		 				 else sb.append(propertieName);
		    			 sb.append(" ");
		    			 sb.append(toSqlInForm(tempPKV.getObjs())); 
		    			 sb.append(") and "); 	
	  			 	}
	  			}
  			}
      	 }
  	 }else{
  		 sb.append("where ");
  	 }	
  	 if(null!=attributes&&attributes.size()>0){
  		   for(String propertieName:attributes.keySet()){
  			   if(null!=attributes.get(propertieName)&&attributes.get(propertieName).length>0){
  				 if(condition!=null){
  					sb.append("(");
					 for(int i=0;i<attributes.get(propertieName).length;i++){
						  sb.append("a.").append(propertieName).append("= :").append(propertieName.replace(".", "")).append(i);
							 condition.put(propertieName.replace(".", "")+i, attributes.get(propertieName)[i]);
						  if(i<attributes.get(propertieName).length-1) sb.append(" or ");
					 }
					 sb.append(") and "); 	
  				 }else{
  					sb.append("( a.").append(propertieName).append(" ");
   				    sb.append(toSqlInForm(attributes.get(propertieName)));
   					sb.append(") and ");
  				 	}   	
  				 }
  		   }
  	 }
  	 int countTable = 0;
		 for(int i=0;i<flags.length;i++){
			 if(flags[i]==true){
				 countTable++;
				 if(countTable>1) sb.append("a.").append(id).append(" = a").append(i).append(".").append(id).append(" and ");
			 }  
		 }
		 if(null!=ssb)ssb.doSpecialExcute(sb,condition);//最后要加and进行统一
		 if(sb.lastIndexOf("and")==-1)sb.delete(sb.lastIndexOf("where"), sb.length());
			else sb.delete(sb.lastIndexOf("and"), sb.length());
			if(orderBy!=null&&!"".equals(orderBy.trim()))sb.append(" order by a.").append(orderBy); 
  	  return sb;
    }
	/**
	    * 集成HqlUtil工具类解析HQL语句的三种情况
	    * 并搜索结果
	    * <br>规则:
	    * <br>1.传入的map的key如果以$L_开头代表连接表的map&lt;String,Object[]&gt;
	    * <br>2.传入的map的key如果以$A_开头代表自身属性的map&lt;String,Object[]&gt;
	    * <br>4.传入的map的key如果以$C_开头代表条件的map&lt;String,Object[]&gt;
	    * <br>其他规则:
	    *  <br>1. 固定的key: $self , value: map&lt;String,Object[]&gt; 条件map
	    *         2. 固定的key: $target, value: String  可以不填 默认为""
	    *         3. 固定的key: $order, value:String 可以不填 默认为null, 属性名称 asc/desc
	    *         4. 固定的key: $id,value String  连表查询时指定的链接关键属性， 连表查询一般是对象的id,自连表查询一般是要查询的关键属性 ,单表查询可以不填 (默认为null,连表查询必填,自身表链接必填);
	    *         5. 固定的key: $class,value Class<?> 查询的hibernate的持久化的类 必填    
	    *         6. 固定的key: $ssb,value 实现了 HqlUtil.SpecialOptionSB接口的实现类 不填为空
	    *         7. 固定的key: $condition,value 一个map&lt;String,Object&gt;  
	    *         8. 固定的key: $pageCount,value int 当前页数  默认是0, -1时表示搜索全部数据
	    *         9. 固定的key: $pageSize,value int 当前页数的数据量 默认是10
	    *  **/
	@SuppressWarnings("unchecked")
	public static EntityCount<Object> query(ADUSBaseDao adus,Map<String,Object> map){
		//第一步分解传入的map到相应的其他map中
		if(!map.containsKey("$pageCount"))map.put("$pageCount", 0);
		if(!map.containsKey("$pageSize"))map.put("$pageSize", 10);
		 int pageCount = (int)map.remove("$pageCount");
		 int pageSize = (int)map.remove("$pageSize");
		 String alias = "a"+map.get("$target");
		 Object[] result = query(map);
		 StringBuilder sb = (StringBuilder)result[0];
		 Map<String,Object> condition = (Map<String,Object>)result[1];
		 if(pageCount!=-1){
			return adus.getEntityCountByCondition(sb.toString(),condition,pageCount, pageSize, alias);
		 }else{
			 EntityCount<Object> ec = new EntityCount<Object>();
			 List<Object> list = adus.getListByCondition(sb.toString(), condition); 
			 ec.setCount(list.size());
			 ec.setListEntity(list);
			 return ec;
		 }
	}
	
	@SuppressWarnings("unchecked")
	public static EntityCount<Object> query(SimpleBaseDao base,Map<String,Object> map){
		//第一步分解传入的map到相应的其他map中
		if(!map.containsKey("$pageCount"))map.put("$pageCount", 0);
		if(!map.containsKey("$pageSize"))map.put("$pageSize", 10);
		 int pageCount = (int)map.remove("$pageCount");
		 int pageSize = (int)map.remove("$pageSize");
		 String alias = "a"+map.get("$target");
		 Object[] result = query(map);
		 StringBuilder sb = (StringBuilder)result[0];
		 Map<String,Object> condition = (Map<String,Object>)result[1];
		 if(pageCount!=-1){
			return base.getEntityCountByCondition(sb.toString(),condition,pageCount, pageSize, alias);
		 }else{
			 EntityCount<Object> ec = new EntityCount<Object>();
			 List<Object> list = base.getListByCondition(sb.toString(), condition); 
			 ec.setCount(list.size());
			 ec.setListEntity(list);
			 return ec;
		 }
	}
	
	@SuppressWarnings("unchecked")
	public static EntityCount<Object> query(BaseDAO base,Map<String,Object> map){
		//第一步分解传入的map到相应的其他map中
		if(!map.containsKey("$pageCount"))map.put("$pageCount", 0);
		if(!map.containsKey("$pageSize"))map.put("$pageSize", 10);
		 int pageCount = (int)map.remove("$pageCount");
		 int pageSize = (int)map.remove("$pageSize");
		 String alias = "a"+map.get("$target");
		 Object[] result = query(map);
		 StringBuilder sb = (StringBuilder)result[0];
		 String hql = sb.toString();
		 Map<String,Object> condition = (Map<String,Object>)result[1];
		 EntityCount<Object> ec = new EntityCount<Object>();
		 if(pageCount!=-1){
			String hqlcount=(null!=alias)?HqlUtil.getDistinctCountHql(hql,alias):HqlUtil.getSimpleCountHql(hql) ;	
			long count = base.getEntityNum(hqlcount, condition);
			ec.setCount(count);
			ec.setListEntity(base.queryfind(hql, condition, pageCount*pageSize+1, pageSize));
		 }else{
			 List<Object> list = base.queryfind(hql, condition);
			 ec.setCount(list.size());
			 ec.setListEntity(list); 
		 }
		 return ec;
	}
	/**
	 * @param map
	 *  * 集成HqlUtil工具类解析HQL语句的三种情况
	    * 并搜索结果
	    * <br>规则:
	    * <br>1.传入的map的key如果以$L_开头代表连接表的map&lt;String,Object[]&gt;
	    * <br>2.传入的map的key如果以$A_开头代表自身属性的map&lt;String,Object[]&gt;
	    * <br>其他规则:
	    *  <br>1. 固定的key: $self , value: map&lt;String,Object[]&gt; Object[]是条件map数组  可以不填， 不填时表示连表查询
	    *         2. 固定的key: $target, value: String  可以不填 默认为""
	    *         3. 固定的key: $order, value:String 可以不填 默认为null, 属性名称 asc/desc
	    *         4. 固定的key: $id,value String  连表查询时指定的链接关键属性， 连表查询一般是对象的id,自连表查询一般是要查询的关键属性 ,单表查询可以不填 (默认为null,连表查询必填,自身表链接必填);
	    *         5. 固定的key: $class,value Class<?> 查询的hibernate的持久化的类 必填    
	    *         6. 固定的key: $ssb,value 实现了 HqlUtil.SpecialOptionSB接口的实现类 不填为空
	    *         7. 固定的key: $condition,value 一个map&lt;String,Object&gt;  如果为空则采用in(xxx...),否则采用=的形式
	    *  <br> 返回值第一个是hql语句StringBuild，第二个是hql的条件参数Map 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] query(Map<String,Object> map){
		 Object[] result = new Object[2];  
		 if(!map.containsKey("$target")) map.put("$target", "");
		 Map<String,Object> condition = (Map<String,Object>)map.remove("$condition"); 
		 Map<String,Object[]> attributes = new HashMap<String, Object[]>();	
		 Map<String,Object> tableProperties = new HashMap<String, Object>();
		 Map<String,Object>[] self = (Map<String,Object>[])map.remove("$self");
		 String target = (String)map.remove("$target");
		 String orderBy = (String)map.remove("$order");
		 String id = (String)map.remove("$id");
		 Class<?> clazz = (Class<?>)map.remove("$class");
		 SpecialOptionSB ssb = (SpecialOptionSB)map.remove("$ssb"); 
		 if(self!=null&&self.length>0){ 
			 result[0] = SelfLinkedQuery.multitableComparable(target, orderBy, id,clazz.getName(), condition, ssb, self); 	 	 
		 }else{ 
			 for(String key:map.keySet()){
				 if(key.startsWith("$L_")) tableProperties.put(key.substring(3), map.get(key));
				 if(key.startsWith("$A_")) attributes.put(key.substring(3), (Object[])map.get(key));
				 if(key.startsWith("$C_")) condition.put(key.substring(3), map.get(key));  
			 } 
			 result[0] = query(condition, clazz.getName(), id, tableProperties, attributes, orderBy, ssb, target);			
		 }
		 result[1] = condition;
       return result; 
	}
 	 
}

