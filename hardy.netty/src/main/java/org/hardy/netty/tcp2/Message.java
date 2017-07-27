package org.hardy.netty.tcp2;

import java.io.Serializable;

import org.hardy.netty.tcp2.message.MessageType;

import com.alibaba.fastjson.JSONObject;

import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class Message implements Serializable{
	 
      /**
	 * 
	 */
	private static final long serialVersionUID = 2964684584607887162L;

	  protected String type;
	  
	  protected MessageType typeEnum;
        
      protected String clientId;
      
      protected boolean closeClient = false;
      

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
		this.typeEnum = MessageType.getMessageType(type);
	}

	
	
	

	public boolean isCloseClient() {
		return closeClient;
	}

	public void setCloseClient(boolean closeClient) {
		this.closeClient = closeClient;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public MessageType getTypeEnum() {
		return typeEnum;
	}

	@Override
	public String toString() {
		return "Message [type=" + type + ", typeEnum=" + typeEnum + ", clientId=" + clientId + ", closeClient="
				+ closeClient + "]";
	}

   public String toJsonString(){
	       return JSONObject.toJSONString(this);
   }
   
   public TextWebSocketFrame toTextWebSocketFrameJson(){
       return new TextWebSocketFrame(this.toJsonString());
}
	      
      
      
}
