package hardy.securite.handler.sqlhandler;

import hardy.securite.exception.RefuseRequestException;

import javax.servlet.http.HttpServletRequest;

/**
 * 调取数据库数据接口
 * @author 09
 *
 */
public interface ExcuteSQL {
	   /**
	    * 根据http传输参数执行sql
	    * @param name
	    * @param sql
	    * @param request
	    */
	   void excute(String name,String sql,HttpServletRequest request) throws RefuseRequestException;
	   
	   
}
