package org.hardy.alegole;


import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.hardy.alegole.LineUtil.Point;

/**
 * 提供经纬度转换功能,但是有一定误差 DDD和DMS转换误差一般在1/1000或秒的级别
 * @author songshangkun
 *
 */
public class GeotudeMetre {
	 /**
	  * 地球平均半径 默认千米
	  */
	 public static double R = 6371.004d;
	 /**
	  * 圆周率
	  */
	 public static final double PI = Math.PI;
	
    /**
     * 将数字形式的DDD E108.90593 转化成度数显示 DMS 108°54'21"E
     * @param ddd
     * @return
     */
	public static DMS transDDD(String ddd){
		if(!ddd.matches("([eswnESWN]?\\d{0,3})|([eswnESWN]?\\d{0,3}\\.\\d{1,10})")) 
			throw new RuntimeException("数据格式不正确");
		DMS dms = new DMS();
		if(ddd.charAt(0)>'9'||ddd.charAt(0)<'0'){
			dms.direct = ddd.charAt(0);
			ddd = ddd.substring(1,ddd.length());
		}
		double dnum = Double.parseDouble(ddd);  
		dms.d = (int) Math.floor(dnum);
		double mnum = (dnum-dms.d)*60.0;
		dms.m = (int)mnum;
		dms.s = (int)(Math.round((mnum-(int)mnum)*60.0));
		return dms;
	} 
	
	
	/**
     * 将 度数显示 DMS 108°54'21"E 转化成 数字形式的DDD E108.90593
     * @param ddd
     * @param direct   为false时不带方向字符
     * @return
     */
	public static String transDMS(DMS dms,boolean direct){
		 double num = (double)dms.d + (dms.m/60.0) + (dms.s/100.0/60.0);
		 if(direct&&dms.direct!=null&&!dms.direct.equals(' '))return dms.direct+String.valueOf(num);
		 else return String.valueOf(num);
	}
	/**
	 * 输出不带方向字符的转换数据
	 * 将 度数显示 DMS 108°54'21"E 转化成 数字形式的DDD 108.90593
	 * @param dms
	 * @return
	 */
	public static String transDMS(DMS dms){
		 return transDMS(dms, false);
	}
	
	/**
	 * 将 度数显示 DMS 108°54'21"E 转化成 数字形式的DDD E108.90593
	 * @param dms
	 * @return
	 */
	public static String transDMS(String dms){
		DMS dmsObj = new DMS();
		if(!dms.matches("\\d{0,3}\\°\\d{1,2}\\'\\d{1,2}\\\"[eswnESWN]?")) 
			throw new RuntimeException();
		if(dms.charAt(dms.length()-1)>'9'||dms.charAt(dms.length()-1)<'0'){
			dmsObj.direct = dms.charAt(0);
			dms = dms.substring(1,dms.length());
		}
		StringTokenizer st = new StringTokenizer(dms, "°'\"");
		dmsObj.d = Integer.parseInt(st.nextToken());
		dmsObj.m = Integer.parseInt(st.nextToken());
		dmsObj.s = Integer.parseInt(st.nextToken());
		return transDMS(dmsObj);
	} 
	
	/**
	 * 将两个DMS经纬度转换成千米
	 * @param LonA  地点A的精度
	 * @param LatA  地点A的纬度
	 * @param LonB  地点B的精度
	 * @param LatB  地点B的纬度
	 * @return
	 */
	public static double transMetres(DMS LonA,DMS LatA,DMS LonB,DMS LatB){
		return transMetres(transDMS(LonA), transDMS(LatA), transDMS(LonB), transDMS(LatB)); 
	}
	/**
	 * 将两个DDD经纬度转换成米
	 * @param LonA  地点A的精度
	 * @param LatA  地点A的纬度
	 * @param LonB  地点B的精度
	 * @param LatB  地点B的纬度
	 * @return
	 */
	public static double transMetres(String LonA,String LatA,String LonB,String LatB){
		   double MLatA = transLatitude(LatA);
		   double MLatB = transLatitude(LatB);
		   double MLonA = transLongitude(LonA);
		   double MLonB = transLongitude(LonB);
		   double C = Math.sin(MLatA)*Math.sin(MLatB)*Math.cos(MLonA-MLonB)+Math.cos(MLatA)*Math.cos(MLatB);
		   double distance = R*Math.acos(C)*PI/180.0;
		   return distance;
	}
	
	/**
	 * 内部处理 将DDD形式转化为Double类型 东经是+,西经是-
	 * @param longitude
	 * @return
	 */
	private static double transLongitude(String longitude){
		 if(longitude.startsWith("w")||longitude.startsWith("W"))
		  return Double.parseDouble("-"+longitude.substring(1,longitude.length()-1));
		 else if(longitude.startsWith("e")||longitude.startsWith("E")) 
			 return Double.parseDouble(longitude.substring(1,longitude.length()-1));
		 else return Double.parseDouble(longitude);
	}
	/**
	 * 内部处理 将DDD形式转化为Double类型   南纬是-,北纬是+
	 * @param latitude
	 * @return
	 */
	private static double transLatitude(String latitude){
		 if(latitude.startsWith("n")||latitude.startsWith("N"))
			  return 90-Double.parseDouble(latitude.substring(1,latitude.length()-1));
			 else if(latitude.startsWith("s")||latitude.startsWith("S")) 
				 return 90+Double.parseDouble(latitude.substring(1,latitude.length()-1));
			 else return 90-Double.parseDouble(latitude);
	}
	
	/**
	 * 精度系数,和netGridLL相关
	 */
	public static long Accuracy_Coefficient = 1000000;
	
	/**
	 * 根据经纬度将一块区域平均分配区域,
	 * 返回排列由左到右排列,下标从0开始
	 * @param rect
	 * @param cols
	 * @param row
	 * @return
	 */
	public static GeoRectDDD[] netGridLL(GeoPointDDD left_up,GeoPointDDD right_down,int cols,int rows){
		GeoPointDDD start = left_up.fois(Accuracy_Coefficient);
		GeoPointDDD end =  right_down.fois(Accuracy_Coefficient);
		GeoRectDDD[] rests = new GeoRectDDD[cols*rows]; //确定输出格子数
		Point[] initPoint = new Point[(cols+1)*(rows+1)]; //确定周长上的确定坐标数组 
		double x_v = (end.longetitude - start.longetitude)/(double)cols;
		double y_v = (end.latitude - start.latitude)/(double)rows;
		for(int i=0;i<initPoint.length;i++){
			initPoint[i] = new Point((int)((i%(cols+1))*x_v+start.longetitude), (int)((i/(cols+1))*y_v+start.latitude));
		}
		for(int i=0;i<rests.length;i++){
			GeoRectDDD gd = new GeoRectDDD();
			 gd.WN_1.copyPoint(initPoint[i/cols+i]);
			 gd.WN_1 = gd.WN_1.fois(1.0/Accuracy_Coefficient);
			 gd.EN_2.copyPoint(initPoint[i/cols+i+1]);
			 gd.EN_2 = gd.EN_2.fois(1.0/Accuracy_Coefficient);
			 gd.WS_4.copyPoint(initPoint[i/cols+i+cols+1]);
			 gd.WS_4 = gd.WS_4.fois(1.0/Accuracy_Coefficient);
			 gd.ES_3.copyPoint(initPoint[i/cols+i+cols+2]);
			 gd.ES_3 = gd.ES_3.fois(1.0/Accuracy_Coefficient);
			rests[i] = gd;
		}
		return rests;
	}
	
	/**
	 * 代表无方向的数字型区域
	 * @author songshangkun
	 *
	 */
	public static class GeoRectDDD{
		/**
		 * 西北
		 */
		public GeoPointDDD WN_1 = new GeoPointDDD();
		/**
		 * 西南
		 */
		public GeoPointDDD WS_4 = new GeoPointDDD();;
		/**
		 * 东北
		 */
		public GeoPointDDD EN_2 = new GeoPointDDD();;
		/**
		 * 东南
		 */
		public GeoPointDDD ES_3 = new GeoPointDDD();;
		/**
		 * 将坐标点以list形式输出
		 * @return
		 */
		public List<GeoPointDDD> list(){
			 List<GeoPointDDD> ls = new ArrayList<>();
			 ls.add(WN_1);
			 ls.add(EN_2);
			 ls.add(ES_3);
			 ls.add(WS_4);
			 return ls;
		}
		@Override
		public String toString() {
			return "GeoRectDDD [WN_1=" + WN_1 + ", WS_4=" + WS_4 + ", EN_2=" + EN_2 + ", ES_3=" + ES_3 + "]";
		}
		
		
	}
	
	
	/**
	 * 一块确定了四个角经纬度的区域
	 * @author songshangkun
	 *
	 */
	public static class GeoRect{
		 
		/**
		 * 西北
		 */
		public GeoPoint WN_1 = new GeoPoint();
		/**
		 * 西南
		 */
		public GeoPoint WS_4 = new GeoPoint();
		/**
		 * 东北
		 */
		public GeoPoint EN_2 = new GeoPoint();
		/**
		 * 东南
		 */
		public GeoPoint ES_3 = new GeoPoint();
		/**
		 * 将坐标点以list形式输出
		 * @return
		 */
		public List<GeoPoint> list(){
			 List<GeoPoint> ls = new ArrayList<>();
			 ls.add(WN_1);
			 ls.add(EN_2);
			 ls.add(ES_3);
			 ls.add(WS_4);
			 return ls;
		}
	}
	/**
	 * 代表无方向的数字型经纬度的点
	 * @author songshangkun
	 *
	 */
	public static class GeoPointDDD{	
		public GeoPointDDD() {}
		public GeoPointDDD(double longetitude, double latitude) {
			super();
			this.longetitude = longetitude;
			this.latitude = latitude;
		}
		/**
		 * 经度
		 */
		public double longetitude;
		/**
		 * 纬度
		 */
		public double latitude;
		/**
		 * 乘以某个倍数
		 * @param fois
		 * @return
		 */
		public GeoPointDDD fois(double fois){
			return new GeoPointDDD(longetitude*fois,latitude*fois);
		}
		
		public void copyPoint(Point point){
			 this.longetitude = point.x;
			 this.latitude = point.y;
		}
		@Override
		public String toString() {
			return "GeoPointDDD [longetitude=" + longetitude + ", latitude=" + latitude + "]";
		}
		
	}
 
	/**
	 * 代表一个经纬度的点
	 * @author songshangkun
	 *
	 */
	public static class GeoPoint{	
		public GeoPoint() {}
		public GeoPoint(DMS longetitude, DMS latitude) {
			super();
			this.longetitude = longetitude;
			this.latitude = latitude;
		}
		/**
		 * 经度
		 */
		public DMS longetitude;
		/**
		 * 纬度
		 */
		public DMS latitude;
	}
	
    /**
     * 经纬度的度分秒
     * @author songshangkun
     *
     */
	
	public static class DMS{
		/**
		 * 方向
		 */
		public Character direct =' ';
		/**
		 * 度数
		 */
		public int d;
		/**
		 * 分
		 */
		public int m;
		/**
		 * 秒
		 */
		public int s;
		@Override
		public String toString() {
			return d+"°"+m+"'"+s+"\""+direct;
		}

	}
	
	 

}
