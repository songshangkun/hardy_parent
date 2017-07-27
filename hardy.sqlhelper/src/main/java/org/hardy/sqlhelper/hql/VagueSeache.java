package org.hardy.sqlhelper.hql;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.song.tool.sql.TableUtil;
import com.song.tool.sql.pcvo.TableInfos;

public class VagueSeache {
	
	public static StringBuilder seacher(Class<?> clazz,Map<String,Object>map,String... values){
		StringBuilder sb = new StringBuilder();
		sb.append("from ").append(clazz.getName()).append(" as o where ");
		for(int i=0;i<values.length;i++){
			sb.append("(");
			Field[] fs = clazz.getDeclaredFields();				
				for(Field f:fs){
					if(f.getType()==String.class){
						 sb.append("o.").append(f.getName()).append(" like :value").append(i).append(" or ");
						 map.put("value"+i, "%"+values[i]+"%");
					}	 
				}
				sb.delete(sb.lastIndexOf("or"), sb.length());
				sb.append(") ");
				if(i<values.length-1) sb.append(" and ");
		}		
		return sb;
	}
  
	public static StringBuilder seacher(Class<?> clazz,Map<String,Object> map,String[] properties,String... values){
		StringBuilder sb = new StringBuilder();
		sb.append("from ").append(clazz.getName()).append(" as o where ");
		for(int i=0;i<values.length;i++){
			sb.append("(");
			for(int j=0;j<properties.length;j++){
				Field[] fs = clazz.getDeclaredFields();				
				for(Field f:fs){
					 if(f.getName().equals(properties[j])){
						 if(f.getType()!=String.class) throw new RuntimeException("properties ["+properties[j]+"] is not String ");
						 else{	 
							 sb.append("o.").append(properties[j]).append(" like :value").append(i);
							 if(j<properties.length-1)sb.append(" or ");
							 map.put("value"+i, "%"+values[i]+"%");
						 } 
					 }
				}
				if(j==properties.length-1)sb.append(")"); 
			}
			 if(i<values.length-1) sb.append(" and ");
		}		
		return sb;
	}
	
	/**
	 * 直接应用另一个seacher方法的全部varchar列。
	 * @param tableName
	 * @param conn
	 * @param values
	 * @return
	 */
	public static StringBuilder seacher(String tableName,Connection conn,String... values){
		TableUtil tu = new TableUtil(conn);
		List<TableInfos> list = tu.getColInfos(tableName, null);
		List<String> colNames = new ArrayList<String>();
		for(TableInfos info:list){
			if(info.getDate_TYPE()==12){
				colNames.add(info.getColumn_NAME());
			}
		}
		String[] colNamesArray = new String[colNames.size()];
		colNames.toArray(colNamesArray);
		return seacher(tableName, colNamesArray, values);
     	}
	/**
	 * 组成一个非标准的命名查询,支持hibernate的sql查询和jdbcTemplate的命名查询
	 * @param tableName
	 * @param colNames
	 * @param values
	 * @return
	 */
	public static StringBuilder seacher(String tableName,String[] colNames,String... values){
		   StringBuilder sb = new StringBuilder("select * from "+tableName);
		   if(values!=null&&values.length>0){
			   sb.append(" where ");
			   for(int i=0;i<values.length;i++){
				   sb.append("(");
				   for(int j=0;j<colNames.length;j++){
					   sb.append(colNames[j]).append(" like ").append(":value").append(i);
					   if(j<colNames.length-1)sb.append(" or ");
				   }
				   sb.append(")");
				   if(i<values.length-1) sb.append(" and ");
			   }
		   }
		   return sb;
	   }
	
	public static  Map<String,Object> getParamsMap(String... values){
		Map<String,Object> map = new HashMap<String, Object>();
		 for(int i=0;i<values.length;i++){
			 map.put("value"+i, "%"+values[i]+"%");
		 }
		 return map;
	}
	
}
