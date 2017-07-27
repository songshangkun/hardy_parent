package org.hardy.netty.tcp;


import org.apache.log4j.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class MyClientHandler extends ChannelHandlerAdapter{
   
	public MyClientHandler(TcpClient client){
		this.client = client;
		prepare();
	}
	private  Object firstMessage ; 
	
	public TcpClient client;
	
    private static final Logger logger = Logger.getLogger(MyClientHandler.class);

	public void prepare(){
		byte[] req = null;
		if(TcpClient.LineBasedFrameDecoder.equalsIgnoreCase(this.client.getDecoderType())){
			req = (this.client.getMessage().toString()+System.lineSeparator()).getBytes();
			firstMessage = Unpooled.buffer(req.length);
			  ((ByteBuf)firstMessage).writeBytes(req);
		}
		if(TcpClient.DelimiterBasedFrameDecoder.equalsIgnoreCase(this.client.getDecoderType())){
			req = (this.client.getMessage().toString()+this.client.getDelim()).getBytes();
			firstMessage = Unpooled.buffer(req.length);
			((ByteBuf)firstMessage).writeBytes(req);
		}
		if(TcpClient.JavaSerialize.equalsIgnoreCase(this.client.getDecoderType())||TcpClient.Marshalling.equalsIgnoreCase(this.client.getDecoderType())){
			firstMessage = this.client.getMessage();
		}  
	 }

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		 ctx.writeAndFlush(firstMessage);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)throws Exception {
		this.client.getExcuteClient().excute(msg);
		if(this.client.isCloseAfterRead())ctx.close();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		 logger.error("Unexpected exception from downstream :",cause);
		 ctx.close();
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		    ctx.flush();
	}
	
	/**
	 * 
	 */
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)throws Exception {
		logger.info("timeout in "+this.client.getTimeout()+" ms");
	    if (evt instanceof IdleStateEvent) {
	        IdleStateEvent e = (IdleStateEvent) evt;
	        if (e.state() == IdleState.READER_IDLE) {
	            ctx.close();
	            logger.info("READER_IDLE timeout");
	          if(this.client.getReadTimeOut()!=null)this.client.getReadTimeOut().afteTimeOutRead(ctx, evt,this.client.getMessage()); 
	        } else if (e.state() == IdleState.WRITER_IDLE) {
	            ByteBuf buff = ctx.alloc().buffer();
	            buff.writeBytes("mayi test".getBytes());
	            ctx.writeAndFlush(buff);
	            logger.info("WRITER_IDLE timeout");
	            if(this.client.getWriteTimeOut()!=null)this.client.getWriteTimeOut().afteTimeOutWrite(ctx, evt,this.client.getMessage()); 
	        }
	        else {
	        	logger.info("some things timeout");
	            if(this.client.getAutorTimeOut()!=null)this.client.getAutorTimeOut().afteAutorTimeOut(ctx, evt,this.client.getMessage()); 
	        }
	    }
	    
	    super.userEventTriggered(ctx, evt);
	}
	
	 
}
