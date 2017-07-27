package org.hardy.util.extention;

import java.util.HashMap;
import java.util.Map;
/**
 * 增加了一个通过value获取key的方法,key和value各自都不能存储相同hashcode对象
 * @author songs
 *
 * @param <K>
 * @param <V>
 */
public class BidirectionalMap<K,V> extends HashMap<K,V>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
     /**
      * 通过value获取key
      * @param v
      * @return
      */
	  public K getK(V v){ 
		  for(Map.Entry<K, V> e:this.entrySet()){
			  if(v.equals(e.getValue())) return  e.getKey();
		  } 
		  return null;
	  }
	@Override
	public V put(K arg0, V arg1) {
		K key = null;
		for(Map.Entry<K, V> e:this.entrySet()){
			  if(arg1.equals(e.getValue())){
				  key = e.getKey();
				  break;
			  }
		  }
		this.remove(key);
		return super.put(arg0, arg1);
	}
	@Override
	public void putAll(Map<? extends K, ? extends V> arg0) { 
		super.putAll(arg0);
	} 
  
}
