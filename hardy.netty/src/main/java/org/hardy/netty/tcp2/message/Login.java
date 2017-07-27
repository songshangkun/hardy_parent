package org.hardy.netty.tcp2.message;

import org.hardy.netty.tcp2.Message;

public class Login extends Message{

	/**
	 * 
	 */
	private static final long serialVersionUID = 538793899728861004L;
    
	public Login(){
		 super.setType(MessageType.LOGIN.name());
	}
	
	private String username;
	
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
