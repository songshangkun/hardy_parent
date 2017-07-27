package org.hardy.jsptag;

import java.lang.reflect.Method;
import java.util.StringTokenizer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.TypeUtils;

public class ParamsUtil {
	public static Object[] analisyParam(Method method,String params){
		 int flag = 0;
		 Class<?>[] clazzs = method.getParameterTypes();
		 Object[] obj_params = new Object[clazzs.length];
		 StringTokenizer st = new StringTokenizer(params,",&[]",true);
		 while(st.hasMoreTokens()){
			     String t = st.nextToken();
			     if(!eqaleDelim(t)){
			    	 Object object  = null;
			    	 if(t.equals("")||t.equals("null"))  obj_params[flag] = null;
			    	 else{
			    	object  = TypeUtils.cast(t, clazzs[flag], ParserConfig.getGlobalInstance());
			    	 obj_params[flag] = object;
			    	 }
			    	 flag++;
			     }else if(t.equals("[")){
			    	 StringBuilder sb = new StringBuilder("[");
			    	 while(st.hasMoreTokens()){
			    		 String tn = st.nextToken();
			    		    if(!tn.equals("]")){
			    		    	sb.append(tn).append(",");
			    		    }else{
			    		    	sb.deleteCharAt(sb.lastIndexOf(","));
			    		    	sb.append("]"); break;
			    		    }    
			    	 }
			    	 Object object  = JSONObject.toJavaObject(JSONObject.parseArray(sb.toString()), clazzs[flag]); 
			    	 obj_params[flag] = object;
			    	 flag++;
			     }
		 }
		 return obj_params;
	}

	public static boolean eqaleDelim(String value){
		return eqaleAll(value, new String[]{",","&","[","]"});
	}

	public static boolean eqaleAll(String value,String[] arrays){
		   for(String v:arrays){
			     if(v.equals(value)){
			    	  return true;
			     }
		   }
		   return false;
	}
}
