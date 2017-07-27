package org.hardy.netty.http.tropsimpl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

import java.util.Map;

import org.hardy.netty.http.HttpRequestInfo;
import org.hardy.netty.http.RequestParser;
import org.hardy.statics.web.ContentType;

/**
 * 具体的执行SimpleTrait接口方法
 * @author 09
 *
 */
public class SimpleHttpHandler extends SimpleChannelInboundHandler<FullHttpRequest>{
    
	private static SimpleTrait simpleTrait;
	
	@Override
	protected void messageReceived(ChannelHandlerContext ctx,FullHttpRequest request) throws Exception {
		  if(!request.decoderResult().isSuccess()){
			  //客户端错误 400 错误请求
			  sendError(ctx,HttpResponseStatus.BAD_REQUEST);
			  return;
		  }
		  HttpRequestInfo info = RequestParser.parse(request);
		   String uri = info.getUri();
		   Map<String,Object> params = info.getParmMap();
		   simpleTrait.traiter(uri, params);
	}
  
	 
	 
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		  System.out.println(cause);
		  sendError(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR);
	}
	
	public static void sendError(ChannelHandlerContext ctx,HttpResponseStatus responseState,ContentType contentType){
		  FullHttpResponse response = new DefaultFullHttpResponse
		  (HttpVersion.HTTP_1_1, responseState,Unpooled.copiedBuffer("Failure: "+responseState.toString()+System.lineSeparator(),CharsetUtil.UTF_8));
		  response.headers().set(HttpHeaderNames.CONTENT_TYPE,contentType.getMimeType()+"; charset=UTF-8");
		  ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}
	
	public static void sendError(ChannelHandlerContext ctx,HttpResponseStatus responseState){
		  FullHttpResponse response = new DefaultFullHttpResponse
		  (HttpVersion.HTTP_1_1, responseState,Unpooled.copiedBuffer("Failure: "+responseState.toString()+System.lineSeparator(),CharsetUtil.UTF_8));
		  response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain; charset=UTF-8");
		  ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}
	
	public static void sendResult(ChannelHandlerContext ctx,String result,ContentType contentType){
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
		response.headers().set(HttpHeaderNames.CONTENT_TYPE,contentType.getMimeType()+"; charset=UTF-8");
		ByteBuf buf = Unpooled.copiedBuffer(result, CharsetUtil.UTF_8);
		response.retain();
		response.content().writeBytes(buf);
		buf.release();
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}

	public static void sendResult(ChannelHandlerContext ctx,String result){
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
		response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain; charset=UTF-8");
		ByteBuf buf = Unpooled.copiedBuffer(result, CharsetUtil.UTF_8);
		response.retain();
		response.content().writeBytes(buf);
		buf.release();
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}



	public static SimpleTrait getSimpleTrait() {
		return simpleTrait;
	}



	public static void setSimpleTrait(SimpleTrait simpleTrait) {
		SimpleHttpHandler.simpleTrait = simpleTrait;
	}
	
	
    public static void main(String[] args) {  
		System.out.println(ContentType.TEXT.getMimeType());
	}
}
