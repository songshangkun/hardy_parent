package org.hardy.statics.exception;

/**
 * 当用户不合法时抛出异常
 * 1-用户名密码错误，用户不存在
 * 2-用户被禁用
 * 3-数据库数据异常，存在同用户名
 * @author 09
 *
 */
public class IllegalUserError extends SysError{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IllegalUserError() {
		super();
		// TODO Auto-generated constructor stub
	}

	public IllegalUserError(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public IllegalUserError(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public IllegalUserError(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public IllegalUserError(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
    
	
}
