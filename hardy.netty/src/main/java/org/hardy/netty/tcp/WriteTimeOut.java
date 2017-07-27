package org.hardy.netty.tcp;

import io.netty.channel.ChannelHandlerContext;

/**
 * 写数据超时处理接口
 * @author sundyn
 *
 */
public interface WriteTimeOut {
       
	   public void afteTimeOutWrite(ChannelHandlerContext ctx, Object evt,Object msg);
}
