package org.hardy.springutil;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
/**
 * 该类实现了BeanFactoryAware，ApplicationContextAware
 * 所以在将此类配置为spring容器中的bean之后
 * 它可以直接获取BeanFactory,ApplicationContext，并转化为static
 * 然后对他们进行操作
 * @author song
 *
 */
public class SpringBeanUtil implements BeanFactoryAware,ApplicationContextAware{
   
	private static BeanFactory beanFactory;
	
	private static ApplicationContext applicationContext;  
	
	@Override
	public void setBeanFactory(BeanFactory factory) throws BeansException {
		SpringBeanUtil.beanFactory = factory;  
	}

	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		SpringBeanUtil.applicationContext = context;
	}
 
	 /** 
     * 根据beanName名字取得bean 
     *  
     * @param beanName 
     * @return 
     */  
    public static Object  getBeanByBeanFactroy(String beanName) {  
        if (null != beanFactory) {  
            return beanFactory.getBean(beanName);  
        }  
        return null;  
    }  
    
    /** 
     * 根据beanName名字取得bean 
     *  
     * @param beanName 
     * @return 
     */  
    public static Object  getBeanByApplicationContext(String beanName) {  
        if (null != applicationContext) {  
            return applicationContext.getBean(beanName);  
        }  
        return null;  
    }
  /**
   * 获取beanFactory
   * @return
   */
	public static BeanFactory getBeanFactory() {
		return beanFactory;
	}
 /**
  * 获取ApplicationContext
  * @return
  */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}  
	/**
	  * 获取DefaultListableBeanFactory
	  * @return
	  */
    public static DefaultListableBeanFactory getDefaultListableBeanFactory(){
    	ConfigurableApplicationContext applicationContext = (ConfigurableApplicationContext) SpringBeanUtil
                .getApplicationContext();
        DefaultListableBeanFactory beanFactoryListable = (DefaultListableBeanFactory) applicationContext
                .getBeanFactory();
		return beanFactoryListable;
    }
    
}
