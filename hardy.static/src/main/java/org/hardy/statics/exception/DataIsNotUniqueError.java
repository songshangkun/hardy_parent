package org.hardy.statics.exception;

/**
 * 当系统逻辑上应该有数据，但是现在却没有时抛出该异常
 * 一般是数据库数据产生错误
 * @author 09
 *
 */
public class DataIsNotUniqueError extends SysError{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataIsNotUniqueError() {
		super("data not unique(NULL OR PLUS 1)");
		// TODO Auto-generated constructor stub
	}

	public DataIsNotUniqueError(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public DataIsNotUniqueError(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public DataIsNotUniqueError(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public DataIsNotUniqueError(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	

}
