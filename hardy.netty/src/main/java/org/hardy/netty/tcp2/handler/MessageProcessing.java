package org.hardy.netty.tcp2.handler;

 

import org.hardy.netty.tcp2.Message;

import io.netty.channel.ChannelHandlerContext;

/**
 * 消息处理接口
 * @author song
 *
 */
public interface MessageProcessing {
        /**
         * 消息接收的处理借口
         * @param ctx
         * @param message
         */
	     public void traiterMessage(ChannelHandlerContext ctx,Message message);
}
