package org.hardy.util.extention;

/**
 * 在java1.7中在内部类中无法使用final类型参数
 * 这个类简单封装final对象,可以在内部类中改变
 * @author 09
 *
 */
public class FinalQuote<T> {
       
	   public T finalObject;
	   
	   public FinalQuote(T t){
		   this.finalObject = t;
	   }
}
