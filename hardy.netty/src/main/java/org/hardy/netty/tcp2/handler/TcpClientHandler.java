package org.hardy.netty.tcp2.handler;

 

import org.hardy.netty.tcp2.ConfigClient;
import org.hardy.netty.tcp2.Message;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;

public class TcpClientHandler extends SimpleChannelInboundHandler<Message>{
	private ConfigClient config;
    public TcpClientHandler(ConfigClient config){
    	     this.config = config;
    }
	@Override
	protected void messageReceived(ChannelHandlerContext ctx, Message msg) throws Exception {
		if(this.config.getMessageProcessing()==null){
		     throw new RuntimeException("client not realiser interface messageProcessing");
	   }     
		this.config.getMessageProcessing().traiterMessage(ctx, msg);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		          cause.printStackTrace();
		          ctx.close();
	}
	
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
	            IdleStateEvent e = (IdleStateEvent) evt;
	            switch (e.state()) {
	                case WRITER_IDLE:
	                    if(this.config.getWriteTimeOut()!=null)
	                    	this.config.getWriteTimeOut().afteTimeOutWrite(ctx, evt, this.config);
	                    break;
	                case READER_IDLE: 
	                    if(this.config.getReadTimeOut()!=null)
	                    	this.config.getReadTimeOut().afteTimeOutRead(ctx, evt, this.config);
	                    break;
	                case ALL_IDLE:
	                	if(this.config.getAutorTimeOut()!=null)
	                    this.config.getAutorTimeOut().afteAutorTimeOut(ctx, evt, this.config);
	                    break;
	                default:
	                    break;
	            }
	        }
	}

}
