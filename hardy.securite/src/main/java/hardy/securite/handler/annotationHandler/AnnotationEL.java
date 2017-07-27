package hardy.securite.handler.annotationHandler;

import javax.servlet.http.HttpServletRequest;

import hardy.securite.exception.RefuseRequestException;

public interface AnnotationEL {
      /**
       * 读取自定义el语句
       * @param request
       * @param e_langage
       * @throws RefuseRequestException
       */
	  public void readEL(HttpServletRequest request,String... e_langage)throws RefuseRequestException;
}
