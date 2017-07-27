package org.hardy.util.io.raf.special1;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import com.song.tool.md5.MD5Util;

/**
 * 这个类记录RafSp1每次读取文件后的文件状态
 * 
 * @author 09
 *
 */
public class ConfigMessage implements Serializable{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 要读取的文件
     */
	 private File file;
	 /**
	  * 该文件完整状态下的md5
	  */
	 private String fileMd5;
	 /**
	  * 保存位置
	  */
	 private String savePath;
	 /**
	  * 读取开始坐标
	  */
	 private long start;
	 /**
	  * 读取结束坐标
	  */
	 private long end;
	 /**
	  * 完整文件长度
	  */
	 private long fileLength;
	 /**
	  * 缓冲区大小
	  */
	 private int byteLength = 1024;
	 /**
	  * 当前读取的坐标,此处的currentIndex是提供给服务端保存数据时使用的
	  * 在客户端并不是用他
	  */
	 private long currentIndex;
	 /**
	  * 构造方法,设置过file文件后,会直接设置fileLength和fileMd5的属性
	  * @param file
	  * @throws IOException
	  */
	public ConfigMessage(File file) throws IOException{
		this.file = file;
		this.fileLength = file.length();
		this.fileMd5 = MD5Util.getFileMD5String(this.file);
	}
	/**
	 * 克隆一个新的ConfigMessage对象,拷贝属性(除了start,end,currentIndex)
	 * @return
	 */
	public ConfigMessage cloneNew(){
		ConfigMessage newCof = null;
		try {
			newCof = new ConfigMessage(this.file);
			newCof.setSavePath(this.getSavePath());
			newCof.setByteLength(this.getByteLength());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newCof;
	}
    /**
     * 获取传输给服务端的当前坐标
     * @return
     */
	public long getCurrentIndex() {
		return currentIndex;
	}
	/**
	 * 设置传输给服务端的当前坐标
	 * @param currentIndex
	 */
	public void setCurrentIndex(long currentIndex) {
		this.currentIndex = currentIndex;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getFileMd5() {
		return fileMd5;
	}
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	public long getStart() {
		return start;
	}
	public void setStart(long start) {
		if(start<0)this.start = 0 ;
		else this.start = start;
	}
	public long getEnd() {
		return end;
	}
	public void setEnd(long end) {
		if(end>this.fileLength)this.end = this.fileLength;
		else this.end = end;
	}
	public long getFileLength() {
		return fileLength;
	}
	public int getByteLength() {
		return byteLength;
	}
	public void setByteLength(int byteLength) {
		this.byteLength = byteLength;
	}
	public void setFileMd5(String fileMd5) {
		this.fileMd5 = fileMd5;
	}
	@Override
	public String toString() {
		return "ConfigMessage [file=" + file + ", fileMd5=" + fileMd5
				+ ", savePath=" + savePath + ", start=" + start + ", end="
				+ end + ", fileLength=" + fileLength + ", byteLength="
				+ byteLength + ", currentIndex=" + currentIndex + "]";
	}
	 
	 
}
