package hardy.securite.handler.annotationHandler;

import hardy.securite.annotation.AuthCheckEnty;

import javax.servlet.http.HttpServletRequest;

/**
 * 一个在生成AuthCheckEnty对象时的拦截接口
 * @author ssk
 *
 */
public interface URLAnnotationEx {
     /**
      * 改变读取Annotation后生成的AuthCheckEnty对象
      * @param ace
      * @param request
      */
	 void control(AuthCheckEnty ace,HttpServletRequest request);
}
