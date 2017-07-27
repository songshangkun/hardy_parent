package hardy.securite.annotation;


/**
 * 承接AuthCheck注解的实体类
 * @author sundyn
 *
 */
public class AuthCheckEnty {
    /**
     * el语句
     */
	private String[] el;
	/**
	 * 提交参数
	 */
	private String param;
	/**
	 * session属性
	 */
	private String[] sessionAttr;
	/**
	 * request属性
	 */
	private String[] requestAttr;
	/**
	 * ip地址白名单
	 */
	private String[] ip;
	/**
	 * ip地址黑名单
	 */
	private String[] refuseIp;
	 
	public String[] getEl() {
		return el;
	}

	public void setEl(String[] el) {
		this.el = el;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String[] getSessionAttr() {
		return sessionAttr;
	}

	public void setSessionAttr(String[] sessionAttr) {
		this.sessionAttr = sessionAttr;
	}

	public String[] getRequestAttr() {
		return requestAttr;
	}

	public void setRequestAttr(String[] requestAttr) {
		this.requestAttr = requestAttr;
	}

	public String[] getIp() {
		return ip;
	}

	public void setIp(String[] ip) {
		this.ip = ip;
	}

	public String[] getRefuseIp() {
		return refuseIp;
	}

	public void setRefuseIp(String[] refuseIp) {
		this.refuseIp = refuseIp;
	}

	 
	
	
	
}
