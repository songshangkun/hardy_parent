package org.hardy.freemakerhelper;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.hardy.statics.constants.PathMode;
import org.hardy.statics.time.DatePatten;

import com.song.tool.rootutil.RootUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
/**
 * 这个类设置后就不能更改，除非创建新的实例
 * @author song
 *
 */
public class FreeMakerPaging {
      private Configuration cfg; 
      private String directory;
      private PathMode mode;
     /**
      * 构造函数
      * @param version  版本号
      * @param defaultEncoding 默认字符编码
      * @param request HttpServletRequest请求，如果不从webRoot下读取可为空
      * @param mode  PathMode 读取模式
      * @param directory 相对或绝对路径 根据mode定义
      * @param configur Configuration的补充接口
      */
	public FreeMakerPaging(Version version,
		String defaultEncoding,HttpServletRequest request,PathMode mode,String directory,FreemakerUtil.Configur configur) {
		super();
		this.cfg = new Configuration(version);
		this.cfg.setDefaultEncoding(defaultEncoding);
		this.cfg.setDateFormat(DatePatten.ZH.getAll());
		if(configur!=null)configur.supplement(this.cfg);
		this.mode = mode;
		switch (this.mode) {
		case WEBROOT:
			this.directory = RootUtil.getWebRoot(request);
			break;
		default:
			this.directory = directory;
			break;
		}
	}
	
	public Template getTemplate(String templateName){
		try {
	        switch (this.mode) {
			case CLASSPATH:  
				cfg.setClassForTemplateLoading(this.getClass(), this.directory);
				break;
			case WEBROOT:
				cfg.setDirectoryForTemplateLoading(new File(this.directory));
				break;
			default:
				cfg.setDirectoryForTemplateLoading(new File(this.directory));
				break;
			}
	        Template temp = cfg.getTemplate(templateName);
	        return temp;
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
 
}
