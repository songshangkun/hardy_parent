package org.hardy.jsonhelper;

import org.hardy.jsonhelper.coreface.OutInterface;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 使用Alibaba的JSONObject实现的OutInterface接口
 * @author songs
 *
 */
public class AlibabaJson implements OutInterface{

	@Override
	public String toJSONObjectString(Object obj) {
		 return JSONObject.toJSONString(obj,true);
	}

	@Override
	public String toJSONArrayString(Object obj) {
		 return JSONArray.toJSONString(obj,true);
	}

}
