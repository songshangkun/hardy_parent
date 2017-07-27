package hardy.special.simple;

import hardy.securite.HardyAuthorization;
import hardy.securite.RefuseType;
import hardy.securite.exception.RefuseRequestException;
import hardy.securite.handler.annotationHandler.AnnotationEL;
import hardy.securite.handler.annotationHandler.AnnotationHandler;
import hardy.securite.handler.trait.AbstractTrait;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 这个类在session权限未通过验证时返回设置页面url.
 * 其他所有情况直接输出最简单的json.
 * noSessionUrl 权限低
 * startUrl  权限高
 * 内部使用AnnotationHandler
 * @author ssk
 *
 */
public class SimpleInterceptor extends HardyAuthorization implements RefuseType{ 
	 /**
	  * 定制当找不到session中的值时返回的页面
	  * 
	  */
	private String noSessionUrl = "forward:/";
	
	
	private String startUrl ;
	
	
	public SimpleInterceptor(){
		this.addHardyAuth(new AnnotationHandler());
		this.setLinkedType("and");
		this.setTr(new AbstractTrait(){
			@Override
			public void taiterRefuseRequestException(
					HttpServletRequest request, HttpServletResponse response,
					Object handler, RefuseRequestException e) {
				  if(e.getType().equals(SESSION_ATTR)){
					   if(getStartUrl()!=null){
						  if(request.getRequestURI().startsWith(startUrl)){
							  if(noSessionUrl.startsWith("forward:")){
									try {
										String url = noSessionUrl.substring(8, noSessionUrl.length());
										request.getRequestDispatcher(url).forward(request, response);
									} catch (ServletException | IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								   }else{
									   try {
										response.sendRedirect(noSessionUrl);
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								   }
						  }else{
							  writeJson(response,e); 
						  } 
					   }else{
						   if(noSessionUrl.startsWith("forward:")){
								try {
									String url = noSessionUrl.substring(8, noSessionUrl.length());
									request.getRequestDispatcher(url).forward(request, response);
								} catch (ServletException | IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							   }else{
								   try {
									response.sendRedirect(noSessionUrl);
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							   }
					   }
					   
				  }else{
					  writeJson(response,e);
				  }
			}

			@Override
			public void taiterRefuseRequest(HttpServletRequest request,
					HttpServletResponse response, Object handler) {
				writeJson(response);
			}

			@Override
			public void taiterCompletRequest(HttpServletRequest request,
					HttpServletResponse response, Object handler, Exception e) {
				writeJson(response, e);
			}
			
		});
	}

	public String getNoSessionUrl() {
		return noSessionUrl;
	}

	public void setNoSessionUrl(String noSessionUrl) {
		this.noSessionUrl = noSessionUrl;
	}
	
	
    public String getStartUrl() {
		return startUrl;
	}

	public void setStartUrl(String startUrl) {
		this.startUrl = startUrl;
	}

	private void writeJson(HttpServletResponse response,Exception e){
    	response.setContentType("application/json;charset=utf-8");
		PrintWriter out = null;
            try{
             out = response.getWriter();
             out.println("{\"success\":false,\"message\":\""+
                     e.getMessage()
            		 +"\"}") ;
            } catch (IOException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
            finally { 
	            out.close();
	        }
    }
    
    private void writeJson(HttpServletResponse response){
    	response.setContentType("application/json;charset=utf-8");
		PrintWriter out = null;
            try{
             out = response.getWriter();
             out.println("{\"success\":false,\"message\":\""+
                     "您没有权限"
            		 +"\"}") ;
            } catch (IOException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
            finally { 
	            out.close();
	        }
    }

	public AnnotationEL getEl() {
		return ((AnnotationHandler)super.getHardyHandlerByName(AnnotationHandler.class.getName())).getEl();
	}

	public void setEl(AnnotationEL el) {
		AnnotationHandler ah = (AnnotationHandler)super.getHardyHandlerByName(AnnotationHandler.class.getName());
		ah.setEl(el);
	}
    
    
}

