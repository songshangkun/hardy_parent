package org.hardy.springutil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
 
/**
 * 该类可以动态销毁和注册一个bean
 * 需要在spring上下文种配置SpringBeanUtil对象来获取beanFactory
 * @author song
 *
 */
public class BeanManager {
    private static Logger logger = LoggerFactory.getLogger(BeanManager.class.getName());
  /**
   * 
   * @param beanNames
   */
    public static  void destroy(String... beanNames) {
    	DefaultListableBeanFactory beanFactory = SpringBeanUtil.getDefaultListableBeanFactory();
        for(String beanName:beanNames){
        	 logger.info("destroy bean " + beanName);
        	 if(beanFactory.containsBean(beanName)){
        		 beanFactory.destroySingleton(beanName);
        		 Object obj = SpringBeanUtil.getBeanByBeanFactroy(beanName);
        		 if(null!=obj){
                	 beanFactory.destroyBean(beanName, obj);
                 }
        		 beanFactory.removeBeanDefinition(beanName);
        	 } else {
                 logger.info("No {} defined in bean container.", beanName);
             } 
        }
       
    }
 
    public static void addMongoToBeanFactory(Class<?> beanClass,String beanName,String init,String destroy,String host,int port,String database,boolean singleton){
        ConfigurableApplicationContext applicationContext = (ConfigurableApplicationContext) SpringBeanUtil.getApplicationContext();
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getBeanFactory();
        if(singleton&&beanFactory.containsBean(beanName)){
         	destroy(beanName);
        }
        if(!beanFactory.containsBean(beanName)){
            BeanDefinitionBuilder beanDefinitionBuilder= BeanDefinitionBuilder.rootBeanDefinition(beanClass);
            if(host!=null)beanDefinitionBuilder.addPropertyValue("host", host);
            if(port!=-1)beanDefinitionBuilder.addPropertyValue("port", port);
            if(database!=null)beanDefinitionBuilder.addPropertyValue("database", database);
            if(init!=null)beanDefinitionBuilder.setInitMethodName("init");
            if(destroy!=null)beanDefinitionBuilder.setDestroyMethodName("destroy");
            beanFactory.registerBeanDefinition(beanName, beanDefinitionBuilder.getBeanDefinition());
            logger.info("Add {} to bean container.", beanName);
        }
    }
    
    public static void addMongoToBeanFactory(Class<?> beanClass,String beanName){
    	addMongoToBeanFactory(beanClass, beanName,null,null, null, -1, null, true);
    }
 
}
