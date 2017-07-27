package org.hardy.netty.tcp2.message;

import org.hardy.netty.tcp2.Message;

public class Comunication extends Message{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    public Comunication(){
    	   super.setType(MessageType.COMMUNICATION.name());
    }
    
	private String target;
	
	private String content;

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
}
