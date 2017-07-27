package org.hardy.test.netty.http;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.hardy.netty.http.HttpConfig;
import org.hardy.netty.http.upload.FileUploadRename;
import org.hardy.netty.http.upload.HttpUploadServer;

import io.netty.handler.codec.http.multipart.DiskFileUpload;
import io.netty.handler.codec.http.multipart.FileUpload;

public class UploadServerTest {
	
  public static void main(String[] args) throws Exception {
	  HttpConfig config = new HttpConfig();
	  config.setUploadTempleDirectory("d:\\666\\");
	  config.setAggregator(10*1024*1024);
	  config.setFileUploadRename(new FileUploadRename() {
		
		@Override
		public void rename(Map<String,String> map,FileUpload file) throws IOException {
			System.out.println(map.toString());
			String fileName = file.getFilename();
			System.out.println("原名称:"+fileName);
			String ext = fileName.lastIndexOf(".")==-1?"":fileName.substring(fileName.lastIndexOf("."));
			System.out.println("后缀名称:"+ext);
			file.renameTo(new File(DiskFileUpload.baseDirectory+UUID.randomUUID().toString()+ext));
			
		}
	});
	   HttpUploadServer server = new HttpUploadServer(config);
	   System.out.println(server);
	   server.run(8888);
  }
  
  public static void main22(String[] args) {
	String s = "API_sample_program_license_agreement_ja.txt";
	
	System.out.println(s.substring(39));
	System.out.println(s.lastIndexOf("."));
}
}
