package org.hardy.proxy.advice;

import org.hardy.proxy.iface.ProxyMethodFace;
import org.hardy.proxy.pointjoint.PointJoint;

/**
 * 这个类模仿aop的切面增强的功能
 * 继承此类就不用实现ProxyMethodFace的全部方法
 * **/
public class Advice implements ProxyMethodFace{
    
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
		Exception e = (Exception)obj;
		System.out.println("massage:"+e.getMessage());
		
	}

	@Override
	public void finalParam(Object[] objs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void finalRetour(Object[] objs,Object obj) {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	public void termineParam(Object[] objs) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void termineretour(Object[] objs,Object obj) {
//		// TODO Auto-generated method stub
//		
//	}

	
     
}
