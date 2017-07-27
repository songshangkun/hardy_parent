package org.hardy.springutil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

public class JeeDevViewResolver implements	ViewResolver{
	
	private static Log logger = LogFactory.getLog(JeeDevViewResolver.class);
	
	private ViewResolver defaultViewResolver = null;
	
	private Map<Set<String>,ViewResolver> viewResolverMap = new HashMap<Set<String>,ViewResolver>();
	
	@Override
	public View resolveViewName(String viewName, Locale locale) throws Exception {
		for(Map.Entry<Set<String>, ViewResolver> map : viewResolverMap.entrySet()){
	           Set<String> suffixs = map.getKey();
	           for(String suffix : suffixs){
	               if (viewName.endsWith(suffix)){
	                   ViewResolver viewResolver = map.getValue();
	                   if(null != viewResolver){
	                       if (logger.isDebugEnabled()) {
	                            logger.debug("found viewResolver '" + viewResolver + "' for viewName '" + viewName+ "'");
	                        }
	                       return viewResolver.resolveViewName(viewName, locale);
	                   }
	               }
	           }
	       }
	       
	       if(defaultViewResolver != null){
	           return defaultViewResolver.resolveViewName(viewName, locale);
	       }
	       return null;
	       // to allow for ViewResolver chainingreturn null;
	}
	
	public Map<Set<String>, ViewResolver> getViewResolverMap() {
        return viewResolverMap;
    }

    public void setViewResolverMap(Map<Set<String>, ViewResolver> viewResolverMap) {
        this.viewResolverMap = viewResolverMap;
    }

    public ViewResolver getDefaultViewResolver() {
        return defaultViewResolver;
    }

    public void setDefaultViewResolver(ViewResolver defaultViewResolver) {
        this.defaultViewResolver = defaultViewResolver;
    }
 
}
/**
<!--定义DefaultAnnotationHandlerMapping bean处理器 为了处理注解风格-->
<bean class="org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping"/> 

<!-- 设置解析视图 -->
<bean id="beanNameViewResolver" class="org.springframework.web.servlet.view.BeanNameViewResolver"/>
 
<!--定义InternalResourceViewResolver视图解析器 -->
<bean id="jspViewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
	<property name="viewClass" value="org.springframework.web.servlet.view.JstlView" />
    <!--property name="prefix" value="/appModel/"/-->
    <property name="prefix" value="/WEB-INF/views/"/>
    <property name="suffix" value=""/>
    <!--property name="order" value="1" /-->
</bean>

<bean id="velocityViewResolver" class = "org.springframework.web.servlet.view.velocity.VelocityViewResolver">
         <property name="order" value="0" />
         <property name="contentType" value="text/html;charset=UTF-8" />
         <property name="requestContextAttribute" value="req"/>
</bean>

<bean id="velocityConfig" class = "org.springframework.web.servlet.view.velocity.VelocityConfigurer">
         <property name="configLocation" value="/WEB-INF/velocity.properties"/>
         <property name="resourceLoaderPath" value="/" />
</bean>
<!-- 设置freemaker解析视图 -->
<bean id="freeMarkerViewResolver" class="org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver">  
        <property name="order" value="0" />
        <property name="viewClass" value="org.springframework.web.servlet.view.freemarker.FreeMarkerView"/>  
</bean>


<bean id="freemarkerConfig"  class="org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer">  
       <property name="configLocation" value="/WEB-INF/freemarker.properties"/>
        <property name="templateLoaderPath" value="/WEB-INF/ftl/"/>  
         <property name="freemarkerVariables">
             <map>
                <entry key="xml_escape" value-ref="fmXmlEscape"/>
            </map>
         </property>
</bean>  

<bean id="fmXmlEscape" class="freemarker.template.utility.XmlEscape"/>

<!--定义上传文件处理器 -->                
<bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
	<!--<property name="maxUploadSize" value="10485760"></property> -->
</bean> 

**/