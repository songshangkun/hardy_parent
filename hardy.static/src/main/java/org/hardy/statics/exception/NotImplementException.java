package org.hardy.statics.exception;

/**
 * 自定义未实现方法时的异常
 * @author sundyn
 *
 */
public class NotImplementException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotImplementException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NotImplementException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public NotImplementException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public NotImplementException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public NotImplementException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	

}
