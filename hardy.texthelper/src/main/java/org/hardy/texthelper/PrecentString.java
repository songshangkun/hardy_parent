package org.hardy.texthelper;

import java.text.DecimalFormat;

/**
 * 将double型字符串转换为百分比数据字符串，
 * 该工具类包含转换float和double的方法
 * @author songshangkun
 *
 */
public class PrecentString {
    
	public static String getStringPrecent(double d,Integer digits,Integer restPoint,String decorate){
		double nd = d*Math.pow(10, digits);
		StringBuilder sb = new StringBuilder("#.");
		for(int i=0;i<restPoint;i++){
			 sb.append(0);
		}
		 DecimalFormat df = new DecimalFormat(sb.toString());
		  String result  = df.format(nd);
		  if(decorate!=null) result = df.format(nd)+decorate;
		  return result;
	}
	 
}
