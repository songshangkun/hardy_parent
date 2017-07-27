package org.hardy.netty.tcp2.handler;

 

import org.hardy.netty.tcp2.Configuration;
import org.hardy.netty.tcp2.Message;
import org.hardy.netty.tcp2.SocketChannelRespo;
import org.hardy.netty.tcp2.message.Comunication;
import org.hardy.netty.tcp2.message.Constant;
import org.hardy.netty.tcp2.message.Login;
import org.hardy.netty.tcp2.message.Request;
import org.hardy.netty.tcp2.message.Response;
import org.hardy.netty.tcp2.message.Result;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;

public class DefaultServerMessageProcessing implements MessageProcessing,Constant{
	private SocketChannelRespo socketChannelRespo;
	
    public DefaultServerMessageProcessing(){
    	     this.socketChannelRespo = 
						SocketChannelRespo.getInstance(Configuration.getTimeOut());
    }
	@Override
	public void traiterMessage(ChannelHandlerContext ctx, Message message) {
		
		this.socketChannelRespo.active(message.getClientId());
		      switch (message.getTypeEnum()) {
			case LOGIN: 
				Result result = new Result();
				Login login = (Login)message; 
				if(login.getUsername().startsWith("song")&&login.getPassword().equals("123")){
			        if(!this.socketChannelRespo.containt(login.getUsername())){
			        	 this.socketChannelRespo.addSocketChannel(login.getUsername(), (SocketChannel)ctx.channel());
						  result.setClientId(HOST_ID);
						  result.setMessage("login success");
						  result.setSucces(true);
			        }else{
			        	 	result.setClientId(HOST_ID);
						  result.setMessage("login error,not login two fois !!");
						  result.setSucces(false);
			        }
				}else{
					  result.setClientId(HOST_ID);
					  result.setMessage("login error,username or password incorrect!!");
					  result.setSucces(false);
					  result.setCloseClient(true);
				}
				 this.socketChannelRespo.active(login.getUsername());
				 ctx.writeAndFlush(result); 
				break;
		 case REQUEST:
			 Request request = (Request)message;
			 Response response = new Response();
			 if(!this.socketChannelRespo.containt(request.getClientId())){
				 response.setClientId(HOST_ID);
				 response.setContent("please login!!");
				 response.setCloseClient(true);
			 }else{
				 response.setClientId(HOST_ID);
				 response.setContent("已经接收 客户端发送的消息["+request.getContent().toString()+"]");
			 }
			 ctx.writeAndFlush(response);
				break;
		 case COMMUNICATION:
			 Comunication comu = (Comunication)message;	
			  response = new Response();
			 if(this.socketChannelRespo.containt(comu.getTarget())){
				  SocketChannel sChannel = this.socketChannelRespo.active(comu.getTarget());
				  response.setClientId(comu.getClientId());
				  response.setContent(comu.getContent());
				  sChannel.writeAndFlush(response);
			 }else{
				 response.setClientId(HOST_ID);
				 response.setContent(comu.getTarget()+" not login, wait login ......");
				 ctx.writeAndFlush(response);
			 }
			 break;
		 case INTERVAL:
			 if(this.socketChannelRespo.containt(message.getClientId())){
				  response = new Response();
				  response.setClientId(message.getClientId());
				  response.setContent("fasong chenggong");		 
			 }else{
				 response = new Response();
				 response.setClientId(HOST_ID);
				 response.setContent("please login!!");
				 response.setCloseClient(true);
			 } 
			 ctx.writeAndFlush(response);
			 break;
			default:
				break;
			}
		
	}

}
