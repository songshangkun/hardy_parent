package org.hardy.netty.tcp;

import org.apache.log4j.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

/**
 * 对于服务默认使用的ChannelHandlerAdapter
 * @author sundyn
 *
 */
public class MyServerHandler extends ChannelHandlerAdapter{
	
	static Logger logger = Logger.getLogger(MyServerHandler.class);
	
	private TcpServer server;
	
	 
	
	public MyServerHandler(TcpServer server){
		 this.server = server;
	} 
	
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		 if(this.server.getExcuteServer().isFisPreexcute()){
			 this.server.getExcuteServer().preexcute(ctx,msg);
		 }else{
			 Object result = this.server.getExcuteServer().preexcute(msg);
			 if(result!=null){
					 if(TcpServer.LineBasedFrameDecoder.equals(this.server.getDecoderType())){
						  if(!result.toString().endsWith(System.lineSeparator()))
							  result = result+System.lineSeparator();
						  ByteBuf resp = Unpooled.copiedBuffer(result.toString(),CharsetUtil.UTF_8);
							ctx.write(resp); 
					 }
					 if(TcpServer.DelimiterBasedFrameDecoder.equals(this.server.getDecoderType())){			
						 if(!result.toString().endsWith(this.server.getDelim()))
							  result = result+this.server.getDelim(); 
						 ByteBuf resp = Unpooled.copiedBuffer(result.toString(),CharsetUtil.UTF_8);
							ctx.write(resp); 
					 }
					 if(TcpServer.JavaSerialize.equals(this.server.getDecoderType())||TcpServer.Marshalling.equals(this.server.getDecoderType())){			
						    ctx.write(result); 
					 }
			 }
			 if( this.server.isAfterSendCloseCtx())ctx.close();
			
		 }
		   
	}
    /**
     * 在客户端回调时触发
     */
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		 ctx.flush(); //将数据发到SocketChannel中
	}
	/**
	 * 当出现异常时触发
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		System.err.println(cause.getMessage());
		logger.error("exception", cause); 
	    ctx.close(); 
	}
 
}
