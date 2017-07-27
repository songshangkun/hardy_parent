package org.hardy.proxy;

import org.hardy.proxy.cglib.CGlibProxyFactory;
import org.hardy.proxy.dynproxy.MyProxy;
import org.hardy.proxy.iface.ProxyFactory;
import org.hardy.proxy.iface.ProxyMethodFace;
 

public class Proxy<T>{
	private T bean ;
	private ProxyMethodFace advice;
	private ProxyFactory<T>  proxyFactory;
	
	 public Proxy(){}
	 
	 public Proxy(T t,ProxyMethodFace advice){
		 this.bean = t;
		 this.advice = advice;
	 }
	 
	 public void setBean(T t){
		 this.bean = t;
	 }
	 public void setAdvice(ProxyMethodFace advice){
		 this.advice = advice;
	 }
	 
	 public T cglib(){
		  this.proxyFactory = new CGlibProxyFactory<>();
		  this.proxyFactory.setAdvice(this.advice);
		  return  this.proxyFactory.getProxy(this.bean);
	 }
	 
	 public T dynProxy(){
		  this.proxyFactory = new MyProxy<>();
		  this.proxyFactory.setAdvice(this.advice);
		  return  this.proxyFactory.getProxy(this.bean);
	 }
	
	 public static <E>E cglib(E e,ProxyMethodFace advice){
		 ProxyFactory<E> proxyFactory = new CGlibProxyFactory<>();
		 proxyFactory.setAdvice(advice);
		  return  proxyFactory.getProxy(e);
	 }
	 
	 public static <E>E dynProxy(E e,ProxyMethodFace advice){
		 ProxyFactory<E> proxyFactory = new MyProxy<>();
		 proxyFactory.setAdvice(advice);
		  return  proxyFactory.getProxy(e);
	 }
}
