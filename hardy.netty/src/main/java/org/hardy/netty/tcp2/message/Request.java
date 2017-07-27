package org.hardy.netty.tcp2.message;

import org.hardy.netty.tcp2.Message;

public class Request extends Message{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    
	
	
	public Request() {
		 super.setType(MessageType.REQUEST.name());
	}

	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
