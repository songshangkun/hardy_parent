package org.hardy.jsonhelper;

import javax.servlet.http.HttpServletResponse;

import org.hardy.jsonhelper.coreface.OutInterface;
import org.hardy.servlet.out.AbstractResource;
import org.hardy.servlet.out.ResourceWrite;
import org.hardy.statics.web.ContentType;

/**
 * 
 * 定义JSON输出的格式,并且简化输出JSON
 * @author ssk
 *
 */
public class JsonTools {
	/**
	 * 获取JSON实例
	 * @return
	 */
	public static JsonTools getInstance(OutInterface oiface){
		 JsonTools jsonTools = new JsonTools();
		 jsonTools.setOiface(oiface);
		 return jsonTools;
	}
	 /**
	  * json统一输出接口
	  */
	private OutInterface oiface;
	
	private JsonTools(){}
	 
	public OutInterface getOiface() {
		return oiface;
	}
	public void setOiface(OutInterface oiface) {
		this.oiface = oiface;
	}
	/**
     * 直接的对象输出JSON格式
     * @param object
     * @return
     */
	public String toJSONObjectString(Object object) {
		return oiface.toJSONObjectString(object);
	}
	/**
	 * 对集合元素的对象输出JSON格式
	 * @param object
	 * @return
	 */
	public String toJSONArrayString(Object object) {
		return oiface.toJSONObjectString(object);
	}
	 /**
	  * 直接输出对象的json形式
	  * @param obj
	  * @param response
	  */
	public void printJsonObject(final Object obj,HttpServletResponse response){
		 ResourceWrite.responseString(response, ContentType.JSON, new AbstractResource() {

			@Override
			public String excuterS() {
				return oiface.toJSONObjectString(obj);
			}
			 
		});
	} 
	/**
	 * 直接输出数组的json形式
	 * @param obj
	 * @param response
	 */
	public void printJsonArray(final Object obj,HttpServletResponse response){
		 ResourceWrite.responseString(response, ContentType.JSON, new AbstractResource() {
			@Override
			public String excuterS() {
				return oiface.toJSONArrayString(obj);
			}
			 
		});
	} 
}
