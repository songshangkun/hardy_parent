package org.hardy.netty.tcp2.message;

import org.hardy.netty.tcp2.Message;

public class Interval extends Message{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1180192790932777558L;
    
	public Interval() {
		super.setType(MessageType.INTERVAL.name());
	}

		
	
}
