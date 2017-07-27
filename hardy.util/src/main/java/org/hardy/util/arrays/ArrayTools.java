package org.hardy.util.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import ognl.Ognl;
import ognl.OgnlException;

/**
 * 操作数组的工具类
 * @author 09
 *
 */
public class ArrayTools {
     /**
      * 遍历循环数组并获取数组元素的某属性符合条件的集合
      * @param array
      * @param propName
      * @param value
      * @return
      * @throws OgnlException
      */
	  public static <T> List<T> seacher(T[] array,String propName,Object value) throws OgnlException{
		  List<T> tlist = new ArrayList<T>();
		  for(T t : array){
			 if(Ognl.getValue(propName, t).equals(value))
				 tlist.add(t);
		  } 
		  return tlist;
	  }
     /**
      * 根据hash值二分法查找,只找到1个相同的对象
      * @param array
      * @param value
      * @return
      */
	  @Deprecated
	  public static <T> T seacherHash(T[] array,T value) { 
		  int index =  Arrays.binarySearch(array,0,array.length,value,new Comparator<T>() {

				@Override
				public int compare(T o1, T o2) {
					 
					return o1.hashCode()-o2.hashCode();
				}
			});
		  if(index>=0){
			  return array[index]; 
		  }else{
			  return null;
		  }
	  }
	  /**
	   * 根据循环返回集合中的对象,根据查找对象返回集合中相同的对象
	   * @param array  一组对象集合
	   * @param value  需要查找的对象
	   * @return
	   */
	  public static <T> T seacherHash_(T[] array,T value,Comparator<T> comparator){
		   if(comparator==null){
			   comparator = new Comparator<T>() {

				@Override
				public int compare(T o1, T o2) {
					if(o1.equals(o2)) return 0;
					else return -1;
				}
			};
		   }
		    for(T t:array){
		    	int i = comparator.compare(t, value);
		    	 if(i==0){
		    		 return t;
		    	 }
		    }
		   return null;
	  }
	   
	  
	  public static <T,E extends T,F extends T> Object[] intersection(E[] e,F[] f){
           List<E> elist = new ArrayList<E>(Arrays.asList(e));
		   List<F> flist = new ArrayList<F>(Arrays.asList(f));
		   elist.retainAll(flist);
		   Object[] result = new Object[elist.size()];
		   return elist.toArray(result);
	  }

	
	public static <T,E extends T,F extends T>Object[] intersection(E[] e,F[] f,Comparator<T> comparator){
		List<E> elist = new ArrayList<E>(Arrays.asList(e));
		List<F> flist = new ArrayList<F>(Arrays.asList(f));
		List<T> tlist = new ArrayList<T>();
		for(E iteme:elist){
			T te = (T)iteme;
			for(F itemf:flist){
                T tf = (T)itemf;
				int r = comparator.compare(te,tf);
				if(r==0){
					tlist.add(iteme);
					break;
				}
			}
		}
		Object[] result = new Object[tlist.size()];
		return  tlist.toArray(result);
	}


	  
}
