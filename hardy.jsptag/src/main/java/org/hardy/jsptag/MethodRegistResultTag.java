package org.hardy.jsptag;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.hardy.springutil.webservice.MethodRepository;
/**
 * 应用于使用spring架构并且使用@RegistMethod标注的方法标签
 * @author song
 *
 */
public class MethodRegistResultTag extends  SimpleTagSupport {
	 
	   private String className ; 
	  
	   private String tagKey;
	  
	   private RenderScript rs ; 
	 
	   private String params ;
	   
	   private String bean_id;
	   
	   private String method_id;
	   
	   private String type = "json";
	   
 
	public void setType(String type) {
		this.type = type;
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
					 TagRespoire.add(rs);
					} catch (InstantiationException | IllegalAccessException|ClassNotFoundException e) {
						e.printStackTrace();
				}  
			} 
		} 
	}

	 

	public void setTagKey(String tagKey) {
		this.tagKey = tagKey;
	}



	public void setBean_id(String bean_id) {
		this.bean_id = bean_id;
	}

	public void setMethod_id(String method_id) {
		this.method_id = method_id;
	}
 
	public void setParams(String params) {
		this.params = params;
	}

 
	   StringWriter sw = new StringWriter();
	   public void doTag() throws JspException, IOException{
			          /* 从属性中使用消息 */    
			           Object[] objs = ParamsUtil.analisyParam(MethodRepository.getMethodById(method_id), params);
			          if(bean_id!=null&&method_id!=null){
				        	  Object value = null;
							try {
								if("json".equalsIgnoreCase(this.type))value = MethodRepository.runMethodPourJson(bean_id, method_id, objs);
								else  value = MethodRepository.runMethod(bean_id, method_id, objs);
								if(this.rs==null){
						        	  this.rs = new RenderScript() {									
										@Override
										public void renderData(String tagKey, JspContext jsp, Object value) {
											 try {
											    	jsp.getOut().print(value);
												} catch (IOException e) {		 
													e.printStackTrace();
												}
										}
									}; 
						          }
						          this.rs.renderData(this.tagKey,getJspContext(), value);
							} catch (IllegalAccessException
									| IllegalArgumentException
									| InvocationTargetException e) {
								e.printStackTrace();
								 getJspContext().getOut().println(e.getMessage());
							} 	
			          }else {
				          /* 从内容体中使用消息 */
				          getJspBody().invoke(sw);
				          getJspContext().getOut().println(sw.toString());
				       }
	             }
 


}
