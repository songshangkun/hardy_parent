package org.hardy.servlet.out;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.hardy.statics.web.ContentType;

import com.song.tool.text.io.IOClose;

/**
 * 对HTTP服务的资源输出,写文件通过http协议发送到页面上
 * @author 09
 *
 */
public class ResourceWrite{
	/**
     * 
     * @param response   HttpServletResponse
     * @param type    HTTP资源头文件类型
     * @param serveletResource 返回资源接口
     */
	public static void responseBytes(HttpServletResponse response,String type,ServletResource  servletResource){
		 response.setContentType(type);
		 OutputStream os = null;  
       try{
       	os = response.getOutputStream();
       	os.write(servletResource.excuterB());
       } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
       finally { 
	            IOClose.closeSource(os);
	        }
	}
    /**
     * 
     * @param response   HttpServletResponse 
     * @param type     HTTP资源头文件类型
     * @param serveletResource 返回资源接口
     */
	public static void responseString(HttpServletResponse response,String type,ServletResource  servletResource){
		 response.setContentType(type);
		 PrintWriter out = null;  
        try{
       	out = response.getWriter();
       	out.print(servletResource.excuterS());
        } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        finally { 
	            out.close();
	        }
	}
	/**
     * 
     * @param response   HttpServletResponse
     * @param type    HTTP资源头文件类型
     * @param serveletResource 返回资源接口
     */
	public static void responseBytes(HttpServletResponse response,ContentType type,ServletResource  servletResource){
		 response.setContentType(type.getContentTypeString(ContentType.getCharset()));
		 OutputStream os = null;  
        try{
        	os = response.getOutputStream();
        	os.write(servletResource.excuterB());
        } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        finally { 
	            IOClose.closeSource(os);
	        }
	}
     /**
      * 
      * @param response   HttpServletResponse 
      * @param type     HTTP资源头文件类型
      * @param serveletResource 返回资源接口
      */
	public static void responseString(HttpServletResponse response,ContentType type,ServletResource  servletResource){
		 response.setContentType(type.getContentTypeString(ContentType.getCharset()));
		 PrintWriter out = null;  
         try{
        	out = response.getWriter();
        	out.print(servletResource.excuterS());
         } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         finally { 
	            out.close();
	        }
	}
	
 
	 
	 
	
	 
	 
}

