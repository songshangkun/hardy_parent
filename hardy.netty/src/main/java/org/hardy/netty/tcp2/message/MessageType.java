package org.hardy.netty.tcp2.message;

public enum MessageType {
	/**
	 * 登陆服务器
	 */
     LOGIN,
     /**
      * 请求服务器响应
      */
     REQUEST,
     /**
      * 服务器回应
      */
     RESPONSE,
     /**
      * 退出服务器
      */
     EXIT,
     /**
      * 与另一个客户端通信
      */
     COMMUNICATION,
     /**
      * 给所有客户端发信息
      */
     GROUP_COMMUNICATION,
     /**
      * 给客户端发文件
      */
     FILE_TRANSFER,
     /**
      * 将文件存储到服务器
      */
     FILE_CLOUD,
     /**
      * 音频服务
      */
     AUDIO,
     /**
      * 视频服务
      */
     VEDIO,
     /**
      * 传送心跳包
      */
     INTERVAL,
     /**
      * 传送心跳包
      */
     RESULT,
     /**
      * 扩展命令
      */
     EXTION;
	
	public static MessageType getMessageType(String typeName){
		MessageType mType = null;
		  try{
			  mType = MessageType.valueOf(typeName);
		  }catch (Exception e) {
			  return MessageType.EXTION;
			}
		   return mType;
	}
    
}
