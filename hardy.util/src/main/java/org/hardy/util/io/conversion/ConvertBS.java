package org.hardy.util.io.conversion;

/**
 * 专门转换byte数组与string字符串和数字的工具类
 * @author song
 *
 */
public class ConvertBS {
    /**
     * 将16进制字符串转化成byte数组
     * @param hexString
     * @return
     */
    public static byte[] hexStringToBytes(String hexString) {   
        if (hexString == null || hexString.equals("")) {   
            return null;   
        }   
        hexString = hexString.toUpperCase();   
        int length = hexString.length() / 2;   
        char[] hexChars = hexString.toCharArray();   
        byte[] d = new byte[length];   
        for (int i = 0; i < length; i++) {   
            int pos = i * 2;   
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));   
        }   
        return d;   
    }
    /**
     * 根据字典获取字符的16进制表示
     * @param c
     * @return
     */
    private static byte charToByte(char c) {   
        return (byte) "0123456789ABCDEF".indexOf(c);   
    }  
    /**
     * 将byte数组转化为16进制字符串
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] bs){   
        StringBuilder stringBuilder = new StringBuilder("");   
        if (bs == null || bs.length <= 0) {   
            return null;   
        }   
        for (int i = 0; i < bs.length; i++) {   
            int v = bs[i] & 0xFF;   
            String hv = Integer.toHexString(v);   
            if (hv.length() < 2) {   
                stringBuilder.append(0);   
            }   
            stringBuilder.append(hv);   
        }   
        return stringBuilder.toString();   
    }   
    
    
    /**
     * 将byte数组转化为10进制字符串
     * @param bs
     * @return
     */
	public static int bytesToDixString(byte[] bs) {   
		  String ret = "";   
		  for (int i = 0; i < bs.length; i++) {   
		   String hex = Integer.toHexString(bs[ i ] & 0xFF);   
		   if (hex.length() == 1) {   
		    hex = '0' + hex;   
		   }   
		   ret += hex.toUpperCase();   
		  }   	  
		  return Integer.parseInt(ret, 16);   
		}  
	
	public static void main(String[] args) {
		System.out.println(bytesToDixString(new byte[]{1,2,3,4}));
	}
}
