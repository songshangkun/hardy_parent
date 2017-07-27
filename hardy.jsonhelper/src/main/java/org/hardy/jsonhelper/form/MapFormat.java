package org.hardy.jsonhelper.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.song.tool.util.servlet.out.AbstractText;
import com.song.tool.util.servlet.out.PrintWriterResource;

public class MapFormat {
	private List<Map<String,Object> >core = new ArrayList<>();
	 /**
	  * 向最上层的map添加json数据
	  * @param name
	  * @param value
	  * @return
	  */
	 public MapFormat addProperties(String name,Object value){ 
		  Map<String,Object>  map = null;
		  if(core.isEmpty()){
			   map =  new HashMap<>();
			   map.put(name, value);
			   core.add(map);
		  }else{
			  map =  core.get(0);
			  map.put(name, value);
		  }
		    return this;
	 }
	 /**
	  * 直接将键值对添加到一个新的map中去
	  * @param name
	  * @param value
	  * @return
	  */
	 public MapFormat addnewMap(String name,Object value){ 
		  Map<String,Object>  map = new HashMap<>();
		   map.put(name, value);
		   core.add(map);
		    return this;
	 }
	 /**
	  * 在最后一个map添加json数据
	  * @param name
	  * @param value
	  * @return
	  */
	 public MapFormat addCurrentMap(String name,Object value){ 
		  Map<String,Object>  map = null;
		  if(core.isEmpty()){
			   map =  new HashMap<>();
			   map.put(name, value);
			   core.add(map);
		  }else{
			  map = core.get(core.size()-1);
			  map.put(name, value);
		  }
		    return this;
	 }
	 /**
	  * 增加一个map
	  * @param map
	  * @return
	  */
	 public MapFormat addMap(Map<String, Object> map){ 
		   core.add(map);
		    return this;
	 }
	 /**
	  * 直接输出对象json
	  * @param prettyFormat
	  * @return
	  */
	 public String  toJsonString(boolean prettyFormat){
		  String string =  null;
		  if(this.core.size()==1)string = JSONObject.toJSONString(this.core.get(0), prettyFormat);
		  else string = JSONArray.toJSONString(this.core, prettyFormat);
		    return string;
	 }
	 /**
	  * 输出数组json
	  * @param prettyFormat
	  * @return
	  */
	 public String  toJsonArrayString(boolean prettyFormat){
		    return  JSONObject.toJSONString(this.core, prettyFormat);
	 }
    
	 /**
	  * 在终端加上边界的json键值对
	  * @param key
	  * @param value
	  * @return
	  */
	 public MapFormat addFirstFontElement(String key,Object value){
		     List<Map<String, Object>> oldCoreList = this.core;
		     this.core = new ArrayList<>(1);
		     Map<String, Object> map = new HashMap<>();
		     map.put(key, value);
		     map.put("data", oldCoreList);
		     this.core.add(map);
		     return this;
	 }
	 /**
	  * 设置返回标识为success:true 并且附上message:xxx信息
	  * @param message
	  * @return
	  */
	 public MapFormat ok(String message){
		  this.addCurrentMap("success", true);
		  this.addCurrentMap("message", message);
		  return this;
	 }
	 /**
	  * 设置返回标识为success:false 并且附上message:xxx信息
	  * @param message
	  * @return
	  */
	 public MapFormat error(String message){
		  this.addCurrentMap("success", false);
		  this.addCurrentMap("message", message);
		  return this;
	 }
	 
	 
	 /**
	  * response直接输出json
	  * @param obj
	  * @param response
	  */
	 public static void printJson(final Object obj,HttpServletResponse response){
		 PrintWriterResource.responseJson(response, new AbstractText() {
			
			@Override
			public String excuterS() {
				return JSONObject.toJSONString(obj);
			}
		});
	 }
	 
	 @Override
	public String toString() {
		return "MapForm [core=" + core + "]";
	}
	 
	 
	 public static MapFormat OK(){
		 return new MapFormat().ok("success");
	 }
	 
	 public static MapFormat ERROR(){
		 return new MapFormat().error("error");
	 }
	 }
