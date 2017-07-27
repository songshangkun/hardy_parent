package org.hardy.proxy.iface;


public interface ProxyFactory<T> {

	public abstract T getProxy(Object obj);

	public abstract void setAdvice(ProxyMethodFace advice);

}