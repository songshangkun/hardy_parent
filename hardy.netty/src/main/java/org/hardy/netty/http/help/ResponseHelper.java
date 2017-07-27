package org.hardy.netty.http.help;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.song.tool.image.ImageUtil;
import com.song.tool.text.io.StringReaderUtil;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelProgressiveFuture;
import io.netty.channel.ChannelProgressiveFutureListener;
import io.netty.handler.codec.http.Cookie;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.ServerCookieDecoder;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.FileUpload;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder.ErrorDataDecoderException;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.InterfaceHttpData.HttpDataType;
import io.netty.handler.stream.ChunkedFile;
import io.netty.util.CharsetUtil;


/**
 * response帮助类 封装了一些常用方法
 * @author ssk
 *
 */
public class ResponseHelper {

	/**
	 * response直接输出
	 * @param ctx
	 * @param response
	 * @param buf 
	 */
	public static void ctxWriteFlushResponse(ChannelHandlerContext ctx,FullHttpResponse response,ByteBuf buf){
		 response.retain();
		 response.content().writeBytes(buf);
		 buf.release();
		 ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}
	/**
	 * response直接输出
	 * @param response
	 * @param buf
	 */
	public static void responserWriteByteArray(FullHttpResponse response,ByteBuf buf){
		 response.retain();
		 response.content().writeBytes(buf);
		 buf.release();
	}
	/**
	 * response直接输出
	 * @param response
	 * @param charSequence
	 * @param charset
	 */
	public static void responserWriteCharquence(FullHttpResponse response,CharSequence charSequence,Charset charset){
		ByteBuf buf = Unpooled.copiedBuffer(charSequence, charset);
		 response.retain();
		 response.content().writeBytes(buf);
		 buf.release();
	}
	/**
	 * response根据dataByteTrans接口直接输出
	 * @param response
	 * @param dataByteTrans
	 * @param obj
	 */
	public static void responserWriteObject(FullHttpResponse response,DataByteTrans dataByteTrans,Object obj){
		ByteBuf buf = dataByteTrans.dataToByteBuf(obj);
		response.retain();
		response.content().writeBytes(buf);
		buf.release();
	}
	/**
	 * 展示图片
	 * @param path
	 * @param request
	 * @param response
	 * @return
	 */
	public static byte[] presentImage(String path, HttpRequest request, FullHttpResponse response) {
		response.headers().set(HttpHeaderNames.PRAGMA, HttpHeaderValues.NO_CACHE);
		response.headers().set(HttpHeaderNames.CACHE_CONTROL, HttpHeaderValues.NO_CACHE);
		response.headers().setInt(HttpHeaderNames.EXPIRES, 0);
		return ImageUtil.GetByteImage(path);
	}
    /**
     * 支持下载
     * @param path
     * @param ctx
     * @param request
     */
	public static void downLoadFile(String path,ChannelHandlerContext ctx, HttpRequest request,String fileName) {
		try {
			File file = new File(path);
			RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
			long filelength = randomAccessFile.length();
			HttpResponse response2 = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
			response2.headers().setLong(HttpHeaderNames.CONTENT_LENGTH,filelength);
			response2.headers().setObject(HttpHeaderNames.CONTENT_TYPE,HttpHeaderValues.APPLICATION_OCTET_STREAM);
			response2.headers().set(HttpHeaderNames.CONTENT_DISPOSITION, "attachment;fileName="+fileName);
			if(isKeepAlive(request)){
				response2.headers().set(HttpHeaderNames.CONNECTION,HttpHeaderValues.KEEP_ALIVE);
			}
			ctx.write(response2);
			ChannelFuture chainnelFuture = ctx.write(new ChunkedFile(randomAccessFile,0,filelength,8192),
					ctx.newProgressivePromise());
			chainnelFuture.addListener(new ChannelProgressiveFutureListener() {
				@Override
				public void operationComplete(ChannelProgressiveFuture future) throws Exception {
					System.out.println("Transfer progress complete");
				}
				@Override
				public void operationProgressed(ChannelProgressiveFuture future, long progress, long total) throws Exception {
					if(total<0){
						System.out.println("Transfer progress "+progress);
					}else{
						System.out.println("Transfer progress "+progress+"/"+total);
					}
				}
			});
			ChannelFuture lastChainnelFuture = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
			if(!isKeepAlive(request))lastChainnelFuture.addListener(ChannelFutureListener.CLOSE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static boolean isKeepAlive(HttpRequest req) {

	      return false;

	   }
	/**
	 * 以被responserWriteCharquence替代
	 * @param path
	 * @param request
	 * @param response
	 * @return
	 */
    @Deprecated
	public static String presentHtml(String path, HttpRequest request, FullHttpResponse response) {
		return StringReaderUtil.getUTF8Text(path);
	}
    @Deprecated
	public static byte[] presentVedio(String path, HttpRequest request, FullHttpResponse response) {
		 throw new RuntimeException("not implement");
	}

    @Deprecated
	public static byte[] presentAudio(String path, HttpRequest request, FullHttpResponse response) {
		 throw new RuntimeException("not implement");
	}

	 /**
	  * url重定向
	  * @param ctx
	  * @param newUrl
	  */
	public static void sendRedircet(ChannelHandlerContext ctx,String newUrl) {
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FOUND);
		response.headers().set(HttpHeaderNames.LOCATION,newUrl); 
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}
 
    /**
     * 发送错误讯息
     * @param ctx
     * @param status
     */
	public static void sendError(ChannelHandlerContext ctx,HttpResponseStatus status) {
		FullHttpResponse response = new DefaultFullHttpResponse
				  (HttpVersion.HTTP_1_1, status,Unpooled.copiedBuffer("Failure: "+status.toString()+System.lineSeparator(),CharsetUtil.UTF_8));
				  response.headers().set("CONTENT_TYPE","text/plain; charset=UTF-8");
				  ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}
	
	/**
	 * 打印HTTP REQUEST 访问的全部头信息
	 * @param ctx
	 * @param msg
	 */
	public  static  void  printHeaders(ChannelHandlerContext ctx,HttpObject msg){
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
		response.headers().set(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.TEXT_PLAIN); 
		StringBuilder responseContent = new StringBuilder();
		 responseContent.setLength(0);
		 //System.out.println(msg.getClass().getName());
		if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            responseContent.append("WELCOME TO THE WILD WILD WEB SERVER\r\n");
            responseContent.append("===================================\r\n");
            responseContent.append("VERSION: " + request.protocolVersion().text() + "\r\n");
            responseContent.append("REQUEST_URI: " + request.uri() + "\r\n\r\n");
            responseContent.append("\r\n\r\n");
            // new getMethod
            for (Entry<CharSequence, CharSequence> entry : request.headers().entries()) {
                responseContent.append("HEADER: " + entry.getKey() + '=' + entry.getValue() + "\r\n");
            }
            responseContent.append("\r\n\r\n");
            responseContent.append("=================Cookie====================");
            responseContent.append("\r\n\r\n");
            Set<Cookie> cookies;
            String value = (String)request.headers().get(HttpHeaderNames.COOKIE);
            if (value == null) {
                cookies = Collections.emptySet();
            } else {
                cookies = ServerCookieDecoder.decode(value);
            }
            for (Cookie cookie : cookies) {
                responseContent.append("COOKIE: " + cookie + "\r\n");
            }
            responseContent.append("\r\n\r\n");
            responseContent.append("=================attribute====================");
            responseContent.append("\r\n\r\n");
           
            // if GET Method: should not try to create a HttpPostRequestDecoder
            if (request.method().equals(HttpMethod.GET)) {
            	 QueryStringDecoder decoderQuery = new QueryStringDecoder(request.uri());
                 Map<String, List<String>> uriAttributes = decoderQuery.parameters();
                 for (Entry<String, List<String>> attr: uriAttributes.entrySet()) {
                     for (String attrVal: attr.getValue()) {
                         responseContent.append("URI PARAMS: " + attr.getKey() + '=' + attrVal + "\r\n");
                     }
                 }
                 responseContent.append("\r\n\r\n");
                // GET Method: should not try to create a HttpPostRequestDecoder
                // So stop here
                responseContent.append("\r\n\r\nEND OF GET CONTENT\r\n");
                // Not now: LastHttpContent will be sent writeResponse(ctx.channel());
//                return;
            }else if(request.method().equals(HttpMethod.POST)){
            	HttpPostRequestDecoder decoder = null;
            	 try {
                     decoder = new HttpPostRequestDecoder(request);
                     boolean readingChunks = HttpUtil.isTransferEncodingChunked(request);
                     responseContent.append("Is Chunked: " + readingChunks + "\r\n");
                     responseContent.append("IsMultipart: " + decoder.isMultipart() + "\r\n");
                     if (readingChunks) {
                         // Chunk version
                         responseContent.append("Chunks: ");
                         readingChunks = true;
                     }
                     responseContent.append(decoder.toString()).append(System.lineSeparator());
                     if(decoder.getBodyHttpDatas()!=null){
                    	 for(InterfaceHttpData param:decoder.getBodyHttpDatas()){
                    		 if(param instanceof Attribute){
          	             	   Attribute data = (Attribute) param;
          	             	responseContent.append("URI PARAMS: " + data.getName() + '=' + data.getValue() + "\r\n");
          	                }
          	            	if(param.getHttpDataType() == HttpDataType.FileUpload) {
          	            		FileUpload fileUpload = (FileUpload) param;            		
          	            		if(fileUpload.isCompleted()) {
          	            			responseContent.append("URI PARAMS: " + fileUpload.getName() 
          	            					+ '=' + fileUpload.getFilename()+"["+fileUpload.getContentType()+"]" + "\r\n");
          	            			//保存到磁盘
//          	            			StringBuffer fileNameBuf = new StringBuffer(); 
//          	            			fileNameBuf.append("d:\\test111.jpg");
//          	            			fileUpload.renameTo(new File(fileNameBuf.toString()));
          	            		}
          	            	}
                    	 }
                     }
                     if (msg instanceof HttpContent) {
					     // New chunk is received
					     HttpContent chunk = (HttpContent) msg;
					     try {
					         decoder.offer(chunk);
					     } catch (ErrorDataDecoderException e1) {
					         e1.printStackTrace();
					         responseContent.append(e1.getMessage());
					         e1.printStackTrace();
					         responseContent.append(e1.getMessage());
					         ByteBuf buf = Unpooled.copiedBuffer(responseContent, CharsetUtil.UTF_8);
					 		 response.content().writeBytes(buf);
					 		 ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
					         return;
					     }
					     responseContent.append('o');
					     // example of reading chunk by chunk (minimize memory usage due to
					     // Factory)
//                             readHttpDataChunkByChunk();
					     // example of reading only if at the end
					     if (chunk instanceof LastHttpContent) {
//                                 writeResponse(ctx.channel());
					         readingChunks = false;
					         reset(request,decoder);
					     }
					 }
     	           responseContent.append("\r\n\r\nEND OF POST CONTENT\r\n");
                 } catch (ErrorDataDecoderException e1) {
                     e1.printStackTrace();
                     responseContent.append(e1.getMessage());
                     ByteBuf buf = Unpooled.copiedBuffer(responseContent, CharsetUtil.UTF_8);
             		 response.content().writeBytes(buf);
             		 ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
//                     ctx.channel().close();
                     return;
                 } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            
		}else{
			responseContent.append("NOT REQUEST VISIT");
		}
		ByteBuf buf = Unpooled.copiedBuffer(responseContent, CharsetUtil.UTF_8);
		response.content().writeBytes(buf);
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}
	/**
	 * 释放资源
	 * @param request
	 * @param decoder
	 */
	 public static void reset(HttpRequest request,HttpPostRequestDecoder decoder) {
	        request = null;

	        // destroy the decoder to release all resources
	        decoder.destroy();
	        decoder = null;
	    }
	 /**
	  * 姐姐发送response信息
	  * @param request
	  * @param channel
	  * @param status
	  * @param msg
	  * @param forceClose
	  */
	 public static void writeResponse(HttpRequest request,
				Channel channel, HttpResponseStatus status, String msg, boolean forceClose){
			ByteBuf byteBuf = Unpooled.wrappedBuffer(msg.getBytes());
			FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, byteBuf);
			boolean close = isClose(request);
			if(!close && !forceClose){
				response.headers().add(org.apache.http.HttpHeaders.CONTENT_LENGTH, String.valueOf(byteBuf.readableBytes()));
			}
			ChannelFuture future = channel.writeAndFlush(response);
			if(close || forceClose){
				future.addListener(ChannelFutureListener.CLOSE);
			}
		}
	 private static boolean isClose(HttpRequest request){
			if(request.headers().contains(org.apache.http.HttpHeaders.CONNECTION, CONNECTION_CLOSE, true) ||
					(request.protocolVersion().equals(HttpVersion.HTTP_1_0) && 
					!request.headers().contains(org.apache.http.HttpHeaders.CONNECTION, CONNECTION_KEEP_ALIVE, true)))
				return true;
			return false;
		}
	 
		private static final String CONNECTION_KEEP_ALIVE = "keep-alive";
	    private static final String CONNECTION_CLOSE = "close";
	 /**
	  * 定义对象转换接口
	  * @author 09
	  *
	  */
	public static interface DataByteTrans{
		/**
		 * 将对象转化为byteBuf
		 * @param object
		 * @return
		 */
		 public  ByteBuf dataToByteBuf(Object object);
	}
}
