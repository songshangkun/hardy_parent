package org.hardy.netty.http.upload;

import java.io.IOException;
import java.util.Map;

import io.netty.handler.codec.http.multipart.FileUpload;

/**
 * 专门用于FileUploadRename的接口,可以重命名上传文件或上传地址
 * @author songshangkun
 *
 */
public interface FileUploadRename {
     /**
      * 重命名或重定向文件地址
      * @param normalParams
      * @param file
      * @throws IOException
      */
	  void rename(Map<String, String> normalParams,FileUpload file)throws IOException;
}
