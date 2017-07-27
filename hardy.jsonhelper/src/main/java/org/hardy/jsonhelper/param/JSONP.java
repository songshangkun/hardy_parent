package org.hardy.jsonhelper.param;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.hardy.reflex.SpringReflext;

import com.alibaba.fastjson.JSONObject;

/**
 * 将对象的属性转换为json字符串
 * @author 09
 *
 */
public class JSONP {
	public static <T> T jsonToObject(String jsonString)
	  {
	    return null;
	  }
	  /**
	   * 对象转化成json
	   * @param bean
	   * @return
	   */
	  public static String beanToJson(Object bean)
	  {
	    List<PUnit> ps = new ArrayList<>();
	    for (Field field : bean.getClass().getDeclaredFields())
	    {
	      PUnit p = new PUnit();
	      p.setName(field.getName());
	      p.setTargetType(field.getType());
	      try
	      {
	        p.setValue(SpringReflext.get(bean, field.getName()));
	        p.setValueType(SpringReflext.get(bean, field.getName()).getClass());
	      }
	      catch (Exception e)
	      {
	        e.printStackTrace();
	      }
	      ps.add(p);
	    }
	    return JSONObject.toJSONString(ps, true);
	  }
	  
	  
	   
}
