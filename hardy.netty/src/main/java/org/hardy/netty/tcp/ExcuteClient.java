package org.hardy.netty.tcp;

/**
 * 
 * 客户端处理消息接口
 * 因为接口使用在异步通信中，所以不要使用方法域以外的公共变量
 * 
 * @author sundyn
 *
 */
public interface ExcuteClient {
      
	 public void excute(Object msg);
}
