package org.hardy.sqlhelper.holographic;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.hardy.springutil.AutoWaveBean;
import org.hardy.springutil.AutoWaveBean.AutoWaveBeanTrait;
import org.hardy.sqlhelper.annotation.structure.Table;

 

public class HologResolve {
    /**
     * 保存全部的表结构和实体对应类
     */
	private Map<Class<?>,AllInfo> globalHolog = new HashMap<Class<?>, AllInfo>();
	 
 
	/**
	 * 将一个类加入解析视图
	 * @param clazz
	 */
	public void addStruct(Class<?> clazz){
		TableConstruct tc = new TableConstruct(clazz);
		AllInfo info = new AllInfo(tc, new OprationalVariable(clazz, tc));
		  this.globalHolog.put(clazz, info);
	}
	
	
	public AllInfo saveAndReturnInfo(Class<?> clazz){
		 AllInfo info = this.globalHolog.get(clazz);
		 if(info==null) {
			 addStruct(clazz);
			 return  this.globalHolog.get(clazz);
		 }else{
			 return info;
		 }
		 
	}
	/**
	 * 根据类获取他的TableConstruct,
	 * 如果没有先创建
	 * @param clazz
	 * @return
	 */
	public TableConstruct getStruct(Class<?> clazz){
		AllInfo info = saveAndReturnInfo(clazz);
		return info.tableConstruct;
	}
	/**
	 * 根据类获取他的OprationalVariable
	 * @param clazz
	 * @return
	 */
	public OprationalVariable getVariabl(Class<?> clazz){
		AllInfo info = saveAndReturnInfo(clazz);
		return info.oprationalVariable;
	}
	 /**
	  * 获取globalHolog
	  * @return
	  */
	public Map<Class<?>, AllInfo> getGlobalHolog() {
		return globalHolog;
	}
  /**
   * 扫描置顶的包
   * @param packageNames
   * @throws ClassNotFoundException
   * @throws IOException
   */
	@SuppressWarnings("unchecked")
	public void setPackageNames(String packageNames) throws ClassNotFoundException, IOException {
		Map<String,AutoWaveBeanTrait> map = new HashMap<String, AutoWaveBean.AutoWaveBeanTrait>();
		map.put(Table.class.getName(), new AutoWaveBeanTrait() {
			
			@Override
			public void raiter(Class<?> c) {
				HologResolve.this.addStruct(c);
			}
		});
		 new AutoWaveBean(packageNames.split(","),map, Table.class); 
	}
	
	  
	 /**
	  * 保存全部已知结构
	  * @author 09
	  *
	  */
    private class AllInfo{
   
    	public AllInfo(TableConstruct tableConstruct,
				OprationalVariable oprationalVariable) {
			super();
			this.tableConstruct = tableConstruct;
			this.oprationalVariable = oprationalVariable;
		}
		public TableConstruct tableConstruct;
    	public OprationalVariable oprationalVariable;
    } 
}
