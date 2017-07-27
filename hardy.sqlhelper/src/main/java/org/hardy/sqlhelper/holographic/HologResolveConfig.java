//package org.hardy.sqlhelper.holographic;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.hardy.springutil.AutoWaveBean;
//import org.hardy.springutil.AutoWaveBean.AutoWaveBeanTrait;
//import org.hardy.springutil.AutoWaveEntityBean;
//import org.hardy.springutil.AutoWaveMethod;
//import org.hardy.sqlhelper.annotation.structure.Table;
//
///**
// * HologResolve的扫描配置类
// * @author songs
// *
// */
//public class HologResolveConfig {
//   
//
//	private HologResolve hologResolve;
//
//	@SuppressWarnings("unchecked")
//	public void setPackageNames(String packageNames) throws ClassNotFoundException, IOException {
//		hologResolve = new HologResolve();
//		Map<String,AutoWaveBeanTrait> map = new HashMap<String, AutoWaveBean.AutoWaveBeanTrait>();
//		map.put(Table.class.getName(), new AutoWaveBeanTrait() {
//			
//			@Override
//			public void raiter(Class<?> c) {
//				hologResolve.addStruct(c);
//			}
//		});
//		 new AutoWaveBean(packageNames.split(","),map, Table.class); 
//	}
//
//	public HologResolve getHologResolve() {
//		return hologResolve;
//	}
//
//	 
//	
//	 
//}
