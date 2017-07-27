package org.hardy.statics.exception;

/**
 * 得到的结果与逻辑判断不符合,有些问题可能不影响功能的使用
 * 但是却与逻辑判断结果不符合，使用这个异常抛出
 * 如直接删除或改变了数据库的某条数据的id,所以在查找这个id时没有数据。
 * 按逻辑传输的id应为已知的数据，所以应该有数据
 * @author sundyn
 *
 */
public class LogicException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LogicException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LogicException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public LogicException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public LogicException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public LogicException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	

}
