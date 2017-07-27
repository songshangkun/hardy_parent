package org.hardy.springutil.mvc;


import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.NotImplementedException;
import org.hardy.jsonhelper.JsonTools;
import org.hardy.jsonhelper.coreface.OutInterface;
import org.hardy.jsonhelper.form.JOSNFORMAT;
import org.hardy.servlet.out.AbstractResource;
import org.hardy.servlet.out.ResourceWrite;
import org.hardy.statics.constants.PathMode;
import org.hardy.statics.web.ContentType;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.song.tool.rootutil.RootUtil;
import com.song.tool.text.io.StringReaderUtil;

/**
 * 1-page接收全部从0开始
 * 2-所有print输出都要在实现方法中传入HttpServletResponse
 * 3-可以使用ControlProcessor调用getName和call方法
 * @author 宋尚堃
 * 
 *
 */
public class AbstractController implements ControlStore,ApplicationListener<ApplicationEvent> {
	
	public static OutInterface oiface = null;
	
	private static JsonTools jsonTools = null;
	/**
	 * http接受页数的参数名称
	 */
	public static String pageName = "page";
	/**
	 * http接受每页数量的参数名称
	 */
	public static String rowsName = "rows";
	{  
		ControlProcessor.getInstance().add(this.getClass().getName(), this);
	}
 
	public void uploadFile(
			MultipartFile file,
			PathMode mode,
			Map<String,Object> params,
			HttpServletRequest request,
			HttpServletResponse response){
		    if(jsonTools!=null)jsonTools = JsonTools.getInstance(oiface);
		    JOSNFORMAT jf = new JOSNFORMAT();
		try {
			 Object dic = params.get("dic");
			 if(dic == null)throw new RuntimeException("parmas's dic is required");
			 String saveDic = dic.toString();
			 switch (mode) {
			case CLASSPATH:
				 saveDic = RootUtil.getRootPath()+dic.toString();
				break;
			case WEBROOT:
				 saveDic = RootUtil.getWebRoot(request)+dic.toString();			
				break;
			case FILE:
				break;
			default:
				break;
			} 
			 File saveFile = new File(saveDic);
			 if(!saveFile.isDirectory())saveFile.mkdirs();
			 FileUtils.writeByteArrayToFile(saveFile, file.getBytes());
		} catch (IOException e) { 
			e.printStackTrace();
			 jf.clean().error("上传失败").cause(e); 
			 jsonTools.printJsonObject(jf, response);
			return  ;
		}
		jf.clean().success("上传成功");
		 jsonTools.printJsonObject(jf, response);
		return  ;
	}
	/**
	 * 下载文件方法
	 * @param name
	 * @param path
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ResponseEntity<byte[]> downloadAttach(String name,String path,
			PathMode mode,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException{
	    HttpHeaders headers = new HttpHeaders();  
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);  
	    headers.setContentDispositionFormData("attachment", name);
	    //System.out.println(RootUtil.getWebRoot(request)+path);
	    String head_path = path;
	    switch (mode) {
		case CLASSPATH:
			head_path = RootUtil.getRootPath()+path;
			break;
		case WEBROOT:
			head_path = RootUtil.getWebRoot(request)+path;		
			break;
		case FILE:
	        break;
		default:
			break;
		}
	    return new ResponseEntity<byte[]> 
	    (FileUtils.readFileToByteArray(
	    new File(head_path)),headers, 
	    		HttpStatus.CREATED);  
		 
	}
	
	/**
	 * 以字符串形式输出路径的代码
	 * @param mode
	 * @param type
	 * @param pathAlias
	 * @param delim
	 * @param request
	 * @param response
	 */
	public static void returnResourceAsResolve(PathMode mode,ContentType type,String pathAlias,String delim,
			  HttpServletRequest request,HttpServletResponse response){
		  String resource = "404";
		  String realPath = pathAlias.replace(delim, "/");
  switch (mode) {
		case CLASSPATH: 
			resource = StringReaderUtil.getTxt(new File(RootUtil.getRootPath()+realPath), "UTF-8");
			break;
		case WEBROOT: 
			resource = StringReaderUtil.getTxt(new File(RootUtil.getWebRoot(request)+realPath), "UTF-8");
			break;
		case FILE: 
			resource = StringReaderUtil.getTxt(new File(realPath), "UTF-8");
			break;
		default:
			resource = StringReaderUtil.getTxt(new File(RootUtil.getWebRoot(request)+realPath), "UTF-8");
			break;
		}
   		final String result = resource.trim();
		 writeJSON(result, response);  
	  }
	 /**
	  * 服务器跳转到指定的位置
	  * @param pathAlias
	  * @param delim
	  * @return
	  */
	 public static String getPathclaire(String pathAlias,String delim){
		   String realPath = pathAlias.replace(delim, "/");
		   return realPath;
	  }
	 
	 /**
	  * 获取当前的request
	  * @return
	  */
	 protected static HttpServletRequest getCurrentRequest(HttpServletRequest request) {
		 if(request==null) request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		  return 	request;
	 }
	 
	 /**
	  * 导入当前response
	  * @param response
	  * @return
	  */
	 protected static HttpServletResponse getCurrentResponse(HttpServletResponse response) { 
		 if(response==null) response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
	        return 	response;
	 }
	 /**
	  * 对url或表单传输时获取对应的参数
	  * @param key
	  * @return
	  */
	 protected static String getParams(HttpServletRequest request, String key){
		 return getCurrentRequest(request).getParameter(key) ;
	 }
	 
	 /**
	  * 获取全部的参数
	  * @return
	  */
	 protected static Map<String, String[]>  getParamsMap(HttpServletRequest request){
		 return getCurrentRequest(request).getParameterMap() ;
	 }
	 
	 /**
	  * 从request中获取page和size
	  * @return
	  */
	 protected static Integer[] getPagingPageSize(HttpServletRequest request){
		  int page = 0 ,size = 10;
		  String pageString = getCurrentRequest(request).getParameter(pageName);
		  String sizeString = getCurrentRequest(request).getParameter(rowsName);
		 if(pageString!=null&&pageString.matches("\\d+")) 
			 page = Integer.valueOf(pageString) ;
		 if(sizeString!=null&&sizeString.matches("\\d+")) 
			 size = Integer.valueOf(sizeString) ;
		  return new Integer[]{page,size};
	 }
	 
	 /**
	  * 根据page和size获取数据库分页的开始，与间距
	  * @param page
	  * @param size
	  * @return
	  */
	 protected static Integer[]  paselFirstCount_MaxResult(int page,int size){
		 return new Integer[]{page*size,size};
	 }
	 /**
	  * 根据page和size获取数据库分页的开始，与间距
	  * @return
	  */
	 protected static Integer[] paselFirstCount_MaxResult(HttpServletRequest request){
		  Integer[] ins = getPagingPageSize(getCurrentRequest(request));
		 return paselFirstCount_MaxResult(ins[0], ins[1]);
	 }
	 /**
	  * 根据数据量和每页的数据量获取页数
	  * @param totalCount
	  * @param size
	  * @return
	  */
	 protected static int getPageCount(long totalCount,int size){
		 return (int) Math.ceil((double)totalCount/(double)size);
	 }
	 /**
	  * 获取当前的session
	  * @return
	  */
	public HttpSession getWeb_session(HttpServletRequest request) {
		  return getCurrentRequest(request).getSession();
	}
    /**
     * 获取session中的
     * @param key
     * @return
     */
	@SuppressWarnings("unchecked")
	public <T extends Object> T getInSession(HttpServletRequest request,String key){
		 return (T)this.getWeb_session(request).getAttribute(key);  
	 }
	 /**
	  * 设置session的
	  * @param request
	  * @param key
	  * @param obj
	  */
	 public void setInSession(HttpServletRequest request,String key,Object obj){
		 this.getWeb_session(request).setAttribute(key, obj);
	 } 
	 
	 /**
	  * 
	  * @param obj
	  * @return
	  */
	 public static String json(Object obj){
		 if(jsonTools==null)jsonTools = JsonTools.getInstance(oiface);
		 return  jsonTools.toJSONObjectString(obj);
	 }
	 
	 public static <T extends Collection<?>>String json(T obj){
		 if(jsonTools==null)jsonTools = JsonTools.getInstance(oiface);
		 return  jsonTools.toJSONArrayString(obj);
	 } 
	 
	  
	 /**
	  * 将对象输出json
	  * @param success
	  * @param message
	  * @param map
	  * @param response
	  */
	 public static void writeJSON(Object obj,HttpServletResponse response){
		 if(jsonTools==null)jsonTools = JsonTools.getInstance(oiface);
		 jsonTools.printJsonObject(obj, response);
	 }
	 /**
	  * 输出string格式的JSON
	  * @param jsonString
	  * @param response
	  */
	 public static void writeJSON(final String jsonString,HttpServletResponse response){
		 writeJSON(jsonString, ContentType.JSON,response);
	 }
	 /**
	  * 直接输出string字符串,字符串格式于ContentType类型对应
	  * @param jsonString
	  * @param ct
	  * @param response
	  */
	 public static void writeJSON(final String jsonString,ContentType ct,HttpServletResponse response){
		 ResourceWrite.responseString(response, ct, new AbstractResource() {

			@Override
			public String excuterS() {
				// TODO Auto-generated method stub
				return jsonString;
			}
			 
		});
		 return;
	 }
	 
	
	 /**
	  * 输出集合的json
	  * @param e
	  * @param response
	  */
	 public static <E extends Object> void writeJSON(Collection<E> e,HttpServletResponse response){
		 if(jsonTools==null)jsonTools = JsonTools.getInstance(oiface);
		 jsonTools.printJsonArray(e, response);
	 }
	 
	 
	

	@Override
	public String getName() { 
		return this.getClass().getName();
	}

	 
    /**
     * 集成实现的方法
     */
	@Override
	public String call(HttpServletRequest request,HttpServletResponse reponse) {
	   return null;
	}
    /**
     * 需要继承实现,在ApplicationEvent时激发推送
     */
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		 throw new NotImplementedException("未实现该方法");
	}
    /**
     * 集成实现的方法
     */
	@Override
	public String call() {
		// TODO Auto-generated method stub
		return null;
	}

	 

	 
}
