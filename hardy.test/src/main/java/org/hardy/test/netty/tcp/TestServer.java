package org.hardy.test.netty.tcp;

import org.hardy.netty.tcp2.Configuration;
import org.hardy.netty.tcp2.TcpServer;
import org.hardy.netty.tcp2.handler.DefaultServerMessageProcessing;

public class TestServer {
	
	   public static void main(String[] args) throws InterruptedException {
		   Configuration.setTimeOut(1000*20);
		   Configuration configuration = new Configuration();
		   configuration.setMessageProcessing(new DefaultServerMessageProcessing()); 
		     TcpServer tcpServer = new TcpServer(configuration);
		     tcpServer.bind();
	  }
}
