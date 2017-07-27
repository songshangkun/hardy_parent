package org.hardy.util.arrays;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.hardy.statics.exception.LogicielException;
import org.hardy.statics.exception.ParamValidateException;

/**
 * 使用2进制数映射数组关系
 * @author songshangkun
 *
 */
public class BinairArray<T> {
	 
	 public BinairArray(){}
	 
     public BinairArray(T[] ts){
    	 this.arrays = ts;
     }
     /**
      * 定义数组实体
      */
	  private T[] arrays;
	  /**
	   * 将传入的按钮权限名称集合转成10进制数字
	   * @param t
	   * @return
	   */
	  @SuppressWarnings("unchecked")
	public int getDigstT(T... tObject){
		  StringBuilder sbuild = new StringBuilder();
		  for(int i=0;i<arrays.length;i++){
			   sbuild.append('0');
		  }
		  for(T t:tObject){
			  if(ArrayUtils.contains(arrays, t)){
				 int i = ArrayUtils.lastIndexOf(arrays, t);
				 sbuild.deleteCharAt(i).insert(i, 1);
			  }else{
				  throw new ParamValidateException(t+" is not valide in inner arrays");
			  }
		  }
		  return Integer.parseInt(sbuild.reverse().toString(), 2);
	  } 
	  /**
	   * 将一个数字转换后对比内置数组
	   * @param binairy
	   * @return
	   */
	  public Set<T> getTbyDigst(int binairy){
		  Set<T> set = new HashSet<T>();
		  String binair = Integer.toBinaryString(binairy);
		  if(binair.length()>this.arrays.length)
			  throw new LogicielException("binaire length out of arrays's length");
		  int complet = this.arrays.length-binair.length();
		  StringBuilder sbuilder = new StringBuilder(binair);
		  for(int i=0;i<complet;i++){
			  sbuilder.insert(0, '0');
		  }
		  sbuilder.reverse();
		  for(int i=0;i<this.arrays.length;i++){
			  if(sbuilder.charAt(i)=='1'){
				   set.add(this.arrays[i]);
			  }
		  }
		  return set;
	  }
  
	  public T[] getArrays() {
		return arrays;
	  }
	public void setArrays(T[] arrays) {
		this.arrays = arrays;
	}
	 
}
