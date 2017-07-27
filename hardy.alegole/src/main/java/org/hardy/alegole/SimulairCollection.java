package org.hardy.alegole;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.song.tool.algoritem.entity.EntitySimulair;


/**
 * 对比输入集合和结果集合的相似度的简单工具类算法
 * @author 宋尚堃
 *
 */
public class SimulairCollection {
         
	    private Map<EntitySimulair,Integer> map = new HashMap<EntitySimulair,Integer>();
	    
	    private double coissant = 0; private double ensemble = 0;
	    /**
	     * 比较EntitySimulair对相集合与result对相集合的相似度,
	     * 注意对象集合间的对象必须完全一致，否则权重将被新加入的
	     * 的EntitySimulair对象的权重覆盖。而得不到正确的值
	     * @param objs
	     * @param result
	     * @return
	     */
	    public double calculerSimulair(EntitySimulair[] ess,EntitySimulair[] result){ 
			for(EntitySimulair es:result){
				 map.put(es, 1);
			}
			for(EntitySimulair es:ess){
				 if(!map.containsKey(es)){
					  map.put(es, 1);
				 }else{
					 map.put(es,2);
				 }
			}
			for(EntitySimulair es:map.keySet()){
		           if(map.get(es)==2){
		        	   coissant += es.getPoid();
		           }
		           ensemble += es.getPoid();
			}
	    	return (double)coissant/(double)ensemble;
	    }
	    
	    /**
	     * 比较EntitySimulair对相集合与result对相集合的相似度,
	     * 不要求传输的对象与结果及对象的权值一致，但是必须将结果集
	     * 的全集输入进方法，以便查找相应的权值。还要注意，这种情况要求结果集
	     * 不能够含有key值相等但权值不同的情况。因为如果权值不同，最终的全集无法确定key到底要包含哪个权值
	     * @param objs
	     * @param result
	     * @param all
	     * @return
	     */
	    public double calculerSimulair(EntitySimulair[] ess,List<EntitySimulair> result,List<EntitySimulair> all){
			for(EntitySimulair es:result){
				 map.put(es, 1);
			}
			for(EntitySimulair es:ess){
				 if(!map.containsKey(es)){
					  map.put(es, 1);
				 }else{ 
					 map.put(es,2);
				 }
			}
			for(EntitySimulair es:map.keySet()){
				  int index = all.indexOf(es);
		           if(map.get(es)==2){
		        	   coissant += all.get(index).getPoid();
		           }
		           ensemble += all.get(index).getPoid();
			}
	    	return (double)coissant/(double)ensemble;
	    }
	    
	    /**
	     * 片面的相似度搜索，因为他只计算结果集合和选择集合都含有的元素，而忽略了那些
	     * 结果级中没有，而在选择集中包含的元素。对于每种不同的结果集，计算的范围也是不同的。
	     * 所以会出现选项集与不同结果集出现相同相似度的情况
	     * @param ess
	     * @param result
	     * @return
	     */
	    public double calculerSimulair(EntitySimulair[] ess,List<EntitySimulair> result){
			for(EntitySimulair es:result){
				 map.put(es, 1);
			}
			for(EntitySimulair es:ess){
				 if(!map.containsKey(es)){
					  map.put(es, 1);
				 }else{ 
					 map.put(es,2);
				 }
			}
			for(EntitySimulair es:map.keySet()){
				  int index = result.indexOf(es);
		           if(map.get(es)==2)coissant += result.get(index).getPoid();
		           if(-1!=index)ensemble += result.get(index).getPoid();
			}
	    	return (double)coissant/(double)ensemble;
	    }
	    
	    public double calculerSimulair(EntitySimulair[] ess,List<EntitySimulair> result,double default_poid){
			for(EntitySimulair es:result){
				 map.put(es, 1);
			}
			for(EntitySimulair es:ess){
				 if(!map.containsKey(es)){
					  map.put(es, 1);
				 }else{ 
					 map.put(es,2);
				 }
			}
			for(EntitySimulair es:map.keySet()){
				  int index = result.indexOf(es);
		           if(map.get(es)==2)coissant += result.get(index).getPoid();
		           if(-1!=index)ensemble += result.get(index).getPoid();
		           else ensemble += default_poid;
			}
	    	return (double)coissant/(double)ensemble;
	    }
	    
	    /**
	     * 需要放入全集，并且可以设置全集单独的默认权值，在从结果集中
	     * 找不到相应结果对象时，使用设置的默认权值，如果没有设置默认权值。
	     * 那么采用默认值1.
	     * @param ess
	     * @param result
	     * @param all
	     * @return
	     */
	    public double calculerSimulairDefault(EntitySimulair[] ess,List<EntitySimulair> result,List<EntitySimulair> all){
			for(EntitySimulair es:result){
				 map.put(es, 1);
			}
			for(EntitySimulair es:ess){
				 if(!map.containsKey(es)){
					  map.put(es, 1);
				 }else{ 
					 map.put(es,2);
				 }
			}
			for(EntitySimulair es:map.keySet()){
				  int index = result.indexOf(es);
		           if(map.get(es)==2)coissant += result.get(index).getPoid();
		           if(-1!=index){ensemble += result.get(index).getPoid();}
		           else {index = all.indexOf(es);
		                   ensemble += all.get(index).getDefault_poid();} 
			}
			
	    	return (double)coissant/(double)ensemble;
	    }
	    public void clear(){
	        	this.map.clear();
	    }  
}
