package org.hardy.netty.http.tropsimpl;

import java.util.Map;
/**
 * 处理具体url和参数的方法
 * @author 09
 *
 */
public interface SimpleTrait {
      /**
       * 具体处理方法
       * @param uri
       * @param params
       */
	  void traiter(String uri,Map<String,Object> params);
}
