package org.hardy.netty.http;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hardy.statics.exception.NotSupportException;

import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.FileUpload;
import io.netty.handler.codec.http.multipart.HttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;

/**
	 * HTTP请求参数解析器, 支持GET, POST
	 * Created by whf on 12/23/15.
	 */
	public class RequestHttpContentParser {
		
		private static final HttpDataFactory factory = new DefaultHttpDataFactory(DefaultHttpDataFactory.MAXSIZE);
		private HttpPostRequestDecoder decoder;
		HttpRequest request = null;
	
	    /**
	     * 解析请求参数
	     * @param req
	     * @return
	     * @throws IOException
	     */
	    public  HttpRequestInfo parse(HttpRequest req) throws IOException {
    	    HttpRequestInfo info = new HttpRequestInfo();
    	    info.setUri(RequestParser.getUri(req));
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
        	HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(factory,  this.request); 
 	        if (decoder != null) {
 	            if (this.request instanceof HttpContent) {
 	                HttpContent chunk = (HttpContent) this.request;
 	                    decoder.offer(chunk);
 	                readHttpDataChunkByChunk(parmMap);
 	                if (chunk instanceof LastHttpContent) {    
 	                    reset();
 	                }
 	            }
 	        }  
        } else {
            // 不支持其它方法
            throw new NotSupportException("support the method GET or POST");
        }
         info.setParmMap(parmMap);
         
        return info;
    }
	    
	    /**
	     * Example of reading request by chunk and getting values from chunk to chunk
		 * @throws IOException 
	     */
	    private void readHttpDataChunkByChunk(Map<String, Object> paramMap) throws IOException {
	    	   List<InterfaceHttpData> datas = decoder.getBodyHttpDatas();
	    	   for (InterfaceHttpData data : datas) {
	           	if(data != null&&data instanceof  Attribute) {
	           		Attribute attribute = (Attribute) data;
	           		paramMap.put(attribute.getName(), attribute.getValue()) ;
	           	}
	           }
	            while (decoder.hasNext()) {
	                InterfaceHttpData data = decoder.next();
	                if (data != null&&data  instanceof FileUpload) {  
	                		FileUpload fileUpload = (FileUpload)data;
	                		paramMap.put(fileUpload.getName(), fileUpload) ;
	                }
	            }
	    }

	   /**
	    * 释放资源
	    */
	    private  void reset() {
	        request = null;
	        // destroy the decoder to release all resources
	        decoder.destroy();
	        decoder = null;
	    }
}
 
