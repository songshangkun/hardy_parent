package org.hardy.netty.http.upload;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.Charsets;
import org.hardy.netty.http.HttpConfig;
import org.hardy.netty.http.help.ResponseHelper;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.FileUpload;
import io.netty.handler.codec.http.multipart.HttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.util.ReferenceCountUtil;

public class HttpUploadServerhandler extends ChannelHandlerAdapter {
     
	private static final HttpDataFactory factory = new DefaultHttpDataFactory(DefaultHttpDataFactory.MAXSIZE);
	
	private HttpConfig config;
	
	public HttpUploadServerhandler(HttpConfig config){
		 this.config = config;
	}
	 @Override
     public void channelReadComplete(ChannelHandlerContext ctx) {
           ctx.flush();
     }

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)throws Exception {
		  HttpRequest request = null;
		  if(msg instanceof HttpRequest){
			  try{
				 request = (HttpRequest) msg;
				 if(request.method()!=HttpMethod.POST){
					  ResponseHelper.sendError(ctx, HttpResponseStatus.METHOD_NOT_ALLOWED);
					  return ; 
				 }
				 HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(factory, request, Charsets.toCharset(CharEncoding.UTF_8));
				 try {
					    Map<String, String> normalParams = new HashMap<String, String>();
						List<InterfaceHttpData> datas = decoder.getBodyHttpDatas();
						 for (InterfaceHttpData data : datas) {
				            	if(data instanceof  Attribute) {
				            		Attribute attribute = (Attribute) data;
				            		normalParams.put(attribute.getName(), attribute.getValue()) ;
				            	}
				            }
			            for (InterfaceHttpData data : datas) {
			            	//后续会加上块传输（HttpChunk），目前仅简单处理
			            	if(data instanceof FileUpload) {
			            		FileUpload fileUpload = (FileUpload) data;
			            		if(fileUpload.isCompleted()) {
			            		   this.config.getFileUploadRename().rename(normalParams,fileUpload);
			            	   }
			            	}
			            }
			        } catch (Exception e) {
			        	//此处仅简单抛出异常至上一层捕获处理，可自定义处理
			            throw new Exception(e);
			        }
				 writeResponse(request, ctx.channel(), HttpResponseStatus.OK, "SUCCESS", false);
			  }catch(Exception e){
				  writeResponse(request,ctx.channel(), HttpResponseStatus.INTERNAL_SERVER_ERROR, "ERROR", true);  
			  }finally{
				  ReferenceCountUtil.release(msg);
			  }
		  }else{
			//discard request...
			ReferenceCountUtil.release(msg);
		  }
	}

 	
	private void writeResponse(HttpRequest request,
			Channel channel, HttpResponseStatus status, String msg, boolean forceClose){
		ByteBuf byteBuf = Unpooled.wrappedBuffer(msg.getBytes());
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, byteBuf);
		boolean close = isClose(request);
		if(!close && !forceClose){
			response.headers().add(org.apache.http.HttpHeaders.CONTENT_LENGTH, String.valueOf(byteBuf.readableBytes()));
		}
		ChannelFuture future = channel.write(response);
		if(close || forceClose){
			future.addListener(ChannelFutureListener.CLOSE);
		}
	}
	
	private boolean isClose(HttpRequest request){
		if(request.headers().contains(org.apache.http.HttpHeaders.CONNECTION, CONNECTION_CLOSE, true) ||
				(request.protocolVersion().equals(HttpVersion.HTTP_1_0) && 
				!request.headers().contains(org.apache.http.HttpHeaders.CONNECTION, CONNECTION_KEEP_ALIVE, true)))
			return true;
		return false;
	}
 
	private static final String CONNECTION_KEEP_ALIVE = "keep-alive";
    private static final String CONNECTION_CLOSE = "close";
	 
}
