package org.hardy.statics.exception;

/**
 * 非逻辑性错误
 * @author 09
 *
 */
public class SysError extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SysError() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SysError(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public SysError(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public SysError(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public SysError(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
