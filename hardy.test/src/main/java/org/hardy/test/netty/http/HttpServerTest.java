package org.hardy.test.netty.http;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.hardy.netty.http.AutoWaveRequestMapping;
import org.hardy.netty.http.HttpServer;
import org.hardy.netty.http.RequestMapping;
import org.hardy.netty.http.RequestMethod;
import org.hardy.netty.http.ServletContainer;
import org.hardy.netty.http.ServletContainer.UrlMethodInfo;
import org.hardy.netty.http.help.ResponseHelper;
import org.hardy.statics.web.ContentType;

import com.alibaba.fastjson.JSONObject;
import com.song.tool.image.ImageUtil;
import com.song.tool.text.io.StringReaderUtil;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.multipart.DiskFileUpload;
import io.netty.util.CharsetUtil;


@RequestMapping
public class HttpServerTest{
	 
	public static void main(String[] args) throws InterruptedException, ClassNotFoundException, IOException {
		HttpServer server = new HttpServer();
		server.getConfig().setAggregator(10*1024*1024);
//		server.getConfig().setUploadTempleDirectory("/Users/song/Desktop");
		 new AutoWaveRequestMapping(new String[]{"org.hardy.test.netty.http"});
		 Map<String, UrlMethodInfo> map=ServletContainer.getServletMap();
		 for(String s:map.keySet()){
			 System.out.println(s); 
		 }
		 System.out.println(DiskFileUpload.baseDirectory);
		server.run(8888);
	 
	}
	
	@RequestMapping(value="xml",requestMethod=RequestMethod.ALL,resultType=ContentType.TEXT)
	public  String testxml(Integer ab){
		System.out.println(ab); System.out.println(ab.getClass().getName());
		return StringReaderUtil.getUTF8Text("/Users/song/Desktop/test.txt");
	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value="/html2",resultType=ContentType.HTML,requestMethod=RequestMethod.ALL)
	public  String testHtml(FullHttpResponse response){
		return ResponseHelper.presentHtml("C:/Users/09/Desktop/upload.html", null, response);
	}
	int i = 0;
	@RequestMapping(value="/html1",resultType=ContentType.HTML,requestMethod=RequestMethod.ALL)
	public  void test1(String newTitle,String name,String password,List<String> ggs,FullHttpResponse response){
//		if(i==0)
//			try {
////				i++;
////				System.out.println("阻塞................");
////				Thread.sleep(15000);
////				System.out.println("阻塞结束................");
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		System.out.println("执行了=="+name);
		String result = "<tr><td><h2>"+"name="+name+"<br>"+
				"password="+password+"<br>"
				+"ggs="+ggs.toString()+"</h2></td></tr>";
		ResponseHelper.responserWriteCharquence(response, result, CharsetUtil.UTF_8);
	}
	 
	
	@RequestMapping(value="/upload1",resultType=ContentType.HTML,requestMethod=RequestMethod.UPLOAD)
	public  String upload1(File file,File file2,String name,String age,HttpRequest request,FullHttpResponse response){
		return "上传成功   "+name+" === "+age+" path==="+ file.getAbsolutePath()+" path2==="+file2.getAbsolutePath();
	}
	
	@RequestMapping(value="/upload2",resultType=ContentType.HTML,requestMethod=RequestMethod.UPLOAD)
	public  String upload2(String name,String age,HttpRequest request,FullHttpResponse response){
		System.out.println(DiskFileUpload.baseDirectory);
		return "上传成功"+name+" === "+age;
	}
	
	@RequestMapping(value="imag1",resultType=ContentType.HTML)	
	public  String test2(FullHttpResponse response,StringBuilder sbuilder){
		 sbuilder = new StringBuilder();
		 try{
			 String b64 = ImageUtil.imageToString64("C:\\Users\\songs\\Desktop\\0.jpg");
			 sbuilder.append("<tr><td><h2><image src='data:image/png;base64,"+b64+"'></h2></td></tr>"); 
		 }catch(Exception e){
			 e.printStackTrace();
			 return "<tr><td><h2>"+e.getCause()+"</h2></td></tr>";
		 }
		
		 return sbuilder.toString();
	}
	@RequestMapping(value="imag2",resultType=ContentType.JEPG)	
	public  byte[] test3(HttpRequest request,FullHttpResponse response){
		return ResponseHelper.presentImage("C:\\Users\\songs\\Desktop\\0.jpg", request, response);   
	 
	}

	@RequestMapping(value="/json1",resultType=ContentType.TEXT,requestMethod=RequestMethod.ALL)
	public  Object test4(String[] aasd,String name,String password,HttpRequest request,FullHttpResponse response){
		if(aasd==null) return "error"; 
		Map<String, Object> map = new HashMap<>(); 
		map.put("ggs", aasd);
		map.put("name", name);
		map.put("password", password);
		 StringBuilder total = new StringBuilder(); 
		for(String i:aasd){
			total.append("$$+"+i+"##-");
		}
		map.put("total", total);
		 return JSONObject.toJSONString(map);
	}

	@RequestMapping(value="/json2",resultType=ContentType.JSON)
	public  String test4(String name,String password,HttpRequest request,FullHttpResponse response){
		Map<String, Object> map = new HashMap<>(); 
		map.put("name", name);
		map.put("password", password);
		return JSONObject.toJSONString(map);
//		ByteBuf buf = Unpooled.copiedBuffer(JSONObject.toJSONString(map),CharsetUtil.UTF_8);
//		ResponseHelper.responserWriteByteArray(response, buf);
//		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}

	@RequestMapping(value="/redirect")
	public  void test5(ChannelHandlerContext ctx){
		ResponseHelper.sendRedircet(ctx,"http://123.15.58.210");
	}
	
	@RequestMapping(value="/file",resultType=ContentType.HTML)
	public  void test5(String path,ChannelHandlerContext ctx,HttpRequest request,FullHttpResponse response){
		ResponseHelper.downLoadFile(path,ctx, request,"download");
	}


	@Override
	public String toString() {
		return "HttpServerTest [getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

	 
	
	
}

