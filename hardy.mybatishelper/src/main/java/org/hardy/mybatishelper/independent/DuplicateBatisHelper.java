package org.hardy.mybatishelper.independent;

import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.hardy.mybatishelper.independent.MyBatisHelper.MapperInvoke;



public class DuplicateBatisHelper {
     

	 private  SqlSessionFactory[] sqlMappers ;
     
	    public DuplicateBatisHelper(String... configFiles){
		    	for(int i=0;i<configFiles.length;i++){
		    		String resource = configFiles[i];
			     	Reader reader = null;
					try {
						reader = Resources.getResourceAsReader(resource);
					} catch (IOException e) {
						e.printStackTrace();
					}
			    	sqlMappers[i] = new SqlSessionFactoryBuilder().build(reader); 
		    	}
		    	
	    }
		 
		 public  SqlSession openSession(int i){
			 return sqlMappers[i].openSession();
		 } 
		 
		 public  SqlSession openSession(){
			 return sqlMappers[0].openSession();
		 } 
		 
		 public  <T>Object execute(Integer index,Class<T> mapperClass,MapperInvoke<T> invok){
			 SqlSession session = openSession(index);
			 Object result = null;
			 try {
		        T mapper = session.getMapper(mapperClass);
		        result = invok.invoke(mapper);
			 }catch(Exception e){
				 e.printStackTrace();
				 session.rollback();
			 }finally{
				 session.commit();
		    	 	 session.close();
			 }
			 return result;
		 }
		 
		 public  <T extends Object>Object execute(Integer index,MapperInvoke<Object> invok,Class<?>... mapperClasses){
			 SqlSession session = openSession(index);
			 Object result = null;
			 try {
				Object[] objects = new Object[mapperClasses.length];
				int i = 0;
				for(Class<?> mapperClass:mapperClasses){
					objects[i] =  session.getMapper(mapperClass);
					i++;
				}
		        result = invok.invoke(objects);
			 }catch(Exception e){
				 e.printStackTrace();
				 session.rollback();
			 }finally{
				 session.commit();
		    	 	 session.close();
			 }
			 return result;
		 }
		 
		 
		 public  <T extends Object>Object execute(Map<Integer, Class<?>[]> mapperClasses,MapperInvoke<Object> invok){
 			 Object result = null; 
			 Map<Class<?>,SqlSession> maps = new HashMap<>();
			 for(int i:mapperClasses.keySet()){
				  SqlSession session = openSession(i);
				 for(Class<?> clazz:mapperClasses.get(i)){
					    maps.put(clazz, session);
				 }	    
			 }
			 Map<Class<?>,Object> mapObjs = new HashMap<>();
			 for(Class<?> clazz:maps.keySet()){
				 mapObjs.put(clazz, maps.get(clazz).getMapper(clazz));
			 }	   
			  Collection<SqlSession> sessions = maps.values();
			  try{
		        result = invok.invoke(mapObjs,sessions);
			  }catch (Exception e) {
				  for(SqlSession session:maps.values()){
					    session.rollback();
				  }
			}finally {
				for(SqlSession session:maps.values()){
				    session.commit();
				    session.close();
			  }

			}
			  return result;
		 }  			  
		 
		 
	
}
