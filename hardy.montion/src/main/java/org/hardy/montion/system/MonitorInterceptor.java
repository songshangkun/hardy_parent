package org.hardy.montion.system;

import java.lang.reflect.Method;

/**
 * 这个自定义接口捕捉信息并执行保存
 * 1-可能保存数据库
 * 2-LOG输出
 * 3-保存SWT/SWING控件
 * 
 * <br>@PS:如果需要使用数据库记录,实现这个类要注意 必须结合系统倒入dao/数据源控制接口插入表,
 * 建表可以从包中或资源保中的monitor.sql中倒入sql语句。对于表数据的记录和逻辑计算需要从新实现(根据具体情况)
 * @author 09
 *
 */
public interface MonitorInterceptor {
     /**
      * 保存执行的结果数据
      * @param clazz   类
      * @param method  方法
      * @param args    参数
      */
	  void recoderExecuteResult(Class<?> clazz,Method method,String argsJson);
	  /**
	   * 保存异常信息
	   * @param clazz
	   * @param method
	   * @param argsJson
	   * @param e
	   */
	  void recoderException(Class<?> clazz,Method method,String argsJson,Exception e);
	  /**
	   * 注册数据库的表 monitor_statistic
	   * <br>在配置好spring中的读取包的ConfigMonitor类切运行registerMethod方法,
	   * 后可读取他的getAuthorDescrip方法
	   * <br> 或者可以运行这个类的实现的此方法，在这个方法中读取registerMethod和getAuthorDescrip运行后的内容
	   */
	  void registerMonitorStatistic();
}
