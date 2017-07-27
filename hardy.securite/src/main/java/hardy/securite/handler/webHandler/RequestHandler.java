package hardy.securite.handler.webHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hardy.securite.HardyAuthInterface;
import hardy.securite.RefuseType;
import hardy.securite.exception.RefuseRequestException;

/**
 * 基于request请求的权限处理
 * @author ssk
 *
 */
public class RequestHandler implements HardyAuthInterface{
	
	/**
	 * 排序
	 */
	private int order = 0;
	/**
	 * 标志
	 */
	private String name = this.getClass().getName();
	/**
	 * 是否打开
	 */
	private boolean open = true;
	 
	/**
	 * 记录ScopeSpace
	 */
	private List<ScopeSpace> scopes = new ArrayList<ScopeSpace>() ;
	/**
	 * 具体处理接口
	 */
    private WebHandlerTrait webHandler = new DefaultWebHandlerTrait();

	@Override
	public void valid(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws RefuseRequestException{
		  for(ScopeSpace scopeSpace:scopes){
			  if(scopeSpace.getScope().equalsIgnoreCase(ScopeSpace.PAGECONTEXT_SCOPE)){
				  throw new RefuseRequestException(RefuseType.NOT_IMPLEMENT, "暂不支持该scope="+ScopeSpace.PAGECONTEXT_SCOPE);
			 }else if(scopeSpace.getScope().equalsIgnoreCase(ScopeSpace.REQUEST_SCOPE)){
				 Map<String,Object> params = new HashMap<String,Object>();   
				 for(String name:scopeSpace.getNames()){
					 params.put(name, request.getAttribute(name));
				    }
				 webHandler.traitScopeSpaceRequest(request, params);
			 }else if(scopeSpace.getScope().equalsIgnoreCase(ScopeSpace.SESSION_SCOPE)){
				 Map<String,Object> params = new HashMap<String,Object>();   
				 for(String name:scopeSpace.getNames()){
					 params.put(name, request.getSession().getAttribute(name));
				    }
				 webHandler.traitScopeSpaceSession(request, params); 
			 }else if(scopeSpace.getScope().equalsIgnoreCase(ScopeSpace.APPLICATIO_SCOPE)){
				 Map<String,Object> params = new HashMap<String,Object>();   
				 for(String name:scopeSpace.getNames()){
					 params.put(name, request.getServletContext().getAttribute(name));
				    }
				 webHandler.traitScopeSpaceApplication(request, params); 
			 }
		  }
		 
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return this.order;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

	@Override
	public boolean open() {
		// TODO Auto-generated method stub
		return this.open;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public List<ScopeSpace> getScopes() {
		return scopes;
	}

	public void setScopes(List<ScopeSpace> scopes) {
		this.scopes = scopes;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public void setName(String name) {
		this.name = name;
	}

	public WebHandlerTrait getWebHandler() {
		return webHandler;
	}

	public void setWebHandler(WebHandlerTrait webHandler) {
		this.webHandler = webHandler;
	}
    
	
	public void setScope(String[] scopeCodes){
		for(String code:scopeCodes){
			ScopeSpace space = new ScopeSpace();
			space.setCode(code);
			this.scopes.add(space);
		}
	}
	
	public void addScope(String scopeCode){
			ScopeSpace space = new ScopeSpace();
			space.setCode(scopeCode);
			this.scopes.add(space);
		 
	}

}
