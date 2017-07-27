package org.hardy.jsonhelper.form;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.hardy.reflex.SpringReflext;

/**
 * 
 * 内置格式1 {"success":true,"message":xxx,"data":{}}
 * @author 09
 *
 */
public  class JOSNFORMAT{
	 private boolean success;
	 
	 private String message;
	 
	 private Map<String,Object> data = new HashMap<String, Object>();
	 
	 /**
      * 定义JSON返回成功
      * @param msg 成功的消息
      * @return
      */
	 public JOSNFORMAT success(String msg) {
			this.success = true;
			this.message = msg;
			return this;
		} 
	 
	 	/**
		 * 定义JSON返回错误
		 * @param msg 错误的消息
		 * @return
		 */
		public JOSNFORMAT error(String msg) {
			this.success = false;
			this.message = msg;
			return this;
		}
		
		/**
		 * 当失败时注明原因
		 * @param cause
		 * @return
		 */
		public JOSNFORMAT cause(String cause) {
			this.data.put("cause", cause);
			return this;
		}
		
		/**
		 * 当失败时注明原因
		 * @param cause
		 * @return
		 */
		public JOSNFORMAT cause(Exception cause) {
			this.data.put("cause", cause.getMessage());
			return this;
		}
		
		/**
		 * 具体的内容
		 * @param data
		 * @return
		 */
		public JOSNFORMAT data(Object data) {
			this.data.put("data", data);
			return this;
		}
		/**
		 * 对于单独对象的输出格式
		 * @param data
		 * @return
		 */
		public JOSNFORMAT dataUnique(Object data) {
			Field[] fs = data.getClass().getDeclaredFields();
			for(Field f:fs){
			try{
			  this.data.put(f.getName(), SpringReflext.get(data, f.getName()));	
			}catch(Exception e){
				e.printStackTrace();
				continue;
			} 
				
			}
			return this;
		}
		/**
		 * 对于List对象的输出格式
		 * @param datas
		 * @return
		 */
		public JOSNFORMAT dataList(List<?> datas) {
			if(datas!=null){
				for(int i=0;i<datas.size();i++){
					this.data.put(String.valueOf(i), datas.get(i));	
				}
			}
			return this;
		}
		/**
		 * 对于SET对象的输出格式
		 * @param datas
		 * @return
		 */
		public JOSNFORMAT dataList(Set<?> datas) {
			int i = 0;
			if(datas!=null){
				for(Object o:datas){
					this.data.put(String.valueOf(i),o);	
				}
			}
			
			return this;
		}
		/**
		 * 对于MAP对象的输出格式
		 * @param datas
		 * @return
		 */
		public <K,V>JOSNFORMAT dataMap(Map<K,V> datas) {
			if(datas!=null){
				for(K k:datas.keySet()){
					this.data.put(k.toString(), datas.get(k));	
				}
			}
			return this;
		}
		/**
		 * 替换JOSNFORMAT对象中的data
		 * @param datas
		 * @return
		 */
		public <K,V>JOSNFORMAT remplaceMap(Map<String,Object> datas) {
			this.data = datas;
			return this;
		}
		/**
		 * 将一个对象加入到内置的map中
		 * @param key
		 * @param obj
		 * @return
		 */
		public JOSNFORMAT add(String key, Object obj) {
			this.data.put(key, obj);
			return this;
		}
		
		/**
		 * 清除已有的数据
		 */
		public JOSNFORMAT clean(){
			 this.success = false;
			 this.message = null;
			 this.data.clear();
			 return this;
		}
		

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	

	@Override
	public String toString() {
		return "JOSNFORMAT [success=" + success + ", message=" + message
				+ ", data=" + data + "]";
	}
	 
	 
}
