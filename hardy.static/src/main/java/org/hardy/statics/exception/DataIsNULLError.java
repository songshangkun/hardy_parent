package org.hardy.statics.exception;

/**
 * 当系统逻辑上应该有数据，但是现在却没有时抛出该异常
 * 一般是数据库数据产生错误
 * @author 09
 *
 */
public class DataIsNULLError extends SysError{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataIsNULLError() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DataIsNULLError(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public DataIsNULLError(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public DataIsNULLError(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public DataIsNULLError(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	

}
