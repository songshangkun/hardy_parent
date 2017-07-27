package org.hardy.util.extention;


import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 自定义简单缓存
 * @author song
 *
 */
public class CacheMap  {
	
	private Map<Object, Data>  cacheCore = new HashMap<>();
	
	private DataResource dataResource;
	
	private int capacite;
	private int delay;
	private int period;
	private int removeCount;
	private int removeLevel;
	private boolean isRun = false;
	
	/**
	 * 构造函数
	 * @param capacite   缓存最大存储
	 * @param removeLevel  移除数据的访问次数临界值
	 * @param delay  自动移除数据 延迟启动时间 秒
	 * @param period  自动移除数据 循环周期
	 * @param precent  移除缓存的比例
	 * @param isAutoCheck 是否自动执行移除缓存
	 */
	public CacheMap(int capacite,int removeLevel,int delay,int period,float precent,boolean isAutoCheck){
		     this.capacite = capacite;
		     this.delay = delay;
		     this.period = period;
		     this.removeLevel = removeLevel;
		     this.removeCount = (int)Math.floor(capacite*precent);
		     if(isAutoCheck) autoCheck();
	}
	/**
	 * 构造函数
	 * @param capacite 缓存最大存储
	 * @param removeLevel 移除数据的访问次数临界值
	 * @param precent 移除缓存的比例
	 */
	public CacheMap(int capacite,int removeLevel,float precent){
		this(capacite, removeLevel, 0, 0, precent, false);
	}
	 /**
	  * 获取key关联的缓存
	  * @param key
	  * @return
	  */
	public Object get(Object key) {
          if(cacheCore.containsKey(key)){
        	        Data data = cacheCore.get(key);
                 data.count++; 
        	  		return   data.orginal;        
        	  	}else  {
	        	   Object result = dataResource.find(key);  
	        	   if(result!=null&&cacheCore.size()<this.capacite){
	        		   Data data = new Data(result,0);
	        		   cacheCore.put(key, data);
	        	   }else{
	        		    check();
	        	   }
	        	   return result;
          }
	}
	/**
	 * 检查缓存是否移除缓存数据
	 */
	 public  void  check(){
		 isRun = true;
	     final Object[] indexs = new Object[removeCount];
	     int skip = 0;
		 for(Object key:cacheCore.keySet()){
			  if(skip<indexs.length&&cacheCore.get(key).count<=removeLevel){
					  indexs[skip] = key;
				      skip++;
			  }else if(skip>=indexs.length){
				   break;
			  }
		 }     
		 for(Object obj:indexs){
			   if(obj!=null)cacheCore.remove(obj);
		 }	 
		 isRun = false;
   }
   private  void  autoCheck(){
	     Timer timer = new Timer("check"); 
	     timer.schedule(new TimerTask() { 
			@Override
			public void run() { 
				if(!isRun)check(); 
			}
		}, delay*1000l, period*1000l);
 
   }
   /**
    * 移除缓存数据
    * @param key
    */
   public void remove(Object key) {
		 cacheCore.remove(key);
   }
   /**
    * 清空缓存
    */
   public void clear(){
	   cacheCore.clear();
   }
   /**
    * 该接口指明在缓存无数据时应怎样查找该数据
    * @author song
    *
    */
   public static interface DataResource{
	   /**
	    * 查找数据的方法
	    * @param conditionKey
	    * @return
	    */
	      public Object find(Object conditionKey);
   }

public DataResource getDataResource() {
	return dataResource;
}

public void setDataResource(DataResource dataResource) {
	this.dataResource = dataResource;
}

}



class Data{
	
	   public Data(){};
	   public Data(Object orginal, int count) {
		super();
		this.orginal = orginal;
		this.count = count;
	}
	public Object orginal;
	   public int count = 0;
}

