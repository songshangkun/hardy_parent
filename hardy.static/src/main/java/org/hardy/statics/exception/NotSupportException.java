package org.hardy.statics.exception;

/**
 * 定义暂时不支持处理的异常
 * @author sundyn
 *
 */
public class NotSupportException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotSupportException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NotSupportException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public NotSupportException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public NotSupportException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public NotSupportException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	

}
