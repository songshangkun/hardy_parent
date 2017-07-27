package org.hardy.statics.exception;

/**
 * 参数空异常 如果某个参数不应为空时,却等于空则抛出该异常
 * 或者参数应当由可用的值/length>0,却不满足条件
 * @author 09
 *
 */
public class ParamsNullException extends ParamValidateException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ParamsNullException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ParamsNullException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public ParamsNullException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ParamsNullException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ParamsNullException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
   
	
}
