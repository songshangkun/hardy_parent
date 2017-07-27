package hardy.securite.handler.webHandler;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hardy.securite.RefuseType;
import hardy.securite.exception.RefuseRequestException;

/**
 * 默认的WebHandlerTrait接口实现类
 * @author ssk
 *
 */
public class DefaultWebHandlerTrait implements WebHandlerTrait,RefuseType{
 
	@Override
	public void traitScopeSpaceApplication(HttpServletRequest request,
			Map<String,Object> parmas) throws RefuseRequestException {
		 for(String key:parmas.keySet()){
			 if(parmas.get(key)==null)
				 throw new RefuseRequestException(RefuseType.SERVLETCONTEXT_ATTR, "attrName:"+key+" IS NULL");
		 }	 
	}

	@Override
	public void traitScopeSpacePageContext(HttpServletRequest request,
			Map<String,Object> parmas) throws RefuseRequestException {
		throw new RefuseRequestException(RefuseType.NOT_IMPLEMENT, "暂不支持该scope="+ScopeSpace.PAGECONTEXT_SCOPE);
	}

	@Override
	public void traitScopeSpaceRequest(HttpServletRequest request,
			Map<String,Object> parmas) throws RefuseRequestException {
		for(String key:parmas.keySet()){
			 if(parmas.get(key)==null)
				 throw new RefuseRequestException(RefuseType.REQUEST_ATTR, "attrName:"+key+" IS NULL");
		 }	
	}

	@Override
	public void traitScopeSpaceSession(HttpServletRequest request,
			Map<String,Object> parmas) throws RefuseRequestException {
		for(String key:parmas.keySet()){
			 if(parmas.get(key)==null)
				 throw new RefuseRequestException(RefuseType.SESSION_ATTR, "attrName:"+key+" IS NULL");
		 }	
	}

}
