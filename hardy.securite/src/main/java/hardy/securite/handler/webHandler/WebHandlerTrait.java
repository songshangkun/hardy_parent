package hardy.securite.handler.webHandler;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hardy.securite.exception.RefuseRequestException;

/**
 * 对RequestHandler处理的接口
 * @author ssk
 *
 */
public interface WebHandlerTrait {
    /**
     * 处理Application
     * @param request
     * @param parmas
     * @throws RefuseRequestException
     */
	void traitScopeSpaceApplication(HttpServletRequest request,Map<String,Object> params) throws RefuseRequestException;

	/**
     * 处理PageContext
     * @param request
     * @param parmas
     * @throws RefuseRequestException
     */
	void traitScopeSpacePageContext(HttpServletRequest request,Map<String,Object> params) throws RefuseRequestException;
	
	/**
     * 处理Request
     * @param request
     * @param parmas
     * @throws RefuseRequestException
     */
	void traitScopeSpaceRequest(HttpServletRequest request,Map<String,Object> params) throws RefuseRequestException;
	
	/**
     * 处理Session
     * @param request
     * @param parmas
     * @throws RefuseRequestException
     */
	void traitScopeSpaceSession(HttpServletRequest request,Map<String,Object> params) throws RefuseRequestException;
}
