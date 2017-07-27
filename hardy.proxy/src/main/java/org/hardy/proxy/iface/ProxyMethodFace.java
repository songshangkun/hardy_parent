package org.hardy.proxy.iface;

import java.io.Serializable;

import org.hardy.proxy.pointjoint.PointJoint;




/**
 *  这个接口定义了许多aop连接点方法
 *  实现此接口或继承Advice类的实例将按照加入目标类的advice集合的顺序执行
 *  并且方法顺序优先于切面加入顺序被执行。
 *  exemple：advice1的begin优先于advice1的isTrue>advice1的around..... ,
 *  advice2的begin优先于advice1的isTrue>advice2的around.....
 * **/
public interface ProxyMethodFace extends Serializable{
	/**
	 *  begin(Object[] objs) 目标了方法一开始最先执行的连接点方法
	 *  该方法总会执行
	 *   @param objs 回调参数
	 *   
	 * **/
	public void begin(Object[] objs);
	/**
	 *  isTrue(Object[] objs) 过滤切面的方法
	 *  如果该方法返回false则所有该方法后执行的连接点方法将不会运行
	 *  该方法不会影响begin(Object[] objs)方法
	 *   @param objs 回调参数
	 *   @return boolean true 会面方法将执行|false 后面方法不执行
	 * **/
	public boolean isTrue(Object[] objs);
	/**
	 *  around(PointJoint pj) 模仿环绕通知 ，如果该切面被isTrue方法过滤掉将不执行
	 *   操作PointJoint类执行回调函数 ，如果实现此方法必须执行pj.process()方法，
	 *   否则目标程序将不会运行。
	 *   * 注意：当实现多个切面，但只想实现一个around方法时，
	 *   最好对每个不想实现around的切面都实现一个空around方法
	 *   
	 *   @param PointJoint 回调函数类
	 *   @return Object 方法执行的返回值对象
	 * **/
	public Object around(PointJoint pj);
	/**
	 *   afterParam(Object[] objs);
	 *   模仿后置通知 如果该切面被isTrue方法过滤掉将不执行,
	 *   @param objs 回调参数
	 *   @return Object 方法执行的返回值对象
	 * **/
	public void afterParam(Object[] objs);
	/**
	 *   afterParam(Object[] objs,Object obj);
	 *   模仿后置通知 如果该切面被isTrue方法过滤掉将不执行
	 *   @param objs 回调参数
	 *   @param obj 回调返回值
	 *  
	 * **/
	public void afterretour(Object[] objs,Object obj);
	/**
	 *   excepRaram(Object[] objs)；
	 *   模仿异常通知 如果该切面被isTrue方法过滤掉将不执行
	 *   @param objs 回调参数
	 * **/
	public void excepRaram(Object[] objs);
	/**
	 *   excepRaram(Object[] objs,Exception e)；
	 *   模仿异常通知 如果该切面被isTrue方法过滤掉将不执行
	 *   @param objs 回调参数
	 *   @param e 出现的异常对象
	 *  
	 * **/
	public void excepRetour(Object[] objs,Exception e);
	/**
	 *   excepException(Object obj);
	 *   模仿异常通知 如果该切面被isTrue方法过滤掉将不执行
	 *   @param obj 出现的异常对象,这里使用obj是为了最大限度的获得Exception对象。
	 *  
	 * **/
	public void excepException(Object obj);
	/**
	 *   finalParam(Object[] objs);
	 *   模仿最终通知 如果该切面被isTrue方法过滤掉将不执行
	 *   @param objs 回调参数
	 *  
	 * **/
	public void finalParam(Object[] objs);
	

	/**
	 *   finalRetour(Object[] objs,Object obj);
	 *   模仿最终通知 如果该切面被isTrue方法过滤掉将不执行
	 *   @param objs 回调参数
	 *   @param objs 回调返回值对想
	 *  
	 * **/
	public void finalRetour(Object[] objs,Object obj);
	
	
//	public void termineParam(Object[] objs);
	
//	public void termineretour(Object[] objs,Object obj);
}
