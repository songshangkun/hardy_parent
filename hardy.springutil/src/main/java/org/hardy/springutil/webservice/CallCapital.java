package org.hardy.springutil.webservice;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 继承此接口可以使类具有自身回掉自己方法的可能
 * @author ssk
 *
 */
public interface CallCapital {
         
	    public Object callback(Method method,Object... objects ) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException ;
       
	    public List<String> getAllRegistHelper();
}
