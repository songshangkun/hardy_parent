package org.hardy.netty.tcp;

import io.netty.channel.ChannelHandlerContext;

/**
 * 读数据超时处理接口
 * @author sundyn
 *
 */
public interface ReadTimeOut {
	public void afteTimeOutRead(ChannelHandlerContext ctx, Object evt,Object msg);
}
