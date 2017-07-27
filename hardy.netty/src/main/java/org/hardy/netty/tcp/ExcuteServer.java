package org.hardy.netty.tcp;

import io.netty.channel.ChannelHandlerContext;

/** 
 * 这个接口制定如何处理服务端获取的消息
 * 因为接口使用在异步通信中，所以不要使用方法域以外的公共变量
 * @author sundyn
 *
 */
public interface ExcuteServer {
	/**
	 * 执行内部handler的处理对象接口
	 * @param msg
	 * @return
	 */
     public  Object preexcute(Object msg);
     
     /**
      * 在多线程调用时传递ChannelHandlerContext到子线程
      * @param ctx
      * @param msg
      * @return
      */
     public  void preexcute(ChannelHandlerContext ctx,Object msg);
     
     /**
      * 是否为在子线程中调用，就是选择运行preexcute(Object msg)还是preexcute(ChannelHandlerContext ctx,Object msg)
      * 默认为false 运行preexcute(Object msg)
      * @return
      */
     public  boolean isFisPreexcute();
}
