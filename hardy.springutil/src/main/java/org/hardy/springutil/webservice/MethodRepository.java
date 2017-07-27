package org.hardy.springutil.webservice;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hardy.springutil.SpringBeanUtil;

import com.alibaba.fastjson.JSONObject;
 

/**
 * 方法存储的中心容器
 * @author ssk
 *
 */
public class MethodRepository {
	 private final static int capacite = 200;
	private static List<String>  allbeansHelper  = new ArrayList<String>(capacite);

	private static Map<String,Method> beans = new HashMap<>(capacite);
	 /**
	  * 注册方法到容器中
	  * @param id
	  * @param method
	  */
	    public static void register(String id,Method method,String bean_id,String description,String path){
	      	    MethodRepository.beans.put(id, method);	
	      	  MethodRepository.allbeansHelper.add("bean_id:"+bean_id+" , method_id:"+id+
	      			  " , description:"+description+" , path:"+path);
	    }
	   /**
	    * 查看容器中是否包含有此ID的方法
	    * @param id
	    * @return
	    */
	  public static boolean containsId(String id){
		   return MethodRepository.beans.containsKey(id);
	  }
	  /**
	   * 以列表形式显示注册的方法
	   */
	  public static void listInfo(){
		    for(String s:MethodRepository.allbeansHelper){
		    	  System.out.println(s);
		    }
	  }
	  /**
	   * 返回注册的方法
	   */
	  public static List<String> allbeansHelper(){
		    return MethodRepository.allbeansHelper;
	  }
	  /**
	   * 根据id获取方法
	   * @param id
	   * @return
	   */
	  public static Method getMethodById(String id){
		     return MethodRepository.beans.get(id);
	  }
	  /**
	   * 运行方法返回对象
	   * @param beanId  spring中配置的bean的ID
	   * @param methodId   使用Annotation标记的方法
	   * @param params  参数，必须注意顺序
	   * @return
	   * @throws IllegalAccessException
	   * @throws IllegalArgumentException
	   * @throws InvocationTargetException
	   */
	  public  static Object runMethod(String beanId,String methodId,Object[] params) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		   Object bean = SpringBeanUtil.getBeanByApplicationContext(beanId);
		  if(bean==null) throw new RuntimeException("on ne peut pas trouve le id de bean nomer comme "+beanId);
		  if(methodId.equalsIgnoreCase("_helper")){
			   CallCapital cc = (CallCapital)bean;
			    return  cc.getAllRegistHelper();
		  }else{
			  Method method = MethodRepository.beans.get(methodId);
			  if(method==null) throw new RuntimeException("on ne peut pas trouve le id de method nomer comme "+methodId+" dans bean "+beanId.getClass().getName());
			  CallCapital cc = (CallCapital)bean;
			  Object resulta = cc.callback(MethodRepository.getMethodById(methodId), params);
			  return resulta;
		  }
	  }
	  /**
	   *  运行方法返回JSON
	   * @param beanId
	   * @param methodId
	   * @param params
	   * @return
	   * @throws IllegalAccessException
	   * @throws IllegalArgumentException
	   * @throws InvocationTargetException
	   */
	  public  static String runMethodPourJson(String beanId,String methodId,Object[] params) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		  Object resulta = runMethod(beanId, methodId, params);
		  return JSONObject.toJSONString(resulta);
	  }
	  
	  /**
	   * 运行方法返回JSON
	   * @param params  ParamJson对象 ，必须有key $bean_id
	   * 指明spring的bean，必须有$method_id 指明调用的方法，
	   * 其余的全部是参数，必须按顺序设置进ParamJson对象中如params.put("0",Object)，
	   * 如果味null也要设置"$NULL"
	   * @return
	   * @throws IllegalAccessException
	   * @throws IllegalArgumentException
	   * @throws InvocationTargetException
	   */
	  public  static String runMethodPourJson(ParamJson params) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		  Map<String, Object> map = params.getAnalise();
		  int flag = 2;
		  if(params.getParam("$token")!=null)flag = 3;
		  Object[] objects = null;
		  if(map.size()>flag){
			  objects = new Object[map.size()-flag];
			  for(int i=0;i<map.size()-flag;i++){
				  Object obj = map.get(String.valueOf(i));
				  if(!"$NULL".equalsIgnoreCase(String.valueOf(obj)))
				  objects[i] = map.get(String.valueOf(i));
				  else objects[i] = null;
			  }
		  } 
			return runMethodPourJson(params.getParam("$bean_id").toString(), params.getParam("$method_id").toString(),objects);
	  }
	  
	  public static Object[] getParamsByParamJson(ParamJson params){
		  Map<String, Object> map = params.getAnalise();
		  int flag = 2;
		  if(params.getParam("$token")!=null)flag = 3;
		  Object[] objects = null;
		  if(map.size()>flag){
			  objects = new Object[map.size()-flag];
			  for(int i=0;i<map.size()-flag;i++){
				  Object obj = map.get(String.valueOf(i));
				  if(!"$NULL".equalsIgnoreCase(String.valueOf(obj)))
				  objects[i] = map.get(String.valueOf(i));
				  else objects[i] = null;
			  }
		  } 
		  return objects;
	  }
	  
}


/**
 * 
 *     <!--申明一个工具类，为了选择bean-->
 *     <bean class="com.song.ssh.springutil.SpringBeanUtil" />
 *     <!--申明一个webservice端口类-->
 *     <bean class="com.song.ssh.jsonhelper.extention.webservice.WebServiceRemotingServer" >
        <property name="interceptors">
             <list>
                <bean class="com.song.tool.constant.Interceptor1" /> <!-- 自定义实现Interceptor接口-->
             </list>
        </property>
    </bean>  
        <!--申明一个类扫描，为了扫描到要使用的bean方法-->
        <bean class="com.song.ssh.springutil.AutoWaveMethod">    
		    <constructor-arg>  
		        <list>  
		            <value>sundyn.hkServiceVedio.service</value>  
		        </list>  
		    </constructor-arg>  
		</bean> 
		<!--申明一个webservice类-->
	<bean class="com.song.ssh.jsonhelper.extention.webservice.WebServiceRemotingServer"/>
	<!--申明一个webservice端口，为了发布服务-->	
	<bean class="org.springframework.remoting.jaxws.SimpleJaxWsServiceExporter" 
	    p:baseAddress="http://localhost:8888/" />
 * **/
