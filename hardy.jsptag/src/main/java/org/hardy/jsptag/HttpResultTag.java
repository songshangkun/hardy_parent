package org.hardy.jsptag;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.song.tool.net.UrlReaderUtil;

/**
 * 应用于跨域GET访问URL的标签
 * @author song
 *
 */
public class HttpResultTag extends  SimpleTagSupport {

	   private String url;
	   
	   private String tagKey;
	   
	   private boolean immiter = false;
	   
	   private String charset = "UTF-8";
	   
	   private String className ;
	   
	   private RenderScript rs ; 
 
	   public void setTagKey(String tagKey) {
		this.tagKey = tagKey;
	}



	public void setUrl(String url) {
		this.url = url;
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
	}

	public void setImmiter(boolean immiter) {
		this.immiter = immiter;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}
	StringWriter sw = new StringWriter();
	   
	   public void doTag() throws JspException, IOException{
			       if (url != null) {
			          /* 从属性中使用消息 */
			          String jsonResult = UrlReaderUtil.read(url, immiter, charset);
			          if(this.rs==null){
			        	  this.rs = new RenderScript() {
							
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
			          this.rs.renderData(this.tagKey,getJspContext(), jsonResult);
			       }
			       else {
			          /* 从内容体中使用消息 */
			          getJspBody().invoke(sw);
			          getJspContext().getOut().println(sw.toString());
			       }
			   }

     
}
