package org.hardy.util.io.raf;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;



public class RafUtil {
    
	 public static void main(String[] args) {
		 
	}
	 /**
	  * 创建文件
	  * @param file  文件
	  * @param fileLength  文件长度
	  * @param autocreate  如果路径不存在是否自动创建路径
	  * @throws IOException
	  */
	 public static void createFile(File file,long fileLength,boolean autocreate) throws IOException{
		 if(autocreate){
			 if(!file.getParentFile().isDirectory())file.getParentFile().mkdir(); 
		 }
		 RandomAccessFile raf = null;
         try {
			raf = new RandomAccessFile(file, "rw");
			raf.setLength(fileLength);
			raf.close();
			}finally{
				if(raf!=null){
					raf.close();
				}
			} 
	 }
	 
	 
	 
	 
}
