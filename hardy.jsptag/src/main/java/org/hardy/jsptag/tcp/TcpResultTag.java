package org.hardy.jsptag.tcp;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.hardy.jsptag.RenderScript;
import org.hardy.jsptag.TagRespoire;
import org.hardy.netty.tcp.TcpClient;

/**
 * 应用于跨域GET访问URL的标签
 * @author song
 *
 */
public class TcpResultTag extends  SimpleTagSupport { 
 
	   private String tagKey;
	   
	   private String message;
	   
	   private String host;
	   
	   private int port;
	   /**
	    * 设置数据返回后的jsp渲染类
	    */
	   private String className ;  
	   
	   private RenderScript rs ; 
	   
	   private TcpClient tcpClient ;
	   
	   private TcpTageExcuteClient tcpTageExcuteClient;
	   
	   private PrepareMessage prepareMessage;
    
	   /**
	    * 设置预处理message发送信息的类，如果实现这个接口，
	    * 实际发送的消息是接口返回的值
	    */
	   private String preClassName; 
	   
	   public TcpResultTag(){
		   tcpClient = new TcpClient();
		   tcpClient.setDecoderType(TcpClient.JavaSerialize);
		   this.tcpTageExcuteClient = new TcpTageExcuteClient();
		   tcpClient.setExcuteClient(this.tcpTageExcuteClient);
		   tcpClient.setCloseAfterRead(true);
	   }
 
	   public void setTagKey(String tagKey) {
		this.tagKey = tagKey;
		this.tcpTageExcuteClient.setTagKey(this.tagKey);
	}
       
	public void setClassName(String className) {
		this.className = className;
		if(this.rs==null){
			 RenderScript rsn = TagRespoire.getRenderByTag(this.className);
			if(rsn!=null){
				 this.rs = rsn;
			}else{
				try {
					Class<?> c = Class.forName(this.className);
					 this.rs = (RenderScript)c.newInstance();
					} catch (InstantiationException | IllegalAccessException|ClassNotFoundException e) {
						e.printStackTrace();
				}  
			} 
		}
		this.tcpTageExcuteClient.setRenderScript(this.rs);
	}

	 
	
	StringWriter sw = new StringWriter();
	   
	   public void doTag() throws JspException, IOException{
		   tcpTageExcuteClient.setJsp(this.getJspContext());
		         Object sendMessage = message;
			       if (message != null) {
			       if(this.prepareMessage!=null)sendMessage = this.prepareMessage.prepare(message);
			       
			    	  this.tcpClient.setMessage(sendMessage);
			          try {
						this.tcpClient.connect(host, port);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
			       }
			       else {
			          /* 从内容体中使用消息 */
			          getJspBody().invoke(sw);
			          getJspContext().getOut().println(sw.toString());
			       }
			   }

	public void setMessage(String message) {
		this.message = message;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setPreClassName(String preClassName) {
		this.preClassName = preClassName;
		if(this.prepareMessage==null){
			PrepareMessage pmsg = TagRespoire.getPreMsgByTag(this.preClassName);
			if(pmsg!=null){
				 this.prepareMessage = pmsg;
			}else{
				try {
					Class<?> c = Class.forName(this.preClassName);
					 this.prepareMessage = (PrepareMessage)c.newInstance();
					} catch (InstantiationException | IllegalAccessException|ClassNotFoundException e) {
						e.printStackTrace();
				}  
			} 
		}
	}

     
}
