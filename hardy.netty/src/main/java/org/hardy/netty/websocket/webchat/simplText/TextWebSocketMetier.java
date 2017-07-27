package org.hardy.netty.websocket.webchat.simplText;

import io.netty.channel.ChannelHandlerContext;

public interface TextWebSocketMetier {
    
	     public void  handlerText(ChannelHandlerContext ctx,String text);
}
