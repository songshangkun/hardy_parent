package hardy.securite;

import hardy.securite.exception.RefuseRequestException;
import hardy.securite.handler.trait.AbstractTrait;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
/**
 * 主程序调用HardyAuthInterface
 * linkedType: none 关闭  and or
 * @author ssk
 *
 */
public class HardyAuthorization extends HandlerInterceptorAdapter{
     /**
      * 是否控制台输出哪个handler拦截
      */
	 private boolean debug = true;
	 /**
	  * 对于打开认证的连接关系，and 或 or
	  */
	 private String linkedType = "and";
	 /**
	  * 排序
	  */
	 private Comparator<HardyAuthInterface> comparator = new ComparatorHandler();
	 /**
	  * 内部处理容器
	  */
	 private List<HardyAuthInterface> hardyHandler = new ArrayList<HardyAuthInterface>();
	 /**
	  * 用于权限被拒绝后对返回的处理方法
	  * 可能返回json或跳转页面
	  */
	 private TraitRefuse tr = new AbstractTrait();
	  
    /**
     * 提交前过滤主要用于权限的认证
     */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if("none".equalsIgnoreCase(linkedType)) return true; 
		if("and".equalsIgnoreCase(linkedType)){
			 for(HardyAuthInterface hardy:hardyHandler){
				 if(hardy.open()){
					 if(debug)System.out.println(hardy.getName()+" Intercept request ....");
					 try{
						 hardy.valid(request, response, handler); 
					 }catch(RefuseRequestException e){
						 if(debug)System.out.println(hardy.getName()+" Intercept request success"); 
						 if(tr!=null)tr.taiterRefuseRequestException(request, response, handler, e);
						 return false;
					 }
				 }
				 
			 }
			 return true;
		 }else if("or".equalsIgnoreCase(linkedType)){
			 for(HardyAuthInterface hardy:hardyHandler){
				 if(hardy.open()){
					 if(debug)System.out.println(hardy.getName()+" Intercept request ....");
					 try{
						 hardy.valid(request, response, handler);
						 if(debug)System.out.println(hardy.getName()+" Through request success"); 
						   return true;
					 }catch(RefuseRequestException e){
						  continue;
					 }
				 }
			 }
			 if(tr!=null)tr.taiterRefuseRequest(request, response, handler);
			 return false;
		 }else{
			 return false;
		 }
	}
    /**
     * 提交后过滤主要用于返回结果再加工
     */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		if(tr!=null)tr.afterPost(request, response, handler, modelAndView);
	}
  /**
   * 请求全部完成后过滤，主要用于异常结果捕获
   */
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		 if(tr!=null)tr.taiterCompletRequest(request, response, handler, ex);
	}

		public String getLinkedType() {
			return linkedType;
		}
		public void setLinkedType(String linkedType) {
			this.linkedType = linkedType;
		}
		public List<HardyAuthInterface> getHardyHandler() {
			return hardyHandler;
		}
		public void setHardyHandler(List<HardyAuthInterface> hardyHandler) {
			Collections.sort(hardyHandler,comparator);
			this.hardyHandler = hardyHandler; 
		}
		
		public boolean isDebug() {
			return debug;
		}
		public void setDebug(boolean debug) {
			this.debug = debug;
		}
		public TraitRefuse getTr() {
			return tr;
		}
		public void setTr(TraitRefuse tr) {
			this.tr = tr;
		}
		/**
		 * 添加一个验证
		 * @param hardyHandler
		 */
		public void addHardyAuth(HardyAuthInterface hardyHandler){
			this.hardyHandler.add(hardyHandler);
			Collections.sort(this.hardyHandler,comparator);
		}
		/**
		 * 对HardyAuthInterface进行排序
		 * @author sundyn
		 *
		 */
	   private class ComparatorHandler implements Comparator<HardyAuthInterface>{

		@Override
		public int compare(HardyAuthInterface o1, HardyAuthInterface o2) {
			 
			return o1.getOrder()-o2.getOrder();
		}
		   
	   }
	   /**
	    * 根据名称查找HardyAuthInterface
	    * @param name
	    * @return
	    */
      public HardyAuthInterface getHardyHandlerByName(String name){
    	    for(HardyAuthInterface hinf:hardyHandler){
    	    	if(hinf.getName().equals(name)){
    	    		 return hinf;
    	    	}
    	    }
    	    return null;
      }
}
