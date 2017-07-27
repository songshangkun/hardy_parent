package org.hardy.util.proputil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;

import com.song.tool.text.io.IOClose;
/**
 * 继承PropertiesReader在web下读取
 * @author 09
 *
 */
public class PropServletReader extends PropertiesReader{
	private static final PropServletReader psr = new PropServletReader();    
	public  String  getRootPath(){
		return this.getClass().getResource("/").getFile();
	}
	
	 @Override
	public Map<String, Properties> getAllProps() {
		// TODO Auto-generated method stub
		return super.getAllProps();
	}

	@Override
	public Properties getProp(String pathFile) {
		// TODO Auto-generated method stub
		return super.getProp(pathFile);
	}

	@Override
	public void setProps(Map<String, Properties> props) {
		// TODO Auto-generated method stub
		super.setProps(props);
	}

	@Override
	public void setCollectionProps(String propsPath) {
		// TODO Auto-generated method stub
		super.setCollectionProps(propsPath);
	}

	@Override
	public String getValue(String key) {
		// TODO Auto-generated method stub
		return super.getValue(key);
	}

	@Override
	public String[] getValues(String... keys) {
		// TODO Auto-generated method stub
		return super.getValues(keys);
	}

	@Override
	public void addOrMerge(String keyProp, String[] keys, String[] values) {
		// TODO Auto-generated method stub
		super.addOrMerge(keyProp, keys, values);
	}

	@Override
	public void merge(String[] keys, String[] values) {
		// TODO Auto-generated method stub
		super.merge(keys, values);
	}
   
	public static String[] getValuesStatic(String propname,String... keys){
  	  Properties prop = getProperties(propname);
  	  String[] result = new String[keys.length];
			for(int i=0;i<keys.length;i++){
				 result[i] = prop.getProperty(keys[i]);
			}	
  	  return result;    
    }
	/**
	 * 通过引用对象传递找到根目录下的properties文件
	 * @param obj  在那个类中引用此方法的类 一般为this
	 * @param propname  根路径名称 /com/.../..   class文件夹下的com文件夹....路径
	 * @return
	 */
	public static Properties getRootProperties(Object obj,String propname){
		  Properties prop = new Properties();
		  InputStream is = obj.getClass().getResourceAsStream(propname);
		  try {
			prop.load(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  return prop;
	}
	/**
	 * 通过引用对象传递找到根目录下的properties文件
	 * @param obj  在那个类中引用此方法的类 一般为this
	 * @param propname  根路径名称 /com/.../..   class文件夹下的com文件夹....路径
	 * @return
	 * @throws IOException 
	 */
	public static Properties getRootPropertiesEx(Object obj,String propname) throws IOException{
		  Properties prop = new Properties();
		  InputStream is = obj.getClass().getResourceAsStream(propname); 
		   prop.load(is);
		  return prop;
	}
	/**
	 * 
	 * @param obj
	 * @param propname /com/xxx/... classes文件夹下
	 * @return
	 */
	public static Properties getRootPropertiesExtion(Object obj,String propname){
		 Properties prop = new Properties();
		 InputStream is = null;
		 is = Object.class.getResourceAsStream(propname); 
		 try{
			prop.load(is);
		 }catch(Exception e){prop.clear(); System.out.println("Object.class.getResourceAsStream("+propname+")无法读取");
			is = obj.getClass().getResourceAsStream(propname);
			try {System.out.println(e.getMessage());
				prop.load(is);
			} catch (Exception e1) {prop.clear();System.out.println(obj.getClass()+".getResourceAsStream("+propname+")无法读取");
			try {
				is = new FileInputStream(new File((psr.getRootPath()+propname)));
				prop.load(is);
			} catch (FileNotFoundException e2) {
				System.out.println("FileInputStream 无法读取");
				prop.clear(); 			 
				e2.printStackTrace();
			} catch (IOException e2) {
				System.out.println("properties文件 无法读取");
				prop.clear(); 	
				e2.printStackTrace();
			}
			}
		 }
		 return prop;
	}
	
	/**
	 * 根据路径propname获取文件properties
	 * @param propname   classpath:xxx代表根部录下
	 * @return
	 * @throws IOException 
	 */
	public static Properties getPropertiesEx(String propname) throws IOException{
		 Properties prop = new Properties();
   	     InputStream is = null;
		 if(propname.startsWith("classpath:")){	
			   
				is = new FileInputStream(new File((psr.getRootPath()+"/"+propname.substring(10))));
			 
		 }else{
			 
				is = new FileInputStream(new File(propname));
			 
		 }
			   
				prop.load(is);
			  
				IOClose.closeSource(is);
			 
			  return prop;
	 }
	/**
	 * 根据路径propname获取文件properties
	 * @param propname   classpath:/xxx代表根部录下
	 * @return
	 */
	public static Properties getProperties(String propname){
		 Properties prop = new Properties();
   	     InputStream is = null;
		 if(propname.startsWith("classpath:")){	
			   try {
				is = new FileInputStream(new File((psr.getRootPath()+"/"+propname.substring(10))));
			} catch (FileNotFoundException e) {
				 throw new RuntimeException("PropServletReader.getProperties for classpath exception "+e.getMessage());
			}
		 }else{
			 try {
				is = new FileInputStream(new File(propname));
			} catch (FileNotFoundException e) {
				 throw new RuntimeException("file not find , maybe this method is not suit this evironement "+e.getMessage());
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
	 * @param propPath
	 * @param keys
	 * @param values
	 */
	public static void addOrUpdate(String propPath,String[] keys,String[] values){
	     Properties prop =  getProperties(propPath);
	     addOrUpdate(propPath, prop, "", keys, values);
   }
	/**
	 * 
	 * @param propPath
	 * @param prop
	 * @param comment
	 * @param keys
	 * @param values
	 */
	public static void  addOrUpdate(String propPath,Properties prop,String comment,String[] keys,String[] values){
  	  OutputStream os = null;
  	  if(propPath.startsWith("classpath:")){
  		  propPath = psr.getClass().getResource("/").getPath()+propPath.substring(10);
//  		  System.out.println(propPath);
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
	 * @param keys
	 * @param values
	 * @param obj
	 */
	public static void addOrUpdate(String propPath,String[] keys,String[] values,Object obj){
	     Properties prop =  getProperties(propPath,obj);
	     addOrUpdate(propPath, prop, "", keys, values,obj);
  }
	/**
	 * 
	 * @param propPath
	 * @param prop
	 * @param comment
	 * @param keys
	 * @param values
	 * @param obj
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
