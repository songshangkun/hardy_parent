package org.hardy.netty.http;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.hardy.netty.http.ServletContainer.UrlMethodInfo;
import org.hardy.netty.http.help.ResponseHelper;
import org.hardy.springutil.SpringBeanUtil;
import org.hardy.springutil.webservice.CallAbstract;
import org.hardy.springutil.webservice.CallCapital;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.TypeUtils;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.multipart.FileUpload;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.util.CharsetUtil;

public class HttpServerHandler extends  SimpleChannelInboundHandler<FullHttpRequest> {
     
	 private  HttpConfig config;
	 
	 public HttpServerHandler(HttpConfig config){
		 this.config = config;
	 }
	@Override
	protected void messageReceived(ChannelHandlerContext ctx,FullHttpRequest request) throws Exception {
		  if(!request.decoderResult().isSuccess()){ 
			  //客户端错误 400 错误请求
			  sendError(ctx,HttpResponseStatus.BAD_REQUEST);
			  return;
		  }	  
		  HttpRequestInfo requestInfo = null;
		  UrlMethodInfo info = null;
		  //1-首先解析排除参数的URL
		  String url = RequestParser.getUri(request);
		  //1.x 排除过滤掉的url
		   if(!config.getExcusion().isEmpty()){ 
			    for(String excuUrl:config.getExcusion()){
			    	if(url.startsWith(excuUrl)){
			    		 sendError(ctx,HttpResponseStatus.NOT_ACCEPTABLE);
						  return;
			    	}
			    }
//				 if(config.getExcusion().contains(url)){
//					 sendError(ctx,HttpResponseStatus.NOT_ACCEPTABLE);
//					  return;
//				 }
			  }
		 //2-如果url不在设置范围内 返回错误信息
		   if(!ServletContainer.getServletMap().containsKey(url)){
			   sendError(ctx,HttpResponseStatus.NOT_FOUND);
				  return;
		   }
		 //3-找到方法参数设置信息类UrlMethodInfo
		   info = ServletContainer.getMethodByUrl(url);
		   //4-验证WEB访问方法是否一致
		   if(!RequestMethod.containsRequestMethod(info.getRequestMethods(), request)){
				//405用来访问本页面的 HTTP 谓词不被允许（方法不被允许）
				  sendError(ctx,HttpResponseStatus.METHOD_NOT_ALLOWED);
				  return;
			} 
		//解析request请求参数
		 /**
		  * 上传情况   比较特殊  //&&"multipart/form-data".equals(RequestParser.getContentType(request))
		  */
	   if(request.method()==HttpMethod.POST&&HttpPostRequestDecoder.isMultipart(request)){
		   //4.x-此方法执行 意味着已经提交结束 并且获取剩下的参数
           requestInfo = RequestParser.parse(request);
		   //**************************************
           //5-创建模板中会使用的必要的类 FullHttpResponse FullHttpRequest ChannelHandlerContext  
			FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
			response.headers().set(HttpHeaderNames.CONTENT_TYPE, info.getResultType().getContentTypeString(this.config.getCharSet().name())); 
			//5.x-声明fileUpload对象数组
			//6-开始对参数进行设置 将web传输的url携带的参数取出来,如果和执行类方法中参数一致则将参数对应
			Map<String, Object> paramMap = requestInfo.getParmMap();
			String[] parameterNames = ServletContainer.getMethodParameterNamesByAsm4(info.getMethod());
			Object[] params = new Object[info.getClazzs().length];	
			Object result = null;
			boolean  hasCtx = false;
			if(parameterNames!=null){
				for(int i=0;i<parameterNames.length;i++){
					if(paramMap.containsKey(parameterNames[i])) {
                        if(info.getClazzs()[i].isArray()||
							  info.getClazzs()[i]==List.class||
							  info.getClazzs()[i]==Set.class){
                    	    String arraystring = (String)paramMap.get(parameterNames[i]);
                    	    StringTokenizer stringTokenizer = new StringTokenizer(arraystring, ",");
                    	    StringBuilder sBuilder = new StringBuilder("[");
                    	    while(stringTokenizer.hasMoreTokens()){
                    	    		sBuilder.append("'").append(stringTokenizer.nextToken()).append("',");
                    	    }
                    	    sBuilder.deleteCharAt(sBuilder.length()-1);
                    	    sBuilder.append("]");
                    	    Object object = JSONObject.toJavaObject(JSON.parseArray(sBuilder.toString()), info.getClazzs()[i]);  
     					   params[i] = object;
                      }else {
                    	  Object object = TypeUtils.cast(paramMap.get(parameterNames[i]), info.getClazzs()[i], ParserConfig.getGlobalInstance());  
    					   params[i] = object;
                      }
				  }else{
						  if(info.getClazzs()[i]==FileUpload.class){
							  params[i] = paramMap.get(parameterNames[i]);
						  }
					      else if(info.getClazzs()[i]==FullHttpRequest.class) params[i] = request;
						  else if(info.getClazzs()[i]==FullHttpResponse.class)params[i] = response;
						  else if(info.getClazzs()[i]==ChannelHandlerContext.class){
							  params[i] = ctx;
							  hasCtx = true;
						  }else params[i] = null;
					  }
				} 
			}
			  //7-反射调取业务逻辑,根据类名参数对实例进行反射
				if(info.isSpringBean()){
					  CallCapital callCapital = (CallAbstract)SpringBeanUtil.getApplicationContext().getBean(info.getBeanName());
					  result = callCapital.callback(info.getMethod(), params);
				}else{
					 result = info.getMethod().invoke(ServletContainer.getSingletoBean(info.getBeanName()), params);
				}
				/**
				 * 如果参数重含有ChannelHandlerContext参数，那就要在具体方法中设置response返回信息
				 * 和输出数据
				 * 否则，直接使用模版
				 */
		    //如果执行类的执行方法中没有ChannelHandlerContext ctx 则调用以下代码
			if(!hasCtx){
				//如果执行类的执行方法中没有返回值则调用以下代码
			 if(result!=null){
				 //如果返回值转换接口未设定,将内置的NormalDataByteTrans转换接口设置进去,这样就可以处理string和byte[]两种形式的数据
//				 if(this.httpServer.getDataByteTrans()==null){
//					    this.httpServer.setDataByteTrans(new NormalDataByteTrans());
//				 }
				 //调用帮助类解决输出问题,将DataByteTrans接口允许的类转化为ByteBuf
				 ResponseHelper.responserWriteObject(response,this.config.getDataByteTrans(), result);
			 }  
			     //使用ChannelHandlerContext ctx返回输出
				 ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
			}
		   //****************************************
	   }
	   /**
	    * 其他情况 如遇特殊处理再次继续添加
	    */
	   else{
		   //1-解析参数 得到requestInfo对象 这个类包含了参数,无参数的URL,和访问方法
		   requestInfo = RequestParser.parse(request);
		   //System.out.println(requestInfo);
		   //2-如果不存在URL 直接返回找不到
//			if(!ServletContainer.containUrl(requestInfo.getUri())){
//				sendError(ctx,HttpResponseStatus.NOT_FOUND);
//				return;
//			} 
			//3-通过uri获取他关联的UrlMethodInfo对象,这个类包含URL相关的bean名称,bean方法，访问方法等信息
//		   info = ServletContainer.getMethodByUrl(requestInfo.getUri());
		   //4-过滤方法 如果UrlMethodInfo对象中的方法与当前访问方法不一致则返回错误
//		   if(!RequestMethod.containsRequestMethod(info.getRequestMethods(), request)){
//				  //405用来访问本页面的 HTTP 谓词不被允许（方法不被允许）
//				  sendError(ctx,HttpResponseStatus.METHOD_NOT_ALLOWED);
//				  return;
//			}
		   //5-创建模板中会使用的必要的类 FullHttpResponse FullHttpRequest ChannelHandlerContext  
			FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
			response.headers().set(HttpHeaderNames.CONTENT_TYPE, info.getResultType().getContentTypeString(this.config.getCharSet().name())); 
			//6-开始对参数进行设置 将web传输的url携带的参数取出来,如果和执行类方法中参数一致则将参数对应
			Map<String, Object> paramMap = requestInfo.getParmMap();
			String[] parameterNames = ServletContainer.getMethodParameterNamesByAsm4(info.getMethod());
			Object[] params = new Object[info.getClazzs().length];	
			Object result = null;
			boolean  hasCtx = false;
			if(parameterNames!=null){
				for(int i=0;i<parameterNames.length;i++){
					if(paramMap.containsKey(parameterNames[i])) {
                        if(info.getClazzs()[i].isArray()||
							  info.getClazzs()[i]==List.class||
							  info.getClazzs()[i]==Set.class){
                    	    String arraystring = (String)paramMap.get(parameterNames[i]);
                    	    StringTokenizer stringTokenizer = new StringTokenizer(arraystring, ",");
                    	    StringBuilder sBuilder = new StringBuilder("[");
                    	    while(stringTokenizer.hasMoreTokens()){
                    	    		sBuilder.append("'").append(stringTokenizer.nextToken()).append("',");
                    	    }
                    	    sBuilder.deleteCharAt(sBuilder.length()-1);
                    	    sBuilder.append("]");
                    	    Object object = JSONObject.toJavaObject(JSON.parseArray(sBuilder.toString()), info.getClazzs()[i]);  
     					   params[i] = object;
                      }else {
                    	  Object object = TypeUtils.cast(paramMap.get(parameterNames[i]), info.getClazzs()[i], ParserConfig.getGlobalInstance());  
    					   params[i] = object;
                      }
				  }else{
						  if(info.getClazzs()[i]==FullHttpRequest.class) params[i] = request;
						  else if(info.getClazzs()[i]==FullHttpResponse.class)params[i] = response;
						  else if(info.getClazzs()[i]==ChannelHandlerContext.class){
							  params[i] = ctx;
							  hasCtx = true;
						  }else params[i] = null;
					  }
				} 
			}
			  //7-反射调取业务逻辑,根据类名参数对实例进行反射
				if(info.isSpringBean()){
					  CallCapital callCapital = (CallAbstract)SpringBeanUtil.getApplicationContext().getBean(info.getBeanName());
					  result = callCapital.callback(info.getMethod(), params);
				}else{
					 result = info.getMethod().invoke(ServletContainer.getSingletoBean(info.getBeanName()), params);
				}
				/**
				 * 如果参数重含有ChannelHandlerContext参数，那就要在具体方法中设置response返回信息
				 * 和输出数据
				 * 否则，直接使用模版
				 */
		    //如果执行类的执行方法中没有ChannelHandlerContext ctx 则调用以下代码
			if(!hasCtx){
				//如果执行类的执行方法中没有返回值则调用以下代码
			 if(result!=null){
				 //如果返回值转换接口未设定,将内置的NormalDataByteTrans转换接口设置进去,这样就可以处理string和byte[]两种形式的数据
//				 if(this.httpServer.getDataByteTrans()==null){
//					    this.httpServer.setDataByteTrans(new NormalDataByteTrans());
//				 }
				 //调用帮助类解决输出问题,将DataByteTrans接口允许的类转化为ByteBuf
				 ResponseHelper.responserWriteObject(response,this.config.getDataByteTrans(), result);
			 }  
			     //使用ChannelHandlerContext ctx返回输出
				 ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
			} 
	   }//一般情况结束
 	
	}
 
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		 System.err.println(cause.getMessage());
		  cause.printStackTrace();
	}
	
	
	
	private static void sendError(ChannelHandlerContext ctx,HttpResponseStatus responseState){
		  FullHttpResponse response = new DefaultFullHttpResponse
		  (HttpVersion.HTTP_1_1, responseState,Unpooled.copiedBuffer("Failure: "+responseState.toString()+System.lineSeparator(),CharsetUtil.UTF_8));
		  response.headers().set(HttpHeaderNames.CONTENT_TYPE,HttpHeaderValues.TEXT_PLAIN);
		  ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}
	
	 	
}
