package org.hardy.proxy.pointjoint;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodProxy;

public class PointJoint {
 
    private Object method;
 
    private Object obj;
     
    private Object[] objs;
	
	public Object process() {
		
		try {
			if(method instanceof Method){
				return ((Method)method).invoke(obj, objs);
			}
			if(method instanceof MethodProxy){
				try {
					return ((MethodProxy)method).invoke(obj, objs);
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return null;
	}

	public Object getMethod() {
		return method;
	}

	public void setMethod(Object method) {
		this.method = method;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public Object[] getObjs() {
		return objs;
	}

	public void setObjs(Object[] objs) {
		this.objs = objs;
	}
	
	
}
