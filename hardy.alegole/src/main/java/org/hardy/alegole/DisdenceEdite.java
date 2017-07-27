package org.hardy.alegole;

 
/**
 * 提供两个字符串的差距
 * @author songshangkun
 *
 */
public class DisdenceEdite {
    //有问题的程序
	public static int getLevenshteinDistance(String strA, String strB) {
 
	 int lenA = (int)strA.length();
	 int lenB = (int)strB.length();
	 int[][] c = new int[lenA+1][lenB+1]; 
	        // Record the distance of all begin points of each string
	        //初始化方式与背包问题有点不同
	 for(int i = 0; i < lenA; i++) c[i][lenB] = lenA - i;
	 for(int j = 0; j < lenB; j++) c[lenA][j] = lenB - j;
	 c[lenA][lenB] = 0;
		for(int i = lenA-1; i >= 0; i--)
		for(int j = lenB-1; j >= 0; j--)
		 {
			char[] cB = strB.toCharArray();
			char[] cA = strA.toCharArray();
		if(cB[j] == cA[i])
		c[i][j] = c[i+1][j+1];
	       else{
	    	   int temp = Math.min(c[i][j+1], c[i+1][j]);
	   		  c[i][j] = Math.min(temp, c[i+1][j+1])+1;
	       }
	    
	}
	 
	 return c[0][0];
	}
	
	
	
}
	
	 
 
