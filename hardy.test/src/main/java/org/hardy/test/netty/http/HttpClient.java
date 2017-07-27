package org.hardy.test.netty.http;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.hardy.netty.http.client.HttpRequest;

 

public class HttpClient {
//      public static void main(String[] args) throws IOException {
//		 Map<String, Object> map = new HashMap<>();
//		 map.put("ggs", new String[]{"a","b","c"});
//		 map.put("name", "song");
//		 map.put("password", "123");
//		 String reString = HttpRequest.sendPost("http://localhost:8888/json1",map);
//		 System.out.println(reString);
//	}
	
//	 public static void main(String[] args) throws IOException {
//		 Map<String, Object> map = new HashMap<>();
//		 map.put("ggs", new String[]{"a","b","c"});
//		 map.put("name", "song");
//		 map.put("password", "123");
//		 String reString = HttpRequest.sendGet("http://localhost:8888/html1",map);
//		 System.out.println(reString);
//	}
	//"D:\\虚拟系统\\window xp\\DNGS_WINXP_RXB_1201.iso"
	 public static void main(String[] args) throws IOException {
		 Map<String, Object> map = new HashMap<>();
		 map.put("name", "window");
		 map.put("age", "xp");
		 map.put("file",new File("D:\\虚拟系统\\window xp\\DNGS_WINXP_RXB_1201.iso"));
		 String reString = HttpRequest.sendFileUpload("http://localhost:8888/upload1",map);
		 System.out.println(reString);
	}
}
