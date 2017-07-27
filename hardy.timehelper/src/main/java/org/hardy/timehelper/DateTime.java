/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hardy.timehelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 *  这个类主要实现了
 * 1-util的Date转sql的Date
 * 2-sql的Date转util的Date (子类可直接转型)
 * 3-将String转化为util的Date和sql的Date
 * 4-util的Date转化为String
 * @author 宋尚堃
 */
public class DateTime {
     private static final String DEFAULT_PATTEN = "yyyy-MM-dd HH:mm:ss";
    // private static final Calendar CALENDAR =  Calendar.getInstance() ;
      /**
       *  util的Date转sql的Date  , sql的Date转util的Date (子类可直接转型)
       * @param date  java.util.Date
       * @return 
       */
    public static  java.sql.Date getSqlDate(java.util.Date date){
         return new java.sql.Date(date.getTime()); 
    }
    
    /**
     *  将String转化为java.sql.Date  ,
     * 不要直接使用方法 java.sql.Date date  =  new java.sql.Date(Integer year,Integer mouth,Integer date)，此方法已过时
     * @param dateString  “2015-1-10  10:20:33” 月份正常输入
     * @param patten  模式"yyyy-MM-dd HH:mm:ss"  
     * @return
     * @throws ParseException 
     */
   public static java.sql.Date convertirStringToDate(String dateString,String patten) throws ParseException{  
       SimpleDateFormat  sdf ;
     if(patten!=null)  sdf  =   new SimpleDateFormat(patten);
     else sdf  =   new SimpleDateFormat(DEFAULT_PATTEN);
      java.util.Date date = sdf.parse(dateString); 
      return new java.sql.Date(date.getTime());
//       System.out.println(sqlDate.getTime()); 
   }
   
   /**
    *  将String转化为java.util.Date  ,
    * 不要直接使用方法 java.sql.Date date  =  new java.sql.Date(Integer year,Integer mouth,Integer date)，此方法已过时
    * @param dateString  “2015-1-10  10:20:33” 月份正常输入
    * @param patten  模式"yyyy-MM-dd HH:mm:ss"  
    * @return
    * @throws ParseException 
    */
  public static Date convertirStringToDateUtil(String dateString,String patten) throws ParseException{  
      SimpleDateFormat  sdf ;
    if(patten!=null)  sdf  =   new SimpleDateFormat(patten);
    else sdf  =   new SimpleDateFormat(DEFAULT_PATTEN);
      Date date = sdf.parse(dateString);
      return date;
  }
   /**
    * 将util的Date转成String
    * @param date      使用new Date()不要带参数 ， 如果想带参数要结合Calendar
    * @param patten  模式"yyyy-MM-dd HH:mm:ss"
    * @return 
    */
   public  static  String convertirDateToString(java.util.Date date , String patten){
            SimpleDateFormat  sdf ;
     if(patten!=null)  sdf  =   new SimpleDateFormat(patten);
     else sdf  =   new SimpleDateFormat(DEFAULT_PATTEN);
     return sdf.format(date);
   }
   /**
    *  通过CALENDAR 得到精确地Date时间
    * @param year
    * @param month   月份从0开始
    * @param day
    * @param heure
    * @param minute
    * @param second
    * @return 
    */
   public static java.util.Date getCalendarDate(int year , int month , int day, int heure, int minute, int second){
	   Calendar c = Calendar.getInstance();   
	   	c.set(year, month, day, heure, minute, second);
              return c.getTime();
   }
   /**
    * 根据model得到时间Date
    * @param year
    * @param month  月份从0开始
    * @param day
    * @param model   “current” h:m:s为当前时间 ，“first”00:00:00 ，"last" 23:59:59
    * @return 
    */
    public static java.util.Date getCalendarDate(int year , int month , int day,String model){
    		Calendar c = Calendar.getInstance();   
         if(model==null||"current".equals(model)) c.set(year, month, day);     
         if("first".equals(model))c.set(year, month, day,0,0,0);
         if("last".equals(model))c.set(year, month, day,23,59,59);
              return c.getTime();
   }
      /**
       * 根据年 月 日获取输入年月日的最早时间  2015 ,0 ,6  - 2015，0,7 代表 2015年一月6号的一天
       * @param year
       * @param month  月份从0开始
       * @param day
       * @return 
       */
     public static java.util.Date getCalendarDate(int year , int month , int day){
               return getCalendarDate(year,month,day,"first");
     }
     
     /**
      *  将字符串转化为Calendar的Date类型,其中的时间部分为一天最早的时间。
      * 都是每天的0点0分0秒
      * @param dateString  时间字符必须是yyyy  mm  dd的顺序，否则出错
      * @param delim
      * @return 
      */
    public static  java.util.Date  getDate(String dateString,String delim){    
        String[] strs = dateString.split(delim);
       return getCalendarDate(Integer.parseInt(strs[0]),Integer.parseInt(strs[1])-1,Integer.parseInt(strs[2]));
    }
    
    /**
      * 将字符串转化为Calendar的Date类型,获得字符串表示的相隔的几天,其中的时间部分为一天最早的时间.
      * 都是每天的0点0分0秒
      * @param dateString
      * @param delim
      * @param days  相差的天数  ＋后面几天  －前面几天
      * @return 
      */
    public static  java.util.Date getNextDate(String dateString,String delim,int days){    
        String[] strs = dateString.split(delim);
       return getCalendarDate(Integer.parseInt(strs[0]),Integer.parseInt(strs[1])-1,Integer.parseInt(strs[2])+days);
    }
    /**
      * 将字符串转化为Calendar的Date类型,获得字符串表示的相隔的几月,其中的时间部分为一天最早的时间.
      * 都是每天的0点0分0秒
      * @param dateString yyyy-MM-dd形式
      * @param delim  -
      * @param months  相差的月数  ＋后面几月  －前面几月
      * @return 
      */
    public static  java.util.Date getNextMonth(String dateString,String delim,int months){    
        String[] strs = dateString.split(delim);
       return getCalendarDate(Integer.parseInt(strs[0]),Integer.parseInt(strs[1])-1+months,Integer.parseInt(strs[2]));
    }
    
    /**
      * 将字符串转化为Calendar的Date类型,获得字符串表示的相隔的几年,其中的时间部分为一天最早的时间.
      * 都是每天的0点0分0秒
      * @param dateString
      * @param delim
      * @param years  相差的年数  ＋后面年月  －前面年天
      * @return 
      */
    public static  java.util.Date getNextYear(String dateString,String delim,int years){    
        String[] strs = dateString.split(delim);
       return getCalendarDate(Integer.parseInt(strs[0])+years,Integer.parseInt(strs[1])-1,Integer.parseInt(strs[2]));
    }
    /**
      * 将字符串转化为Calendar的Date类型,获得字符串表示的相隔的年月日,其中的时间部分为一天最早的时间.
      * 都是每天的0点0分0秒
      * @param dateString
      * @param delim
      * @param years  相差的年数  ＋后面年月  －前面年月
      * @param months  相差的月数  ＋后面几月  －前面几月
      * @param days  相差的天数  ＋后面天月  －前面天月
      * @return 
      */
     public static  java.util.Date getNextTime(String dateString,String delim,int years,int months,int days){    
        String[] strs = dateString.split(delim);
       return getCalendarDate(Integer.parseInt(strs[0])+years,Integer.parseInt(strs[1])-1+months,Integer.parseInt(strs[2])+days);
    }
    /**
     * 返回距离今天day天的最早时间
     * @param day
     * @return
     */
     public Date nextDayOfToday(Integer day){
    	 Calendar c = Calendar.getInstance();
    	 return getCalendarDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE)+day);
     }
     /**
      * 获取指定时间的相差时间
      * @param date
      * @param year
      * @param month
      * @param day
      * @param hour
      * @param minit
      * @param sencond
      * @return
      */
     public Date getNextTime(Date date,int year,int month,int day,int hour,int minit,int sencond){
    	     Calendar c = Calendar.getInstance();
    	     c.setTime(date);
    	     c.set(c.get(Calendar.YEAR)+year, c.get(Calendar.MONTH)+month, c.get(Calendar.DATE)+day,
    	     c.get(Calendar.HOUR_OF_DAY)+hour,c.get(Calendar.MINUTE)+minit,c.get(Calendar.SECOND)+sencond);
         return c.getTime();
     }
     /**
      * 获取指定时间的相差时间从最早的time开始
      * @param date
      * @param year
      * @param month
      * @param day
      * @return
      */
     public Date getNextTime(Date date,int year,int month,int day){
	     Calendar c = Calendar.getInstance();
	     c.setTime(date);
	     c.set(c.get(Calendar.YEAR)+year, c.get(Calendar.MONTH)+month, c.get(Calendar.DATE)+day,
	     0,0,0);
         return c.getTime();
       }
}
