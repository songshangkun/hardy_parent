package hardy.securite.handler.annotationHandler;

import hardy.securite.HardyAuthInterface;
import hardy.securite.RefuseType;
import hardy.securite.annotation.AuthCheck;
import hardy.securite.annotation.AuthCheckEnty;
import hardy.securite.exception.RefuseRequestException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;

/**
 * 基于Annotation的验证
 * 所有的AnnotationHandler实例共享一个可操作的cache
 * @author ssk
 *
 */
public class AnnotationHandler implements HardyAuthInterface,RefuseType{
	/**
	 * 对应param的后台容器
	 */
	private static List<String> cache = new ArrayList<String>();
	/**
	 * 一个拦截改变注解结果的接口
	 */
	private URLAnnotationEx ue;
	/**
	 * AnnotationEL接口在el()被使用时会调用接口方法
	 * 否则抛出异常
	 */
	private AnnotationEL el;
    /**
     * 执行顺序
     */
	private int order = 0;
	/**
	 * 默认名称
	 */
	private String name = this.getClass().getName();
	/**
	 * 默认开启
	 */
	private boolean open = true;
	
	@Override
	public void valid(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws RefuseRequestException{
		 AuthCheckEnty acenty = null;
		 AuthCheck ac = null;
		 AuthCheck acmethod = null;
		if(handler.getClass().isAssignableFrom(HandlerMethod.class)){
			HandlerMethod hm = (HandlerMethod) handler;
			Class<?> clazz = hm.getBeanType();
			if(clazz.isAnnotationPresent(AuthCheck.class)){
			  ac = clazz.getAnnotation(AuthCheck.class);
			  if(ac.exclude())return ;
			}
			if(hm.getMethodAnnotation(AuthCheck.class)!=null){
			  acmethod = ((HandlerMethod) handler).getMethodAnnotation(AuthCheck.class);
			  if(acmethod.exclude())return ;
			}
			//根据类标注和方法接口标注进行组装最终的标注解析实体
			//1-规则如果不继承,则使用方法注解
			//2-方法注解没有使用类注解
			//3-如果继承,必须有方法注解。如有类注解,方法注解会将类注解的全部属性加入到自身
			//4-如果没有类注解则不变
			if(ac!=null&&acmethod==null){
				acenty = buildAuthCheckEnty(ac);
			}else if(ac==null&&acmethod!=null){
				acenty = buildAuthCheckEnty(acmethod);
			}else if(ac!=null&&acmethod!=null){
				acenty = buildAuthCheckEnty(ac,acmethod);
			}else{
				return;
			}
		}
		if(ue!=null){
			ue.control(acenty,request);
		}
		if(acenty!=null){
			if(acenty.getRefuseIp()!=null){
				String currentIp = request.getRemoteAddr();
				 for(String ip:acenty.getRefuseIp()){
   				  if(currentIp.equals(ip)){
   					   throw new RefuseRequestException(IP, "ip被阻止");
   				  }
   			  }
			}
			if(acenty.getIp()!=null){
				boolean flag = false;
				String currentIp = request.getRemoteAddr();
				 for(String ip:acenty.getIp()){
   				  if(currentIp.equals(ip)){
   					flag = true;
   					break;
   				  }
   			   }
				 if(!flag) throw new RefuseRequestException(IP, "ip不被允许");//return false;
			}
			if(acenty.getParam()!=null){
				 String paramName = acenty.getParam(); 
				 Map<String,String[]> map = request.getParameterMap();
				 if(!map.containsKey(paramName)||!cache.contains(map.get(paramName)[0])) 
				 throw new RefuseRequestException(PARAM, "传输参数有误");//return false;
			}
			if(acenty.getRequestAttr()!=null){
				 for(String attrName:acenty.getRequestAttr()){
					 if(request.getAttribute(attrName)==null)
						 throw new RefuseRequestException(REQUEST_ATTR, "REQUEST_ATTR错误");
				 }
			}
			if(acenty.getSessionAttr()!=null){
				for(String attrName:acenty.getSessionAttr()){
					 if(request.getSession().getAttribute(attrName)==null)
						 throw new RefuseRequestException(SESSION_ATTR, "SESSION_ATTR错误");
				 }
			}
			if(acenty.getEl()!=null){
				if(this.el==null)
				throw new RuntimeException("not implement el:"+AnnotationEL.class.getName());
			    this.el.readEL(request,acenty.getEl());
			}
			
		 } 
	}
	
	/**
	 * 根据AuthCheck生成AuthCheckEnty
	 * @param ac
	 */
	private AuthCheckEnty buildAuthCheckEnty(AuthCheck ac){
		AuthCheckEnty acenty = new AuthCheckEnty();
		if(ac.el()!=null&&ac.el().length>0&&!"".equals(ac.el()[0].trim()))
			acenty.setEl(ac.el());
		if(ac.ip()!=null&&ac.ip().length>0&&!"".equals(ac.ip()[0].trim()))
			acenty.setIp(ac.ip());
		if(ac.param()!=null&&!"".equals(ac.param().trim()))
			acenty.setParam(ac.param());
		if(ac.refuseIp()!=null&&ac.refuseIp().length>0&&!"".equals(ac.refuseIp()[0].trim()))
			acenty.setRefuseIp(ac.refuseIp());
		if(ac.requestAttr()!=null&&ac.requestAttr().length>0&&!"".equals(ac.requestAttr()[0].trim()))
			acenty.setRequestAttr(ac.requestAttr());
		if(ac.sessionAttr()!=null&&ac.sessionAttr().length>0&&!"".equals(ac.sessionAttr()[0].trim()))
			acenty.setSessionAttr(ac.sessionAttr());
		return acenty;
	}
	/**
	 * 根据类的AuthCheck和方法AuthCheck生成AuthCheckEnty
	 * @param acClass
	 * @param acMethod
	 */
	private AuthCheckEnty buildAuthCheckEnty(AuthCheck acClass,AuthCheck acMethod){
		if(!acMethod.inherit()) return buildAuthCheckEnty(acMethod);
		else{
			AuthCheckEnty ae = new AuthCheckEnty();
			AuthCheckEnty ae1 = buildAuthCheckEnty(acClass);
			AuthCheckEnty ae2 = buildAuthCheckEnty(acMethod);
			ae.setEl(arrayContact(ae1.getEl(), ae2.getEl()));
			ae.setIp(arrayContact(ae1.getIp(), ae2.getIp()));
			ae.setRefuseIp(arrayContact(ae1.getRefuseIp(), ae2.getRefuseIp()));
			ae.setRequestAttr(arrayContact(ae1.getRequestAttr(), ae2.getRequestAttr()));
			ae.setSessionAttr(arrayContact(ae1.getSessionAttr(), ae2.getSessionAttr()));
			if(ae2.getParam()!=null)ae.setParam(ae2.getParam());
			else ae.setParam(ae1.getParam());
			return ae;
		}
	}
	/**
	 * 连接两个String数组
	 * @param array1
	 * @param array2
	 * @return
	 */
	private String[] arrayContact(String[] array1,String[] array2){
		String[] result = null;
		 int length1 = array1==null?0:array1.length;
		 int length2 = array2==null?0:array2.length;
		 if((length1+length2)>0){
			 result = new String[length1+length2];
			 int i = 0;
			 if(array1!=null){
				 for(String s1:array1){
					 result[i] = s1;
					 i++;
				 }
			 }
			 if(array2!=null){
				 for(String s2:array2){
					 result[i] = s2;
					 i++;
				 }
			 }
		 }
		 return result;
	}
	
	 
    /**
     * 获取执行顺序
     */
	@Override
	public int getOrder() {	 
		return this.order;
	}
    /**
     * 获取处理器名称
     */
	@Override
	public String getName() {
		return this.name;
	}
	/**
	 * 处理器是否启用
	 */
	@Override
	public boolean open() {
		return this.open;
	}

	public boolean isOpen() {
		return open;
	}
     /**
      * 设置是否启用，默认为启用
      * @param open
      */
	public void setOpen(boolean open) {
		this.open = open;
	}
    /**
     * 设置顺序 默认为第一个
     * @param order
     */
	public void setOrder(int order) {
		this.order = order;
	}
    /**
     * 设置名称
     * @param name
     */
	public void setName(String name) {
		this.name = name;
	}



	public AnnotationEL getEl() {
		return el;
	}


    public URLAnnotationEx getUe() {
		return ue;
	}
    /**
     * 设置一个拦截接口,可改变最终的AuthCheckEnty对象
     * @param ue
     */
	public void setUe(URLAnnotationEx ue) {
		this.ue = ue;
	}

	/**
     * 设置AnnotationEL接口处理el标注
     * @param el
     */
	public void setEl(AnnotationEL el) {
		this.el = el;
	}
	
	
	/**
	 * 对应 param传入的参数
	 * @param obj
	 */
	public static void addInCache(String paramValue){
		 cache.add(paramValue);
	}
	/**
	 * 移除paramValue
	 * @param paramValue
	 */
	public static void removeOutCache(String paramValue){
		 cache.remove(paramValue);
	}
	/**
	 * 清空cache
	 */
	public static void cleanCache(){
		 cache.clear();
	}

}
