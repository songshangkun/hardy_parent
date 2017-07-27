package org.hardy.netty.tcp2.handler;


import org.hardy.netty.tcp2.Configuration;
import org.hardy.netty.tcp2.Message;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class TcpServerHandler extends SimpleChannelInboundHandler<Message>{
	private Configuration configuration;
    public TcpServerHandler(Configuration config){
    	    this.configuration = config;
    }
	@Override
	protected void messageReceived(ChannelHandlerContext ctx, Message msg) throws Exception {
		 if(this.configuration.getMessageProcessing()==null){
		     throw new RuntimeException("client not realiser interface messageProcessing");
	   }
		 this.configuration.getMessageProcessing().traiterMessage(ctx, msg);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();System.err.println("vvvvvvv");
	}

}
