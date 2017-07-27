package org.hardy.util.randomutil;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.ArrayUtils;

import com.song.tool.array.util.ArraysUtils;
import com.song.tool.datetime.DateTime;
import com.song.tool.runtime.exception.NoDerminerException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 这个类是一个产生随机数的类，
 * 因为是利用random去创建随机效果，并且random基本是一个无状态的类，
 * 所以这里使用单例，且将random设置成私有静态变量
 * **/
public class RandomUtil {
	private static Random rand = new Random();
	private RandomUtil(){}
	
	public static Random getRandomInstance(){return rand;}
    
	/**
	 * 产生一个数组，次数组有n个 0-n以内的随机数 包括0 不包括n,数组元素不会重复
	 * @param n  int类型的整数 指定数组随机数的范围 和 位数
	 * @return int[] 返回数组,数组由n个 0-n的随机数组成
	 * exemple： getRandomInt(4)，可能得到 {2,3,0,1}
	 * */
    public static int[] getRandomIntArray(int n){  
    	int[] result = new int[n];
        boolean[] bool = new boolean[n];      
        int num =0;       
        for (int i = 0; i<n; i++){              
            do{
               //如果产生的数相同继续循环
                num = rand.nextInt(n);              
            }while(bool[num]);       
            bool[num] =true;          
            result[i]=num;       
        }
          return result;
  } 
    /**
	 * 产生一个数组，次数组有n个 0-n以内的随机数 包括0 不包括n
	 * @param n  int类型的整数 指定数组随机数的范围 和 位数
	 * @return Integer[] 返回数组,数组由n个 0-n的随机数组成
	 * exemple： getRandomInt(4)，可能得到 {2,3,2,1}
	 * */
    public static Integer[] getRandomIntegerArray(int n){  
    	Integer[] result = new Integer[n];
        boolean[] bool = new boolean[n];      
        int num =0;       
        for (int i = 0; i<n; i++){              
            do{
               //如果产生的数相同继续循环
                num = rand.nextInt(n);              
            }while(bool[num]);          
            bool[num] =true;          
            result[i]=num;       
        }
           
          return result;
  } 
    /**
	 *   产生n个随机数 范围由m决定 x是开始数字
	 *   @param n 一个整数，决定得到的数组包含整数的个数
	 *   @param m 一个整数，决定随机数的范围大小
	 *   @param x 一个整数，决定随机数的开始位置
	 *   @return int[] 整数随机数的数组  
	 *   exemple: getRandomInt(5,300,12) 得到结果可能是{16,305,244,21,97};
	 *   这里对m的操作时:1-Math.random() 产生大于或等于 0.0，小于 1.0 .
     *  	2- exemple: (int)Math.random() * m--->[0-m).
     *  	3- exemple:m+x ---->[x-m+x)
	 * */
    public static int[] getRandomIntArray(int n,int m,int x){
    	return getRandomIntArray(n, m, x, false);
    }
    /**
	 *   产生n个随机数 范围由m决定 x是开始数字
	 *   @param n 一个整数，决定得到的数组包含整数的个数
	 *   @param m 一个整数，决定随机数的范围大小
	 *   @param x 一个整数，决定随机数的开始位置
	 *   @param r boolean，在结果的数组里是否有重复随机数 true 是不重复，false为可重复
	 *   @return int[] 整数随机数的数组  
	 *   exemple: getRandomInt(5,300,12) 得到结果可能是{16,305,244,21,97};
	 *   这里对m的操作时:1-Math.random() 产生大于或等于 0.0，小于 1.0 .
     *  	2- exemple: (int)Math.random() * m--->[0-m).
     *  	3- exemple:m+x ---->[x-m+x)
	 * */
    public static int[] getRandomIntArray(int n,int m,int x,boolean r)
    {
        int[] arr = new int[n];

        for (int i = 0; i < n; i++)
        {
        	//Math.random() 产生大于或等于 0.0，小于 1.0 .
        	// exemple: (int)Math.random() * 25--->[0-24]
            arr[i] = (int) (Math.random() * m )+x;
            if(r){
            	if(m<n){
            		throw new NoDerminerException("Impossible to satisfy the required number \r\n " +
            				  "of different random number generated(if you choiser r is true,\r\n" +
            				  "the value of m["+m+"] must bigger the value of n["+n+"])");
            	}
            	//这里是为了保证没有重复的随机数
                for (int j = 0; j < i; j++)
                {
                    if (arr[j] == arr[i])
                    {
                        i--;
                        break;
                    }
                }
            }
        }
       return  arr;
}
    
    /**
	 *   产生n个随机数 范围由m决定 x是开始数字
	 *   @param n 一个整数，决定得到的数组包含整数的个数
	 *   @param m 一个整数，决定随机数的范围大小
	 *   @param x 一个整数，决定随机数的开始位置
	 *   @return Integer[] 整数随机数的数组  
	 *   exemple: getRandomInt(5,300,12) 得到结果可能是{16,305,244,21,97};
	 *   这里对m的操作时:1-Math.random() 产生大于或等于 0.0，小于 1.0 .
     *  	2- exemple: (int)Math.random() * m--->[0-m).
     *  	3- exemple:m+x ---->[x-m+x)
	 * */
    public static Integer[] getRandomIntegerArray(int n,int m,int x){
    	return getRandomIntegerArray(n, m, x, false);
    }
    /**
	 *   产生n个随机数 范围由m决定 x是开始数字
	 *   @param n 一个整数，决定得到的数组包含整数的个数
	 *   @param m 一个整数，决定随机数的范围大小
	 *   @param x 一个整数，决定随机数的开始位置
	 *   @param r boolean，在结果的数组里是否有重复随机数 true 是不重复，false为可重复
	 *   @return Integer[] 整数随机数的数组  
	 *   exemple: getRandomInt(5,300,12) 得到结果可能是{16,305,244,21,97};
	 *   这里对m的操作时:1-Math.random() 产生大于或等于 0.0，小于 1.0 .
     *  	2- exemple: (int)Math.random() * m--->[0-m).
     *  	3- exemple:m+x ---->[x-m+x)
	 * */
    public static Integer[] getRandomIntegerArray(int n,int m,int x,boolean r)
    {
    	Integer[] arr = new Integer[n];

        for (int i = 0; i < n; i++)
        {
        	//Math.random() 产生大于或等于 0.0，小于 1.0 .
        	// exemple: (int)Math.random() * 25--->[0-24]
            arr[i] = (int) (Math.random() * m )+x;
            if(r){
            	if(m<n){
            		throw new NoDerminerException("Impossible to satisfy the required number \r\n " +
            				  "of different random number generated(if you choiser r is true,\r\n" +
            				  "the value of m["+m+"] must bigger the value of n["+n+"])");
            	}
            	//这里是为了保证没有重复的随机数
                for (int j = 0; j < i; j++)
                {
                    if (arr[j] == arr[i])
                    {
                        i--;
                        break;
                    }
                }
            }
        }
       return  arr;
}
    /**
     * 这个方法为了打乱原有数组的顺序,
     * 原理是采用一个新数组使用随机位置接收原数组的数字，
     * 速度快但费内存，应为要创建一个与原数组等大小的数组
     * @param <T>
     * @param param
     * */
    public static  <T extends Object> void orderRandomTArray(T[] param){
		int temp1;
		T temp2;
		int len = param.length;
		@SuppressWarnings("unchecked")
		T[] returnValue =  (T[]) new Object[len];
		for(int i=0;i<param.length;i++){
			temp1 = Math.abs(rand.nextInt())% len;
			returnValue[i] = param[temp1];
			temp2 = param[temp1];
			param[temp1] = param[len-1];
			param[len-1] = temp2;
			len--;
		} 
	}

    /**
	 *  这个方法将一个对象数组调换objs.length次顺序形成随机数组 
	 *  原理，设一个临时对象接收数组的对象，想冒泡排序一样转换两个对象的位置
	 *  由于对象位置随机长生且置换objs.length次，所以结果被均匀打乱。
	 * @param objs 一个对象数组
	 * @return  一个改变了顺序的数组，
	 * 注意输入参数对象和返回对象是同一对象,所以会有引用传递
	 * */
    
    public static void orderRandomOArray(Object[] objs)
	{
	  
	   int temp1,temp2;
	   Object temp3;
	   Random r = new Random();
	   for(int i=0;i<objs.length;i++) //随机交换send.length次
	   {
	    temp1 = Math.abs(r.nextInt())%(objs.length); //随机产生一个位置
	    temp2 = Math.abs(r.nextInt())%(objs.length); //随机产生另一个位置
	    if(temp1 != temp2)
	    {
	     temp3 = objs[temp1];
	     objs[temp1] = objs[temp2];
	     objs[temp2] = temp3;
	    }
	   }
	   
	}
    
    /**
	 * 产生一个数组，次数组有m个min-max以内的随机数 包括min 也包括max
	 * @param min  int类型的整数 指定数组随机数的范围的最小的数 
	 * @param max  int类型的整数 指定数组随机数的范围的最大的数
	 * @param m  int类型的整数 指定数组的长度
	 * @param r  boolean r=false 随机数可重复|r=true 随机数不能重复
	 * @return int[] 返回数组,数组由n个 0-n的随机数组成
	 * exemple： produitRandomIntArray(2,3,3,false);可能得到 {2	,3,3}
	 * */
    public static int[] produitRandomIntArray(int min,int max,int m,boolean r){  
    	int[] result = new int[m];
    	for(int i=0;i<m;i++){
    	  int temp = rand.nextInt(max-min+1);
				result[i] = temp+min;     		
			  
            if(r){
            	//如果数组随机数要求不重复，但m随机数个数大于随机数范围则直接抛异常
            	if(max-min+1<m){
            		throw new NoDerminerException("Impossible to satisfy the required number \r\n " +
            				  "of different random number generated(if you choiser r is true,\r\n" +
            				  "the value ["+(max-min+1)+"] must bigger the value of m["+m+"])");
            	}	
            	//这里是为了保证没有重复的随机数
                for (int j = 0; j < i; j++)
                {
                    if (result[j] == result[i])
                    {
                        i--;
                        break;
                    }
                }
            }
    	 }
    	
          return result;
  } 
    /**
	 * 产生一个字符串，字符串为n个1位数的随机字符组成，可以重复。
	 * @param n  代表字符串长度
	 * @return String 返回的字符串,数组由n个[String]的随机数组成
	 * 
	 * */
    public static String produitRandom(int n){
		StringBuffer s = new StringBuffer();
			for (int i = 0; i < n; i++) {
				s.append(rand.nextInt(10)) ;
			}
			return s.toString();
		}

		 

    /**
   	 * 产生一个Set<Character>，由i个char字符组成，不可以重复。
   	 * 以英文字符为准，最大长度为26. 
   	 * @param i  代表字符串长度
   	 * @return Set<Character> 返回的有字符组成的set集合
   	 * 
   	 * */
	    public static Set<Character> getRandomCharSet(int i){ 
	    	Set<Character> set1=null; 
	      if(i<=26){
	        Set<Character> numberSet01 = new HashSet<Character>();  
	        Random rdm = new Random();  
	        char ch;  
	        while(numberSet01.size()<i){   
	           int rdGet = Math.abs(rdm.nextInt())%26+97;//产生97到122的随机数a-z值  
	            ch=(char)rdGet;  
	            numberSet01.add(ch);  
	            //Set中是不能放进重复的值的，当它有20个时，就满足你的条件了   
	        }  
	         set1=numberSet01;
	      }else{
	    	  set1=getRandomCharSet(26);
	      }
	          return set1;  
	        }  
	    /**
	   	 * 产生一个List<Character>，由i个char字符组成，可以重复。
	   	 * 以英文字符为准，无最大限制. 
	   	 * @param i  代表字符串长度
	   	 * @return List<Character> 返回的有字符组成的List集合
	   	 * 
	   	 * */
	    public static List<Character> getRandomCharList(int i){  
	    	List<Character> numberSet01 = new ArrayList<Character>();  
	        char ch;  
	        while(numberSet01.size()<i){   
	           int rdGet = Math.abs(rand.nextInt())%26+97;//产生97到122的随机数a-z值  
	            ch=(char)rdGet;  
	            numberSet01.add(ch);     
	        }   
	          return numberSet01;  
	        }  
	    
	    
	    /**
	   	 * 产生一个char[]，由i个char字符组成，可以重复。
	   	 * 以英文字符为准，无最大限制. 
	   	 * @param i  代表字符串长度
	   	 * @return char[] 返回的有字符组成的char数组
	   	 * 
	   	 * */
	    public static char[] getRandomChar(int i){
	  	   char[] chars=new char[i];
	  	  char ch;  
	      for(int j=0;j<i;j++){   
	         int rdGet = Math.abs(rand.nextInt())%26+97;//产生97到122的随机数a-z值  
	          ch=(char)rdGet;  
	          chars[j]=ch;  
	      }   
	      return chars;
	    }
	    
	    /**
	   	 * 产生一个字符串，由i个char字符组成，可以重复。
	   	 * 以英文字符为准，无最大限制. 
	   	 * @param i  代表字符串长度
	   	 * @return String 返回字符组成的字符串
	   	 * 
	   	 * */
	      public static String getRandomString(int i){
	    	  return String.valueOf(getRandomChar(i));
	    	  
	      }
	      /**
		   	 * 产生一个字符串，由i个char字符按自然排序组成，可以重复。
		   	 * 以英文字符为准，无最大限制. 
		   	 * @param i  代表字符串长度
		   	 * @return String 返回字符组成的字符串 每个字符按自然排序
		   	 * 
		   	 * */
	      public static String orderString(int i) {  
	    	StringBuffer sb=new StringBuffer();
	        Set<Character> numberSet = new TreeSet<Character>();   
	        numberSet.addAll(getRandomCharSet(i));   
	        for(Iterator<Character> it=numberSet.iterator();it.hasNext();){   
	            sb.append(it.next());   
	            }   
	    	
	    	return sb.toString();
	    }  
          
	      /**
		   	 * 从一个对象数组中按个数随机取值
		   	 * @param param  代表一个对象数组
		   	 * @param n  从数组中取出多少个元素 如果n超过param的个数某则新的数组一定会有重复
		   	 * @return <T>T[] 返回打乱书序的新对象数组。注意这个方法虽然使用泛型，但还是只能用
		   	 * Object[]数组接收
		   	 * 
		   	 * */
		@SuppressWarnings("unchecked")
		public static <T>T[] randomArray(T[] param,int n){
	    	   Object[] result = new Object[n];
	    	     orderRandomOArray(param);		 
			   int j = 0;
			   for(int i=0;i<n;i++){
				  if(j<param.length){
					  result[i] = param[j];
					  j++; 
				  }else{		
					  orderRandomTArray(param); 
					  j=0;
					  i--;
				  }
			   }
			   
	    	  return  (T[]) result;
	      }
	      /**
		   	 * 产生一个随机密码，由i个char字符组成，可以重复。
		   	 * 以英文字符为准，无最大限制. 且有特殊字符组成
		   	 * @param n  英文字符长度
		   	 * @param modeString  是否大小写，是否可重复，是否等分排列等  Aa aa AA Ab...
		   	 * @param m  特殊字符长度
		   	 * @param special 特殊字符数组
		   	 * @return String 生成的密码字符串
		   	 * 
		   	 * */
	     public static String randomPassword(int n,String modeString,int m,Character[] special){
	    	 Object[] css = null; 
	    	 //第一步 产生随机引文字符串 长度为n
	    	  Object[] cs1 = ArrayUtils.toObject(getRandomChar(n));
	    	  //根据模式改变"Aa"
	    	  if(n!=0&&(modeString!=null&&(modeString.contains("Aa")||modeString.contains("AA")))){
	    		 int rn = rand.nextInt(n);
	    		 int[] rns = null;
	    		 if(modeString.contains("Aa")){
	    		  rns = getRandomIntArray(rn, n, 0,true);
	    		 }
	    		 if(modeString.contains("AA")){
		    		  rns = getRandomIntArray(n);
		    		 }
	    		  for(int i:rns){
	    			   char c = (Character)cs1[i];
	    			  cs1[i]=(char)(((short)c)-32);
	    		  }
	    	  }
	    	  //第二部 从special里选m个字符
	    	  Object[] cs2 = randomArray(special, m);
	    	  //根据模式改变"Oo"
	    	  if(n!=0&&m!=0&&(modeString!=null&&modeString.contains("Oo"))){
	    		  css = new Object[n+m];
	    		   int min =(n<m?n:m);
	    		   
	    		   for(int i=0;i<min;i++){
	    			   css[i*2]=cs1[i];
	    			   css[i*2+1]=cs2[i];
	    		   }
	    		   if(n>m){
	    			   for(int i=2*min;i<n+m;i++){
		    			   css[i]=cs1[i-min];
		    		   }
	    		   }
	    		   if(n<m){
	    			   for(int i=2*min;i<n+m;i++){
		    			   css[i]=cs2[i-min];
		    		   } 
	    		   }
	    	  }else{
	    		//合并了两个数组
		    	   css = ArraysUtils.concatAll(cs1, cs2);
		    	   orderRandomOArray(css);
	    	  }
	    	  StringBuffer sb = new StringBuffer();
	    	  for(Object o:css){
	    		  sb.append(o);
	    	  }
	    	  return sb.toString();
	     }
             /**
              *  random随机使用的SimpleDateFormat 固定为 dd-MM-YYYY-HH-mm-ss形式便于使用“-”分割
              */
             private final static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");
             /**
              * 根据初始时间和结束时间 产生大于等于初始时间，小于等于结束时间,
              * 注意这只是大概模拟时间，因为单双月和润二月的存在，所以可能并不准确，
              * 但是如果循环判断，不符合要求从新选取随机时间，那么太浪费效率，所以不采用
              * @param startDate  开始时间
              * @param endDate  结束时间
              * @return 
              */
             public static Date getRandomDateTime(Date startDate,Date endDate){
                    String[] strs1 = sdf.format(startDate).split("-");
                    String[] strs2 = sdf.format(endDate).split("-");
                    int[]  is1 = new int[3] ;int[] is2 = new int[3];
                    for(int i=0;i<3;i++){
                           is1[i] = Integer.parseInt(strs1[i]);
                           is2[i] = Integer.parseInt(strs2[i]);
                    }
                    int year = rand.nextInt(is2[2]-is1[2]+1)+is1[2];
                    int month ;
                    if(year==is1[2]) month = rand.nextInt(12-is1[1])+is1[1];
                    else if(year==is2[2]) month = rand.nextInt(is2[1]);
                    else month = rand.nextInt(12);
                    int day ;
                    if(year==is1[2]&&month==is1[1]) day =  rand.nextInt(30-is1[0])+is1[0];
                    else if(year==is2[2]&&month==is2[1]) day = rand.nextInt(is2[0]+1);
                    else day = rand.nextInt(30);
                  return  DateTime.getCalendarDate(year,month,day,
                            rand.nextInt(24),
                            rand.nextInt(60), rand.nextInt(60));
                  
             }
             
              /**
              * 根据初始时间和结束时间 产生大于等于初始时间，小于等于结束时间,
              * 注意这只是大概模拟时间，因为单双月和润二月的存在，所以可能并不准确，
              * 但是如果循环判断，不符合要求从新选取随机时间，那么太浪费效率，所以不采用
              * @param startDate  开始时间
              * @param endDate  结束时间
              * @param length  产生数据的长度
              * @return 
              */
             public static Date[] getRandomDateTime(Date startDate,Date endDate,int length){
                    Date[] ds = new Date[length];
                    String[] strs1 = sdf.format(startDate).split("-");
                    String[] strs2 = sdf.format(endDate).split("-");
                    int[]  is1 = new int[3] ;int[] is2 = new int[3];
                    for(int i=0;i<3;i++){
                           is1[i] = Integer.parseInt(strs1[i]);
                           is2[i] = Integer.parseInt(strs2[i]);
                    }
                    
                    for(int i=0;i<length;i++){
                    int year = rand.nextInt(is2[2]-is1[2]+1)+is1[2];
                    int month ;
                    if(year==is1[2]) month = rand.nextInt(12-is1[1])+is1[1];
                    else if(year==is2[2]) month = rand.nextInt(is2[1]);
                    else month = rand.nextInt(12);
                    int day ;
                    if(year==is1[2]&&month==is1[1]) day =  rand.nextInt(30-is1[0])+is1[0];
                    else if(year==is2[2]&&month==is2[1]) day = rand.nextInt(is2[0]+1);
                    else day = rand.nextInt(30);
                    ds[i]=DateTime.getCalendarDate(year,month,day,
                            rand.nextInt(24),
                            rand.nextInt(60), rand.nextInt(60));
                    }
                  return ds;
             }
             
             /**
              * 特别粗略的返回时间在起始年份和截止年份的时间
              * @param startYear
              * @param endYear
              * @return 
              */
             public static Date getRandomYearTime(int startYear,int endYear) {
                     int year = rand.nextInt(endYear-startYear)+startYear;
                     int month = rand.nextInt(12); 
                     int day;
                     if(month==12)day=31;
                     else day=32;
                       return DateTime.getCalendarDate(year,month,day,
                            rand.nextInt(24),
                            rand.nextInt(60), rand.nextInt(60));
        }    
             /**
              * 特别粗略的返回时间在起始年份和截止年份的时间
              * @param startYear
              * @param endYear
              * @param length
              * @return 
              */
              public static Date[] getRandomYearTime(int startYear,int endYear,int length) {
                  Date[] ds = new Date[length];
                   for(int i=0;i<length;i++){
                         ds[i] = getRandomYearTime(startYear, endYear);
                   }
                   return ds;
        }
        
              /**
               * 使用即时时间+n位随机数组成一列随机数字,用以标识文件随机名称
               * @param n
               * @return
               */
      public static String randomTimeString(int n){
 		 String s = DateTime.convertirDateToString(new Date(), "yyyyMMddHHmmss");
 		  return s+RandomUtil.produitRandom(n);
      }
}
