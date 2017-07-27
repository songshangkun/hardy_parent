package org.hardy.statics.exception;
/**
 * 专门用于参数不能为空的异常提示
 * @author sundyn
 *
 */
public class ParamNotNullException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ParamNotNullException(String paramName) {
		super(paramName+" IS NULL", new Throwable(paramName+"要求不能为空"));	
	}

	
	
	

}
