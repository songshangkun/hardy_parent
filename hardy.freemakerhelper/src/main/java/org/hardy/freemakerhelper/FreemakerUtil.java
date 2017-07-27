package org.hardy.freemakerhelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.song.tool.rootutil.RootUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;

public class FreemakerUtil {
    private static FreemakerUtil fu = new FreemakerUtil();
	/**
	 * 从classpath下获取模版
	 * @param version freeMaker版本
	 * @param classRootPath 相对于classpath路径下的文件夹位置 如在classpath下使用 File.separator
	 * @param name 模版名称
	 * @param configur  Configuration的补充接口
	 * @return
	 */
    public static Template getTemplateByClassLoding(Version version,String classRootPath,String name,Configur configur) {
	    try {
	        Configuration cfg = new Configuration(version); 
	        cfg.setDefaultEncoding("UTF-8");
	        cfg.setClassForTemplateLoading(fu.getClass(), classRootPath);
	        if(configur!=null)configur.supplement(cfg);
	        Template temp = cfg.getTemplate(name);
	        return temp;
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return null;
	    }
    
    /**
     *  从文件系统下获取模版
     * @param version  Freemaker的版本
     * @param directory 文件夹路径 
     * @param name 模版名称
     * @param configur  Configuration的补充接口
     * @return
     */
    public static Template getTemplateByDirectory(Version version,File directory,String name,Configur configur) {
	    try {
	        Configuration cfg = new Configuration(version); 
	        cfg.setDefaultEncoding("UTF-8");
	        cfg.setDirectoryForTemplateLoading(directory);
	        if(configur!=null)configur.supplement(cfg);
	        Template temp = cfg.getTemplate(name);
	        return temp;
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return null;
	    }
    
    /**
     * 从webContent下获取模版
     * @param version Freemaker的版本
     * @param request 
     * @param relativPath   web工程下的文件夹相对路径
     * @param name  模版名称
     * @param configur  Configuration的补充接口
     * @return
     */
    public static Template getTemplateByWebRoot(Version version,HttpServletRequest request,String relativPath,String name,Configur configur) {
	    try {
	        Configuration cfg = new Configuration(version);   
	        cfg.setDefaultEncoding("UTF-8");
	        cfg.setDirectoryForTemplateLoading(new File(RootUtil.getWebRoot(request)+relativPath));
	        if(configur!=null)configur.supplement(cfg);
	        Template temp = cfg.getTemplate(name);
	        return temp;
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return null;
	    }
   /**
    * 将模版输出为String形式
    * @param temp
    * @param params
    * @return
    * @throws TemplateException
    * @throws IOException
    */
    public static String getStringForTemplate(Template temp ,Map<String,Object> params) throws TemplateException, IOException{		 
		StringWriter writerStr = new StringWriter();
		temp.process(params, writerStr);
		return writerStr.toString();
	}
    
    public static void writeToSousClasspath(Template temp ,Map<String,Object> params,String relativPath) throws TemplateException, IOException{		 	
		PrintWriter pw = new PrintWriter(new File(fu.getClass().getResource("/").getPath()+relativPath));
		temp.process(params, pw);
	}
    
    public static void writeToSousWebRoot(Template temp ,Map<String,Object> params,HttpServletRequest request,String relativPath) throws TemplateException, IOException{		 	
		PrintWriter pw = new PrintWriter(RootUtil.getWebRoot(request)+relativPath);
		temp.process(params, pw);
	}
    
    public static void writeToDiskFile(Template temp ,Map<String,Object> params,File file) throws TemplateException, IOException{		 	
    	 String encoding = "UTF-8";//设置文件的编码！！
    	  OutputStreamWriter outstream = new OutputStreamWriter(new FileOutputStream(file), encoding);
    	PrintWriter pw = new PrintWriter(outstream);
		 
		temp.process(params, pw);
	}
	
	public interface Configur{
		void supplement(Configuration configuration);
	}
	
}
