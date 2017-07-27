package org.hardy.jsonhelper.menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hardy.jsonhelper.coreface.OutInterface;


/**
 * 将com.song.tool.constant.ace.MenuO
 * 转化为可供ace或其他前台框架使用的json返回值
 * @author 宋尚堃
 *
 */
public class JsonExportation {
	
	public static OutInterface oif;
    
	@SuppressWarnings("unchecked")
	public static String ToJsonAce(List<Menu> list,String rootString,String prefix){
		        //菜单暂存地址
				List<Map<String,Object>> contans = new ArrayList<>();
				//菜单封装(对child需要排序)
				Map<String,Object> map = new LinkedHashMap<>();
				//临时记录需要移除的map的容器
				List<Map<String,Object>> removeMaps = new ArrayList<>();
		
		        //将菜单封装后保存到contans中
				for(Menu a:list){
					if(a!=null){
						map = new HashMap<>();
						map.put("pcode", a.getParentCode());
						map.put("code", a.getMenuCode());
						map.put("stair", a.getMenuName());
						map.put("isActive", "false");
						map.put("isSecond", "false");
						map.put("isToggle", "false");
						map.put("icons", a.getMenuClass());
						if(rootString.equals(a.getMenuUrl()))map.put("path", a.getMenuUrl());
						else map.put("path", prefix+a.getMenuUrl());
						map.put("squence", a.getSequence());
						map.put("child", new ArrayList<Map<String,Object>>());
					    contans.add(map);
					}
				}
				//对contans中的map封装进行排序
				Collections.sort(contans,new compa());
				//便利循环contan依据pcode进行关联
				for(int i=0;i<contans.size();i++){
					for(int j=0;j<contans.size();j++){
						String pcode = contans.get(i).get("pcode").toString().trim();
						String code = contans.get(j).get("code").toString().trim();
						//如果i！=j，且i坐标的父code==j坐标的code说明j是i的父菜单
						if(i!=j&&pcode.equals(code)){
							List<Map<String,Object>> childs = 
							(List<Map<String,Object>>)contans.get(j).get("child"); 
							childs.add(contans.get(i));
							removeMaps.add(contans.get(i));
							break;
						}
					}
				}
				//从contans中移除被福彩但索引过的子菜单
				contans.removeAll(removeMaps);
				//返回json字符串
				return oif.toJSONArrayString(contans);
	}

 private static class compa implements Comparator<Map<String,Object>>{

	@Override
	public int compare(Map<String,Object> o1, Map<String,Object> o2) {
		 
		return (int)o1.get("squence")-(int)o2.get("squence");
	}
	
  }

		public static OutInterface getOif() {
			return oif;
		}
		
		public static void setOif(OutInterface oif) {
			JsonExportation.oif = oif;
		}
 
 
}
