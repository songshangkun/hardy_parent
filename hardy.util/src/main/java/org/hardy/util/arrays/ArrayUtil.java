package org.hardy.util.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * array和collection的工具类
 * @author ssk
 *
 */
public class ArrayUtil {
   /**
    * 使用java的Arrays.asList,转化的list不支持remove方法
    * 所以这里使用此方法可转化ArraList实现的数组
    * @param t
    * @return
    */
	@SafeVarargs
	public static <T>List<T> asList(T... t){
		List<T> list = Arrays.asList(t);
		List<T> arraysList = new ArrayList<T>(list);
		return arraysList;
	}
}
