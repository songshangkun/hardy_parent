package org.hardy.jsptag.tcp;

import java.io.IOException;

import javax.servlet.jsp.JspContext;

import org.hardy.jsptag.RenderScript;
import org.hardy.netty.tcp.ExcuteClient;

public class TcpTageExcuteClient implements ExcuteClient {
	
	private String tagKey;
	
	private RenderScript renderScript;
    
	private JspContext jsp;
	@Override
	public void excute(Object msg) {
		 if(this.renderScript!=null)this.renderScript.renderData(tagKey,jsp,msg);
		else
			try {
				jsp.getOut().print(msg);
			} catch (IOException e) {	 
				e.printStackTrace();
			}
	}
	public JspContext getJsp() {
		return jsp;
	}
	public void setJsp(JspContext jsp) {
		this.jsp = jsp;
	}
	public RenderScript getRenderScript() {
		return renderScript;
	}
	public void setRenderScript(RenderScript renderScript) {
		this.renderScript = renderScript;
	}
	public String getTagKey() {
		return tagKey;
	}
	public void setTagKey(String tagKey) {
		this.tagKey = tagKey;
	}

}
