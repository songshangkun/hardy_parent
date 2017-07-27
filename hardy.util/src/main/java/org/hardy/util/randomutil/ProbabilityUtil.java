package org.hardy.util.randomutil;

import java.util.HashMap;
import java.util.Map;
public class ProbabilityUtil {
    
	 private  int alias = 0; 
	 private Map<Object,Integer> aliasTable = new HashMap<Object,Integer>();
	 private Map<Integer,Long> map = new HashMap<Integer,Long>();
	 
	 private Integer[] pool;
	 
	 public ProbabilityUtil addObject(Object obj,double precent){
		   this.aliasTable.put(obj, alias++);
		   this.map.put(aliasTable.get(obj), Math.round(precent*100));
		   return this;
	 }
	 
	 /**
	  * 均匀打乱顺序
	  * @return
	  */
	 public ProbabilityUtil uniformity(){
		 int count = 0;
		 int point = 0;
		 for(int key:map.keySet()){
			  count +=map.get(key);
		 }
		 pool = new Integer[count];
		 for(int key:map.keySet()){
			  for(int i=point;i<point+map.get(key);i++){
				    pool[i] = key;
			  }
			  point +=map.get(key);
		 }
		 RandomUtil.orderRandomOArray(pool);
		 return this;
	 } 
	 
	 public Object randomSelect(){
		  int randIndex = RandomUtil.getRandomInstance().nextInt(pool.length);
		  int name = pool[randIndex];
		  for(Object obj:aliasTable.keySet()){
			   if(aliasTable.get(obj).equals(name)) return obj;
		  }
		  return null;
	 }
		 public static void main(String[] args) {
			 ProbabilityUtil pu = new ProbabilityUtil();
			 pu.addObject("A", 0.1).addObject("B", 0.1).addObject("C", 0.8).uniformity();
//			 System.out.println(Arrays.toString(pu.aliasTable.keySet().toArray()));;
//			 System.out.println(Arrays.toString(pu.aliasTable.values().toArray()));;
//			 System.out.println(Arrays.toString(pu.pool));;
			 for(int i=0;i<100;i++){
				 System.out.println(pu.randomSelect());
			 }
		}
	 
}
