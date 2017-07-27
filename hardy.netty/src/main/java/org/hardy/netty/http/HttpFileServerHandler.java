//package com.song.util.netty.http;
//
//import java.io.File;
//import java.io.RandomAccessFile;
//import java.io.UnsupportedEncodingException;
//import java.net.SocketAddress;
//import java.net.URLDecoder;
//import java.util.regex.Pattern;
//
//import javax.swing.text.AbstractDocument.Content;
//
//import io.netty.buffer.Unpooled;
//import io.netty.channel.ChannelFuture;
//import io.netty.channel.ChannelFutureListener;
//import io.netty.channel.ChannelHandler;
//import io.netty.channel.ChannelHandlerAdapter;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.ChannelProgressiveFuture;
//import io.netty.channel.ChannelProgressiveFutureListener;
//import io.netty.channel.ChannelPromise;
//import io.netty.channel.SimpleChannelInboundHandler;
//import io.netty.handler.codec.http.DefaultFullHttpResponse;
//import io.netty.handler.codec.http.DefaultHttpResponse;
//import io.netty.handler.codec.http.FullHttpRequest;
//import io.netty.handler.codec.http.FullHttpResponse;
//import io.netty.handler.codec.http.HttpHeaders;
//import io.netty.handler.codec.http.HttpMethod;
//import io.netty.handler.codec.http.HttpResponse;
//import io.netty.handler.codec.http.HttpResponseStatus;
//import io.netty.handler.codec.http.HttpVersion;
//import io.netty.handler.codec.http.LastHttpContent;
//import io.netty.handler.stream.ChunkedFile;
//import io.netty.util.CharsetUtil;
//
//public class HttpFileServerHandler extends  SimpleChannelInboundHandler<FullHttpRequest> {
//     
//	 private  String url;
//	 
//	 public HttpFileServerHandler(String url){
//		   this.url = url;
//	 }
//	@Override
//	protected void messageReceived(ChannelHandlerContext ctx,FullHttpRequest request) throws Exception {
//		  if(!request.decoderResult().isSuccess()){
//			  //客户端错误 400 错误请求
//			  sendError(ctx,HttpResponseStatus.BAD_REQUEST);
//			  return;
//		  }
//		  if(request.method()!=HttpMethod.GET){
//			  //405用来访问本页面的 HTTP 谓词不被允许（方法不被允许）
//			  sendError(ctx,HttpResponseStatus.METHOD_NOT_ALLOWED);
//			  return;
//		  }
//		final String uri = request.uri();
//		final String path = sanitizUri(uri);
//		if(path==null){
//			//403禁止访问：IIS 定义了许多不同的 403 错误，它们指明更为具体的错误原因
//			sendError(ctx, HttpResponseStatus.FORBIDDEN);
//			return;
//		}
//		File file = new File(path);
//		if(file.isHidden()||!file.exists()){
//			  sendError(ctx, HttpResponseStatus.NOT_FOUND);
//			  return;
//		}
//		if(file.isDirectory()){
//			if(uri.endsWith("/")){
//				 	sendListing(ctx,file);
//			}else{
//				sendRedirect(ctx,url+"/");
//			}
//			return;
//		}
//		if(!file.isFile()){
//			sendError(ctx, HttpResponseStatus.FORBIDDEN);
//			return;
//		}
//		RandomAccessFile randomAccessFile = null;
//		try{
//			randomAccessFile = new RandomAccessFile(file, "r");
//		}catch (Exception e) {
//			sendError(ctx, HttpResponseStatus.NOT_FOUND);
//			return;
//		}
//		long fileLength = randomAccessFile.length();
//		HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
//		setContentLength(response,fileLength);
//		setContentTypeHeader(response,file);
//		if(isKeppAlive(response)){
//			 response.headers().set("CONNECTION", HttpHeaders.xx);	 
//		}
//		ctx.write(response);
//		ChannelFuture sendfilefuture = ctx.write(new ChunkedFile(randomAccessFile,0,fileLength,8192),
//				ctx.newProgressivePromise());
//		sendfilefuture.addListener(new ChannelProgressiveFutureListener() {
//			
//			@Override
//			public void operationComplete(ChannelProgressiveFuture future) throws Exception {
//				System.out.println("Transfer progress complete");
//				
//			}
//			
//			@Override
//			public void operationProgressed(ChannelProgressiveFuture future, long progress, long total) throws Exception {
//				if(total<0){
//					System.out.println("Transfer progress "+progress);
//				}else{
//					System.out.println("Transfer progress "+progress+"/"+total);
//				}
//			}
//		});
//		ChannelFuture lastContentFuture = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
//			if(!isKeppAlive(response)){
//				lastContentFuture.addListener(ChannelFutureListener.CLOSE);
//			}
//	}
//  
//	 
//	private String sanitizUri(String uri) {
//		try {
//			uri = URLDecoder.decode(url, "UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			try {
//				uri = URLDecoder.decode(url, "UTF-8");
//			} catch (UnsupportedEncodingException e1) {throw new Error();
////					e1.printStackTrace();
//			}
//			
//		}
//		if(!uri.startsWith(url)||!uri.startsWith("/")) return null;
//		// uri.replace("/", File.separatorChar);
//		return uri;
//	}
//	@Override
//	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//		  System.out.println("chu xian yichangshidiaoyong ");
//	}
//	
//	private static void sendError(ChannelHandlerContext ctx,HttpResponseStatus responseState){
//		  FullHttpResponse response = new DefaultFullHttpResponse
//		  (HttpVersion.HTTP_1_1, responseState,Unpooled.copiedBuffer("Failure: "+responseState.toString()+System.lineSeparator(),CharsetUtil.UTF_8));
//		  response.headers().set("CONTENT_TYPE","text/plain; charset=UTF-8");
//		  ctx.writeAndFlush(response);
//	}
//
//	  private static final  Pattern INSECURE_URI = Pattern.compile(".*[<>&\"].*");
//	  
//}
