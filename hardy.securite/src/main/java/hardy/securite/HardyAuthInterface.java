package hardy.securite;

import hardy.securite.exception.RefuseRequestException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 执行认证的统一接口
 * @author sundyn
 *
 */
public interface HardyAuthInterface {
   /**
    * 请求是否可通过认证
    * @param request
    * @param response
    * @param handler
    * @throws RefuseRequestException
    */
	void valid(HttpServletRequest request,
			HttpServletResponse response, Object handler)
			throws RefuseRequestException;
	/**
	 * 返回顺序
	 * @return
	 */
	int getOrder();
	/**
	 * 返回名称
	 * @return
	 */
	String getName();
	/**
	 * 是否启动
	 * @return
	 */
	boolean open();
	
}
