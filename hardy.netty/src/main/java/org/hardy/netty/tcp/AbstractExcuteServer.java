package org.hardy.netty.tcp;

import io.netty.channel.ChannelHandlerContext;

public abstract class AbstractExcuteServer implements ExcuteServer{

	@Override
	public abstract Object preexcute(Object msg);

	@Override
	public void preexcute(ChannelHandlerContext ctx, Object msg) {}

	@Override
	public boolean isFisPreexcute() {
		return false;
	}

}
