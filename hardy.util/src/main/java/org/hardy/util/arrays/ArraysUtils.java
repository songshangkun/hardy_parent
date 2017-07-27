package org.hardy.util.arrays;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * 这是一个操作数组的工具类
 * **/
public class ArraysUtils {
	/**
	 * 这个方法可以将多个数组合并成一个新的数组返回，因为是产生新数组拷贝元集合的元素
	 * 所以不会有引用传递。
	 * @param T[] first 第一个数组，方法至少有一个数组
	 * @param T[]... rest 其他数组
	 * @return <T> T[] 返回新的数组
	 * 
	 * ***/
	@SafeVarargs
	public static <T> T[] concatAll(T[] first, T[]... rest) { 
		  int totalLength = first.length; 
		  for (T[] array : rest) { 
		    totalLength += array.length; 
		  } 
		  T[] result = Arrays.copyOf(first, totalLength); 
		  int offset = first.length; 
		  for (T[] array : rest) { 
			 // array 要拷贝的数组，0 要拷贝的数组的始坐标，result目标数组，目标数组始坐标，array.length 增加的长度
		    System.arraycopy(array, 0, result, offset, array.length); 
		    offset += array.length; 
		  } 
		  return result; 
		}
 	 
			/**
		     *   比较数组元素是否相同
		     *   注意：比较equals方法 hashcode的一致性
		     *  @说明  比较两个Object数组是否相等，这里的相等值元素个数和元素相同。
		     *  @原理 1- 如果数组长度不等，直接返回不相同
		     *       2- 长度相等的话，为不改变原数组的引用传递，使用System.arraycopy方法
		     *       拷贝两个一样的对比数组。
		     *       3- 使用嵌套循环对比两个数组中的元素,如果想等将两个元素全部标成0，
		     *       4-循环完毕后，从新循环看两个数组元素是否都为0,,如果全部为0,则返回相等，否则为不相等
		     *  @param l1  第一个Object[]数组 
		     *  @param l2  第二个Object[]数组 
		     *  @return boolean  true为相等，false为不等
		     *  @注意 使用Arrays.equals(new int[]{0,3}, new int[]{3,0});
		     *   方法比较时 因为数组是有顺序的，所以 即使元素都相同因顺序不同 数组也不是相等的
		     *    所以这里的方法是为了查看数组元素是否相等 与顺序无关。这里只是对long[]数组的操作
		     *    如果需要按需求对比别的数组，可按相同逻辑编程。
		     * */
					public static boolean arrayEquals(Object[] l1, Object[] l2){
				        if(l1.length!=l2.length){
				                return false;
				        }
				        String[] t1 = new String[l1.length] ;
				        String[] t2 = new String[l2.length] ;
				        System.arraycopy(l1, 0, t1, 0, l1.length);
				        System.arraycopy(l2, 0, t2, 0, l2.length);
				        for(int i=0;i<l1.length;i++){
				                for(int j=0;j<l1.length;j++){
				                        if(t1[i]!=null&&t1[i].equals(t2[j])){
				                                t1[i]=null;
				                                t2[j]=null;
				                        }
				                }
				        }
				        for(int i=0;i<l1.length;i++){
				                if(t1[i]!=null||t2[i]!=null){
				                        return false;
				                }
				        }
				        return true;
				}	
			
	        /**
            *   比较数组元素是否相同
            *   注意：
            *  @说明  比较两个long数组是否相等，这里的相等值元素个数和元素相同。
            *  @原理 1- 如果数组长度不等，直接返回不相同
            *       2- 长度相等的话，为不改变原数组的引用传递，使用System.arraycopy方法
            *       拷贝两个一样的对比数组。
            *       3- 使用嵌套循环对比两个数组中的元素,如果想等将两个元素全部标成0，
            *       4-循环完毕后，从新循环看两个数组元素是否都为0,,如果全部为0,则返回相等，否则为不相等
            *  @param l1  第一个long[]数组 
            *  @param l2  第二个long[]数组 
            *  @return boolean  true为相等，false为不等
            *  @注意 使用Arrays.equals(new int[]{0,3}, new int[]{3,0});
            *   方法比较时 因为数组是有顺序的，所以 即使元素都相同因顺序不同 数组也不是相等的
            *    所以这里的方法是为了查看数组元素是否相等 与顺序无关。这里只是对long[]数组的操作
            *    如果需要按需求对比别的数组，可按相同逻辑编程。
            * */

           public static boolean arrayEquals(long[] l1, long[] l2){
                   if(l1.length!=l2.length){
                           return false;
                   }
                   long[] t1 = new long[l1.length] ;
                   long[] t2 = new long[l2.length] ;
                   System.arraycopy(l1, 0, t1, 0, l1.length);
                   System.arraycopy(l2, 0, t2, 0, l2.length);
                   for(int i=0;i<l1.length;i++){
                           for(int j=0;j<l1.length;j++){
                                   if(t1[i]==t2[j]){
                                           t1[i]=0;
                                           t2[j]=0;
                                   }
                           }
                   }
                   for(int i=0;i<l1.length;i++){
                           if(t1[i]!=0||t2[i]!=0){
                                   return false;
                           }
                   }
                   return true;
           }
           /**
            *   比较数组元素是否相同
            *   注意：
            *  @说明  比较两个int数组是否相等，这里的相等值元素个数和元素相同。
            *  @原理 1- 如果数组长度不等，直接返回不相同
            *       2- 长度相等的话，为不改变原数组的引用传递，使用System.arraycopy方法
            *       拷贝两个一样的对比数组。
            *       3- 使用嵌套循环对比两个数组中的元素,如果想等将两个元素全部标成0，
            *       4-循环完毕后，从新循环看两个数组元素是否都为0,,如果全部为0,则返回相等，否则为不相等
            *  @param l1  第一个int[]数组 
            *  @param l2  第二个int[]数组 
            *  @return boolean  true为相等，false为不等
            *  @注意 使用Arrays.equals(new int[]{0,3}, new int[]{3,0});
            *   方法比较时 因为数组是有顺序的，所以 即使元素都相同因顺序不同 数组也不是相等的
            *    所以这里的方法是为了查看数组元素是否相等 与顺序无关。这里只是对long[]数组的操作
            *    如果需要按需求对比别的数组，可按相同逻辑编程。
            * */                        
	 public static boolean arrayEquals(int[] l1, int[] l2){
               if(l1.length!=l2.length){
                       return false;
               }
               int[] t1 = new int[l1.length] ;
               int[] t2 = new int[l2.length] ;
               System.arraycopy(l1, 0, t1, 0, l1.length);
               System.arraycopy(l2, 0, t2, 0, l2.length);
               for(int i=0;i<l1.length;i++){
                       for(int j=0;j<l1.length;j++){
                               if(t1[i]==t2[j]){
                                       t1[i]=0;
                                       t2[j]=0;
                               }
                       }
               }
               for(int i=0;i<l1.length;i++){
                       if(t1[i]!=0||t2[i]!=0){
                               return false;
                       }
               }
               return true;
       }
	 /**
      *   比较数组元素是否相同
      *   注意：
      *  @说明  比较两个boolean数组是否相等，这里的相等值元素个数和元素相同。
      *  @原理 1- 如果数组长度不等，直接返回不相同
      *       2- 长度相等的话，为不改变原数组的引用传递，使用System.arraycopy方法
      *       拷贝两个一样的对比数组。
      *       3- 使用嵌套循环对比两个数组中的元素,如果想等将两个元素全部标成0，
      *       4-循环完毕后，从新循环看两个数组元素是否都为0,,如果全部为0,则返回相等，否则为不相等
      *  @param l1  第一个boolean[]数组 
      *  @param l2  第二个boolean[]数组 
      *  @return boolean  true为相等，false为不等
      *  @注意 使用Arrays.equals(new int[]{0,3}, new int[]{3,0});
      *   方法比较时 因为数组是有顺序的，所以 即使元素都相同因顺序不同 数组也不是相等的
      *    所以这里的方法是为了查看数组元素是否相等 与顺序无关。这里只是对long[]数组的操作
      *    如果需要按需求对比别的数组，可按相同逻辑编程。
      * */
          public static boolean arrayEquals(boolean[] l1, boolean[] l2){
               if(l1.length!=l2.length){
                       return false;
               }
               boolean[] t1 = new boolean[l1.length] ;
               boolean[] t2 = new boolean[l2.length] ;
               System.arraycopy(l1, 0, t1, 0, l1.length);
               System.arraycopy(l2, 0, t2, 0, l2.length);
               for(int i=0;i<l1.length;i++){
                       for(int j=0;j<l1.length;j++){
                               if(t1[i]==t2[j]){
                                       t1[i]=false;
                                       t2[j]=false;
                               }
                       }
               }
               for(int i=0;i<l1.length;i++){
                       if(t1[i]!=false||t2[i]!=false){
                               return false;
                       }
               }
               return true;
       }
          /**
           *   比较数组元素是否相同
           *   注意：
           *  @说明  比较两个byte数组是否相等，这里的相等值元素个数和元素相同。
           *  @原理 1- 如果数组长度不等，直接返回不相同
           *       2- 长度相等的话，为不改变原数组的引用传递，使用System.arraycopy方法
           *       拷贝两个一样的对比数组。
           *       3- 使用嵌套循环对比两个数组中的元素,如果想等将两个元素全部标成0，
           *       4-循环完毕后，从新循环看两个数组元素是否都为0,,如果全部为0,则返回相等，否则为不相等
           *  @param l1  第一个byte[]数组 
           *  @param l2  第二个byte[]数组 
           *  @return boolean  true为相等，false为不等
           *  @注意 使用Arrays.equals(new int[]{0,3}, new int[]{3,0});
           *   方法比较时 因为数组是有顺序的，所以 即使元素都相同因顺序不同 数组也不是相等的
           *    所以这里的方法是为了查看数组元素是否相等 与顺序无关。这里只是对long[]数组的操作
           *    如果需要按需求对比别的数组，可按相同逻辑编程。
           * */
          public static boolean arrayEquals(byte[] l1, byte[] l2){
               if(l1.length!=l2.length){
                       return false;
               }
               byte[] t1 = new byte[l1.length] ;
               byte[] t2 = new byte[l2.length] ;
               System.arraycopy(l1, 0, t1, 0, l1.length);
               System.arraycopy(l2, 0, t2, 0, l2.length);
               for(int i=0;i<l1.length;i++){
                       for(int j=0;j<l1.length;j++){
                               if(t1[i]==t2[j]){
                                       t1[i]=0;
                                       t2[j]=0;
                               }
                       }
               }
               for(int i=0;i<l1.length;i++){
                       if(t1[i]!=0||t2[i]!=0){
                               return false;
                       }
               }
               return true;
       }
          /**
            * 将Object数组中为null的元素剔除出数组，并改变数组的长度
            * 如果数组为空，则返还一个空数组
           * @param objs
           * @return 
           */
           public static Object[] compressNull(Object[] objs){
                int i = 0;
                for(Object object:objs){
                    if(null!=object)++i;
                }
                int j = 0;
                Object[] objs2 = new Object[i];
                for(Object object:objs){
                    if(null!=object){
                        objs2[j++] = object;
                    }
                }
                return objs2;
           }
           
           
           @SuppressWarnings("unchecked")
		public static <T> T[] compressTNull(T[] objs,Class<T> c){
               int i = 0;
               for(T object:objs){
                   if(null!=object)++i;
               }
               int j = 0;  
               T[] objs2 =  (T[]) Array.newInstance(c, i);
               for(T object:objs){
                   if(null!=object){
                       objs2[j++] =  object;
                   }
               }
               return objs2;
          }
           
           /**
            * 计算数组中有多少元素等于给定对象(内存相等)
            * @param objs
            * @param obj
            * @return
            */
           public static <T extends Object> int getTCount(T[] objs,T obj){
        	   int count = 0;
        	      for(Object o:objs){
        	    	   if(o == obj)count++;
        	      }
        	      return count;
           }
           
           /**
            * 计算数组中有多少元素等于给定对象 
            * @param objs
            * @param obj
            * @param isReal  true:比较内存地址,false:比较equals方法
            * @return
            */
           public static int getObjectCount(Object[] objs,Object obj,boolean isReal){
        	   int count = 0;
        	   if(isReal){
        	      for(Object o:objs){
        	    	   if(o == obj)count++;
        	      }
        	   }else{
        		   for(Object o:objs){
        	    	   if(o.equals(obj))count++;
        	      } 
        	   }
        	      return count;
           }
           /**
            * 计算数组中有多少元素等于给定对象 
            * @param objs
            * @param obj
            * @return
            */
           public static int getObjectCount(boolean[] objs,boolean obj){
        	   int count = 0;
        	      for(boolean o:objs){
        	    	   if(o == obj)count++;
        	      }
        	      return count;
           }
           
           /**
            * 将有相同的对象的数据删除,并形成新的array
            * @param array
            * @return
            */
          public static <T>T[] hashArray(T[] array,Class<T> c){
        	  for(int i=0;i<array.length;i++){
     			 for(int j=i+1;j<array.length;j++){
     				 if(array[i]==null) break;
     				 if(array[i].equals(array[j])){
     					 array[j] = null;
     				 }
     			 }
     		 }
        	 return compressTNull(array,c);
          }  
          
           
            
}
