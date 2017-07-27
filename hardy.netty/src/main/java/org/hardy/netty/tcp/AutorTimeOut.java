package org.hardy.netty.tcp;

import io.netty.channel.ChannelHandlerContext;

public interface AutorTimeOut {
         
	    public void afteAutorTimeOut(ChannelHandlerContext ctx, Object evt,Object msg);
}
