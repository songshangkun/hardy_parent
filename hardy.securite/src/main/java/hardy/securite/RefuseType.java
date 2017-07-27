package hardy.securite;

public interface RefuseType {
     /**
      * 代表拒绝原因  参数不符
      */
	  String PARAM = "PARAM";
	  /**
	      * 代表拒绝原因  IP不符
	      */
	  String IP = "IP";
	  /**
      * 代表拒绝原因  角色不符
      */
	  String ROLE = "ROLE";
	  /**
      * 代表拒绝原因 权限不符
      */
	  String DROIT = "DROIT";
	  /**
      * 代表拒绝原因  用户名密码不符
      */
	  String USERPASS = "USERPASS";
	  /**
	   * 代表拒绝原因  EL不符
	   */
	  String EL = "EL";
	  /**
	   * 代表拒绝原因  Requst中参数不符
	   */
	  String REQUEST_ATTR = "REQUEST_ATTR";
	  /**
	   * 代表拒绝原因  SESSION中参数不符
	   */
	  String SESSION_ATTR = "SESSION_ATTR";
	  /**
	   * 代表拒绝原因  SESSION中参数不符
	   */
	  String SERVLETCONTEXT_ATTR = "SERVLETCONTEXT_ATTR";
	  /**
	   * 系统不支持
	   */
	  String NOT_IMPLEMENT = "NOT_IMPLEMENT";
	  /**
	   * 找不到要找的值
	   */
	  String NOT_FIND = "NOT_FIND";
	  /**
	   * 代表拒绝原因  其他情况
	   */
	  String OTHER = "OTHER";
	  
	  
}
