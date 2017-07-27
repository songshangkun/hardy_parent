package org.hardy.util.extention;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class DuplicateMap<K, V>   implements Map<K, V>{
 
	public static class Entry<K,V>{
		  Object key;
		  Object value;
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((key == null) ? 0 : key.hashCode());
			result = prime * result + ((value == null) ? 0 : value.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Entry other = (Entry) obj;
			if (key == null) {
				if (other.key != null)
					return false;
			} else if (!key.equals(other.key))
				return false;
			if (value == null) {
				if (other.value != null)
					return false;
			} else if (!value.equals(other.value))
				return false;
			return true;
		}
		@Override
		public String toString() {
			return "Entry [key=" + key + ", value=" + value + "]";
		}
		public Object getKey() {
			return key;
		}
		public void setKey(Object key) {
			this.key = key;
		}
		public Object getValue() {
			return value;
		}
		public void setValue(Object value) {
			this.value = value;
		}
		  
	}
    private int fixSize = 0;
    private StackRegulier stack ;
	public DuplicateMap(int initialCapacity,boolean fixSize,StackRegulier stack){
		if(null==stack) this.stack = StackRegulier.NINO;
		else  this.stack = stack;
		  if(fixSize)this.fixSize = initialCapacity;
		  core = new ArrayList<Entry<K,V>>(initialCapacity);
	}
	public DuplicateMap(int initialCapacity){
		 core = new ArrayList<Entry<K,V>>(initialCapacity);
	}
	public DuplicateMap(){
		 core = new ArrayList<Entry<K,V>>();
	}
	 private List<Entry<K, V>> core;
	 
	@Override
	public int size() {
		return core.size();
	}

	@Override
	public boolean isEmpty() {
		return core.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		
			 for(Entry<K, V> entry:core){
				    if(key==entry.key||(entry.key!=null&&entry.key.equals(key))) return true;			 		
		      }
		 return false;
	}

	@Override
	public boolean containsValue(Object value) {
		for(Entry<K, V> entry:core){
		    if(value==entry.value||(entry.value!=null&&entry.value.equals(value))) return true;
	 }
		return false;
	}
   
	/**
	 * 找到key指向的第一个value
	 * @param key  
	 */
	@Override
	public V get(Object key) {
		for(Entry<K, V> entry:core){
			 if(key==entry.key||(entry.key!=null&&entry.key.equals(key))) return (V)entry.value;
	 }
		return null;
	}


	@Override
	public V put(K key, V value) {	
		Entry<K, V> entry = new DuplicateMap.Entry();
		entry.key = key;
		entry.value = value;
		if(fixSize>0){
			switch (stack) {
			case FIFO:
				if(this.core.size()<this.fixSize)this.core.add(entry);
				else {
					this.core.remove(0);
					this.core.add(entry);
				}
				break;
			case FILO:
				if(this.core.size()<this.fixSize)this.core.add(0,entry);
				else {
					this.core.remove(0);
					this.core.add(0,entry);
				}
				break;
			case NINO:
				if(this.core.size()<this.fixSize)this.core.add(entry);
				break;
			default:
				break;
			}
		}else{
			this.core.add(entry);
		}
		return (V)entry.value;
	}
    
	/**
	 * 根据key移除 第一个匹配的Entry
	 * @param key 
	 */
	@Override
	public V remove(Object key) {
		for(Entry<K,V> entry:core){
			  if(key==entry.key||(entry.key!=null&&entry.key.equals(key))){
				core.remove(entry);
				  return (V) entry.key;
			  }
		}
		return null;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		if(fixSize>0){
			if(m.size()>fixSize) throw new RuntimeException(" this collection's size must be < "+this.fixSize);
			if(this.core.size()+m.size()<fixSize){
				 for(Map.Entry<? extends K, ? extends V> e:m.entrySet()){
				        Entry<K,V> e2 = new Entry<K, V>();
				        		e2.key = e.getKey();
				        		e2.value = e.getValue();
				        		core.add(e2);
			  }
			}else{
				switch (stack){
				case FIFO:		
						 //1-取出先进的等于m.size数量的元素
						 for(int i=0;i<m.size();i++){
							  if(null!=this.core.get(0))this.core.remove(0);
							  else break;
						 }
						 for(Map.Entry<? extends K, ? extends V> e:m.entrySet()){
						        Entry<K,V> e2 = new Entry<K, V>();
						        		e2.key = e.getKey();
						        		e2.value = e.getValue();
						        		core.add(e2);
					  }
					break;
				case FILO:			
						 //1-取出后进的等于m.size数量的元素
						 for(int i=0;i<m.size();i++){
							  if(!this.core.isEmpty()&&null!=this.core.get(this.core.size()-1))this.core.remove(this.core.size()-1);
							  else break;
						 }
						 for(Map.Entry<? extends K, ? extends V> e:m.entrySet()){
						        Entry<K,V> e2 = new Entry<K, V>();
						        		e2.key = e.getKey();
						        		e2.value = e.getValue();
						        		core.add(0,e2);
					  }
					break;
				case NINO:
					break;
				default:
					break;
				}
			}	
		}else{
			 for(Map.Entry<? extends K, ? extends V> e:m.entrySet()){
			        Entry<K,V> e2 = new Entry<K, V>();
			        		e2.key = e.getKey();
			        		e2.value = e.getValue();
			        		core.add(e2);
		  }
		}
		 
	}

	@Override
	public void clear() {
		  core.clear();
	}

	@Override
	public Set<K> keySet() { 
		Set<K> set = new HashSet<K>();
		for(Entry<K,V> e:core){
			 set.add((K)e.key);
		}
		return set;
	}

	@Override
	public Collection<V> values() {
		List<V> list = new ArrayList<V>();
		for(Entry<K,V> e:core){
			 list.add((V)e.value);
		}
		return list;
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		Set<Map.Entry<K,V>> set = new HashSet<Map.Entry<K,V>>();
		Map<K,V> map  = null;
		for(Entry<K,V> e:core){
			 map = new HashMap<K, V>();
			map.put((K)e.key, (V)e.value);
			set.add(map.entrySet().iterator().next());
		}
		return set;
	}
	
 
	public List<Entry<K, V>> entryList() {
		return core;
	}
  /**
   * 获取相同key对应的全部value
   * @param key
   */
	public List<V> getAll(Object key){
		List<V> list = new ArrayList<V>();
		for(Entry<K, V> e:core){
			 if(key==e.key||(e.key!=null&&e.key.equals(key))) list.add((V)e.value);
		}
		return list;
	}
	/**
	 * 获取相同key对应的全部Entry
	 * @param key
	 * @return
	 */
	public List<V> getAllEntry(Object key){
		List<Entry<K, V>> list = new ArrayList<DuplicateMap.Entry<K,V>>();
		for(Entry<K, V> e:list){
			 if(key==e.key||(e.key!=null&&e.key.equals(key))) list.add(e);
		}
		return null;
	}
	/**
	 * 移除key对应的全部value
	 * @param key
	 * @return
	 */
	public int removeAll(Object key){
		List<Entry<K, V>> list = new ArrayList<DuplicateMap.Entry<K,V>>();
		int n = 0;
		for(int i=0;i<core.size();i++){
			 if(key==core.get(i).key||(core.get(i).key!=null&&core.get(i).key.equals(key))){
				 list.add(core.get(i));
				 n++;
			 }
		}
	core.removeAll(list);
		return n;
	}
	/**
	 * 移除全部的指定value
	 * @param value
	 * @return
	 */
	public int removeValue(Object value){
		int i = 0;
		for(Entry<K, V> e:core){
			 if(e.value.equals(value)){
				 core.remove(e);
				 i++;
			 }
		}
		return i;
	}
	/**
	 * 按内部index移除
	 * @param index
	 * @return
	 */
	public Entry<K,V> remove(int index) {
		return this.core.remove(index);
	}
	
	public Entry<K,V> removeFirst() {
		return this.core.remove(0);
	}
	
	public Entry<K,V> removeLast() {
		return this.core.remove(this.core.size()-1);
	}
	
	
	
	/**
	 * 返回全部的key
	 * @return
	 */
	public List<K> keyList() { 
		List<K> list = new ArrayList<K>();
		for(Entry<K,V> e:core){
			 list.add((K)e.key);
		}
		return list;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((core == null) ? 0 : core.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DuplicateMap other = (DuplicateMap) obj;
		if (core == null) {
			if (other.core != null)
				return false;
		} else if (!core.equals(other.core))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DuplicateMap [core=" + core + "]";
	}
	/**
	 * 会去除相同的key，保留第一个添加的
	 * @return
	 */
	 public HashMap<K,V>  convertHashMap(){
		 HashMap<K,V> map = new HashMap<K, V>();
		     map.putAll(this);
		     return map;
	 }
	 
	 /**
	  * 将一个Map转为DuplicateMap
	  * @param map
	  * @param clear  是否清空原有数据
	  * @return
	  */
	 public DuplicateMap<K,V>  convertHashMap(Map<K,V> map,boolean clear){
		  if(clear)this.clear();
		     this.putAll(map);
		     return this;
	 }
    
	 
}
