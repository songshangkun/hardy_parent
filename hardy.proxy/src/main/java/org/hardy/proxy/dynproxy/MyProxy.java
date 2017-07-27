package org.hardy.proxy.dynproxy;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.hardy.proxy.exception.ProxyFilterException;
import org.hardy.proxy.iface.ProxyFactory;
import org.hardy.proxy.iface.ProxyMethodFace;
import org.hardy.proxy.pointjoint.PointJoint;


/**
 * 这个类模仿aop的切面增强的功能
 * 继承此类就不用实现ProxyMethodFace的全部方法
 * **/
public class MyProxy<T> implements InvocationHandler, ProxyFactory<T>{
   private Object obj;
   private ProxyMethodFace advice;
   public MyProxy(){	  
	   advice  = new ProxyMethodFace() {

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
		
    
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		 PointJoint pj = new PointJoint();
		 pj.setObj(obj);
		 pj.setMethod(method);
		 pj.setObjs(args);
		 Object result = null;
		 
		  boolean flag = this.advice.isTrue(args);
		 if(!flag)throw new ProxyFilterException("isTrue return false");
			 try{
				 this.advice.begin(args);
				 result = this.advice.around(pj); 
				 this.advice.afterParam(args);
				 this.advice.afterretour(args,result);
			     }catch(Exception e){
			    	 this.advice.excepRaram(args);
			    	 this.advice.excepRetour(args,e);
			    	 this.advice.excepException(e);
			 }finally{
				 this.advice.finalParam(args);
				this.advice.finalRetour(args,result);	
			 }
			return result;
	}
  	
	/* (non-Javadoc)
	 * @see com.song.tool.aop.proxy.dynproxy.ProxyFactory#getProxy(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T getProxy(Object obj){
	   this.obj = obj;
	   return (T) Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), this);
   }
   
  
   /* (non-Javadoc)
 * @see com.song.tool.aop.proxy.dynproxy.ProxyFactory#setAdvice(com.song.tool.aop.proxy.iface.ProxyMethodFace)
 */
@Override
public void setAdvice(ProxyMethodFace advice){
	    this.advice = advice;
   }
   
}

