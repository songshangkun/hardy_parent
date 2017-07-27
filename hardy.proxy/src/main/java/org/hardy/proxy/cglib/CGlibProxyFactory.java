package org.hardy.proxy.cglib;

import java.lang.reflect.Method;

import org.hardy.proxy.exception.ProxyFilterException;
import org.hardy.proxy.iface.ProxyFactory;
import org.hardy.proxy.iface.ProxyMethodFace;
import org.hardy.proxy.pointjoint.PointJoint;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CGlibProxyFactory<T> implements MethodInterceptor, ProxyFactory<T>{
	
	private Object tagetObject;

	private ProxyMethodFace advice;
	
	public CGlibProxyFactory(){
		
		this.advice = new ProxyMethodFace() {

			/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			@Override
			public void begin(Object[] objs) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public boolean isTrue(Object[] objs) {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public Object around(PointJoint pj) {
				return pj.process();
			}

			@Override
			public void afterParam(Object[] objs) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void afterretour(Object[] objs,Object obj) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void excepRaram(Object[] objs) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void excepRetour(Object[] objs,Exception e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void excepException(Object obj) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void finalParam(Object[] objs) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void finalRetour(Object[] objs,Object obj) {
				// TODO Auto-generated method stub
				
			}
			
		  }; 
		}
    
    @SuppressWarnings("unchecked")
	public T getProxy(Object tagetObject){
    	this.tagetObject = tagetObject;
    	Enhancer enhancer = new Enhancer();
    	enhancer.setSuperclass(this.tagetObject.getClass());
    	enhancer.setCallback(this);
    	return (T) enhancer.create();
    }

	@Override
	public Object intercept(Object proxy, Method method, Object[] args,
			MethodProxy methodProxy)  {
		 PointJoint pj = new PointJoint();
		 pj.setObj(this.tagetObject);
		 pj.setMethod(methodProxy);
		 pj.setObjs(args);
		Object result = null;
		 boolean flag = this.advice.isTrue(args);
		 if(!flag) throw new ProxyFilterException("isTrue return false");
			try {
				 this.advice.begin(args);
				 result =  this.advice.around(pj);
				 this.advice.afterParam(args);
				 this.advice.afterretour(args,result);
				
				
			} catch (Exception e) {
				 this.advice.excepRaram(args);
				 this.advice.excepRetour(args,e);
				 this.advice.excepException(e);

			}finally{
					 this.advice.finalParam(args);
					 this.advice.finalRetour(args,result);
			}
		
		return result;
	}
	
	public void setAdvice(ProxyMethodFace advice){
		  this.advice = advice;
	}
	
}
