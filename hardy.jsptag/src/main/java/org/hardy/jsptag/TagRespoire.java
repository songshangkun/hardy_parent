package org.hardy.jsptag;

import java.util.HashMap;
import java.util.Map;

import org.hardy.jsptag.tcp.PrepareMessage;



/**
 * 存储处理接口的容器
 * @author song
 *
 */
public class TagRespoire {
	 /**
	  * 数据回调显示到页面的接口容器
	  */
     private static final Map<String,RenderScript> renders = new HashMap<String, RenderScript>();
     /**
      * SpringBeanTag的回掉操作方法的容器
      */
     private static final Map<String,AppleBeanInface> appBeans = new HashMap<String, AppleBeanInface>();
     /**
      * 发送请求的SendHandler的容器
      */
     private static final Map<String,SendHandler> handler = new HashMap<String, SendHandler>();
     /**
      * Tcp客户端发送的消息处理接口
      */
     private static final Map<String,PrepareMessage> preMessages = new HashMap<String, PrepareMessage>();
     
     public static SendHandler getHandlerByTag(String tag) {
		return handler.get(tag);
	}

	public static RenderScript getRenderByTag(String tag){
    	  return renders.get(tag);
     }
  
     public static AppleBeanInface getBeanByTag(String tag){
   	  return appBeans.get(tag);
    }
     
     public static PrepareMessage getPreMsgByTag(String tag){
      	  return preMessages.get(tag);
       }
     
     public  static void add(RenderScript rs){
    	 TagRespoire.renders.put(rs.getClass().getName(), rs);
     }
    
    public  static void add(AppleBeanInface abif){
    	TagRespoire.appBeans.put(abif.getClass().getName(), abif);
    }
    
    public  static void add(SendHandler handler){
    	TagRespoire.handler.put(handler.getClass().getName(), handler);
    }
    
    public  static void add(PrepareMessage preMsg){
    	TagRespoire.preMessages.put(preMsg.getClass().getName(), preMsg);
    }
}
