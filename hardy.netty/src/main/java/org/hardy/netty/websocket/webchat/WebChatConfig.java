package org.hardy.netty.websocket.webchat;

import org.hardy.netty.websocket.webchat.simplText.DefaultTextWebSocketMetier;
import org.hardy.netty.websocket.webchat.simplText.TextWebSocketMetier;

public class WebChatConfig {
     private int port = 8080;   
	 private TextWebSocketMetier textMetier = new DefaultTextWebSocketMetier();
	 private String webSocketAddres = "ws://localhost:8080/websocket";
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public TextWebSocketMetier getTextMetier() {
		return textMetier;
	}
	public void setTextMetier(TextWebSocketMetier textMetier) {
		this.textMetier = textMetier;
	}
	public String getWebSocketAddres() {
		return webSocketAddres;
	}
	public void setWebSocketAddres(String webSocketAddres) {
		this.webSocketAddres = webSocketAddres;
	} 
	 
}
