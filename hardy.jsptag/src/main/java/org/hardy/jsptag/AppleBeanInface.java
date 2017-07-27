package org.hardy.jsptag;

/**
 * 对于使用springBeanTag标签回调数据处理接口
 * @author ssk
 *
 */
public interface AppleBeanInface {
   
	    public void callBean(String tagKey,Object springbean,String beanName);
}
