package org.hardy.springutil.webservice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hardy.util.extention.DuplicateMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.TypeUtils;

/**
 * 构思做一个以json作为方法调用并传参数
 * 的传输对象,这个类有两种用法，
 * 当用作制造参数传输时使用public ParamJson(int capacite)或者public ParamJson()构造
 * ，当作为解析json字符串对象时，使用public ParamJson(String jsonString)构造。
 * 注意传递的参数都是一些基本有状态的类，对于流的形式并不支持
 * @author song
 *
 */
public class ParamJson  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用于客户端组织传送参数
	 */
	public ParamJson(){
		core = new ArrayList<>();
	}
	
	/**
	 * 用于客户端组织传送参数
	 * @param capacite  含有参数的个数
	 */
	public ParamJson(int capacite){
		core = new ArrayList<>(capacite+2);
	}
	/**
	 * 由于服务端获取Json后的解析
	 * @param jsonString
	 * @throws java.lang.ClassNotFoundException 
	 * @throws ClassNotFoundException
	 */
	public ParamJson(String jsonString) throws java.lang.ClassNotFoundException{
		  this.analise = getParams(jsonString);
	}
	
	private List<Entity> core;
	
	private Map<String, Object> analise;
	/**
	 * 添加参数
	 * @param key   $bean_id ,$method_id,"0","1"....
	 * @param value
	 * @return
	 */
	public ParamJson put(String key,Object value){
		Entity entity = new Entity();
		entity.setKey(key);
		entity.setValue(value);
		core.add(entity);
		return this;
	}
     
	/**
	 * 将完整的Json消息转化成DuplicateMap对象
	 * @param jsonString
	 * @return
	 * @throws java.lang.ClassNotFoundException 
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getParams(String jsonString) throws java.lang.ClassNotFoundException{
		 JSON json = (JSON)JSONObject.parse(jsonString);
		 List<JSONObject> list = JSONObject.toJavaObject(json, List.class);
		Map<String, Object> map = new DuplicateMap<>(list.size());
		for(JSONObject e:list){ 
			 System.out.println(e.toString()); 
			 Class<?> clazz = Class.forName(e.get("clazz").toString());
			 Object object = TypeUtils.cast(e.get("value"), clazz, ParserConfig.getGlobalInstance());
			 map.put(e.get("key").toString(), object);
		}
		return  map;
	}
	/**
	 * 根据key获取value
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  <T>T getParam(String key){
		if(this.analise==null) return null;
		return (T) this.analise.get(key);
	}
	/**
	 * 设置调用的bean和方法名称
	 * @param token
	 * @param bean_id
	 * @param method_id
	 * @return
	 */
	public ParamJson addBeanMethod(Object token,String bean_id,String method_id){
		core.clear();
		if(token!=null){
			Entity etoken = new Entity();
			etoken.setKey("$token");
			etoken.setValue(token);
			core.add(etoken);
		}
		Entity bean = new Entity();
		bean.setKey("$bean_id");
		bean.setValue(bean_id);
		core.add(bean);
		Entity method = new Entity();
		method.setKey("$method_id");
		method.setValue(method_id);
		core.add(method);
		return this;
	}
	/**
	 * 设置调用的bean和方法名称,带参数
	 * @param token
	 * @param bean_id
	 * @param method_id
	 * @param params
	 * @return
	 */
	public ParamJson addBeanMethod(Object token,String bean_id,String method_id,Object[] params){
		addBeanMethod(token, bean_id, method_id);
		if(params!=null&&params.length>0){
			for(int i=0;i<params.length;i++){
			if(!(params[i] instanceof Entity)){
				Entity pe = new Entity();
				pe.setKey(String.valueOf(i));
				pe.setValue(params[i]);
				core.add(pe);	
			   }else{
				 core.add((Entity)params[i]);	 
			   }
				
			}
		}
		return this;
	}
	/**
	 * 设置调用的bean和方法名称,带参数
	 * @param bean_id
	 * @param method_id
	 * @param params
	 * @return
	 */
	public ParamJson addBeanMethod(String bean_id,String method_id,Object[] params){
		 return addBeanMethod(null, bean_id, method_id, params);
	}
  
	 /**
	  * 将设置的参数全部转化为json消息
	  */
	@Override
	public String toString() {
		return JSONObject.toJSONString(this.core);
	}



	public List<Entity> getCore() {
		return core;
	}

	public Map<String, Object> getAnalise() {
		return analise;
	}



	public static class Entity{
		 private String key;
		 private Object value;
		 private Class<?> clazz;
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public Object getValue() {
			return this.value;
		}
		public void setValue(Object value) {
			if(value!=null){
				this.value = value;
				if(value instanceof List)this.clazz = List.class;
				else if(value instanceof Map)this.clazz = Map.class;
				else if(value instanceof Set)this.clazz = Set.class;
				else this.clazz = value.getClass();
			}
		}
		public Class<?> getClazz() {
			return clazz;
		}
		public void setClazz(Class<?> clazz) {
			this.clazz = clazz;
		}
		@Override
		public String toString() {
			return JSONObject.toJSONString(this);
		}
	 
	} 
	
}
