package org.hardy.test.netty.tcp;

import org.hardy.netty.tcp2.ConfigClient;
import org.hardy.netty.tcp2.TcpClient;
import org.hardy.netty.tcp2.handler.DefaultClientMessageProcessing;
import org.hardy.netty.tcp2.message.Comunication;
import org.hardy.netty.tcp2.message.Login;
import org.hardy.netty.tcp2.message.Request;

public class TestClient1 {
   public static void main(String[] args) throws InterruptedException {
	   
    ConfigClient configClient = new ConfigClient();
    configClient.setPrincipal("song1");
    configClient.setMessageProcessing(new DefaultClientMessageProcessing());
//	configClient.setW_timeOut(1000) ;
    TcpClient client = new TcpClient(configClient);
	  
	 
	   client.attend();
	   
	   
	   Login login = new Login();
	   login.setUsername("song1");
	   login.setPassword("123");
	   client.sendMessage(login);
	   
	   Request request = new Request();
	   request.setClientId("song1");
	   request.setContent("ce shi yi xia 1");
	   client.sendMessage(request);
   
	   Comunication cum = new Comunication();
	   cum.setClientId("song1");
	   cum.setTarget("song2");
	   cum.setContent("song1 -> song2 :jiao ge peng you friend 1");
	   client.sendMessage(cum);
	         
	   }
}
