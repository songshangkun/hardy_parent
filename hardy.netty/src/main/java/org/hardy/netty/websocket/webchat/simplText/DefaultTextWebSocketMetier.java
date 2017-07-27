package org.hardy.netty.websocket.webchat.simplText;

import org.hardy.netty.tcp2.Message;
import org.hardy.netty.tcp2.SocketChannelRespo;
import org.hardy.netty.tcp2.message.Comunication;
import org.hardy.netty.tcp2.message.Exit;
import org.hardy.netty.tcp2.message.Login;
import org.hardy.netty.tcp2.message.Response;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;

public class DefaultTextWebSocketMetier implements TextWebSocketMetier{
	  private SocketChannelRespo respo = SocketChannelRespo.getInstance(0);
	@Override
	public void handlerText(ChannelHandlerContext ctx, String text) {
	try{
		JSON jsonObject = (JSON)JSONObject.parse(text);
		Message message = JSONObject.toJavaObject(jsonObject, Message.class);
		Response response = null;
		switch (message.getTypeEnum()) {
		case LOGIN:
			Login login = JSONObject.toJavaObject(jsonObject, Login.class);
			if(!respo.containt(login.getClientId())){
				respo.addSocketChannel(login.getClientId(), (SocketChannel)ctx.channel());
			    response = new Response();
				response.setClientId(login.getClientId());
				response.setContent(new SiteMessage(0, "welcome:"+login.getClientId()+",登陆成功"));
				ctx.channel().writeAndFlush(response.toTextWebSocketFrameJson());
				final Response response2 = new Response();
				 response2.setContent(new SiteMessage(1, String.valueOf(respo.size())));
				new Thread(new Runnable() {	
					@Override
					public void run() {
					     for(SocketChannel sChannel : respo.listSocketChannel()){
					    	 sChannel.writeAndFlush(response2.toTextWebSocketFrameJson());
					     }
					}
				}).start();
			}else{
				if(ctx.channel()!=respo.active(login.getClientId())){
					respo.active(login.getClientId()).close();
					respo.remove(login.getClientId());
					respo.addSocketChannel(login.getClientId(), (SocketChannel)ctx.channel());
				}
			    response = new Response();
				response.setClientId(login.getClientId());
				response.setContent(new SiteMessage(0, "welcome:"+login.getClientId()+",登陆成功"));
				ctx.channel().writeAndFlush(response.toTextWebSocketFrameJson());
			}
			break;
		case EXIT: 
			Exit exit = JSONObject.toJavaObject(jsonObject, Exit.class);
			if(respo.containt(exit.getClientId())){
				respo.remove(exit.getClientId());
				 response = new Response();
				 response.setClientId(exit.getClientId());
				 response.setContent(new SiteMessage(0, exit.getClientId()+"退出成功"));
				ctx.writeAndFlush(response.toTextWebSocketFrameJson()).addListener(ChannelFutureListener.CLOSE);
			}else{
				 response = new Response();
				 response.setClientId(exit.getClientId());
				 response.setContent(new SiteMessage(0, exit.getClientId()+":您还没有登陆") );
				ctx.writeAndFlush(response.toTextWebSocketFrameJson()).addListener(ChannelFutureListener.CLOSE);
			} 
			break;
		case COMMUNICATION:
			Comunication comunication = JSONObject.toJavaObject(jsonObject, Comunication.class);
			     SocketChannel sChannel = respo.active(comunication.getTarget());
			     response  = new Response();
			     if(sChannel!=null){
			    	 	 response.setContent(new SiteMessage(1, comunication.getContent()));
				     response.setClientId(comunication.getClientId());
				     sChannel.writeAndFlush(response.toTextWebSocketFrameJson());	 
			     }else{
			    	 	response.setContent(new SiteMessage(0, comunication.getTarget()+"没有登陆，不能发送信息"));
				     response.setClientId(comunication.getClientId());
				     ctx.writeAndFlush(response.toTextWebSocketFrameJson());	 
			     }
			     
			break;
		default:
			break;
			}
		}catch (Exception e) {
			e.printStackTrace();
			Response response  = new Response();
		     response.setContent(new SiteMessage(0, "传送信息格式不正确"));
			ctx.writeAndFlush(response.toTextWebSocketFrameJson());
		}
	}
    
	
	public static class  SiteMessage{
		
		 private int position;
		 private String message;
		public SiteMessage(int position,String message) {
		       this.position = position;
		       this.message = message;
		}
		public int getPosition() {
			return position;
		}
		public void setPosition(int position) {
			this.position = position;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		
		 
	} 
}
