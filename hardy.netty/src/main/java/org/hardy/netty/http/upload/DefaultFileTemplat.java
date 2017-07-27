package org.hardy.netty.http.upload;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import io.netty.handler.codec.http.multipart.DiskFileUpload;
import io.netty.handler.codec.http.multipart.FileUpload;
/**
 * 这个类实现FileUploadRename接口
 * 将文件保存默认临时文件夹,并使用随机不重复文件名称
 * @author songshangkun
 *
 */
public class DefaultFileTemplat implements FileUploadRename{

	@Override
	public void rename(Map<String, String> normalparams,FileUpload file) throws IOException {
		System.out.println(normalparams.toString());
		//保存到磁盘
		StringBuffer fileNameBuf = new StringBuffer(); 
		fileNameBuf.append(DiskFileUpload.baseDirectory).append(file.getFilename());
		file.renameTo(new File(fileNameBuf.toString()));

	}

}
