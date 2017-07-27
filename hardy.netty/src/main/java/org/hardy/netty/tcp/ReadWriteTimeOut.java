package org.hardy.netty.tcp;

import io.netty.channel.ChannelHandlerContext;

public class ReadWriteTimeOut implements ReadTimeOut,WriteTimeOut,AutorTimeOut{

	@Override
	public void afteTimeOutWrite(ChannelHandlerContext ctx, Object evt,Object msg) {
		   ctx.close();
	}

	@Override
	public void afteTimeOutRead(ChannelHandlerContext ctx, Object evt,Object msg) {
		ctx.close();
	}

	@Override
	public void afteAutorTimeOut(ChannelHandlerContext ctx, Object evt,Object msg) {
		 ctx.close();
	}

}
