package org.hardy.netty.http;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hardy.statics.exception.RequestParamException;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.FileUpload;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.InterfaceHttpData.HttpDataType;

/**
	 * HTTP请求参数解析器, 支持GET, POST
	 * Created by whf on 12/23/15.
	 */
	public class RequestParser {
	 
	    /**
	     * 解析请求参数
	     * @return 包含所有请求参数的键值对, 如果没有参数, 则返回空Map
	     *
	     * @throws BaseCheckedException
	     * @throws IOException
	     */
	    public static HttpRequestInfo parse(FullHttpRequest fullReq) throws IOException {
	    	    HttpRequestInfo info = new HttpRequestInfo();
	    	    info.setUri(getUri(fullReq));
	    	    info.setMethod(fullReq.method());
	        Map<String, Object> parmMap = new HashMap<>();

	        if (HttpMethod.GET == info.getMethod()) {
	            // 是GET请求
	            QueryStringDecoder decoder = new QueryStringDecoder(fullReq.uri());
	            /**
	             *  jdk 1.8
	             */
//	            decoder.parameters().entrySet().forEach( entry -> {
//	                // entry.getValue()是一个List, 只取第一个元素
//	                parmMap.put(entry.getKey(), entry.getValue().get(0));
//	            });
	            java.util.Set <Entry<String, List<String>>> set = decoder.parameters().entrySet();
	            for(Entry<String, List<String>> ens:set){
	            	parmMap.put(ens.getKey(), ens.getValue().get(0)); 
	            }
	        } else if (HttpMethod.POST == info.getMethod()) {
	            // 是POST请求
	            HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(fullReq);
	            decoder.offer(fullReq);
	            List<InterfaceHttpData> parmList = decoder.getBodyHttpDatas();
	            for (InterfaceHttpData parm : parmList) { 
	            	if(parm instanceof Attribute){
	             	   Attribute data = (Attribute) parm;
	                   parmMap.put(data.getName(), data.getValue());
	                }
	            	if(parm.getHttpDataType() == HttpDataType.FileUpload) {
	            		FileUpload fileUpload = (FileUpload) parm;            		
	            		if(fileUpload.isCompleted()) {
	            			parmMap.put(parm.getName(),fileUpload);
	            			//保存到磁盘
//	            			StringBuffer fileNameBuf = new StringBuffer(); 
//	            			fileNameBuf.append("d:\\test111.jpg");
//	            			fileUpload.renameTo(new File(fileNameBuf.toString()));
	            		}
	            	}
	            }
	        } else {
	            // 不支持其它方法
	            throw new RequestParamException("support the method GET or POST");
	        }
             info.setParmMap(parmMap);
             
	        return info;
	    }
	    /**
	     * 解析请求参数
	     * @param req
	     * @return
	     * @throws IOException
	     */
	    public static HttpRequestInfo parse(HttpRequest req) throws IOException {
    	    HttpRequestInfo info = new HttpRequestInfo();
    	    info.setUri(getUri(req));
    	    info.setMethod(req.method());
        Map<String,Object> parmMap = new HashMap<>();
        if (HttpMethod.GET == info.getMethod()) {
            // 是GET请求
            QueryStringDecoder decoder = new QueryStringDecoder(req.uri());
            java.util.Set <Entry<String, List<String>>> set = decoder.parameters().entrySet();
            for(Entry<String, List<String>> ens:set){
            	parmMap.put(ens.getKey(), ens.getValue().get(0)); 
            }
        } else if (HttpMethod.POST == info.getMethod()) {
            // 是POST请求  PS:执行者一句会直接将文件传输到服务器  不知道为什么????
        	HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(req);
            if(decoder!=null){
                List<InterfaceHttpData> parmList = decoder.getBodyHttpDatas();
                for (InterfaceHttpData parm : parmList) {
                   if(parm instanceof Attribute){
                	   Attribute data = (Attribute) parm;
                       parmMap.put(data.getName(), data.getValue());
                   }
                   if(parm.getHttpDataType() == HttpDataType.FileUpload) {
               		FileUpload fileUpload = (FileUpload) parm;
               		if(fileUpload.isCompleted()) {
               			parmMap.put(parm.getName(),fileUpload);
               			//保存到磁盘
//               			StringBuffer fileNameBuf = new StringBuffer(); 
//               			fileNameBuf.append(DiskFileUpload.baseDirectory).append(fileName);
//               			fileUpload.renameTo(new File(fileNameBuf.toString()));
               		}
               	}
                }
            }
     
        } else {
            // 不支持其它方法
            throw new RequestParamException("support the method GET or POST");
        }
         info.setParmMap(parmMap);
         
        return info;
    }
	    /**
	     * 处理FullHttpRequest对象获取uri
	     * @param request
	     * @return
	     */
	    public static String getUri(FullHttpRequest request){
	    	      String uri = request.uri();
	    	      int flag = uri.indexOf("?");
	    	      if(flag!=-1) uri=uri.substring(0, flag);
	    	      return uri;
	    }
	    
	    /**
	     * 处理HttpRequest对象获取uri
	     * @param request
	     * @return
	     */
	    public static String getUri(HttpRequest request){
  	      String uri = request.uri();
  	      int flag = uri.indexOf("?");
  	      if(flag!=-1) uri=uri.substring(0, flag);
  	      return uri;
  }
	    
	    /**
		 * 获取http协议的Content-Type
		 * @param request
		 * @return
		 */
		public static String getContentType(HttpRequest request){
			String typeStr = "NU_KOWN";
			if(request.headers().get("Content-Type")!=null){
				typeStr = request.headers().get("Content-Type").toString();
				 String[] list = typeStr.split(";");
				 return list[0];
			}
			return typeStr;
			
			
		}    
}
 
