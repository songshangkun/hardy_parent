package org.hardy.netty.tcp2.handler;


import org.hardy.netty.tcp.WriteTimeOut;
import org.hardy.netty.tcp2.ConfigClient;
import org.hardy.netty.tcp2.message.Interval;

import io.netty.channel.ChannelHandlerContext;

public class DefaultInterval implements WriteTimeOut{

	@Override
	public void afteTimeOutWrite(ChannelHandlerContext ctx, Object evt, Object msg) {
		ConfigClient config = (ConfigClient)msg;
		Interval interval = new Interval();
	    interval.setClientId(config.getPrincipal());
	    ctx.writeAndFlush(interval);
	    
	}

}
