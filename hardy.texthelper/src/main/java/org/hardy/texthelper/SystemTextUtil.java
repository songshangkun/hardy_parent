package org.hardy.texthelper;

import org.hardy.texthelper.StringLocalUtil;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.List;


 
/**
 * 得到系统环境变量的一些字符样式，和可支持中文的字符样式
 * @author 宋尚堃
 */
public class SystemTextUtil {
    private static final GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    
	public static Font[] getAllFonts(){	
	    return env.getAllFonts();
	}
	
	public static String[] getAvailableFontFamilyNames(){	
	    return env.getAvailableFontFamilyNames();
	}
	
	public static String[]  getNonChinoisNames(){	
	       List<String> names = new ArrayList<String>();
	       for(String name:env.getAvailableFontFamilyNames()){
	    	       if(!StringLocalUtil.hasChinoisChar(name)){
	    	    	   names.add(name);
	    	       }
	       }
	       String[] a = new String[names.size()];
	        names.toArray(a);
	       return  a;
	}
	
	public static String[]  getChinoisNames(){	
	       List<String> names = new ArrayList<String>();
	       for(String name:env.getAvailableFontFamilyNames()){
	    	       if(StringLocalUtil.hasChinoisChar(name)){
	    	    	   names.add(name);
	    	       }
	       }
	       String[] a = new String[names.size()];
	        names.toArray(a);
	       return  a;
	}
  
	public static String[] getUsuallyEnglishExemple(){	
		return new String[]{ 
				"Adobe Arabic","Adobe Caslon Pro","Adobe Hebrew"
				,"Arial","Arial Black","Consolas","Comic Sans MS",
				"Curlz MT","Times New Roman" 
		};
	}
	public static String[] getUsuallyChiniosExemple(){	
		return new String[]{"仿宋","华文中宋","华文仿宋","华文宋体","华文彩云","华文新魏",
				 "华文楷体","华文琥珀","华文细黑","华文行楷","华文隶书","宋体","幼圆",
				 "微软雅黑","新宋体","方正姚体","方正舒体","楷体","隶书","黑体"
				 };
	}
	
	
}
