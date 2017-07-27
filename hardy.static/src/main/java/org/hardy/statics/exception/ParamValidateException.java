package org.hardy.statics.exception;

/**
 * 定义参数没有通过验证
 * 如某些参数不能为空等，或参数不合法
 * @author 09
 *
 */
public class ParamValidateException extends LogicielException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ParamValidateException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ParamValidateException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public ParamValidateException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ParamValidateException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ParamValidateException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
   
	
	
}
