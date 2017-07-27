package org.hardy.jsonhelper.coreface;




/**
 * 对json处理的接口
 * @author sundyn
 *
 */
public interface OutInterface {
   /**
    * 对于非集合对象的接送处理
    * @param obj
    * @return
    */
	String toJSONObjectString(Object obj);
	/**
	 * 对于集合对象的接送处理
	 * @param obj
	 * @return
	 */
	String toJSONArrayString(Object obj);
}
