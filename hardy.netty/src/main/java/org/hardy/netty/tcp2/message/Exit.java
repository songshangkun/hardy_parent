package org.hardy.netty.tcp2.message;

import org.hardy.netty.tcp2.Message;

public class Exit extends Message{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Exit() {
		 super.setType(MessageType.EXIT.name());
	}
   
	 
}
