package org.hardy.jsptag;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

 

public class CommonTag extends SimpleTagSupport{
	/**
	 * 发送请求的类名称
	 */
	private String handlerClass;
	/**
	 * 回调结果的类名称
	 */
	private String callbackClass;
	
	private String tagKey;
	
	
	private SendHandler handler;
	
	private RenderScript renderScript;
	
	
   
	public void setTagKey(String tagKey) {
		this.tagKey = tagKey;
	}

	public void setHandlerClass(String handlerClass) {
		this.handlerClass = handlerClass;
		if(this.handler==null){
			SendHandler hdr = TagRespoire.getHandlerByTag(this.handlerClass);
			if(hdr!=null){
				 this.handler = hdr;
			}else{
				try {
					Class<?> c = Class.forName(this.handlerClass);
					 this.handler = (SendHandler)c.newInstance();
					 TagRespoire.add(this.handler);
					} catch (InstantiationException | IllegalAccessException|ClassNotFoundException e) {
						e.printStackTrace();
				}  
			} 
		} 
	}
 
	public void setCallbackClass(String callbackClass) {
		this.callbackClass = callbackClass;
		if(this.renderScript==null){
			 RenderScript rsn = TagRespoire.getRenderByTag(this.callbackClass);
			if(rsn!=null){
				 this.renderScript = rsn;
			}else{
				try {
					Class<?> c = Class.forName(this.handlerClass);
					 this.renderScript = (RenderScript)c.newInstance();
					 TagRespoire.add(renderScript);
					} catch (InstantiationException | IllegalAccessException|ClassNotFoundException e) {
						e.printStackTrace();
				}  
			} 
		} 
	}

	private  StringWriter sw = new StringWriter();

	@Override
	public void doTag() throws JspException, IOException {
	          /* 从属性中使用消息 */
	       if(this.handler!=null){
	          Object obj = this.handler.sendData(tagKey);
	          if(this.renderScript==null){
	        	  this.renderScript = new RenderScript() {
					@Override
					public void renderData(String tagKey, JspContext jsp, Object value) {
						 try {
						    	jsp.getOut().print(value);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						
					}
				}; 
	          }
	          this.renderScript.renderData(this.tagKey,getJspContext(), obj);
	       }
	       else {
	          /* 从内容体中使用消息 */
	          getJspBody().invoke(sw);
	          getJspContext().getOut().println(sw.toString());
	       }
	}
	
	
}
