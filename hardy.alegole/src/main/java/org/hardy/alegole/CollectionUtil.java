package org.hardy.alegole;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hardy.texthelper.StringConvertor;
/**
 * 这是一个集合操作的工具类，该类提供一些取子集的
 * 方法。
 * **/
public class CollectionUtil { 
	/**
	 * 这是方法可以求得参数几何元素的子集，不包括空集。
	 *  @param set 一个set集合
	 *  @param List<List<T>> 一个List集合列表 ，包含的子list就是原集合的单个集合
	 *  exemple： {1,2,3} 得到记过{1}，{2}，{3}，{1,2}，{2,3}，{1，3}，{1,2,3}
	 *   * 注意：此方法极度消耗资源，当集合很大时将无法运行，所以尽量使用极小数据量时使用
	 *   *  这个方法在我的电脑配置上运行最多支持20个数据， 21个数据时楚翔outofmemoir异常
		 *      数据量： 20
		 *		执行时间：2646 ns
		 *		产生数据：1048576 
	 * **/
	    public static <T> List<List<T>> getAllCollection(Set<T> set){
	    	List<T> list = new ArrayList<T>(set);
	    	return  getAllCollection(list);
	    }
	    /**
		 * 这是方法可以求得参数几何元素的子集，不包括空集。
		 *  @param ls 一个list集合
		 *  @param List<List<T>> 一个List集合列表 ，包含的子list就是原集合的单个集合
		 *  exemple： {1,2,3} 得到记过{1}，{2}，{3}，{1,2}，{2,3}，{1，3}，{1,2,3}
		 * * 注意：此方法极度消耗资源，当集合很大时将无法运行，所以尽量使用极小数据量时使用，
		 * 运行速度快，但因为是递归方法，非常占内存。在读取大量数据时，可能出现outofmemoir异常
		 * *  这个方法在我的电脑配置上运行最多支持20个数据， 21个数据时楚翔outofmemoir异常
		 *      数据量： 20
		 *		执行时间：2646 ns
		 *		产生数据：1048576 
		 * **/
	    public static <T> List<List<T>> getAllCollection(List<T> ls){
	    	List<List<T>> listL = new ArrayList<List<T>>();
	    	     doFils(ls, listL);
	    	return listL;
	    }
	    /**
		 * 这是方法是一个私有方法 为了内部迭代递归循环
		 *  @param ls 一个list集合
		 *  @param listL 一个List集合列表 ，包含的子list就是原集合的单个集合
		 * *  这个方法在我的电脑配置上运行最多支持20个数据， 21个数据时楚翔outofmemoir异常
		 *      数据量： 20
		 *		执行时间：2646 ns
		 *		产生数据：1048576 
		 * **/
	    @SuppressWarnings("unchecked")
		private static <T> void doFils(List<T> ls,List<List<T>> listL){
	    	if((ls.size())>0){
	    		T s = ls.remove(ls.size()-1);
	            if(listL.size()>0){
	            	List<Object> list = null;
	            	int size = listL.size();
	        	  for(int i=0;i<size;i++){
	        		  list = new ArrayList<Object>();
	        		  list.addAll(listL.get(i));
	        		  list.add(s); 
	        		  listL.add((List<T>) list);
	        	  }
	        	  
	            }
	        	  List<T> list = new ArrayList<T>();
	        	  list.add(s);
	        	  listL.add(list);

	          doFils(ls,listL);
	    	}
	        
	    }
	    
	    /**
		 * 这是方法是一个 寻找集合子集的方法，原理，将集合元素的个数转成二进制表示，
		 * 如param{3} 集合有3个子元素，将3表示为3-1的二进制 即111,然后将包括111的所有小于它
		 * 的二进制全部列出，如111,110,101,100..... 最终通过加载二进制位数为1的标志位来完成搜索所有子集合
		 *  @param param 一个泛型对象数组
		 *  @param List<List<T>> 一个List集合列表 ，包含的子list就是原集合的单个集合
		 * 这个方法效率较低，有大量循环，但是不想递归那样占用内存，
		 * 运行时间较长，但更安全，
		 *  这个方法在我的电脑配置上运行最多支持20个数据， 21个数据时楚翔outofmemoir异常
		 *      数据量： 20
		 *		执行时间：2646 ns
		 *		产生数据：1048576 
		 * **/
	    
		public static <T> List<List<T>> getAllCollection(T[] param){			
	    	//得到param的长度,元素个数
	    	int len = param.length;
	    	//根据元素个数算出位数 比如3个元素 2的3次方-1=7,7的二进制表达最高为111, 
	    	int n = (int)Math.pow(2,len)-1;	  
	    	//要返回的list	 
	    	System.out.println(n);
	    	List<List<T>> result = new ArrayList<List<T>>(n);
	    	// n=7 ，所以只要比n小的数字转变为二进制字符串只会小于111，如110,11,1等
	    	//使参数i小于n且循环，递减
	    	for(int i=n;i>=0;i--){
	    		//i 没改变一次意味着产生一个集合,所以创建一个list
	    		List<T> list = new ArrayList<T>(len);
	    		char[] cs = StringConvertor.binaryString(len, i).toCharArray();
	    		 for(int j=cs.length-1;j>=0;j--){
	    			 if(cs[j]=='1'){
	    				 list.add((T) param[len-1-j]) ;
	    			 }
	    		 }
	    		
	    		 result.add(list);
	    	}
	    	return result;
	    }
	    
	   
	    
}
