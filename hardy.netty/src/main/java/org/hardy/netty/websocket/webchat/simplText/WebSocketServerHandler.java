package org.hardy.netty.websocket.webchat.simplText;

import org.apache.log4j.Logger;
import org.hardy.netty.websocket.webchat.WebChatConfig;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.CharsetUtil;

public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {
	public WebChatConfig config;
	
    private WebSocketServerHandshaker handshaker;
    
    private Logger logger = Logger.getLogger(WebSocketServerHandler.class);
 
    public WebSocketServerHandler(WebChatConfig config){
    	 this.config = config;
    }
    
	@Override
	protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
		  
		    if(msg instanceof FullHttpRequest){
		    	     handleHttpRequest(ctx, (FullHttpRequest)msg);
		    }else if(msg instanceof WebSocketFrame){
		    	    handleWebSocket(ctx, (WebSocketFrame)msg);
		    }
	}
    /**
     * websocket核心程序
     * @param ctx
     * @param frame
     */
	 private void handleWebSocket(final ChannelHandlerContext ctx, WebSocketFrame frame){
		 if(frame instanceof CloseWebSocketFrame){
			  handshaker.close(ctx.channel(), (CloseWebSocketFrame)frame.retain());
			   return;
		 }
		 if(frame instanceof PingWebSocketFrame){
			 ctx.write(new PingWebSocketFrame(frame.content().retain()));
			 return;
		 }
		 //只支持TextWebSocketFrame文本传输
		 if(!(frame instanceof TextWebSocketFrame)){
			 throw new UnsupportedOperationException(String.format("%s frame types not supported ", frame.getClass().getName()));
		 }
		String text = ((TextWebSocketFrame)frame).text();
		this.config.getTextMetier().handlerText(ctx, text);
	
		 
	 }
	 
	 private static void sendHttpResponse(ChannelHandlerContext ctx,FullHttpRequest request,FullHttpResponse response){
		    if(response.status().code()!=200){
		    	    ByteBuf buf = Unpooled.copiedBuffer(response.status().toString(),CharsetUtil.UTF_8);
		    	    response.content().writeBytes(buf);
		    	    buf.release();
		    	    response.headers().setLong(HttpHeaderNames.CONTENT_LENGTH,response.content().readableBytes()); 
		    }
		    ChannelFuture cFuture  = ctx.channel().writeAndFlush(response);
		    if(!isKeepAlive(request)||response.status().code()!=200){
		    	cFuture.addListener(ChannelFutureListener.CLOSE);
		    }
	 }
	 
	 @Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		 logger.error("WebSocketServerHandler exception:",cause);
		  cause.printStackTrace();
		  ctx.close();
	}

	private static boolean isKeepAlive(FullHttpRequest request) {
		return false;
	}

	private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request){
		     if(!request.decoderResult().isSuccess()||(!"websocket".equals(request.headers().get("Upgrade")))){
		    	         System.out.println("error for request");
		    	         sendHttpResponse(ctx, request, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
		     }
		     WebSocketServerHandshakerFactory wsFactory  =  new WebSocketServerHandshakerFactory(this.config.getWebSocketAddres(), null, false);
		     handshaker = wsFactory.newHandshaker(request);
		     if(handshaker==null){
		    	 WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
		     }else{
		    	 handshaker.handshake(ctx.channel(), request);
		     }
		     
	 }

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		   ctx.flush();
	}
 
}
