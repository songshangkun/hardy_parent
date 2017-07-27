package org.hardy.netty.http1_1.composant;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import io.netty.handler.codec.http.multipart.DiskFileUpload;
import io.netty.handler.codec.http.multipart.FileUpload;

/**
 * 默认实现FileUploadHelper接口的实现类
 * 保存文件到硬盘
 * @author 09
 *
 */
public class DefaultFileUploadHelper implements FileUploadHelper{

	@Override
	public File rename(FileUpload fileupload) throws IOException {
		String ext = getExt(fileupload.getFilename());
		File file = new File(DiskFileUpload.baseDirectory+UUID.randomUUID().toString()+ext);
		fileupload.renameTo(file);
		return file;
	}
	/**
	 * 获取文件的后缀名带".",ex:".jpg"
	 * @param orginalName
	 * @return
	 */
	protected String getExt(String orginalName) {
		return  orginalName.lastIndexOf(".")==-1?"":
			orginalName.substring(orginalName.lastIndexOf("."), orginalName.length());
	}
     
    
}
