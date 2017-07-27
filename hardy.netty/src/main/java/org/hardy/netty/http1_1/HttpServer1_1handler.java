package org.hardy.netty.http1_1;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hardy.netty.http.HttpConfig;
import org.hardy.netty.http.RequestParser;
import org.hardy.netty.http.ServletContainer;
import org.hardy.netty.http.ServletContainer.UrlMethodInfo;
import org.hardy.netty.http.help.ResponseHelper;
import org.hardy.springutil.SpringBeanUtil;
import org.hardy.springutil.webservice.CallAbstract;
import org.hardy.springutil.webservice.CallCapital;
import org.hardy.statics.exception.CustomInnerErrorException;
import org.hardy.statics.exception.NotSupportException;
import org.hardy.statics.exception.RequestParamException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.TypeUtils;
import com.song.tool.reflex.RefletClazzUtil;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
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
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.FileUpload;
import io.netty.handler.codec.http.multipart.HttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.InterfaceHttpData.HttpDataType;
import io.netty.util.CharsetUtil;
import org.hardy.netty.http.RequestMethod;

/**
 * http主服务的handler处理程序
 * @author 09
 *
 */
public class HttpServer1_1handler extends SimpleChannelInboundHandler<HttpObject>{
	
	private static final Logger logger=Logger.getLogger(HttpServer1_1handler.class);
    /**
     * 配置文件对象
     */
	 private HttpConfig config;
	 /**
	  * 全聚德request对象
	  */
	 private HttpRequest request;
	 /**
	  * 全局的response返回对象
	  */
	 private FullHttpResponse response ; 
	 /**
	  * POST请求时的解码器
	  */
	 private HttpPostRequestDecoder decoder;
	 /**
	  * GET请求是的解码器
	  */
	 private QueryStringDecoder decoderGET;
	 /**
	  * 每个url的详细内容
	  */
	 private UrlMethodInfo info;
	 /**
	  * 终端方法参数名称,依照原始参数顺序
	  */
	 private String[] parameterNames;
	 /**
	  * 最终方法调用是的参数
	  */
	 private Object[] params ;
	 /**
	  * 最终输出方法是否有ChannelHandlerContext对象
	  */
	 boolean hasCtx = false;
	 
	 public HttpServer1_1handler(HttpConfig config){
		  this.config = config;
	 }
	 
	 private static final HttpDataFactory factory =
	            new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE); // Disk if size exceed
	 
	 
	
	 @Override
	    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
	        if (decoder != null) {
	            decoder.cleanFiles();
	        }
	    }
	 
	 @Override
	    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
	        logger.error(cause.getMessage(), cause);
	        sendStatus(ctx,cause.getMessage() ,HttpResponseStatus.EXPECTATION_FAILED);
		    cause.printStackTrace();
	        ctx.channel().close();
	    }

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, HttpObject msg)
			throws Exception {	
		 //1-判断request并赋值给类变量
			if (msg instanceof HttpRequest) {
				   HttpRequest request = this.request = (HttpRequest) msg;
				   /**
				    * 1.0 开始检查设置的URI访问的方法和类型等参数
				    */
				   if(!request.decoderResult().isSuccess()){ 
						  //客户端错误 400 错误请求
					   	  sendStatus(ctx,"错误的请求",HttpResponseStatus.BAD_REQUEST);
						  return;
					  }	  
					   info = null;
					  //1.1-首先解析排除参数的URL
					  String url = RequestParser.getUri(request);
					  //1.1.x 排除过滤掉的url
					   if(!config.getExcusion().isEmpty()){ 
						    for(String excuUrl:config.getExcusion()){
						    	if(url.startsWith(excuUrl)){
						    		sendStatus(ctx,"uri已被禁用或过滤",HttpResponseStatus.NOT_ACCEPTABLE);
									  return;
						    	}
						    }
						}
					 //1.2-如果url不在设置范围内 返回错误信息
					   if(!ServletContainer.getServletMap().containsKey(url)){
						   sendStatus(ctx,"找不到要访问的uri",HttpResponseStatus.NOT_FOUND);
							  return;
					   }
					 //1.3-找到方法参数设置信息类UrlMethodInfo
					   info = ServletContainer.getMethodByUrl(url);
					 //1.4-验证WEB访问方法是否一致
					   if(!RequestMethod.containsRequestMethod(info.getRequestMethods(), request)){
							//405用来访问本页面的 HTTP 谓词不被允许（方法不被允许）
						    sendStatus(ctx,"访问方法不被允许",HttpResponseStatus.METHOD_NOT_ALLOWED);
							  return;
						}
					  //1.5-设置默认的response输出对象
					   response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
					   response.headers().set(HttpHeaderNames.CONTENT_TYPE, info.getResultType().getContentTypeString(this.config.getCharSet().name())); 
				       
					 //1.6-根据获取的UrlMethodInfo继续解析
					 //1.7-获取执行方法的参数名称,该名成和方法的类型数量对应
					   parameterNames = ServletContainer.getMethodParameterNamesByAsm4(info.getMethod());
					   params = new Object[info.getClazzs().length];
					   if(parameterNames.length!=info.getClazzs().length){
						   throw new CustomInnerErrorException("获取终端方法参数个数与参数类型个数不相等,通常这种异常与netty无关"); 
					   }
				   
				   //1.0 检查结束
				   //2.0 设置GET请求解码器
				   if(request.method()==HttpMethod.GET){
					    if(this.decoder!=null){
					    	this.decoder.destroy();
					    	this.decoder = null;
					    }
	        	        
					    decoderGET = new QueryStringDecoder(request.uri());
				   }
				   if(request.method()==HttpMethod.POST){
				       if(this.decoderGET!=null){
				    	   this.decoderGET = null;
				       }
					   decoder = new HttpPostRequestDecoder(factory, request);  
				   }
				}
			
			if(this.request.method()==HttpMethod.GET){ 
				if(this.decoderGET!=null){
					java.util.Set <Entry<String, List<String>>> set = decoderGET.parameters().entrySet();
		            buildParamsGET(hasCtx, ctx, parameterNames, set);
		            //以下方法可被终端方法替代
 		            //sendStatus(ctx, Arrays.toString(params), HttpResponseStatus.OK);
		            finalreturn(ctx);
		            return;
				}else{
					sendStatus(ctx,"GET访问:但是解码decoder为空",HttpResponseStatus.BAD_REQUEST);
				}
			}else
			//2-判断是否为一般的POST非上传文件访问
			if(this.request.method()==HttpMethod.POST&&
					!HttpPostRequestDecoder.isMultipart(this.request)){
				
			   if(this.decoder!=null){ 
				   if(msg instanceof HttpContent){
						  HttpContent chunk = (HttpContent) msg;
		                    decoder.offer(chunk);				                  			                  
		                    List<InterfaceHttpData> datas = decoder.getBodyHttpDatas();
						    buildParamsPOST(hasCtx, ctx, parameterNames,datas);
						    if (chunk instanceof LastHttpContent) { 
						    	//sendStatus(ctx, Arrays.toString(params), HttpResponseStatus.OK);
						    	finalreturn(ctx);
						    	decoder.destroy();
			        	        decoder = null;
					            return;
			                }
					  }	
			   }else{
				   sendStatus(ctx,"非上传POST访问:但是解码decoder为空",HttpResponseStatus.BAD_REQUEST);   
			   }
			}else
			//2-判断访问类型是上传
			if(this.request.method()==HttpMethod.POST&&
					HttpPostRequestDecoder.isMultipart(this.request)){
				if(this.decoder!=null){ 
		            if (msg instanceof HttpContent) {
		                // New chunk is received
		                HttpContent chunk = (HttpContent) msg;
		                decoder.offer(chunk);           
		                while (decoder.hasNext()) {
			                InterfaceHttpData data = decoder.next();
			                if (data != null) {				              
			                    try {
		                    	 if (data.getHttpDataType() == HttpDataType.Attribute) {
		             	            Attribute attribute = (Attribute) data;
		             	            String name = attribute.getName();
		             	            String value = attribute.getValue();
		             	            System.out.println(name+"  "+value);
		             	           buildPostMultipart(name, value, ctx);
		             	           }else{   
		             		            if (data.getHttpDataType() == HttpDataType.FileUpload) {
		             		                FileUpload fileUpload = (FileUpload) data;
		             		                if (fileUpload.isCompleted()) {
		             		                	/**
		             		                	 * 此处无法直接使用buildPostMultipart方法操作FileUpload
		             		                	 * 会产生fileupload未定义的错误
		             		                	 */
//		             		                	 buildPostMultipart(fileUpload.getName(), fileUpload, ctx);  
		             		                		/**
		             		                		 * 这个方法可以使用,但无法在终端方法中操作,
		             		                		 * 最好使用try catch处理
		             		                		 */
//		             		                	fileUpload.renameTo(new File("D:\\testfile.jpg"));
		             		                	String fileName = fileUpload.getName();
		             		                	File file = this.config.getFileuploadHelper().rename(fileUpload);	
		             		                	 buildPostMultipart(fileName, file, ctx); 
		             		                } 
		             		            }
		             		          
		             	           }
			                      //**********************************
			                    } finally {
			                        data.release(); 
			                    }
			                }
			            }	     
		                //*************************************
		                // example  of reading only if at the end
		                if (chunk instanceof LastHttpContent) {
//		                	sendStatus(ctx,"OK" ,HttpResponseStatus.OK);
// 		                sendStatus(ctx,Arrays.toString(params) ,HttpResponseStatus.OK);
		                	finalreturn(ctx);
		        	        decoder.destroy();
		        	        decoder = null;
		        	        return;
		                }
		            }
				        
			      }else {
			    	  sendStatus(ctx,"上传的POST访问:但是解码decoder为空",HttpResponseStatus.BAD_REQUEST);
		        }
			}//label:上传操作结束
			else{
				throw new NotSupportException("暂时不支持除GET,POST以外的其他方法");
			}
	
			}//label:主程序结束
	
	
	/**
	 * 发送response返回方法
	 * @param ctx
	 * @param responseState
	 */
	private static void sendStatus(ChannelHandlerContext ctx,String msg,HttpResponseStatus responseState){
		  FullHttpResponse response = new DefaultFullHttpResponse
		  (HttpVersion.HTTP_1_1, responseState,Unpooled.copiedBuffer("RESULT: "+responseState.toString()+
				  System.lineSeparator()+msg,CharsetUtil.UTF_8));
		  response.headers().set(HttpHeaderNames.CONTENT_TYPE,HttpHeaderValues.TEXT_PLAIN);
		  ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}
	
	/**
	 * 处理POST上传文件请求
	 * @param name
	 * @param value
	 * @param ctx
	 */
	private void buildPostMultipart(String name,Object value,ChannelHandlerContext ctx){
		for(int i=0;i<parameterNames.length;i++){
			if(name.equals(parameterNames[i])){
  	    	  //定义特殊的参数类型
  	    	  if(info.getClazzs()[i]==File.class){
					if(name.equals(parameterNames[i])){
						if(value instanceof File){
							if(params[i]==null)params[i] = value;
							//break;
						}
						else throw new RequestParamException("上传参数名称与文件类型不匹配:["+name+" 应该是FileUpload类型]");
					}
				  }else{
					  //如果是单独参数
                    if(!info.getClazzs()[i].isArray()&&!RefletClazzUtil.isExistInterface(info.getClazzs()[i], "java.util.Collection")){
                  	   Object object = TypeUtils.cast(value, info.getClazzs()[i], ParserConfig.getGlobalInstance());  
                  	 if(params[i]==null)params[i] = object;
  					  // break;
                    }else
                  	//定义集合参数类型  
                  	if(info.getClazzs()[i].isArray()||RefletClazzUtil.isExistInterface(info.getClazzs()[i], "java.util.Collection")){
                  	   Object object = JSONObject.toJavaObject(JSON.parseArray("["+value+"]"), info.getClazzs()[i]);  
                  	if(params[i]==null)params[i] = object;
   					  // break;
                    }  
				  }
		
			}else{
				 if(info.getClazzs()[i]==FullHttpRequest.class){
					  if(params[i]==null){
						  if(!(request instanceof FullHttpRequest))
						throw new RequestParamException("无法将内置的HttpRequest对象转换为子类FullHttpRequest");
						  params[i] = request; 
					  }
				  }else if(info.getClazzs()[i]==HttpRequest.class){
					  if(params[i]==null)params[i] = request; 
				  }else if(info.getClazzs()[i]==FullHttpResponse.class){
					  if(params[i]==null) params[i] = response;  
				   }else if(info.getClazzs()[i]==HttpResponse.class){
					  if(params[i]==null)params[i] = response;
				   }
				   else if(info.getClazzs()[i]==ChannelHandlerContext.class){
					  if(params[i]==null)params[i] = ctx;
					  hasCtx = true;
					  //break;
				  }
			}
		}
	}
	
	/**
	 * 处理GET请求参数
	 * @param hasCtx
	 * @param ctx
	 * @param parameterNames
	 * @param set
	 */
	private void buildParamsGET(boolean hasCtx,ChannelHandlerContext ctx,String[]parameterNames, 
    		Set <Entry<String, List<String>>> set){
    	int index = 0;
    	for(int i=0;i<info.getClazzs().length;i++){
    		if(info.getClazzs()[i]==FullHttpRequest.class){
				  if(!(request instanceof FullHttpRequest))
						throw new RequestParamException("无法将内置的HttpRequest对象转换为子类FullHttpRequest");
				  params[index] = request; index++;continue;
			  }else if(info.getClazzs()[i]==HttpRequest.class){
				  params[index] = request; index++;continue;
			  }else if(info.getClazzs()[i]==FullHttpResponse.class){
				   params[index] = response;index++; continue;
			   }else if(info.getClazzs()[i]==HttpResponse.class){
				   params[index] = response;index++; continue;
			   }
			   else if(info.getClazzs()[i]==ChannelHandlerContext.class){
				  params[index] = ctx;
				  hasCtx = true;
				  index++;continue;
			  }
    	    for(Entry<String, List<String>> ens:set){	
    	    	  //定义特殊的参数类型
    	    	  if(info.getClazzs()[i]==FileUpload.class){
					  sendStatus(ctx,"GET 请求无法处理上传文件类型", HttpResponseStatus.NOT_ACCEPTABLE);
					  throw new RequestParamException("METHOD　GET NOT SUPPORT UPLOAD FILES :"+parameterNames[i]);
				  }else
				   //定义一般类型
				  if(parameterNames[i].equals(ens.getKey())){
					  //如果是单独参数
                      if(!info.getClazzs()[i].isArray()&&!RefletClazzUtil.isExistInterface(info.getClazzs()[i], "java.util.Collection")){
                    	   Object object = TypeUtils.cast(ens.getValue().get(0), info.getClazzs()[i], ParserConfig.getGlobalInstance());  
    					   params[index] = object;
    					   index++;break;
                      }else
                    	//定义集合参数类型  
                    	if(info.getClazzs()[i].isArray()||RefletClazzUtil.isExistInterface(info.getClazzs()[i], "java.util.Collection")){
                    	   Object object = JSONObject.toJavaObject(JSON.parseArray(ens.getValue().toString()), info.getClazzs()[i]);  
     					   params[index] = object;
     					   index++;break;
                      }  
			}else {
				params[index] = null;
				if(i==info.getClazzs().length-1){
					index++;	
				}
			}
		}
    	    
    	} 
    	//System.out.println(Arrays.toString(params)+"  GET");
    }
    
	/**
	 * 处理POST请求参数(一般的POST请求,不上传文件)
	 * @param hasCtx
	 * @param ctx
	 * @param parameterNames
	 * @param datas
	 * @throws IOException
	 */
	 public void buildParamsPOST(boolean hasCtx,ChannelHandlerContext ctx,String[]parameterNames, 
	    		List<InterfaceHttpData> datas) throws IOException{
	    	int index = 0;
			  for (int i=0;i<info.getClazzs().length;i++) {
				  if(info.getClazzs()[i]==FullHttpRequest.class){
					  if(!(request instanceof FullHttpRequest))
							throw new RequestParamException("无法将内置的HttpRequest对象转换为子类FullHttpRequest");
					  params[index] = request; index++;continue;
				  }else if(info.getClazzs()[i]==HttpRequest.class){
					  params[index] = request; index++;continue;
				  }else if(info.getClazzs()[i]==FullHttpResponse.class){
					   params[index] = response;index++; continue;
				   }else if(info.getClazzs()[i]==HttpResponse.class){
					   params[index] = response;index++; continue;
				   }
				   else if(info.getClazzs()[i]==ChannelHandlerContext.class){
					  params[index] = ctx;
					  hasCtx = true;
					  index++;continue;
				  }
	           	 for(InterfaceHttpData data:datas){
	           		 if(data instanceof Attribute){
			            	 Attribute att = (Attribute)data;
	       	    	  //定义特殊的参数类型
	       	    	  if(info.getClazzs()[i]==FileUpload.class){
							  throw new RequestParamException("方法接口要求有上传文件,普通上传参数不接受上传文件类型:"+parameterNames[i]);
						  }else
						   //定义一般类型
						  if(parameterNames[i].equals(att.getName())){
							  try{
								//如果是单独参数
			                      if(!info.getClazzs()[i].isArray()&&!RefletClazzUtil.isExistInterface(info.getClazzs()[i], "java.util.Collection")){
			                    	   Object object = TypeUtils.cast(att.getValue(), info.getClazzs()[i], ParserConfig.getGlobalInstance());  
			    					   params[index] = object;
			    					   index++;break;
			                      }else
			                    	//定义集合参数类型  
			                    	if(info.getClazzs()[i].isArray()||RefletClazzUtil.isExistInterface(info.getClazzs()[i], "java.util.Collection")){
			                    	   Object object = JSONObject.toJavaObject(JSON.parseArray("["+att.getValue().toString()+"]"), info.getClazzs()[i]);  
			     					   params[index] = object;
			     					   index++;break;
			                      }   
							  }catch(Exception e){
								 throw new RequestParamException("参数形式设置错误(提示:如果是数组或集合使用xx,xx,x...)"); 
							  }
							  
					}else {
						params[index] = null;
						if(i==info.getClazzs().length-1){
							index++;	
						}
					}	    
		           }    
				}
		                 
		     }//while 结束 
			 //System.out.println(Arrays.toString(params)+"  POST"); 
	    }
	  /**
	   * 最终的http_response终端输出
	   * @param ctx
	   * @throws IllegalAccessException
	   * @throws IllegalArgumentException
	   * @throws InvocationTargetException
	   */
	  public void finalreturn(ChannelHandlerContext ctx) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		  Object result = null; 
		  if(info.isSpringBean()){
			  CallCapital callCapital = (CallAbstract)SpringBeanUtil.getApplicationContext().getBean(info.getBeanName());
			  result = callCapital.callback(info.getMethod(), params);
		}else{
			 result = info.getMethod().invoke(ServletContainer.getSingletoBean(info.getBeanName()), params);
		}
		  if(!hasCtx){
				//如果执行类的执行方法中没有返回值则调用以下代码
			 if(result!=null){
				 //调用帮助类解决输出问题,将DataByteTrans接口允许的类转化为ByteBuf
				 ResponseHelper.responserWriteObject(response,this.config.getDataByteTrans(), result);
			 }  
			     //使用ChannelHandlerContext ctx返回输出
				 ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
			}
	  }
} //label:类结束
