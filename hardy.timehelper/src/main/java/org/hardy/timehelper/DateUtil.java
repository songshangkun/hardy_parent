package org.hardy.timehelper;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.song.tool.datetime.DateTime;

public class DateUtil {
	 /**
	  * 获取当前时间差的时间
	  * @param date
	  * @param y
	  * @param m
	  * @param d
	  * @param h
	  * @param f
	  * @param s
	  * @return
	  */
	  public static Date getNextTime(Date date,int y,int m,int d,int h,int f,int s){
			Calendar c = Calendar.getInstance();
			c.setTime(date); 
			return DateTime.getCalendarDate(c.get(Calendar.YEAR)+y, c.get(Calendar.MONTH)+m, c.get(Calendar.DATE)+d,c.get(Calendar.HOUR_OF_DAY)+h,c.get(Calendar.MINUTE)+f,c.get(Calendar.SECOND)+s);
		}
	  
	  /**
	   * 获取当前时间差的时间
	   * @param date
	   * @param y
	   * @param m
	   * @param d
	   * @param type  0 最早 1 最晚
	   * @return
	   */
	  public static Date getNextTime(Date date,int y,int m,int d,int type){
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			if(type==1)return DateTime.getCalendarDate(c.get(Calendar.YEAR)+y, c.get(Calendar.MONTH)+m, c.get(Calendar.DATE)+d,23,59,59);
			else return DateTime.getCalendarDate(c.get(Calendar.YEAR)+y, c.get(Calendar.MONTH)+m, c.get(Calendar.DATE)+d);
		}
	  
	  /**
	   * 获取当前时间差的时间
	   * @param date
	   * @param params   year month day hour minute second
	   * @return
	   */
	  public static Date getNextTimeAll(Date date,Map<String,Integer> params){
		  Calendar c = Calendar.getInstance();
			c.setTime(date);
			int year = params.containsKey("year")&&params.get("year")!=null?Calendar.YEAR+params.get("year"): Calendar.YEAR;
			int month = params.containsKey("month")&&params.get("month")!=null?Calendar.YEAR+params.get("month"): Calendar.MONTH;
			int day = params.containsKey("day")&&params.get("day")!=null?Calendar.YEAR+params.get("day"): Calendar.DATE;
			int heure = params.containsKey("hour")&&params.get("hour")!=null?Calendar.YEAR+params.get("hour"): Calendar.HOUR_OF_DAY;
			int minut = params.containsKey("minute")&&params.get("minute")!=null?Calendar.YEAR+params.get("minute"): Calendar.MINUTE;
			int second = params.containsKey("second")&&params.get("second")!=null?Calendar.YEAR+params.get("second"): Calendar.SECOND;
			return DateTime.getCalendarDate(year,month,day,heure,minut,second);
		}
	  /**
	   * 获取当前时间差的时间
	   * @param date
	   * @param params  year month day type (0 最早 1 最晚)
	   * @return
	   */
	  public static Date getNextTime(Date date,Map<String,Integer> params){
		  Calendar c = Calendar.getInstance();
			c.setTime(date);
			int year = params.containsKey("year")&&params.get("year")!=null?Calendar.YEAR+params.get("year"): Calendar.YEAR;
			int month = params.containsKey("month")&&params.get("month")!=null?Calendar.YEAR+params.get("month"): Calendar.MONTH;
			int day = params.containsKey("day")&&params.get("day")!=null?Calendar.YEAR+params.get("day"): Calendar.DATE;
			int type = !params.containsKey("type")||params.get("type")!=1?0: 1;
			if(type==1)return DateTime.getCalendarDate(year,month,day,23,59,59);
			else return DateTime.getCalendarDate(year,month,day);
		}
	  /**
	   * 获取当前时间差的时间
	   * @param date
	   * @param params  year month day hour minute second
	   * @return
	   */
	  public static Date getNextTimeD(Date date,Map<String,Integer> params){
		  Calendar c = Calendar.getInstance();
			c.setTime(date);
			int year = params.containsKey("year")&&params.get("year")!=null?Calendar.YEAR+params.get("year"): Calendar.YEAR;
			int month = params.containsKey("month")&&params.get("month")!=null?Calendar.YEAR+params.get("month"): Calendar.MONTH;
			int day = params.containsKey("day")&&params.get("day")!=null?Calendar.YEAR+params.get("day"): Calendar.DATE;
			int heure = params.containsKey("hour")&&params.get("hour")!=null?Calendar.YEAR+params.get("hour"): Calendar.HOUR_OF_DAY;
			int minut = params.containsKey("minute")&&params.get("minute")!=null?Calendar.YEAR+params.get("minute"): Calendar.MINUTE;
			int second = params.containsKey("second")&&params.get("second")!=null?Calendar.YEAR+params.get("second"): Calendar.SECOND;
			return DateTime.getCalendarDate(year,month,day,heure,minut,second);
		}
	  /**
	   * 获取小时时间
	   * @param date
	   * @param y
	   * @param m
	   * @param d
	   * @param h
	   * @param type 1 最晚  0 最早
	   * @return
	   */
	  public static Date getNextHour(Date date,int y,int m,int d,int h,int type){
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			if(type==1)return DateTime.getCalendarDate(c.get(Calendar.YEAR)+y, c.get(Calendar.MONTH)+m, c.get(Calendar.DATE)+d,c.get(Calendar.HOUR_OF_DAY)+h,59,59);
			else return DateTime.getCalendarDate(c.get(Calendar.YEAR)+y, c.get(Calendar.MONTH)+m, c.get(Calendar.DATE)+d,c.get(Calendar.HOUR_OF_DAY)+h,0,0);
		}
	  /**
	   * 获取比当前时间参数-1的时间
	   * @param date
	   * @param fields  Calenda.YEAR ...
	   * @return
	   */
	  public static Date floor(Date date,int... fields){
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			for(int filed : fields){
				 c.set(filed, c.get(filed)-1); 
			}
		    return c.getTime();
		}
	  
	  /**
	   * 获取比当前时间参数-1的时间 后面单位为初始值
	   * @param date
	   * @param field
	   * @return
	   */
	  public static Date floorInitial(Date date,int field){
			Calendar c = Calendar.getInstance();
			c.setTime(date);
		    if(field==Calendar.YEAR){
		    	c.set(Calendar.YEAR, c.get(Calendar.YEAR)-1); 
		    	c.set(Calendar.MONTH, 0); 
		    	c.set(Calendar.DATE, 1); 
		    	c.set(Calendar.HOUR_OF_DAY, 0); 
		    	c.set(Calendar.MINUTE, 0); 
		    	c.set(Calendar.SECOND, 0); 
		    } 
		    if(field==Calendar.MONTH){	System.out.println(c.get(Calendar.YEAR));
		    	c.set(Calendar.MONTH, c.get(Calendar.MONTH)-1); 
		    	c.set(Calendar.DATE, 1); 
		    	c.set(Calendar.HOUR_OF_DAY, 0); 
		    	c.set(Calendar.MINUTE, 0); 
		    	c.set(Calendar.SECOND, 0); 
		    } 
		    if(field==Calendar.DATE){	System.out.println(c.get(Calendar.YEAR));
		    	c.set(Calendar.DATE,  c.get(Calendar.DATE)-1); 
		    	c.set(Calendar.HOUR_OF_DAY, 0); 
		    	c.set(Calendar.MINUTE, 0); 
		    	c.set(Calendar.SECOND, 0); 
		    } 
		    if(field==Calendar.HOUR_OF_DAY){	System.out.println(c.get(Calendar.YEAR));
		    	c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY)-1); 
		    	c.set(Calendar.MINUTE, 0); 
		    	c.set(Calendar.SECOND, 0); 
		    } 
		    if(field==Calendar.MINUTE){	System.out.println(c.get(Calendar.YEAR));
		    	c.set(Calendar.MINUTE, c.get(Calendar.MINUTE)-1); 
		    	c.set(Calendar.SECOND, 0); 
		    } 
		    if(field==Calendar.SECOND){	System.out.println(c.get(Calendar.YEAR));
		    	c.set(Calendar.SECOND,  c.get(Calendar.SECOND)-1); 
		    } 
		    return c.getTime();
		}
	  /**
	   * 获取当前月第一天0点0分
	   * @param date
	   * @return
	   */
	  public static Date initialMonth(Date date){
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.set(Calendar.DATE, 1); 
			c.set(Calendar.HOUR_OF_DAY, 0); 
			c.set(Calendar.MINUTE, 0); 
	    	c.set(Calendar.SECOND, 0); 
		return c.getTime();
	  }
	  /**
	   * 当前初始小时
	   * @param date
	   * @return
	   */
	  public static Date initialHour(Date date){
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.set(Calendar.MINUTE, 0); 
	    	c.set(Calendar.SECOND, 0); 
		return c.getTime();
	  }
	  /**
	   * 当前初始分钟
	   * @param date
	   * @return
	   */
	  public static Date initialMinut(Date date){
			Calendar c = Calendar.getInstance();
			c.setTime(date);
	    	c.set(Calendar.SECOND, 0); 
		return c.getTime();
	  }
	  /**
	   * 当前初始天
	   * @param date
	   * @return
	   */
	  public static Date initialDay(Date date){
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.set(Calendar.HOUR_OF_DAY, 1); 
			c.set(Calendar.MINUTE, 0); 
	    	c.set(Calendar.SECOND, 0); 
		return c.getTime();
	  }
	  
	  /**
	   * 月份需要加1才是当前的月数
	   * @param date
	   * @return
	   */
	  public static Integer[] dispersed (Date date){
		 Calendar c = Calendar.getInstance();
		   c.setTime(date);
		   Integer[]  ins = new Integer[6];
		   ins[0] = c.get(Calendar.YEAR);
		   ins[1] = c.get(Calendar.MONTH);
		   ins[2] = c.get(Calendar.DATE);
		   ins[3] = c.get(Calendar.HOUR_OF_DAY);
		   ins[4] = c.get(Calendar.MINUTE);
		   ins[5] = c.get(Calendar.SECOND);
		return ins;
	  }
}
