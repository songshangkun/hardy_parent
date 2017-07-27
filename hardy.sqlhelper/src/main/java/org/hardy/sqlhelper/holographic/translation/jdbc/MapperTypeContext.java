package org.hardy.sqlhelper.holographic.translation.jdbc;

import java.util.HashMap;
import java.util.Map;

import org.hardy.sqlhelper.holographic.translation.inf.MapperType;

/**
 * 保存一系列的MapperType接口实现
 * @author song
 *
 */
public class MapperTypeContext {
     /**
      * 保存MapperType接口实现 key->COLUMN标签的mapperType制定名称
      */
	   private Map<String, MapperType> map = new HashMap<>();
	   
	   public void  addMapperType(String name,MapperType mapper){
		    this.map.put(name, mapper);
	   }
	   
	   public MapperType getMapperType(String name){
		    return this.map.get(name);
	   }
}
