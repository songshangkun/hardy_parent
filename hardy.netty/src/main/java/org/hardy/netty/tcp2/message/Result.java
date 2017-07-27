package org.hardy.netty.tcp2.message;

import org.hardy.netty.tcp2.Message;

public class Result extends Message{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3180275462201047996L;
	
	
	public Result() {
		super.setType("RESULT");
		// TODO Auto-generated constructor stub
	}

	private boolean isSucces;
	
	private String message;

	public boolean isSucces() {
		return isSucces;
	}

	public void setSucces(boolean isSucces) {
		this.isSucces = isSucces;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
