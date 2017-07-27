package org.hardy.netty.http.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import io.netty.handler.codec.http.HttpResponseStatus;


public class HttpRequest {
	
	private static final Logger LOG = Logger.getLogger(HttpRequest.class);
	/**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
	 * @throws IOException 
     */
    public static String sendGet(String url, String param) throws IOException {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
            String urlNameString = url ;
            if(param!=null&&!param.trim().equals(""))urlNameString = urlNameString+"?" + param;
            LOG.info("request target:"+urlNameString);
            LOG.info("request method:"+"GET");
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
 //           Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
//            for (String key : map.keySet()) {
//                System.out.println(key + "--->" + map.get(key));
//            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
            	result.append(line);
            }
            LOG.info("request result:"+result.toString());
        }
        // 使用finally块来关闭输入流
        finally {
                if (in != null) {
                    in.close();
                }
        }
        return result.toString();
    }
    
    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     * @throws IOException 
     */
    public static String sendPost(String url, String param) throws IOException {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
        	LOG.info("request target:"+url);
        	LOG.info("request method:"+"POST");
        	LOG.info("POST PARAMS:"+param);
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            LOG.info("request result:"+result.toString());
        }
        //使用finally块来关闭输出流、输入流
        finally{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
        }
        return result.toString();
    }    
    /**
     * 发送HTTP的GET请求
     * @param url
     * @param map
     * @return
     * @throws IOException 
     */
    public static String sendGet(String url, Map<String, Object> map) throws IOException{
    	           StringBuilder sBuilder = new StringBuilder();
    	           for(String key:map.keySet()){
    	        	       sBuilder.append(key).append("=");
    	        	       if(map.get(key).getClass().isArray()){
    	        	    	    StringBuilder sb = new StringBuilder(ArrayUtils.toString(map.get(key)));
    	        	    	    sb.deleteCharAt(0).deleteCharAt(sb.length()-1);
    	        	    	   sBuilder.append(sb.toString());
    	        	       }else{
    	        	        sBuilder.append(map.get(key));
    	        	       }
    	        	       sBuilder.append("&");
    	           }
    	           sBuilder.deleteCharAt(sBuilder.length()-1);
    				return sendGet(url, sBuilder.toString());
    }
    /**
     * 发送HTTP的POST请求
     * @param url
     * @param map
     * @return
     * @throws IOException 
     */
    public static String sendPost(String url, Map<String, Object> map) throws IOException{
        StringBuilder sBuilder = new StringBuilder();
        for(String key:map.keySet()){
     	       sBuilder.append(key).append("=");
     	       if(map.get(key).getClass().isArray()){
     	    	    StringBuilder sb = new StringBuilder(ArrayUtils.toString(map.get(key)));
     	    	    sb.deleteCharAt(0).deleteCharAt(sb.length()-1);
     	    	   sBuilder.append(sb.toString());
     	       }else{
     	        sBuilder.append(map.get(key));
     	       }
     	       sBuilder.append("&");
        }
        sBuilder.deleteCharAt(sBuilder.length()-1);
			return sendPost(url, sBuilder.toString());
    }
    
    /**
     * 上传文件和参数
     * @param url
     * @param map  如果上传文件则map必须有命名与接口对应的File类型格式.key:接口中文件类型命名,value:对应文件的File对象
     * @return
     * @throws IOException
     */
    public static String sendFileUpload(String url, Map<String, Object> map) throws IOException{
    	 LOG.info("request target:" + url);  
    	 if(map!=null)LOG.info("request params:" + map.toString());  
         CloseableHttpClient httpclient = HttpClients.createDefault();  
         try {  
             HttpPost httppost = new HttpPost(url);
             MultipartEntityBuilder meb = MultipartEntityBuilder.create();
             for(String key:map.keySet()){ 	 
				Object contentBody = map.get(key);
            	 if(contentBody instanceof File){
            		 FileBody fileBody = new FileBody((File)contentBody);
            		 meb.addPart(key, fileBody);
            	 }else if(contentBody instanceof CharSequence){
            		 StringBody stringBody = new StringBody(contentBody.toString(),  
            	             ContentType.TEXT_PLAIN);  
            		 meb.addPart(key, stringBody);
            	 }
             }
             HttpEntity reqEntity = meb.build();  
             httppost.setEntity(reqEntity);  
             LOG.info("executing request " + httppost.getRequestLine());  
             CloseableHttpResponse response = httpclient.execute(httppost);  
             try {   
            	 LOG.info(response.getStatusLine());  
                 HttpEntity resEntity = response.getEntity();  
                 if (resEntity != null) {  
                	 LOG.info("Response content length: "  
                             + resEntity.getContentLength());  
                	 String result = EntityUtils.toString(resEntity);
                     LOG.info("return code:"+result);
                     return result;
                 }  
                 EntityUtils.consume(resEntity);  
             } finally {  
                 response.close();  
             }  
         } finally {  
             httpclient.close();  
         }  
         return HttpResponseStatus.EXPECTATION_FAILED.toString();
    } 
    
    
    
    
    /**
     * 上传文件和参数
     * @param url
     * @param map  如果上传文件则map必须有命名与接口对应的File类型格式.key:接口中文件类型命名,value:对应文件的File对象
     * @return
     * @throws IOException
     */
//    public String sendFileUpload(String url, Map<String, Object> map) throws IOException{
//    	String[] args = new String[] { "D:\\ooo.docx" };  
//         if (args.length != 1) {  
//             System.out.println("File path not given");  
//             System.exit(1);  
//         }  
//         CloseableHttpClient httpclient = HttpClients.createDefault();  
//         try {  
//             HttpPost httppost = new HttpPost(  
//                     "http://localhost:8888/upload1");  
//   
//             FileBody bin = new FileBody(new File(args[0]));  
//             StringBody comment = new StringBody("aaasss",  
//             ContentType.TEXT_PLAIN);  
//             StringBody comment2 = new StringBody("vvvbbb",  
//                     ContentType.TEXT_PLAIN);
//             HttpEntity reqEntity = MultipartEntityBuilder.create()  
//                     .addPart("file", bin).addPart("name", comment).addPart("age", comment2).build();  
//   
//             httppost.setEntity(reqEntity);  
//   
//             System.out.println("executing request " + httppost.getRequestLine());  
//             CloseableHttpResponse response = httpclient.execute(httppost);  
//             try {   
//                 System.out.println(response.getStatusLine());  
//                 HttpEntity resEntity = response.getEntity();  
//                 if (resEntity != null) {  
//                     System.out.println("Response content length: "  
//                             + resEntity.getContentLength());  
//                     //System.out.println(EntityUtils.toString(resEntity)); 
//                     return EntityUtils.toString(resEntity);
//                 }  
//                 EntityUtils.consume(resEntity);  
//             } finally {  
//                 response.close();  
//             }  
//         } finally {  
//             httpclient.close();  
//         }  
//         return "error";
//    } 
    
}
