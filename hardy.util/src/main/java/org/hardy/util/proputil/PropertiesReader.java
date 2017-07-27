package org.hardy.util.proputil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


import com.song.tool.file.FileUtil;
import com.song.tool.file.face.FileOpe;
import com.song.tool.text.io.IOClose;

/**
 *  这是一个读取properties的类
 *  因为properties的内容相对固定
 *  不经常改变所以采用预读所有properties文件
 *  的方式，将properties文件全部读入一个List操作
 * */
public class PropertiesReader {
	/**
	 *  定义全部的properties文件
	 * */
      private Map<String,Properties> props;
      /**
       * @说明 获取全部的Properties对象，该方法前要使用setCollectionProps方法
       * @return  Map<Properties> 
       */
	public Map<String,Properties> getAllProps() {
		return props;
	}
	 /**
     * @说明 获取Properties对象，该方法前要使用setCollectionProps方法
     * @param pathFile Properties文件路径
     * @return  Properties 
     */
    public  Properties getProp(String pathFile){
    	   return this.props.get(pathFile);
    }
	public void setProps(Map<String,Properties> props) {
		this.props = props;
	}
      /**
       *  根据propsPath设置此路径下全部properties文件加载到
       *  props中.
       *  @param propsPath 要加载的properties文件路径
       **/
      public void setCollectionProps(String propsPath){
    	    if(null==props){
    	    	props = new HashMap<String,Properties>();
    	    }
    	    FileUtil.dolist(propsPath, new String[]{"properties"},new FileOpe() {	
				@Override
				public void execute(File file) {
					InputStream is = null;
					 Properties prop = new Properties();
					 try {
						 is = new FileInputStream(file);
						prop.load(is);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{
						IOClose.closeSource(is);
					}
					 props.put(file.getPath(),prop);
				}
			});
      }
      /**
       * @说明 查找全部properties文件的key的value
       * @param key properties的一个key，
       * @return  String properties文件的key的value 
       * @注意  如果没有key相对应返回null，如果在多个properties中有相同key，则任意输出其中一个
       */
      public String getValue(String key){
    	  for(String pkey:props.keySet()){
 				  if(this.props.get(pkey).containsKey(key)){
                      return this.props.get(pkey).getProperty(key);
 				  }
 				 }
 			 return null;
      }
      /**
       * @说明 查找全部properties文件的key的value
       * @param keys 可变参数，properties文件的key
       * @return  String[] properties文件的key的values数组
       * @注意  如果没有key相对应返回null，如果在多个properties中有相同key，则任意输出其中一个
       */
      public String[] getValues(String... keys){
    	  String[] result = new String[keys.length];
    	  int index = 0;
    		 for(String key:keys){
    			result[index] = getValue(key);
    			index++;
    		 }
    	  
		   return result;
    	  
      }
      
      /**
       * @说明 静态方法查找指定properties文件的key的value
       * @param propname  文件的路径，如果以classpath:开头表示类路径下文件。
       * @param keys 可变参数，properties文件的key
       * @return  String[] properties文件的key的values数组
       * @注意 以classpath:开头时，资源名前缀不加"/",exemple: "classpath:resource/XXX/..."
       */
      public static String[] getValuesStatic(String propname,String... keys){
    	  Properties prop = getProperties(propname);
    	  String[] result = new String[keys.length];
			for(int i=0;i<keys.length;i++){
				 result[i] = prop.getProperty(keys[i]);
			}	
    	  return result;    
      }
      /**
       * 
       *@说明 通过Properties的路径获取Properties对象
       * @param propname Properties文件的路径，classpath下使用"classpath:"
       * @return Properties对象
       * @throws IOException
       */
      public static Properties getPropertiesEx(String propname) throws IOException{
    	  Properties prop = new Properties();
    	  InputStream is = null;
		 if(propname.startsWith("classpath:")){	
			 //System.out.println("/"+propname.substring(10));
			   is = Object.class.getResourceAsStream("/"+propname.substring(10));
		 }else{
			 
				is = new FileInputStream(new File(propname));
			 
		 }
			 
				prop.load(is);
			 
				IOClose.closeSource(is);
			 
			  return prop;
      }
     
      /**
       * @说明 通过Properties的路径获取Properties对象
       * @param propname Properties文件的路径，classpath下使用"classpath:"
       * @return Properties对象
       */
      public static Properties getProperties(String propname){
    	  Properties prop = new Properties();
    	  InputStream is = null;
		 if(propname.startsWith("classpath:")){	
			 System.out.println("/"+propname.substring(10));
			   is = Object.class.getResourceAsStream("/"+propname.substring(10));
		 }else{
			 try {
				is = new FileInputStream(new File(propname));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		 }
			  try {
				prop.load(is);
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				IOClose.closeSource(is);
			}
			  return prop;
      }
      /**
       * 
       * @param propname
       * @param obj 引用实例
       * @return
       */
      public static Properties getProperties(String propname,Object obj){
    	  Properties prop = new Properties();
    	  InputStream is = null;
		 if(propname.startsWith("classpath:")){	
			 System.out.println("/"+propname.substring(10));
			   is = obj.getClass().getResourceAsStream("/"+propname.substring(10));
		 }else{
			 try {
				is = new FileInputStream(new File(propname));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		 }
			  try {
				prop.load(is);
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				IOClose.closeSource(is);
			}
			  return prop;
      }
      /**
       * @说明 通过keyProp    获取Properties
       * @param keyProp   即Properties的路径
       * @param keys 
       * @param values
       */
      public void addOrMerge(String keyProp,String[] keys,String[] values){
    	     Properties p = this.props.get(keyProp);
    	     addOrUpdate(keyProp, p, "", keys, values);
      }  
      public void merge(String[] keys,String[] values){
    	  List<String> list = new ArrayList<String>();
    	  for(String keyProp:props.keySet()){
    		     Properties p = this.props.get(keyProp);
    		     for(int i=0;i<keys.length;i++){
    		    	 if(p.containsKey(keys[i])){
       		    	  addOrUpdate(keyProp, p, "", keys, values);
       		    	  list.add(keys[i]);
       		      } 	  
    		     } 
    	  } 
    	  if(list.size()!=keys.length){
    		  StringBuffer sb = new StringBuffer();
    		  for(String k1:keys){
    			  if(!list.contains(k1)){
    				  sb.append(" Key = "+k1);
    			  }   			
    		  }
    		  throw new RuntimeException("the key is not exsit | All propertes d'ont contains  "+sb.toString()); 
    	  }
    	
   }
      /**
       * @说明 改变指定路径的Properties的内容并保存回该路径
       * @param propPath  读取并保存的Properties路径
       * @param key   
       * @param value
       */
//      public static void addOrUpdate(String propPath,String[] keys,String[] values){
//    	     Properties prop =  getProperties(propPath);
//    	     addOrUpdate(propPath, prop, "", keys, values);
//      }
      
      /**
       * 
       * @param propPath
       * @param keys
       * @param values
       * @param obj 引用实例
       */
      public static void addOrUpdate(String propPath,String[] keys,String[] values,Object obj){
 	     Properties prop =  getProperties(propPath,obj);
 	     addOrUpdate(propPath, prop, "", keys, values,obj);
   }
      /**
       * @说明 改变Properties的内容并保存到指定路径
       * @param propPath   保存的Properties路径
       * @param prop         操作的Properties对象
       * @param comment    标记
       * @param keys         
       * @param values    values与key必须对应
       */
      public static void  addOrUpdate(String propPath,Properties prop,String comment,String[] keys,String[] values){
    	  OutputStream os = null;
    	  if(propPath.startsWith("classpath:")){
    		  propPath = Object.class.getResource("/").getPath()+propPath.substring(10);
    		  System.out.println(propPath);
    	  }
    	  try {
			 os = new FileOutputStream(new File(propPath));
			for(int i=0;i<keys.length;i++){
				 prop.setProperty(keys[i], values[i]);
			}
			 prop.store(os,comment);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			 IOClose.closeSource(os);
		}
    	  
      }  	 
      /**
       * 
       * @param propPath
       * @param prop
       * @param comment
       * @param keys
       * @param values
       * @param obj 引用实例
       */
      public static void  addOrUpdate(String propPath,Properties prop,String comment,String[] keys,String[] values,Object obj){
    	  OutputStream os = null;
    	  if(propPath.startsWith("classpath:")){
    		  propPath = obj.getClass().getResource("/").getPath()+propPath.substring(10);
    		  System.out.println(propPath);
    	  }
    	  try {
			 os = new FileOutputStream(new File(propPath));
			for(int i=0;i<keys.length;i++){
				 prop.setProperty(keys[i], values[i]);
			}
			 prop.store(os,comment);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			 IOClose.closeSource(os);
		}
    	  
      }  	 
}
