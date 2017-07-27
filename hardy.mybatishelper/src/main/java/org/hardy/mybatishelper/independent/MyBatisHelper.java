package org.hardy.mybatishelper.independent;

import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisHelper {
    private  SqlSessionFactory sqlMapper ;
     
    public MyBatisHelper(String configFile){
	    	String resource = configFile;
	     	Reader reader = null;
			try {
				reader = Resources.getResourceAsReader(resource);
			} catch (IOException e) {
				e.printStackTrace();
			}
	    	sqlMapper = new SqlSessionFactoryBuilder().build(reader); 
    }
	 
	 public  SqlSession openSession(){
		 return sqlMapper.openSession();
	 } 
	 
	 public  <T>Object execute(Class<T> mapperClass,MapperInvoke<T> invok){
		 SqlSession session = openSession();
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
	 
	 public  <T extends Object>Object execute(MapperInvoke<Object> invok,Class<?>... mapperClasses){
		 SqlSession session = openSession();
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
	 
	 public static interface MapperInvoke<T>{
		  public Object invoke(T t);
		  
		  public Object invoke(Object... objs);
		  
		  public Object invoke(Map<Class<?>,Object> map,Collection<SqlSession> sessions);
	 }
}
/**
 * 
<?xml version="1.0" encoding="utf-8"?>  
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN"  
"http://mybatis.org/dtd/mybatis-3-config.dtd">  
<configuration>  
  <properties resource="configuration/database.properties">  
    <property name="username" value="sundyn" />  
    <property name="password" value="a+1b+2" />  
  </properties>  
  <settings>  
    <setting name="cacheEnabled" value="true" />  
  </settings>  
  <typeAliases>  
    <typeAlias alias="Data" type="message.control.tcprecu.Data"/>  
  </typeAliases>  
  <!--typeHandlers>  
    <typeHandler handler="typeHandler.PhoneHandler" />  
  </typeHandlers-->  
  <environments default="development">  
    <environment id="development">  
      <transactionManager type="JDBC" />  
      <dataSource type="POOLED">  
        <property name="driver" value="${jdbc.driverClassName}" />  
        <property name="url" value="${jdbc.url}" />  
        <property name="username" value="${username}" />  
        <property name="password" value="${password}" />  
      </dataSource>  
    </environment>  
    <!--environment id="production">  
      <transactionManager type="MANAGED" />  
      <dataSource type="JNDI">  
        <property name="data_source" value="java:comp/jdbc/MyBatisDemoDS" />  
      </dataSource>  
    </environment-->  
  </environments>  
  <mappers>  
    <!--mapper resource="dao/BlogMapper.xml" /-->  
    <!--mapper url="file:///D:/mybatisdemo/mappers/TutorMapper.xml" /-->  
    <mapper class="message.control.dao.DataMapper" />  
  </mappers>  
</configuration>  
 * 
 **/
