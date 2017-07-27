package org.hardy.netty.http;

import org.apache.commons.lang.ArrayUtils;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;

public enum RequestMethod {
     
	    GET,POST,DELETE,PUT,OPTION,ALL,UPLOAD;
	 
	   public  static  boolean  containsRequestMethod(RequestMethod[] methods,FullHttpRequest request){
		   //获取当前访问的request的方法
		   HttpMethod method = request.method();
		   //1-如果是上传 则RequestMethod必须包含upload方法 且request访问方法也是post 才返回true
		   if("multipart/form-data".equals(RequestParser.getContentType(request))&&
				   method==HttpMethod.POST&&
				   ArrayUtils.contains(methods, RequestMethod.UPLOAD)){
			    return true;
		   }
		   //2-如果不是上传 且如果注解中含有methods数组包含RequestMethod.ALL 返回true
		  if(!"multipart/form-data".equals(RequestParser.getContentType(request))&&
				   ArrayUtils.contains(methods, RequestMethod.ALL)) {
			  return true;  
		   }
		   //3-如果不是上传 循环标记的方法 发现
		  if(!"multipart/form-data".equals(RequestParser.getContentType(request))){
			  for(RequestMethod m:methods){    
				     if(m.name().equalsIgnoreCase(method.name().toString())) {
				    	 	return true; 
				     }
			   }    
		  } 
		  return false;
	   } 
	   
	   public  static  boolean  containsRequestMethod(RequestMethod[] methods,HttpRequest request){
		   //获取当前访问的request的方法
		   HttpMethod method = request.method();
		   //1-如果是上传 则RequestMethod必须包含upload方法 且request访问方法也是post 才返回true
		   if("multipart/form-data".equals(RequestParser.getContentType(request))&&
				   method==HttpMethod.POST&&
				   ArrayUtils.contains(methods, RequestMethod.UPLOAD)){
			    return true;
		   }
		   //2-如果不是上传 且如果注解中含有methods数组包含RequestMethod.ALL 返回true
		  if(!"multipart/form-data".equals(RequestParser.getContentType(request))&&
				   ArrayUtils.contains(methods, RequestMethod.ALL)) {
			  return true;  
		   }
		   //3-如果不是上传 循环标记的方法 发现
		  if(!"multipart/form-data".equals(RequestParser.getContentType(request))){
			  for(RequestMethod m:methods){    
				     if(m.name().equalsIgnoreCase(method.name().toString())) {
				    	 	return true; 
				     }
			   }    
		  } 
		  return false;
	   } 
	   
}
