package org.hardy.jsonhelper.form;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.hardy.jsonhelper.AlibabaJson;
import org.hardy.jsonhelper.JsonTools;
import org.hardy.reflex.SpringReflext;
import org.hardy.reflex.WrapperConvertor;
import org.hardy.statics.json.RequiredPolicy;
import org.hardy.statics.json.ToJson;
import org.hardy.util.arrays.ArrayTools;
import org.springframework.beans.NotReadablePropertyException;

/**
 * Created by song on 2017/7/1.
 * 主要用于将一个类的基本属性按预期转为JSON,对自定义属性也可定制,
 * 注意不能对继承的父对象做操作
 *
 */
public class JsonBean {

    private Map<String,Object> coreMap = new HashMap<String, Object>();
    
    private JsonTools jTools ;

    public JsonBean(Object obj,String[] policys, RequiredPolicy... requireds){
//        Class<?> clazz = obj.getClass();
        
        Field[] fs = obj.getClass().getDeclaredFields();
        
        for(Field f:fs){
          	int i = 0;boolean[] flags = new boolean[requireds.length];
            for(RequiredPolicy req:requireds){
                switch (req){
                    case ALL:
                        flags[i] = true;
                        i++;
                        break;
                    case ESSENTIAL:
                        flags[i] =  WrapperConvertor.isEssentielData(f.getType());
                        i++;
                        break;
                    case GET:
                        try{
                            SpringReflext.get(obj,f.getName());
                            flags[i] = true;
                        }catch (NotReadablePropertyException e){
                             e.printStackTrace();
                        }finally {
                            i++;
                        }
                        break;
                    case ANNOTATION:
                        if(f.isAnnotationPresent(ToJson.class)){
                        	ToJson tJson = f.getAnnotation(ToJson.class);
                        	   if(policys==null||policys.length<1
                        			   ||ArrayUtils.contains(tJson.policy(), "GLOBAL")
                        			   ||ArrayTools.intersection(policys, tJson.policy()).length>0) flags[i] = true;
                        }
                        if(f.getName().equals("age"))System.out.println(flags[i]);
                        i++;
                        break;
                }

            }
            boolean b = true;
            for(boolean flag:flags){
            	    if(!flag){
            	    	        b = false;
            	    			break;
            	    }
            }
            if(b) coreMap.put(f.getName(),  SpringReflext.get(obj,f.getName()));
        }

    }

	public JsonTools getjTools() {
		return jTools;
	}

	public void setjTools(JsonTools jTools) {
		this.jTools = jTools;
	}

	@Override
	public String toString() {
		if(this.jTools==null)this.jTools = JsonTools.getInstance(new AlibabaJson());
		return this.jTools.toJSONObjectString(this.coreMap);
	}
    
   

     
}
