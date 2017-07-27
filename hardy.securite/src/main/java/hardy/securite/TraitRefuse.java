package hardy.securite;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import hardy.securite.exception.RefuseRequestException;

/**
 * 对权限被拒绝后的处理
 * 对访问完结的处理
 * 对访问成功后的处理
 * @author sundyn
 *
 */
public interface TraitRefuse {
    /**
     * 如果出现RefuseRequestException的处理方法
     * 只在HardyAuthorization.linkedType=and时才会调用
     * @param request
     * @param response
     * @param handler
     * @param e
     */
	 void taiterRefuseRequestException(HttpServletRequest request,
				HttpServletResponse response, Object handler,
				RefuseRequestException e);
	 /**
	  * 只在HardyAuthorization.linkedType=or时才会调用
	  * @param request
	  * @param response
	  * @param handler
	  */
	 void taiterRefuseRequest(HttpServletRequest request,
				HttpServletResponse response, Object handler);
	 /**
	  * 用于对request请求完毕后的处理
	  * 主要是对请求产生异常的处理
	  * @param request
	  * @param response
	  * @param handler
	  */
	 void taiterCompletRequest(HttpServletRequest request,
				HttpServletResponse response, Object handler, Exception ex);
     
	 /**
	  * 提交完成后调用
	  * @param request
	  * @param response
	  * @param handler
	  * @param modelAndView
	  */
	 void afterPost(HttpServletRequest request,
				HttpServletResponse response, Object handler,
				ModelAndView modelAndView);
}
