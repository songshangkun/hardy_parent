package org.hardy.statics.exception;

/**
 * 请求参数异常,包括参数格式不正确,类型不对等异常
 * @author sundyn
 *
 */
public class RequestParamException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RequestParamException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RequestParamException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public RequestParamException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public RequestParamException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public RequestParamException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	

}
