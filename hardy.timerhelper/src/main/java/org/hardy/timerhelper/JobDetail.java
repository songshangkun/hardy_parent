package org.hardy.timerhelper;

/**
 * 用于java原生的TimerTask实体方法接口
 * @author song
 * @param <E>
 *
 */
public interface JobDetail {
	/**
	 * 继续执行的方法
	 * @param params
	 * @return
	 */
   public Object  execute(Object params);
   /**
    * 是否结束的方法
    * @param params
    * @return
    */
   public boolean isContinu(Object params);
}
