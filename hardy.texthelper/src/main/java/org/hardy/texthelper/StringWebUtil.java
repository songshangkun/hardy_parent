package org.hardy.texthelper;

/***
 * 这个类是为web方面操作String的一个工具类
 * **/
public class StringWebUtil {
	/***
     * 这个方法将填写的ip地址补充完整
     * exemple:192.168.1.05 操作结果为 192.168.001.005
     *
     * @param ip
     * @return  **/
	public static String getFullIp(String ip) {
		StringBuilder buf = new StringBuilder();
		if (ip != null) {
			String str[] = ip.split("\\.");
			for (int i = 0; i < str.length; i++) {
				if(i>0){
					buf.append(".");
				}
				buf.append(addZero(str[i], 3));
			}
		}
		return buf.toString() ;
	}
	
	public static String addZero(String str, int len) {
		StringBuffer s = new StringBuffer();
		s.append(str);
		while (s.length() < len) {
			s.insert(0, "0");
		}
		return s.toString();
	}
}
