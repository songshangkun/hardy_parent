package org.hardy.sqlhelper.hql;


import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.song.tool.array.util.ArraysUtils;


/**
 *查询不处理group by和聚合函数等操作
 * @author song
 */
public class HqlUtil {
  
    /**
     * 该方法利用输入的参数组成一个获取属性值并放入map中的hql语句，
     * 仅仅生成hql语句，生成的语句还需要被执行才能获取map。
     * getMapString("Role", "r", new String[]{"rid","name","description","createDate"}, "rid", "=");生成
     * select new map (r.rid as rid,r.name as name,r.description as description,r.createDate as createDate) from Role as r where r.rid = ?
     * @param className  类的名称
     * @param alias 类的缩写  
     * @param propertiesNames  要查询的属性名称集合
     * @param key  查找的key
     * @param ope  查询key的操作符
     * @return  hql语句
     */
    public static String getMapString(String className,String alias,String[] propertiesNames,String key,String ope){       
         StringBuilder condition = new StringBuilder();
         condition.append(alias).append(".").append(key).append(" ").append(ope).append(" ").append("?");
         return getMapString(className, alias, propertiesNames, condition.toString());
    }
    /**
     * 该方法利用输入的参数组成一个获取属性值并放入map中的hql语句，
     * 仅仅生成hql语句，生成的语句还需要被执行才能获取map。
     * @param className
     * @param alias
     * @param propertiesNames
     * @param condition where后的条件从句 r.rid = ?
     * @return 
     */
    public static String getMapString(String className,String alias,String[] propertiesNames,String condition){       
         StringBuilder sb = new StringBuilder("select new map (");
         for(int i=0;i<propertiesNames.length;i++){
              sb.append(alias).append(".").append(propertiesNames[i]).append(" as ").append(propertiesNames[i]);
              if(i<propertiesNames.length-1)sb.append(",");
         }
         sb.append(") from ").append(className).append(" as ").append(alias).append(" where ").append(condition);
         return sb.toString();
    }

    
    /**
     *结合方法BaseDaoImpl的queryUpdate(hql, map);方法，该方法会传入一个Map来为hql语句赋值
     * 此方法返回hql语句并且补充用于给hql赋值的map的关键值参数
     * @param className 类的名称
     * @param alias  类的别名
     * @param map   参数名和参数值map
     * @param keyName   查找关键字
     * @param keyAliasName  查找关键字别名  因为map的key必须唯一
     * @param keyAliasValue  关键值的对象 原数据
     * @return 
     */
    public static String getUpdateHql(String className,String alias,Map<String, Object> map, String keyName, String keyAliasName,Object keyAliasValue){
       StringBuilder sb = new StringBuilder("update ").append(className).append(" as ").append(alias).append(" set ");
       for(Iterator<Entry<String, Object>> ite=map.entrySet().iterator();ite.hasNext();){    
            String s = ite.next().getKey();
            sb.append(alias).append(".").append(s).append(" = :").append(s);   
            if(ite.hasNext()){
                sb.append(",");
            }
        }
        sb.append(" where ").append(alias).append(".").append(keyName).append(" = :").append(keyAliasName);
        map.put(keyAliasName, keyAliasValue);
        return sb.toString();
    }
    
  
    /**
     * 本方法只对简单的hql语句做简单的改变
     * 不支持复杂的hql语句，将简单的hql语句截取order或group前的子句，
     * 并将from前的选择语句改为select count子句，
     * 去掉原句中的fetch关键字。
     * 将选择语句变成查询记录数的语句
     * @param hql
     * @return 
     */
    public static String getSimpleCountHql(String hql){
        int temp = hql.length();
        int from = hql.indexOf("from");
        int order = hql.indexOf("order");
        int group = hql.indexOf("group");
        if(order!=-1&&group!=-1) temp = order<group?order:group;
        if(order==-1&&group!=-1) temp = group;
        if(order!=-1&&group==-1) temp = order;
        String s2 = "select count(*) "+hql.substring(from, temp);
        return s2.replace(" fetch", "");
    }
     /**
      *  搜索不重复的搜索记录数，
      * 这种情况需要原来的hql语句同样适用 select distinct  x from XXX as x.
      * 所以直接将其变为从句
      * @param hql
      * @param aliasCondition  别名短句  最简单的形式就是  你写的hql的对象别名  如hql： from Employee as e，那么aliasCondition就是e
      * @return 
      */
      public static String getDistinctCountHql(String hql,String aliasCondition){
         int temp = hql.length();
        int from = hql.indexOf("from");
        int order = hql.indexOf("order");
        int group = hql.indexOf("group");
        if(order!=-1&&group!=-1) temp = order<group?order:group;
        if(order==-1&&group!=-1) temp = group;
        if(order!=-1&&group==-1) temp = order;
        String s2 = "select count(distinct "+aliasCondition+") "+hql.substring(from, temp);
        return s2.replace(" fetch", "");
      }
     
      /**
       * 通过输入的数组参数判断是否连接某个表进行多表连接查询
       *此方法针对pojo类进行操作.持久化数据对象别名默认为a。
       * @param map   参数的输入对象， 在外部实例化传入
       * @param clazz  要查找的类对象
       * @param id   要查找的类对象的id
       * @param tableProperties  需要连接的表 以map形式 key是表在查找类中的属性名称 value是要传入的对象数组
       * @param attributes  不需要连接的表 以map形式 key是表在查找类中的属性名称 value是要传入的对象数组
       * @param orderBy 排序规则
       * @param ssb SpecialOptionSB 接口 对其他类型的其他特殊操作
       * @return  完整hql语句 返回的最后会多一个and，如果需要继续拼装可直接使用，如果不需要必须删除
       */
      public static StringBuilder multiTableRelatedQuery(Map<String,Object> map,Class<?> clazz,String id,Map<String,Object[]> tableProperties ,Map<String,Object[]> attributes,String orderBy,SpecialOptionSB ssb){  
    	   return multiTableRelatedQuery(map, clazz.getName(), id, tableProperties, attributes,orderBy,ssb);
      }
      /**
       * 通过输入的数组参数判断是否连接某个表进行多表连接查询
       *此方法针对pojo类进行操作。
       * @param map   参数的输入对象， 在外部实例化传入
       * @param clazzName  要查找的类对象名称
       * @param id   要查找的类对象的id
       * @param tableProperties  需要连接的表 以map形式 key是表在查找类中的属性名称 value是要传入的对象数组
       * @param attributes  不需要连接的表 以map形式 key是表在查找类中的属性名称 value是要传入的对象数组
       * @param orderBy 排序规则
       * @param ssb SpecialOptionSB 接口 对其他类型的其他特殊操作 可以直接利用最后的and 进行继续拼装
       * @return  完整hql语句 返回的最后会多一个and，如果需要继续拼装可直接使用，如果不需要必须删除
       */
      public static StringBuilder multiTableRelatedQueryID(Map<String,Object> map,String clazzName,String id,Map<String,Object[]> tableProperties ,Map<String,Object[]> attributes,String orderBy,SpecialOptionSB ssb){
    	  return multiTableRelatedQuery(map, clazzName, id, tableProperties, attributes, orderBy, ssb, "."+id);
      }
      /**
       *  通过输入的数组参数判断是否连接某个表进行多表连接查询
       *此方法针对pojo类进行操作.持久化数据对象别名默认为a。
      * @param map   参数的输入对象， 在外部实例化传入
       * @param clazzName  要查找的类对象名称
       * @param id   要查找的类对象的id
       * @param tableProperties  需要连接的表 以map形式 key是表在查找类中的属性名称 value是要传入的对象数组
       * @param attributes  不需要连接的表 以map形式 key是表在查找类中的属性名称 value是要传入的对象数组
       * @param orderBy 排序规则
       * @param ssb SpecialOptionSB 接口 对其他类型的其他特殊操作 可以直接利用最后的and 进行继续拼装
       * @return  完整hql语句 返回的最后会多一个and，如果需要继续拼装可直接使用，如果不需要必须删除 搜索实体类对象
       */
      public static StringBuilder multiTableRelatedQuery(Map<String,Object> map,String clazzName,String id,Map<String,Object[]> tableProperties ,Map<String,Object[]> attributes,String orderBy,SpecialOptionSB ssb){
    	  return multiTableRelatedQuery(map, clazzName, id, tableProperties, attributes, orderBy, ssb, " ");
      }
      /**
       *  通过输入的数组参数判断是否连接某个表进行多表连接查询
       *此方法针对pojo类进行操作.持久化数据对象别名默认为a。
       *注意 attributes的key 不要出现如 xxx.ww 和xxxww这种情况，因为xxx.ww会被转化成xxxww，这样就出现key和value的覆盖
       * @param map   参数的输入对象， 在外部实例化传入
       * @param clazzName  要查找的类对象名称
       * @param id   要查找的类对象的id
       * @param tableProperties  需要连接的表 以map形式 key是表在查找类中的属性名称 value是要传入的对象数组
       * @param attributes  不需要连接的表 以map形式 key是表在查找类中的属性名称 value是要传入的对象数组
       * @param orderBy 排序规则
       * @param ssb SpecialOptionSB 接口 对其他类型的其他特殊操作 可以直接利用最后的and 进行继续拼装
       * @param target  搜索的标量，target字符是“.id”,他要与表别名a相连
       * @return  完整hql语句 返回的最后会多一个and，如果需要继续拼装可直接使用，如果不需要必须删除 搜索target标量
       */
      public static StringBuilder multiTableRelatedQuery(Map<String,Object> map,String clazzName,String id,Map<String,Object[]> tableProperties ,Map<String,Object[]> attributes,String orderBy,SpecialOptionSB ssb,String target){  
    	  StringBuilder sb = new StringBuilder("select distinct a"+target+" from "+clazzName+" as a ");
    	  boolean[] flags = new boolean[null==tableProperties?0:tableProperties.size()];
    	  int n = 0;
    	 if(null!=tableProperties&&tableProperties.size()>0){
    		 for(String propertieName:tableProperties.keySet()){
      		   if(null!=tableProperties.get(propertieName)&&tableProperties.get(propertieName).length>0){
      			    flags[n] = true;
      			    sb.append(clazzName+" as a").append(n).append(" inner join a").append(n).append(".").append(propertieName).
      			    append(" as ").append(propertieName).append(", ");
      		   }
      		   n++;
      	 }
    		 sb.append("where ");
//    		 System.out.println("test1=="+sb.toString());
    		 if(ArraysUtils.getObjectCount(flags, true)>0)
    		 sb.delete(sb.indexOf("as a")+5, sb.indexOf("inner")).delete(sb.indexOf("join")+6, sb.indexOf("join")+7).delete(sb.indexOf("where")-2, sb.indexOf("where")-1);
    		 for(String propertieName:tableProperties.keySet()){
        		 if(null!=tableProperties.get(propertieName)&&tableProperties.get(propertieName).length>0){
        			 sb.append("(");
        			 for(int i=0;i<tableProperties.get(propertieName).length;i++){
        				 sb.append(propertieName).append(" = :").append(propertieName).append(i);
        					 map.put(propertieName+i, tableProperties.get(propertieName)[i]);
        				  if(i<tableProperties.get(propertieName).length-1) sb.append(" or ");
        			 }
        			 sb.append(") and "); 	
        		 }
        	 }
    	 }else{
    		 sb.append("where ");
    	 }
    	 if(null!=attributes&&attributes.size()>0){
    		   for(String propertieName:attributes.keySet()){
    			   if(null!=attributes.get(propertieName)&&attributes.get(propertieName).length>0){
    					 sb.append("(");
    					 for(int i=0;i<attributes.get(propertieName).length;i++){
    						  sb.append("a.").append(propertieName).append("= :").append(propertieName.replace(".", "")).append(i);
    							 map.put(propertieName.replace(".", "")+i, attributes.get(propertieName)[i]);
    						  if(i<attributes.get(propertieName).length-1) sb.append(" or ");
    					 }
    					 sb.append(") and "); 	
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
		 if(null!=ssb)ssb.doSpecialExcute(sb,map);//最后要加and进行统一
		 if(sb.lastIndexOf("and")==-1)sb.delete(sb.lastIndexOf("where"), sb.length());
			else sb.delete(sb.lastIndexOf("and"), sb.length());
		 if(orderBy!=null&&!"".equals(orderBy.trim()))sb.append(" order by a.").append(orderBy); 
    	  return sb;
      }
      
      
      
      public interface SpecialOptionSB{
    	  void doSpecialExcute(StringBuilder sb,Map<String,Object> condition);
      }
    
      /**
       * 这里的alias实际是从表属性名称
       *通过输入的数组参数判断是否连接某个表进行多表连接查询
       *此方法针对pojo类进行操作.持久化数据对象别名默认为a。
       *注意 attributes的key 不要出现如 xxx.ww 和xxxww这种情况，因为xxx.ww会被转化成xxxww，这样就出现key和value的覆盖
       * @param map   参数的输入对象， 在外部实例化传入
       * @param clazzName  要查找的类对象名称
       * @param id   要查找的类对象的id
       * @param tableProperties  需要连接的表 以map形式 key是表在查找类中的属性名称 value是要传入的对象数组
       * @param attributes  不需要连接的表 以map形式 key是表在查找类中的属性名称 value是要传入的对象数组
       * @param orderBy 排序规则
       * @param ssb SpecialOptionSB 接口 对其他类型的其他特殊操作 可以直接利用最后的and 进行继续拼装
       * @param target  搜索的标量，target字符是“.id”,他要与表别名a相连
       * @return  完整hql语句 返回的最后会多一个and，如果需要继续拼装可直接使用，如果不需要必须删除 搜索target标量
       */
      public static StringBuilder multiTableRelatedAliasQuery(Map<String,Object> map,String clazzName,String id,Map<String,PairKV> tableProperties ,Map<String,Object[]> attributes,String orderBy,SpecialOptionSB ssb,String target){  
    	  StringBuilder sb = new StringBuilder("select distinct a"+target+" from "+clazzName+" as a ");
    	  boolean[] flags = new boolean[null==tableProperties?0:tableProperties.size()];
    	  int n = 0;
    	 if(null!=tableProperties&&tableProperties.size()>0){
    		 for(String propertieName:tableProperties.keySet()){
      		   if(null!=tableProperties.get(propertieName)&&null!=tableProperties.get(propertieName).getObjs()&&tableProperties.get(propertieName).getObjs().length>0){
      			    flags[n] = true;
      			    sb.append(clazzName+" as a").append(n).append(" inner join a").append(n).append(".").append(propertieName).
      			    append(" as ").append(propertieName).append(", ");
      		   }
      		   n++;
      	 }
    		 sb.append("where ");
//    		 System.out.println("test1=="+sb.toString());
    		 if(ArraysUtils.getObjectCount(flags, true)>0)
    		 sb.delete(sb.indexOf("as a")+5, sb.indexOf("inner")).delete(sb.indexOf("join")+6, sb.indexOf("join")+7).delete(sb.indexOf("where")-2, sb.indexOf("where")-1);
    		 for(String propertieName:tableProperties.keySet()){
        		 if(null!=tableProperties.get(propertieName)&&null!=tableProperties.get(propertieName).getObjs()&&tableProperties.get(propertieName).getObjs().length>0){
        			 sb.append("(");
        			 for(int i=0;i<tableProperties.get(propertieName).getObjs().length;i++){
        				if(null!=tableProperties.get(propertieName).getKey()) sb.append(propertieName+"."+tableProperties.get(propertieName).getKey());
        				else sb.append(propertieName);
        				sb.append(" = :").append(propertieName).append(i);
        					 map.put(propertieName+i, tableProperties.get(propertieName).getObjs()[i]);
        				  if(i<tableProperties.get(propertieName).getObjs().length-1) sb.append(" or ");
        			 }
        			 sb.append(") and "); 	
        		 }
        	 }
    	 }else{
    		 sb.append("where ");
    	 }
    	 if(null!=attributes&&attributes.size()>0){
    		   for(String propertieName:attributes.keySet()){
    			   if(null!=attributes.get(propertieName)&&attributes.get(propertieName).length>0){
    					 sb.append("(");
    					 for(int i=0;i<attributes.get(propertieName).length;i++){
    						  sb.append("a.").append(propertieName).append("= :").append(propertieName.replace(".", "")).append(i);
    							 map.put(propertieName.replace(".", "")+i, attributes.get(propertieName)[i]);
    						  if(i<attributes.get(propertieName).length-1) sb.append(" or ");
    					 }
    					 sb.append(") and "); 	
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
		 if(null!=ssb)ssb.doSpecialExcute(sb,map);//最后要加and进行统一
		 if(sb.lastIndexOf("and")==-1)sb.delete(sb.lastIndexOf("where"), sb.length());
			else sb.delete(sb.lastIndexOf("and"), sb.length());
		 if(orderBy!=null&&!"".equals(orderBy.trim()))sb.append(" order by a.").append(orderBy); 
    	  return sb;
      }
      
      /**
       *  通过输入的数组参数判断是否连接某个表进行多表连接查询
       *此方法针对pojo类进行操作.持久化数据对象别名默认为a。
       *注意 attributes的key 不要出现如 xxx.ww 和xxxww这种情况，因为xxx.ww会被转化成xxxww，这样就出现key和value的覆盖
       *此方法将“=”循环换为sql的in语句
       * @param clazzName  要查找的类对象名称
       * @param id   要查找的类对象的id
       * @param tableProperties  需要连接的表 以map形式 key是表在查找类中的属性名称 value是要传入的对象数组
       * @param attributes  不需要连接的表 以map形式 key是表在查找类中的属性名称 value是要传入的对象数组
       * @param orderBy 排序规则
       * @param ssb SpecialOptionSB 接口 对其他类型的其他特殊操作 可以直接利用最后的and 进行继续拼装
       * @param target  搜索的标量，target字符是“.id”,他要与表别名a相连
       * @return  完整hql语句 返回的最后会多一个and，如果需要继续拼装可直接使用，如果不需要必须删除 搜索target标量
       */
      public static StringBuilder multiTableRelatedQuery(String clazzName,String id,Map<String,Object[]> tableProperties ,Map<String,Object[]> attributes,String orderBy,SpecialOptionSB ssb,String target){  
    	  StringBuilder sb = new StringBuilder("select distinct a"+target+" from "+clazzName+" as a ");
    	  boolean[] flags = new boolean[null==tableProperties?0:tableProperties.size()];
    	  int n = 0;
    	 if(null!=tableProperties&&tableProperties.size()>0){
    		 for(String propertieName:tableProperties.keySet()){
      		   if(null!=tableProperties.get(propertieName)&&tableProperties.get(propertieName).length>0){
      			    flags[n] = true;
      			    sb.append(clazzName+" as a").append(n).append(" inner join a").append(n).append(".").append(propertieName).
      			    append(" as ").append(propertieName).append(", ");
      		   }
      		   n++;
      	 }
    		 sb.append("where ");
    		 if(ArraysUtils.getObjectCount(flags, true)>0)
    		 sb.delete(sb.indexOf("as a")+5, sb.indexOf("inner")).delete(sb.indexOf("join")+6, sb.indexOf("join")+7).delete(sb.indexOf("where")-2, sb.indexOf("where")-1);
    		 for(String propertieName:tableProperties.keySet()){
        		 if(null!=tableProperties.get(propertieName)&&tableProperties.get(propertieName).length>0){
        			 sb.append("(").append(propertieName).append(" ");
        			 sb.append(toSqlInForm(tableProperties.get(propertieName)));
        			 sb.append(") and "); 	
        		 }
        	 }
    	 }else{
    		 sb.append("where ");
    	 }
    	 if(null!=attributes&&attributes.size()>0){
    		   for(String propertieName:attributes.keySet()){
    			   if(null!=attributes.get(propertieName)&&attributes.get(propertieName).length>0){
    				   sb.append("( a.").append(propertieName).append(" ");
    				   sb.append(toSqlInForm(attributes.get(propertieName)));
    					 sb.append(") and "); 	
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
		 if(null!=ssb)ssb.doSpecialExcute(sb,null);//最后要加and进行统一
		 if(sb.lastIndexOf("and")==-1)sb.delete(sb.lastIndexOf("where"), sb.length());
			else sb.delete(sb.lastIndexOf("and"), sb.length());
			if(orderBy!=null&&!"".equals(orderBy.trim()))sb.append(" order by a.").append(orderBy); 
    	  return sb;
      }
      
      /**
       *这里的alias实际是从表属性名称
       *通过输入的数组参数判断是否连接某个表进行多表连接查询
       *此方法针对pojo类进行操作.持久化数据对象别名默认为a。
       *注意 attributes的key 不要出现如 xxx.ww 和xxxww这种情况，因为xxx.ww会被转化成xxxww，这样就出现key和value的覆盖
       * @param clazzName  要查找的类对象名称
       * @param id   要查找的类对象的id
       * @param tableProperties  需要连接的表 以map形式 key是表在查找类中的属性名称 value是要传入的对象数组
       * @param attributes  不需要连接的表 以map形式 key是表在查找类中的属性名称 value是要传入的对象数组
       * @param orderBy 排序规则
       * @param ssb SpecialOptionSB 接口 对其他类型的其他特殊操作 可以直接利用最后的and 进行继续拼装
       * @param target  搜索的标量，target字符是“.id”,他要与表别名a相连
       * @return  完整hql语句 返回的最后会多一个and，如果需要继续拼装可直接使用，如果不需要必须删除 搜索target标量
       */
      public static StringBuilder multiTableRelatedAliasQuery(String clazzName,String id,Map<String,PairKV> tableProperties ,Map<String,Object[]> attributes,String orderBy,SpecialOptionSB ssb,String target){  
    	  StringBuilder sb = new StringBuilder("select distinct a"+target+" from "+clazzName+" as a ");
    	  boolean[] flags = new boolean[null==tableProperties?0:tableProperties.size()];
    	  int n = 0;
    	 if(null!=tableProperties&&tableProperties.size()>0){
    		 for(String propertieName:tableProperties.keySet()){
      		   if(null!=tableProperties.get(propertieName)&&null!=tableProperties.get(propertieName).getObjs()&&tableProperties.get(propertieName).getObjs().length>0){
      			    flags[n] = true;
      			    sb.append(clazzName+" as a").append(n).append(" inner join a").append(n).append(".").append(propertieName).
      			    append(" as ").append(propertieName).append(", ");
      		   }
      		   n++;
      	 }
    		 sb.append("where ");
//    		 System.out.println("test1=="+sb.toString());
    		 if(ArraysUtils.getObjectCount(flags, true)>0)
    		 sb.delete(sb.indexOf("as a")+5, sb.indexOf("inner")).delete(sb.indexOf("join")+6, sb.indexOf("join")+7).delete(sb.indexOf("where")-2, sb.indexOf("where")-1);
    		 for(String propertieName:tableProperties.keySet()){
        		 if(null!=tableProperties.get(propertieName)&&null!=tableProperties.get(propertieName).getObjs()&&tableProperties.get(propertieName).getObjs().length>0){
        			 sb.append("(");
        			 if(null!=tableProperties.get(propertieName).getKey()) 
        			 sb.append(propertieName+"."+tableProperties.get(propertieName).getKey());
     				 else sb.append(propertieName);
        			 sb.append(" ");
        			 sb.append(toSqlInForm(tableProperties.get(propertieName).getObjs())); 
        			 sb.append(") and "); 	
        		 }
        	 }
    	 }else{
    		 sb.append("where ");
    	 }
    	 if(null!=attributes&&attributes.size()>0){
    		   for(String propertieName:attributes.keySet()){
    			   if(null!=attributes.get(propertieName)&&attributes.get(propertieName).length>0){
    				 sb.append("(a.").append(propertieName).append(" ");
          			 sb.append(toSqlInForm(attributes.get(propertieName))); 
          			 sb.append(") and "); 	
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
		 if(null!=ssb)ssb.doSpecialExcute(sb,null);//最后要加and进行统一
		 if(sb.lastIndexOf("and")==-1)sb.delete(sb.lastIndexOf("where"), sb.length());
			else sb.delete(sb.lastIndexOf("and"), sb.length());
		 if(orderBy!=null&&!"".equals(orderBy.trim()))sb.append(" order by a.").append(orderBy); 
    	  return sb;
      }
      
      
      public static class PairKV{
    	   private String key;
    	   private Object[] objs;
    	   
		public PairKV() {
			super();
			 
		}
		public PairKV(String key, Object[] objs) {
			super();
			this.key = key;
			this.objs = objs;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public Object[] getObjs() {
			return objs;
		}
		public void setObjs(Object[] objs) {
			this.objs = objs;
		}
    	   
      }
      
      /**
       * 将数组 list set 等列表结合转化为sql语句的 in(xxx,xxx,xxx)字符串形式
       * @param obj 只能输入Array，List，Set
       * @return
       */
      @SuppressWarnings({ "rawtypes", "unchecked" })
	public static String toSqlInForm(Object obj){
    	  StringBuilder  result = null;
      	  if(obj instanceof String[]){  
      		  String[] objs = (String[])obj;
      		  result =  new StringBuilder("(");
      		  for(int i=0;i<objs.length;i++){
      			  result.append("'").append(objs[i]).append("'");
      			  if(i<objs.length-1)result.append(",");
      		  }
      		  result.append(")");
      	  }
      	  if(obj instanceof int[]){
      		  int[] objs = (int[])obj;
      		  result =  new StringBuilder(Arrays.toString(objs));
      	  }
      	  if(obj instanceof long[]){
      		  long[] objs = (long[])obj;
      		  result =  new StringBuilder(Arrays.toString(objs));
      	  }
      	  if(obj instanceof double[]){
      		  double[] objs = (double[])obj;
      		  result =  new StringBuilder(Arrays.toString(objs));
      	  }
      	  if(obj instanceof float[]){
      		  float[] objs = (float[])obj;
      		  result =  new StringBuilder(Arrays.toString(objs));
      	  }
      	  if(obj instanceof boolean[]){
      		  boolean[] objs = (boolean[])obj;
      		  result =  new StringBuilder(Arrays.toString(objs));
      	  }
      	  if(obj instanceof byte[]){
      		  byte[] objs = (byte[])obj;
      		  result =  new StringBuilder(Arrays.toString(objs));
      	  }
      	  if(obj instanceof short[]){
      		  short[] objs = (short[])obj;
      		  result =  new StringBuilder(Arrays.toString(objs));
      	  }
      	  if(!(obj instanceof String[]) && obj instanceof Object[]){ 
      		  Object[] objs = (Object[])obj;
      		  result =  new StringBuilder(Arrays.toString(objs));
      	  }
      	  if(obj instanceof List){
      		  List objs = (List)obj;
              if(!objs.isEmpty()&&objs.get(0) instanceof String){ 
            	  String[] objsArray = new String[objs.size()];
            	  objs.toArray(objsArray);
          		  result =  new StringBuilder("(");
          		  for(int i=0;i<objsArray.length;i++){
          			  result.append("'").append(objsArray[i]).append("'");
          			  if(i<objsArray.length-1)result.append(",");
          		  }
          		  result.append(")");
              }else{
            	  result =  new StringBuilder(objs.toString());   
              }
      		 
      	  }
      	  if(obj instanceof Set){
      		  Set objs = (Set)obj;
      		if(!objs.isEmpty()&&objs.iterator().next() instanceof String){
      			String[] objsArray = new String[objs.size()];
          	    objs.toArray(objsArray);
        		  result =  new StringBuilder("(");
        		  for(int i=0;i<objsArray.length;i++){
        			  result.append("'").append(objsArray[i]).append("'");
        			  if(i<objsArray.length-1)result.append(",");
        		  }
        		  result.append(")");
      		}
      		  result =  new StringBuilder(objs.toString()); 
      	  } 
      	  result.deleteCharAt(0).deleteCharAt(result.length()-1);
      	  result.insert(0, "in (").insert(result.length(), ")");  
      	  return result.toString();
      }
      
      /**
       * 直接在原有语句基础上增加搜索count
       * @param hql
       * @return
       */
      public static String getPolymerizationCountHql(String hql){
        return "select count(1) from (" + hql + ") as $song ";
      }
}
