package org.hardy.netty.http1_1.composant;

import java.io.File;
import java.io.IOException;

import io.netty.handler.codec.http.multipart.FileUpload;

/**
 * 该接口帮助终端调用类完成FileUpload的保存操作
 * @author 09
 *
 */
public interface FileUploadHelper {
	/**
	 * 方法表明如何处理这个FileUpload类型文件
	 * @param fileupload
	 * @throws IOException
	 */
	public File  rename(FileUpload fileupload)throws IOException; 
}
