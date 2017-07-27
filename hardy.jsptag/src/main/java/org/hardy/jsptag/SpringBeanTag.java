package org.hardy.jsptag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.hardy.springutil.SpringBeanUtil;

public class SpringBeanTag  extends SimpleTagSupport{
     
	private String bean_id;
     
	private String tagKey;
	
	private String className;
	
	private AppleBeanInface appBean;
	
	
     
	public void setBean_id(String bean_id) {
		this.bean_id = bean_id;
	}



	public void setTagKey(String tagKey) {
		this.tagKey = tagKey;
	}



	public void setClassName(String className) {
		this.className = className;
		if(this.appBean==null){
			 AppleBeanInface abif = TagRespoire.getBeanByTag(this.className);
			if(abif!=null){
				 this.appBean = abif;
			}else{
				try {
					Class<?> c = Class.forName(this.className);
					 this.appBean = (AppleBeanInface)c.newInstance();
					 TagRespoire.add(abif);
					} catch (InstantiationException | IllegalAccessException|ClassNotFoundException e) {
						e.printStackTrace();
				}  
			} 
		} 
	}



	@Override
	public void doTag() throws JspException, IOException {
		       Object object = SpringBeanUtil.getApplicationContext().getBean(bean_id);
		       if(this.appBean==null){
		    	   this.appBean = new AppleBeanInface() {
					@Override
					public void callBean(String tagKey, Object springbean,String beanName) {
						  try {
							getJspContext().getOut().print("call bean ["+beanName+"] and tagKey = "+tagKey);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				};
				this.appBean.callBean(tagKey, object, bean_id);
		       }
	}
   
	   
}
