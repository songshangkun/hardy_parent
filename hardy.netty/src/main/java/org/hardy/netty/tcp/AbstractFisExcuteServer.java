package org.hardy.netty.tcp;

import io.netty.channel.ChannelHandlerContext;

public abstract class AbstractFisExcuteServer implements ExcuteServer{

	@Override
	public  Object preexcute(Object msg){
		 return null;
	}

	@Override
	public abstract void preexcute(ChannelHandlerContext ctx, Object msg);

	@Override
	public boolean isFisPreexcute() {
		return true;
	}

}
