package org.hardy.springutil.webservice.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;

import org.apache.commons.lang.ArrayUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.hardy.springutil.webservice.MethodRepository;
import org.hardy.springutil.webservice.ParamJson;
import org.hardy.springutil.webservice.terminal.WebServiceClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.TypeUtils;
import com.song.tool.util.servlet.out.AbstractText;
import com.song.tool.util.servlet.out.ContentType;
import com.song.tool.util.servlet.out.PrintWriterResource;

/**
 * 一个公共的Controller层，可以通过Http访问直接调用service或dao层的方法，
 * 和spring结合，两种调用方式，直接在可使用直接的调用或跨域调用，或者webservice调用。
 * 以json为传输参数和返回对象
 * @author ssk
 *
 */
@Controller
@RequestMapping("/commonController")
public class CommonController {
	
	/**
	 * 访问此接口获取可供使用的文档帮助如果
	 * @param request
	 * @param response
	 * @param type
	 */
	@RequestMapping("/helper/{type}")
	 public void getHelper(HttpServletRequest request,HttpServletResponse response,@PathVariable("type") String type){
		       final List<String> result = MethodRepository.allbeansHelper();
		       if("xml".equalsIgnoreCase(type)){
		    	     PrintWriterResource.responseXml(response, new AbstractText() {	
						@Override
						public String excuterS() {
							  Document doc=DocumentHelper.createDocument();  
						 	    Element ele1=doc.addElement("HELPS");
						 	 	for(int i=0;i<result.size();i++){
						 	 		Element ele2 = ele1.addElement("help_"+i);
						 	 		 String r = result.get(i);
						 	 		 String bean_id = r.substring(r.indexOf("bean_id:"),r.indexOf("method_id:"));
						 	 		 String method_id = r.substring(r.indexOf("method_id:"),r.indexOf("description:"));
						 	 		String description = r.substring(r.indexOf("description:"),r.indexOf("path:"));
						 	 		String path = r.substring(r.indexOf("path:"),r.length());
						 	 		 ele2.addElement("BEAN").addText(bean_id);
						 	 		 ele2.addElement("METHOD").addText(method_id);
						 	 		 ele2.addElement("DESCRIPTION").addText(description);
						 	 		 ele2.addElement("PATH").addText(path);
						 	 	}
							  return doc.asXML();
						}
					});
		       }
		       if("json".equalsIgnoreCase(type)){
		    	   PrintWriterResource.responseJson(response, new AbstractText() {
						@Override
						public String excuterS() {
							 String s = JSONObject.toJSONString(result,true);
				    	       return s;
						}
					});  
		       }
	 }
	
	@RequestMapping("/call/ws")
    public void getResult_ws(HttpServletRequest request,HttpServletResponse response,@RequestParam("ip") String ip,@RequestParam("port") int port,
    		@RequestParam(name="token",required=false) String token,@RequestParam("bean_id") String bean_id,@RequestParam("method_id") String method_id,@RequestParam("params") String params){
		           WebServiceClient   wsc = new WebServiceClient(ip, port);
		           Object[] objs = analisyParam(MethodRepository.getMethodById(method_id), params);
		           try {
					  final String result = wsc.callMethod(token,bean_id, method_id, objs);
					  PrintWriterResource.responseJson(response, new AbstractText() {
							@Override
							public String excuterS() {
								// TODO Auto-generated method stub
								return result;
							}
						});
				} catch (SOAPException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JAXBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
           /**
            * 
            * @param request
            * @param response
            * @param bean_id
            * @param method_id
            * @param params
            */
	        @RequestMapping("/call/json")
	        public void getResult_json(HttpServletRequest request,HttpServletResponse response,@RequestParam("params") String params){
	        	  try {
					ParamJson pj = new ParamJson(params);
					String value = MethodRepository.runMethodPourJson(pj);
					PrintWriterResource.responseString(response, ContentType.JSON, value);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	      	   
	        }
	        
	        @RequestMapping("/call/params")
	        public void getResult_params(HttpServletRequest request,HttpServletResponse response,
	        @RequestParam("bean_id") String bean_id,@RequestParam("method_id") String method_id,@RequestParam(name="params",required=false) String params){
	        	  try {
	        		Object[] objs = analisyParam(MethodRepository.getMethodById(method_id), params);
					final String value = MethodRepository.runMethodPourJson(bean_id, method_id, objs);
					System.out.println(value);
					PrintWriterResource.responseJson(response, new AbstractText() {
						@Override
						public String excuterS() {
							// TODO Auto-generated method stub
							return value;
						}
					});
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	      	   
	        }
	        
	        private Object[] analisyParam(Method method,String params){
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
	        
	        private boolean eqaleDelim(String value){
	        	return eqaleAll(value, new String[]{",","&","[","]"});
	        }
	        
	        @SuppressWarnings("unused")
			private boolean eqaleArrayDelim(String value){
	        	return eqaleAll(value, new String[]{"[","]"});
	        }
	        
	        public boolean eqaleAll(String value,String[] arrays){
	        	   for(String v:arrays){
	        		     if(v.equals(value)){
	        		    	  return true;
	        		     }
	        	   }
	        	   return false;
	        }
	        
	        public static void main(String[] args) {
	        
	        	Object o=JSONObject.toJavaObject(JSONObject.parseArray("[3,4,5]"), String[].class);
	        	 
	        	System.out.println(ArrayUtils.toString(o)+" "+o.getClass());
			}
}
