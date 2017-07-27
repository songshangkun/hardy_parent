package org.hardy.netty.tcp2;

import org.hardy.netty.tcp.AutorTimeOut;
import org.hardy.netty.tcp.ReadTimeOut;
import org.hardy.netty.tcp.WriteTimeOut;
import org.hardy.netty.tcp2.handler.DefaultInterval;
import org.hardy.netty.tcp2.handler.MessageProcessing;

public class ConfigClient {
private  String decoderType = Configuration.JavaSerialize;
	
	private MessageProcessing messageProcessing ;
	
	private int port = 8080;
	
    private String host = "localhost";
    
    private String principal;
    
    private int maxObjectSize = 1024;
    
    private long r_timeOut = 0;
	  
	private long w_timeOut = 0;
	  
	private long bothTimeOut_r_w = 0;
	  
	private ReadTimeOut readTimeOut;
		 
	private WriteTimeOut writeTimeOut = new DefaultInterval();
		 
	private AutorTimeOut autorTimeOut;

	public String getDecoderType() {
		return decoderType;
	}

	public ConfigClient setDecoderType(String decoderType) {
		this.decoderType = decoderType;
		return this;
	}

	public MessageProcessing getMessageProcessing() {
		return messageProcessing;
	}

	public ConfigClient setMessageProcessing(MessageProcessing messageProcessing) {
		this.messageProcessing = messageProcessing;
		return this;
	}

	public int getPort() {
		return port;
	}

	public ConfigClient setPort(int port) {
		this.port = port;
		return this;
	}

	public String getHost() {
		return host;
	}

	public ConfigClient setHost(String host) {
		this.host = host;
		return this;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public int getMaxObjectSize() {
		return maxObjectSize;
	}

	public ConfigClient setMaxObjectSize(int maxObjectSize) {
		this.maxObjectSize = maxObjectSize;
		return this;
	}

	public long getR_timeOut() {
		return r_timeOut;
	}

	public ConfigClient setR_timeOut(long r_timeOut) {
		this.r_timeOut = r_timeOut;
		return this;
	}

	public long getW_timeOut() {
		return w_timeOut;
	}

	public ConfigClient setW_timeOut(long w_timeOut) {
		this.w_timeOut = w_timeOut;
		return this;
	}

	public long getBothTimeOut_r_w() {
		return bothTimeOut_r_w;
	}

	public ConfigClient setBothTimeOut_r_w(long bothTimeOut_r_w) {
		this.bothTimeOut_r_w = bothTimeOut_r_w;
		return this;
	}

	public ReadTimeOut getReadTimeOut() {
		return readTimeOut;
	}

	public ConfigClient setReadTimeOut(ReadTimeOut readTimeOut) {
		this.readTimeOut = readTimeOut;
		return this;
	}

	public WriteTimeOut getWriteTimeOut() {
		return writeTimeOut;
	}

	public ConfigClient setWriteTimeOut(WriteTimeOut writeTimeOut) {
		this.writeTimeOut = writeTimeOut;
		return this;
	}

	public AutorTimeOut getAutorTimeOut() {
		return autorTimeOut;
	}

	public ConfigClient setAutorTimeOut(AutorTimeOut autorTimeOut) {
		this.autorTimeOut = autorTimeOut;
		return this;
	}
	
	
}
