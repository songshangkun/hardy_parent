package org.hardy.springutil.webservice;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 实现CallCapital的抽象类
 * @author ssk
 *
 */
public abstract class CallAbstract implements CallCapital {

	@Override
	public Object callback(Method method,Object... objects) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Object resulta = method.invoke(this,objects);
		return resulta;
	}

	@Override
	public List<String> getAllRegistHelper() {
		return MethodRepository.allbeansHelper();
	}
	
	
	
	

}
