package org.hardy.netty.http;

import io.netty.handler.codec.http.multipart.DiskFileUpload;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.hardy.netty.http.help.NormalDataByteTrans;
import org.hardy.netty.http.help.ResponseHelper;
import org.hardy.netty.http.upload.DefaultFileTemplat;
import org.hardy.netty.http.upload.FileUploadRename;
import org.hardy.netty.http1_1.composant.DefaultFileUploadHelper;
import org.hardy.netty.http1_1.composant.FileUploadHelper;
import org.hardy.statics.exception.ParamNotNullException;

/**
 * 该类主要用于设置HttpServer的一些参数。
 * 每个参数都有默认值方便开发
 * @author sundyn
 *
 */
public class HttpConfig {
    /**
     * 定义主机地址，这里其实就是localhost，或运行主机的ip
     */
	private String host = "localhost";
	/**
	 * 对上传的FileUpload类型文件进行操作的接口
	 * 如不使用此接口无法在终端方法中直接操作FileUpload对象
	 */
	private FileUploadHelper fileuploadHelper = new DefaultFileUploadHelper();
    /**
     * 默认端口
     */
    private int port = 8080;
    /**
     * 字符集
     */
    private   Charset charSet = CharsetUtil.UTF_8;
    /**
     * 定义对象转换接口
     */
    private ResponseHelper.DataByteTrans dataByteTrans = new NormalDataByteTrans();
    /**
     * 设置多个request和response合并的总大小，如果一次性上传文件大于这个数字，将返回badequest
     */
    private int aggregator = 65536;
    /**
     * 过滤掉的url集合
     */
    private List<String> excusion = new ArrayList<String>();
    
    /**
     * 只用于HttpUploadServer的上传文件更名接口
     */
    private FileUploadRename fileUploadRename = new DefaultFileTemplat();
    
	public HttpConfig() {
		DiskFileUpload.baseDirectory =  System.getProperty("java.io.tmpdir");;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		if(host==null) throw new ParamNotNullException(host);
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public Charset getCharSet() {
		return charSet;
	}
	public void setCharSet(Charset charSet) {
		this.charSet = charSet;
	}
	public ResponseHelper.DataByteTrans getDataByteTrans() {
		return dataByteTrans;
	}
	public void setDataByteTrans(ResponseHelper.DataByteTrans dataByteTrans) {
		if(dataByteTrans==null) throw new ParamNotNullException("dataByteTrans");
		this.dataByteTrans = dataByteTrans;
	}
	public int getAggregator() {
		return aggregator;
	}
	public void setAggregator(int aggregator) {
		this.aggregator = aggregator;
	}
	public String getUploadTempleDirectory() {
		return DiskFileUpload.baseDirectory;
	}
	public void setUploadTempleDirectory(String uploadTempleDirectory) {
		if(uploadTempleDirectory==null) throw new ParamNotNullException("uploadTempleDirectory");
		DiskFileUpload.baseDirectory = uploadTempleDirectory;
	}
	
	public FileUploadRename getFileUploadRename() {
		return fileUploadRename;
	}
	/**
	 * 这个接口专用于HttpUploadServer更改上传文件使用,如果为空将发生空指针异常
	 */
	public void setFileUploadRename(FileUploadRename fileUploadRename) {
		if(fileUploadRename==null) throw new ParamNotNullException("fileUploadRename");
		this.fileUploadRename = fileUploadRename;
	}
	/**
	 * 获取排除的url连接
	 * @return
	 */
	public List<String> getExcusion() {
		return excusion;
	}
	public void setExcusion(List<String> excusion) {
		this.excusion = excusion;
	}
	public List<String> addExcusion(String excusionUrl) {
		this.excusion.add(excusionUrl);
		return this.excusion;
	}
	
	
	public FileUploadHelper getFileuploadHelper() {
		return fileuploadHelper;
	}
	public void setFileuploadHelper(FileUploadHelper fileuploadHelper) {
		this.fileuploadHelper = fileuploadHelper;
	}
	@Override
	public String toString() {
		return "HttpConfig [host=" + host + ", fileuploadHelper="
				+ fileuploadHelper + ", port=" + port + ", charSet=" + charSet
				+ ", dataByteTrans=" + dataByteTrans + ", aggregator="
				+ aggregator + ", excusion=" + excusion + ", fileUploadRename="
				+ fileUploadRename + "]";
	}
	 
    
    
}
