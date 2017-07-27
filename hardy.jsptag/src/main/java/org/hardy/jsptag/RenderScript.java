package org.hardy.jsptag;

import javax.servlet.jsp.JspContext;

/**
 * 将value渲染到jsp对象上
 * @author sundyn
 *
 */
public interface RenderScript {
    
	   public void renderData(String tagKey,JspContext jsp,Object value);
}
