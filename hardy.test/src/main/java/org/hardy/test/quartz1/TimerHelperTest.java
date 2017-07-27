package org.hardy.test.quartz1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hardy.timerhelper.JobDetail;
import org.hardy.timerhelper.SchedulerUtil;

public class TimerHelperTest {
	
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
    	List<Integer> list = new ArrayList<>();
        list.add(11);list.add(21);list.add(31);list.add(41);list.add(51);list.add(61);
        list.add(14);list.add(15);list.add(16);list.add(19);list.add(21);list.add(18);
   int n = 0;
   for(Integer i:list){
	    n+=i;
   }
   System.out.println(n);
    	SchedulerUtil.execute(list, new Date(), 2000l,new JobDetail() {
			@SuppressWarnings("unchecked")
			@Override
			public Object execute(Object params) {
				 List<Integer> list2 = (List<Integer>) params;
				 if(list2.size()>1) {
					 int r1 = list2.remove(0)+list2.remove(0);
					 list2.add(r1);
					 System.out.println("jisuan==="+r1);
				 }
				 else {
					 System.out.println("jieguo="+list2.remove(0));
				 }
				return list2;
			}

			@Override
			public boolean isContinu(Object params) {
				 List<Integer> list2 = (List<Integer>) params;
				return !list2.isEmpty();
			}
		});
    
    }


	 
	
	
	  
}