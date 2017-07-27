package org.hardy.statics.exception;

/**
 * 自定义的处理方法在netty服务中发生异常,
 * 通常这种异常与netty无关
 * @author sundyn
 *
 */
public class CustomInnerErrorException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomInnerErrorException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CustomInnerErrorException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public CustomInnerErrorException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public CustomInnerErrorException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public CustomInnerErrorException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	

}
