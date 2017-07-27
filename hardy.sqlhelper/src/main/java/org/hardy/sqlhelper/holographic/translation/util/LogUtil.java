package org.hardy.sqlhelper.holographic.translation.util;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.hardy.sqlhelper.holographic.translation.inf.Condition;

public class LogUtil{
   /**
    * log日志输出  
    * @param log
    * @param condition
    * @return
    */
	@SuppressWarnings("unchecked")
	public static <T,E extends Condition<T>>E log(Logger log,Condition<T> condition){
		log.info("SQL:"+condition.getPhrase());
	    log.info("PARAMS:"+condition.getT());
	    return (E) condition;
	}
	/**
	 * 打印语句
	 * @param condition
	 */
	public static <T>void print(Condition<T> condition){
		System.out.println("SQL:"+condition.getPhrase());
		if(condition.getT().getClass().isArray())
		System.out.println("PARAMS:"+Arrays.toString((Object[])condition.getT()));
		else{
			System.out.println("PARAMS:"+condition.getT().toString());
		}
	}
	/**
	 * 记录日志并打印
	 * @param log
	 * @param sql
	 * @param params
	 */
	public static void print(Logger log,String sql,Object params){
		log.info("SQL:"+sql);
	    log.info("PARAMS:"+params);
	    System.out.println("SQL:"+sql);
	    if(!params.getClass().isArray())
	    System.out.println("PARAMS:"+params);
	    else  System.out.println("PARAMS:"+Arrays.toString((Object[])params));
	}
	
	public static void print(Logger log,Object obj){
		log.info("INFO:"+obj);
		 if(!obj.getClass().isArray())
	    System.out.println("INFO:"+obj.toString());
		 else  System.out.println("INFO:"+Arrays.toString((Object[])obj));
	   
	}
}
