package org.hardy.netty.tcp2;

import org.hardy.netty.tcp2.handler.MessageProcessing;

public class Configuration {
     /**
      * 超过改时间，自动关闭连接
      * 大于0时启用
      */
     private static long timeOut = 0;
     
 	public static final String JavaSerialize = "JavaSerialize";
	
 	public static final String Marshalling = "Marshalling";
 	
 	private  String decoderType = JavaSerialize;
  
 	private MessageProcessing messageProcessing;
 	
 	private int SO_BACKLOG = 1024;
 	 	
 	private int maxObjectSize = 1024;
 	 
 	  private int port = 8080;

	public static long getTimeOut() {
		return Configuration.timeOut;
	}

	public static void setTimeOut(long timeOut) {
		Configuration.timeOut = timeOut;
	}

	public String getDecoderType() {
		return decoderType;
	}

	public Configuration setDecoderType(String decoderType) {
		this.decoderType = decoderType;
		return this;
	}

	public MessageProcessing getMessageProcessing() {
		return messageProcessing;
	}

	public Configuration setMessageProcessing(MessageProcessing messageProcessing) {
		this.messageProcessing = messageProcessing;
		return this;
	}


	public int getMaxObjectSize() {
		return maxObjectSize;
	}

	public Configuration setMaxObjectSize(int maxObjectSize) {
		this.maxObjectSize = maxObjectSize;
		return this;
	}

	public int getPort() {
		return port;
	}

	public Configuration setPort(int port) {
		this.port = port;
		return this;
	}

	public int SO_BACKLOG() {
		return SO_BACKLOG;
	}

	public Configuration SO_BACKLOG(int sO_BACKLOG) {
		this.SO_BACKLOG = sO_BACKLOG;
		return this;
	}
	

} 
