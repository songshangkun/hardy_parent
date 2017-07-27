package org.hardy.netty.tcp2.handler;

 

import org.hardy.netty.tcp2.Message;
import org.hardy.netty.tcp2.message.Response;
import org.hardy.netty.tcp2.message.Result;

import io.netty.channel.ChannelHandlerContext;

public class DefaultClientMessageProcessing implements MessageProcessing{
   
	@Override
	public void traiterMessage(ChannelHandlerContext ctx, Message message) {
		     switch (message.getTypeEnum()) {
			case RESPONSE:
				Response response = (Response)message;
				System.out.println("response:"+response.getClientId()+"向您发送了一个返回消息 , 内容:"+response.getContent().toString());				
				break;
			case RESULT:
				Result result = (Result)message;
				System.out.println("result:"+result.getClientId()+"向您发送了一个返回消息 , 内容:"+result.getMessage()+" , 结果:"+result.isSucces());
				break;
			default:
				break;
			}
		     if(message.isCloseClient())ctx.close();
	}

}
