package org.hardy.netty.tcp2.message;

import org.hardy.netty.tcp2.Message;

public class Response extends Message{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4715950097244111451L;
    
	public Response() {
		super.setType(MessageType.RESPONSE.name());
	}
    //回复的对象内容
	private Object  content;
//	//回复的子类别
//	private int catalog ;

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

//	public int getCatalog() {
//		return catalog;
//	}
//
//	public void setCatalog(int catalog) {
//		this.catalog = catalog;
//	}
	
	

}
